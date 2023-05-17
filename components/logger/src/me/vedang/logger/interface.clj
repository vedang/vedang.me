(ns me.vedang.logger.interface
  (:require [me.vedang.logger.core :as core]))

(defn set-log-level!
  "Turn on logging! Messages will now be printed!"
  [log-level]
  (core/set-log-level! log-level))

(defn log
  "Log `msg`. Default logging-level used is :debug, which is below :info
  and therefore does not print anything. Change logging level
  using`set-log-level!`."
  ([msg]
   (core/log msg))
  ([log-level msg]
   (core/log log-level msg)))
