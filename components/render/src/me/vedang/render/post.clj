(ns me.vedang.render.post
  (:require
   [clojure.string :as cstr]
   [hiccup2.core :as hiccup]))

(defn add-post-links
  [section-name section-data id->html-map opts]
  (when (seq section-data)
    [:div {:id (str "post-" (name section-name))
           :class "prose md:prose-lg lg:prose-xl"}
     [:h3 {:class "links-header"}
      (cstr/capitalize (name section-name))]
     [:ul
      (for [s section-data
            :when (or (not (get-in id->html-map [s :metadata :draft]))
                      (:publish-drafts opts))]
        [:li
         [:a {:href (str "/"
                         (get-in id->html-map [s :metadata :html-filename]))}
          s]])]]))

(defn add-tag-links
  [tags]
  (when (seq tags)
    [:div {:id "post-tags" :class "prose md:prose-lg lg:prose-xl"}
     [:h3 {:class "links-header"} "Tags"]
     [:ul
      (for [t tags]
        [:li
         [:a {:href (str "/tags/" t ".html")}
          t]])]]))

(defn post-body
  "Return the Hiccup for the main post"
  [id->html-map
   {:keys [title body date parents children tags backlinks] :as opts}]
  (hiccup/html {:mode :xhtml}
   [:h1 title]
   (hiccup/raw body)
   [:p [:i (str "Published: " date)]]
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