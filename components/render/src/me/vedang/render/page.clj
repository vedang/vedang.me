(ns me.vedang.render.page
  "Render an HTML page for every post"
  (:require
   [babashka.fs :as fs]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.process :as process]
   [me.vedang.render.util :as util]
   [selmer.parser :as selmer]))

(defn render-file
  [{:keys [metadata body] :as html-map} opts]
  (let [page-opts (cond-> opts
                    true (merge metadata)

                    (seq (str body)) (assoc :body body)

                    (seq (:md-filename metadata))
                    (assoc :discuss-link (str (:discuss-link opts)
                                              (process/get-content-path html-map))))
        page-html (-> opts
                      :templates-dir
                      (util/read-template "base.html")
                      (selmer/render page-opts))
        html-file (fs/file (:public-dir page-opts) (:html-filename page-opts))]
    (logger/log (str "Writing HTML to public-dir: " html-file))
    (fs/create-dirs (fs/parent html-file))
    (spit html-file page-html)))
