(ns repro.popup.main
  (:require
    [cljs.core.async :refer [<! go-loop]]
    [chromex.ext.runtime :as runtime]
    [chromex.logging :refer [log]]
    [chromex.protocols.chrome-port :refer [post-message!]]
    [chromex.support :as util]
    ;;;
    [repro.plugin-frontend.channel :refer [send-to-background!]]
    [repro.user.auth.ui :as auth.ui]))

(defn connect-to-background! []
  (let [background-port (runtime/connect)]
    (go-loop
      []
      (log "inside go-loop")
      (when-some [msg (<! background-port)]
        (log "background says:" msg)
        (recur))
      (log "leaving go-loop"))))


(defn mount-root! [root-id]
  (let [root-node (.getElementById js/document root-id)]
    (log "Mounting to: " root-node)

    (.replaceWith root-node (auth.ui/login-form {:login! (fn [{:keys [email password]}]
                                                           (send-to-background! "user submitted login form"))}))))


(defn init! []
  (log "Popup: init!")
  (connect-to-background!)
  (mount-root! "app-root"))


(util/runonce (init!))

(log "evaled popup")

