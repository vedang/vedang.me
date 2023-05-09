(ns me.vedang.logger.core)

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  false)

(defn activate-logging!
  []
  (alter-var-root #'*print-debug* (constantly true)))

(defn log
  [msg]
  (when *print-debug* (println msg)))
