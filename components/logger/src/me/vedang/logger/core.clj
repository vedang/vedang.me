(ns me.vedang.logger.core)

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  :info)

(defn log
  ([msg]
   (log :debug msg))
  ([log-level msg]
   (when (= *print-debug* log-level) (println msg))))

(let [log-levels [:trace :debug :info :warn :error :fatal]]
  (defn set-log-level!
    [log-level]
    (assert ((set log-levels) log-level))
    (alter-var-root #'*print-debug* (constantly log-level))
    (log "Logging is now activated!")))
