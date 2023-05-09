(ns me.vedang.render.page
  "Render an HTML page for every post"
  (:require
   [babashka.fs :as fs]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.util :as util]
   [selmer.parser :as selmer]))

(defn render-file
  [{:keys [metadata html]} opts]
  (let [post-body (-> opts
                      :templates-dir
                      (util/read-template "post.html")
                      (selmer/render (assoc metadata :body html)))
        page-opts (merge opts
                         metadata
                         {:body post-body
                          :discuss-link (str (:discuss-link opts)
                                             (:md-filename metadata))})
        page-html (-> opts
                      :templates-dir
                      (util/read-template "base.html")
                      (selmer/render page-opts))
        html-file (fs/file (:public-dir opts) (:html-filename metadata))]
    (logger/log (str "Writing HTML to public-dir: " html-file))
    (fs/create-dirs (fs/parent html-file))
    (spit html-file page-html)))
