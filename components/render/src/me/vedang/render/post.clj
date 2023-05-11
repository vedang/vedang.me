(ns me.vedang.render.post
  (:require
   [me.vedang.render.util :as util]
   [selmer.parser :as selmer]))

(defn add-html-body
  [{:keys [metadata html] :as html-map} opts]
  (assoc html-map
         :body (-> opts
                   :templates-dir
                   (util/read-template "post.html")
                   (selmer/render (assoc metadata :body html)))))
