(ns me.vedang.render.index
  (:require
   [cljc.java-time.format.date-time-formatter :as cjtfdf]
   [cljc.java-time.zone-id :as cjtzi]
   [cljc.java-time.zoned-date-time :as cjtz]
   [clojure.string :as cstr]
   [hiccup2.core :as hiccup]))

(defn human-readable
  "Return a date in yyyy/MM/dd format, because it's more readable that way"
  [date]
  (cjtz/format (cjtz/of-instant date (cjtzi/of "UTC"))
               (cjtfdf/of-pattern "yyyy-MM-dd")))

(let [capitalize (fnil cstr/capitalize "Archive")]
  (defn post-links
    "Build the HTML (in Hiccup form) for all the links that should be
  rendered on the Index/Archive page."
    [html-maps]
    [:div
     [:h1 (-> html-maps first (get-in [:metadata :categories]) first capitalize)]
     [:ul
      (for [{{:keys [title date skip-archive html-filename]} :metadata
             html :html} html-maps
            :when (and (seq html) (not skip-archive))]
        [:li
         [:span (human-readable date)]
         [:span " - "]
         [:a {:href (str "/" html-filename)}
          title]])]]))

(defn body
  "Build the HTML body of the Index page"
  [html-maps]
  (let [groups (group-by #(or (first (get-in % [:metadata :categories]))
                              "not categorised")
                         html-maps)
        cat->post-links (reduce-kv
                         (fn [m category posts]
                           (assoc m category (post-links posts)))
                         {}
                         groups)]
    (hiccup/html [:div
                  (get cat->post-links "programming")
                  (get cat->post-links "tools")
                  (get cat->post-links "tinylog")
                  (get cat->post-links "organization")
                  (get cat->post-links "interviews")
                  (get cat->post-links "notes")
                  (get cat->post-links "projects")
                  (get cat->post-links "mahabharata")
                  (get cat->post-links "random")])))
