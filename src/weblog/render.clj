(ns weblog.render
  (:require [babashka.fs :as fs]
            [clojure.data.xml :as xml]))

(xml/alias-uri 'atom "http://www.w3.org/2005/Atom")

(def ^:dynamic *print-debug*
  "Helper variable to print debug messages when developing"
  false)

(def base-html
  "Return the base-html template from the templates-dir.

  @TODO: The name of the template is hard-coded at the moment, fix this."
  (memoize (fn [templates-dir]
             (slurp (fs/file templates-dir "base.html")))))

(def create-assets
  "Return the assets directory that has been requested. Create it if it
  does not exist."
  (memoize (fn [parent-dir assets-dir-name]
             (fs/create-dirs (fs/file parent-dir assets-dir-name)))))

(defn debug-msg
  "Print `msg`, if `*print-debug* is true"
  [msg]
  (when *print-debug* (println msg)))

;;;; Sync images and CSS
(defn copy-assets
  "Copy assets into the public dir"
  [opts]
  (let [public (create-assets (:public-dir opts) (:public-assets-dir-name opts))
        local (create-assets (:content-dir opts) (:local-assets-dir-name opts))]
    (println (str "Copying assets from: " local " to: " public))
    (fs/copy-tree local public {:replace-existing true})))
