(ns me.vedang.logger.core)

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  false)

(defn log
  [msg]
  (when *print-debug* (println msg)))

(defn activate-logging!
  []
  (alter-var-root #'*print-debug* (constantly true))
  (log "Logging is now activated!"))
