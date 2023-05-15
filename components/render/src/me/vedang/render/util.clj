(ns me.vedang.render.util
  (:require
   [cljc.java-time.format.date-time-formatter :as cjtfdf]
   [cljc.java-time.zone-id :as cjtzi]
   [cljc.java-time.zoned-date-time :as cjtz]))

(defn human-readable
  "Return a date in yyyy/MM/dd format, because it's more readable that way"
  [date]
  (cjtz/format (cjtz/of-instant date (cjtzi/of "UTC"))
               (cjtfdf/of-pattern "yyyy-MM-dd")))

(defn post-links
  "Build the HTML (in Hiccup form) for links to all the HTML maps we have
  been given."
  [html-maps opts]
  [:div {:id "post-links"}
   [:h1 (:title opts)]
   [:ul
    (for [{{:keys [title date skip-archive html-filename]} :metadata
           html :html} html-maps
          :when (and (seq html) (not skip-archive))]
      [:li
       [:span (human-readable date)]
       [:span " - "]
       [:a {:href (str "/" html-filename)}
        title]])]])
