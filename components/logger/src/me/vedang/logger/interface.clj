(ns me.vedang.logger.interface
  (:require [me.vedang.logger.core :as core]))

(defn activate-logging!
  "Turn on logging! Messages will now be printed!"
  []
  (core/activate-logging!))

(defn log
  "Log `msg`. Only works when logging has been activated,
using`activate-logging!`."
  [msg]
  (core/log msg))
