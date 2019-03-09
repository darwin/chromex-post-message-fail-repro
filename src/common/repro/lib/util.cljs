(ns repro.lib.util
  (:require
    [cljs.core.async :as async :refer [chan]]))

(defn sleep-async [milli-secs]
  (let [c (chan)]
    (js/setTimeout (fn [] (async/close! c)) milli-secs)
    c))
