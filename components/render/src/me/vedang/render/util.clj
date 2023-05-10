(ns me.vedang.render.util
  (:require [babashka.fs :as fs]
            [me.vedang.logger.interface :as logger]))

(def read-file
  (memoize
   (fn read-file* [_checksum filename]
     ;; The checksum is to ensure that memoize re-reads the file when
     ;; it changes
     (slurp filename))))

(defn read-template
  "Given the `templates-dir` and the `template`, return the contents of
  the template file at the root of the dir."
  [templates-dir template]
  (logger/log (str "Reading: " templates-dir " and " template))
  (let [filename (fs/file templates-dir template)
        checksum (fs/file-time->millis (fs/last-modified-time filename))]
    (read-file checksum (str filename))))
