(ns repro.plugin-background.main
  (:require
    [chromex.chrome-event-channel :refer [make-chrome-event-channel]]
    [chromex.ext.runtime :as runtime]
    [chromex.ext.tabs :as tabs]
    [chromex.protocols.chrome-port :refer [post-message!]]
    [chromex.logging :refer [log]]
    [chromex.support :refer [runonce]]
    [cljs.core.async :refer [<! go-loop chan]]
    ;;;
    [repro.lib.util :as u]))

(defn tap-all-events! []
  (let [channel (make-chrome-event-channel (chan))]
    (runtime/tap-all-events channel)
    (tabs/tap-all-events channel)
    channel))


(defn handle-client-connect [client-port]
  (post-message! client-port "before go loop")

  #_(go-loop
      []
      (<! (u/sleep-async 6000))
      (post-message! client-port "loop message") ; works
      (recur))

  (go-loop
    []
    (log "inside go-loop")
    (when-some [msg (<! client-port)]
      (log "client says:" msg)
      (post-message! client-port "received message-from-client") ; doesn't work
      (log "sent message to client")
      (recur))))


(defn dispatch-events [event-chan]
  (go-loop
    []
    (when-some [event (<! event-chan)]
      (let [[event-id event-args] event]
        (case event-id
          ::runtime/on-connect (apply handle-client-connect event-args)
          (log "unhandled event:" event-id))
        (recur)))))


(defn init! []
  (log "Background: init!")
  (dispatch-events (tap-all-events!)))


(runonce (init!))

(log "evaled background")

