(ns me.vedang.content.core
  (:require [babashka.fs :as fs]))

(def create-assets
  "Return the assets directory that has been requested. Create it if it
  does not exist."
  (memoize (fn [parent-dir assets-dir-name]
             (fs/create-dirs (fs/file parent-dir assets-dir-name)))))

(defn copy-assets
  [opts]
  (let [public (create-assets (:public-dir opts) (:public-assets-dir-name opts))
        local (create-assets (:content-dir opts) (:local-assets-dir-name opts))]
    (println (str "Copying assets from: " local " to: " public))
    (fs/copy-tree local public {:replace-existing true})))
