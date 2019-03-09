(ns repro.plugin-frontend.channel
  (:require
    [chromex.logging :refer [log]]
    [chromex.ext.runtime :as runtime]
    [chromex.protocols.chrome-port :refer [post-message! get-sender]]))

(defn send-to-background! [background-port message]
  (post-message! background-port message)
  (log "sent message:" message "to:" background-port))

