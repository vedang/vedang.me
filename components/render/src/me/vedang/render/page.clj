(ns me.vedang.render.page
  "Render an HTML page for every post"
  (:require
   [babashka.fs :as fs]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.util :as util]
   [selmer.parser :as selmer]))

(defn render-file
  [{:keys [metadata body]} opts]
  (let [page-opts (merge opts
                         metadata
                         {:body body
                          :discuss-link (str (:discuss-link opts)
                                             (:md-filename metadata))})
        page-html (-> opts
                      :templates-dir
                      (util/read-template "base.html")
                      (selmer/render page-opts))
        html-file (fs/file (:public-dir page-opts) (:html-filename page-opts))]
    (logger/log (str "Writing HTML to public-dir: " html-file))
    (fs/create-dirs (fs/parent html-file))
    (spit html-file page-html)))
