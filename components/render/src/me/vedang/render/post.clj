(ns me.vedang.render.post
  (:require
   [clojure.string :as cstr]
   [hiccup2.core :as hiccup]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.markdown :as markdown]
   [me.vedang.render.process :as process]
   [me.vedang.render.util :as util]))

(defn ->html-map
  "Convert `post-file` from md format to HTML format"
  [opts post-file]
  (logger/log (str "Converting from Markdown to HTML: " post-file))
  (-> post-file
      slurp
      (markdown/md-to-html post-file)
      (assoc-in [:metadata :md-filename] post-file)
      (assoc-in [:metadata :root-dir] (:root-dir opts))
      (assoc-in [:metadata :content-dir] (:content-dir opts))))

(defn add-post-links
  [section-name section-data id->html-map opts]
  (when (seq section-data)
    (let [title (cstr/capitalize (name section-name))
          div-id (str "post-" (name section-name))
          opts (assoc opts :title title)]
      ;; When rendering these post-links, we also want the pages that
      ;; would normally skip the archive to show up.
      [:div {:id div-id}
       (util/post-links (->> section-data
                             (map (comp #(assoc-in % [:metadata :skip-archive] false)
                                        id->html-map))
                             process/sort-and-filter)
                        opts)])))

(defn add-tag-links
  [tags]
  (when-let [last-tag (last tags)]
    [:div {:id "post-tags"}
     [:h1 {:class "links-header"} "Tags"]
     [:div {:class "flex"}
      (for [t tags]
        (if (= t last-tag)
          [:div {:class "inline-flex"}
           [:a {:href (str "/tags/" t ".html")} t]
           [:span {:class "mx-2"} ""]]
          [:div {:class "inline-flex"}
           [:a {:href (str "/tags/" t ".html")} t]
           [:span {:class "mx-2"} "|"]]))]]))

(defn post-body
  "Return the Hiccup for the main post"
  [id->html-map
   {:keys [body date parents children tags backlinks] :as opts}]
  (hiccup/html
   [:div {:id "post-body"}
    (hiccup/raw body)]
   [:div {:id "post-last-updated"}
    [:p [:i (str "Last Updated: " date)]]]
   (when (seq backlinks) [:hr])
   (add-post-links :backlinks backlinks id->html-map opts)
   (when (seq parents) [:hr])
   (add-post-links :parents parents id->html-map opts)
   (when (seq children) [:hr])
   (add-post-links :children children id->html-map opts)
   (when (seq tags) [:hr])
   (add-tag-links tags)))

(defn add-html-body
  [{:keys [metadata html] :as html-map} id->html-map opts]
  (let [post-opts (cond-> opts
                    true (merge metadata)

                    (seq (str html)) (assoc :body html))]
    (assoc html-map
           :body (post-body id->html-map post-opts))))
