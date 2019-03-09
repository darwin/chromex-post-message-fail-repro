(ns repro.user.auth.ui
  (:require
    [hoplon.core :as h :refer [defelem]]
    [hoplon.jquery])) ; Included for attribute provider multimethods.

(defelem login-form [{:keys [login!]}]
  (h/form :submit #(do (.preventDefault %)
                       (login! {:email    "fake@email.com"
                                :password "super-secure-password"}))

          (h/input :type "submit")))

