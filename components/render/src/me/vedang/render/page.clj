(ns me.vedang.render.page
  "Render an HTML page for every post"
  (:require
   [babashka.fs :as fs]
   [hiccup2.core :as hiccup]
   [me.vedang.logger.interface :as logger]
   [me.vedang.render.process :as process]))

(defn base-html
  [{:keys [blog-description blog-title title public-assets-dir-name body
           skip-archive discuss-link]}]
  [:html
   [:head
    [:meta {:charset "UTF-8", :content blog-description}]
    [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
    [:link
     {:type "application/atom+xml",
      :rel "alternate",
      :href "/atom.xml",
      :title blog-title}]
    [:link
     {:href "/style.css",
      :rel "stylesheet",
      :type "text/css"}]
    [:script
     {:src
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js",
      :integrity
      "sha512-7Z9J3l1+EYfeaPKcGXu3MS/7T+w19WtKQY/n+xzmw4hZhJ9tyYmcUS+4QqAlzhicE5LAfMQSF3iFTK9bQdTxXg==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "application/javascript"}]
    [:script
     {:src
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/components/prism-clojure.min.js",
      :integrity
      "sha512-BrJeu0wD2TAYh1Vb3N0BSn/7t43FfeEkDNof6XUX5wFLmFb1z3tS/8bbr1XZqDg96KjtfmuSyI0AUCayDMQYNw==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "application/javascript"}]
    [:link
     {:rel "stylesheet",
      :href
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-tomorrow.min.css",
      :integrity
      "sha512-vswe+cgvic/XBoF1OcM/TeJ2FW0OofqAVdCZiEYkd6dwGXthvkSFWOoGGJgS2CW70VK5dQM5Oh+7ne47s74VTg==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "text/css"}]
    [:script
     {:src
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.js",
      :integrity
      "sha512-st608h+ZqzliahyzEpETxzU0f7z7a9acN6AFvYmHvpFhmcFuKT8a22TT5TpKpjDa3pt3Wv7Z3SdQBCBdDPhyWA==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "application/javascript"}]
    [:link
     {:rel "stylesheet",
      :href
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.css",
      :integrity
      "sha512-Dqf5696xtofgH089BgZJo2lSWTvev4GFo+gA2o4GullFY65rzQVQLQVlzLvYwTo0Bb2Gpb6IqwxYWtoMonfdhQ==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "text/css"}]
    [:script
     {:src
      "https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/copy-to-clipboard/prism-copy-to-clipboard.min.js",
      :integrity
      "sha512-/kVH1uXuObC0iYgxxCKY41JdWOkKOxorFVmip+YVifKsJ4Au/87EisD1wty7vxN2kAhnWh6Yc8o/dSAXj6Oz7A==",
      :crossorigin "anonymous",
      :referrerpolicy "no-referrer",
      :type "application/javascript"}]
    [:title title]
    [:link
     {:rel "apple-touch-icon",
      :sizes "180x180",
      :href (str public-assets-dir-name "/apple-touch-icon.png")}]
    [:link
     {:rel "icon",
      :type "image/png",
      :sizes "32x32",
      :href (str public-assets-dir-name "/favicon-32x32.png")}]
    [:link
     {:rel "icon",
      :type "image/png",
      :sizes "16x16",
      :href (str public-assets-dir-name "/favicon-16x16.png")}]
    [:link
     {:rel "manifest",
      :href (str public-assets-dir-name "/site.webmanifest")}]
    [:link
     {:rel "mask-icon",
      :href (str public-assets-dir-name "/safari-pinned-tab.svg")
      :color "#5bbad5"}]
    [:meta {:name "msapplication-TileColor", :content "#da532c"}]
    [:meta {:name "theme-color", :content "#ffffff"}]]
   [:body
    [:div {:id "site-header" :class "m-2 border-b-2 border-gray-300"}
     [:div {:id "site-title"
            :class "prose prose-stone mx-auto grid grid-cols-2"}
      [:div
       [:h1 {:class "m-0 lg:text-2xl"}
        [:a {:href "/index.html"} blog-title]]
       [:p {:class "font-semi-bold mt-1 text-xl text-gray-800"}
        blog-description]]
      [:div
       [:nav {:id "site-nav"}
        [:a
         {:class "p-2 font-medium text-gray-700 transition duration-200 hover:bg-gray-100 hover:text-gray-900 hover:ease-in-out"
          :href "/atom.xml"}
         "Feed"]
        [:a
         {:class "p-2 font-medium text-gray-700 transition duration-200 hover:bg-gray-100 hover:text-gray-900 hover:ease-in-out",
          :href "https://twitter.com/vedang",
          :target "_blank"}
         "Twitter"]
        [:a
         {:class "p-2 font-medium text-gray-700 transition duration-200 hover:bg-gray-100 hover:text-gray-900 hover:ease-in-out",
          :href "https://fosstodon.org/@vedang",
          :target "_blank"}
         "Mastodon"]
        [:a
         {:class "p-2 font-medium text-gray-700 transition duration-200 hover:bg-gray-100 hover:text-gray-900 hover:ease-in-out",
          :href "/about.html"}
         "About"]]]]]
    [:div {:id "post-body",
           :class "prose prose-stone mx-auto px-2 prose-ul:list-none prose-img:rounded-xl prose-img:shadow-xl lg:px-0"}
     body
     (when-not skip-archive
       [:nav
        {:id "post-nav", :class "bottom-0 right-0 float-right mb-6"}
        [:a {:href "/index.html" :class "px-2"} "Archive"]
        [:a {:href discuss-link, :class "px-2" :target "_blank"}
         "Discuss this post"]])
     [:nav
      {:id "copyright-nav", :class "bottom-0 left-0 float-left mb-6"}
      [:p "Except where otherwise noted, content on this site is licensed under a "
       [:a {:href "https://creativecommons.org/licenses/by/4.0/"}
        "Creative Commons Attribution 4.0 International license"]
       "."]
      [:p "Built with love, using "
       [:a {:href "https://www.gnu.org/software/emacs/"} "Emacs"]
       ", "
       [:a {:href "https://orgmode.org/"} "Org-mode"]
       ", "
       [:a {:href "https://clojure.org/index"} "Clojure"]
       ", "
       [:a {:href "https://babashka.org/"} "Babashka"]
       ", "
       [:a {:href "https://tailwindcss.com/"} "TailwindCSS"]
       ", "
       [:a {:href "https://prismjs.com/"} "PrismJS"]]]]]])

(defn render-file!
  [{:keys [metadata body] :as html-map} opts]
  (let [page-opts (cond-> opts
                    true (merge metadata)

                    (seq (str body)) (assoc :body body)

                    (seq (:md-filename metadata))
                    (assoc :discuss-link (str (:discuss-link opts)
                                              (process/strip-root-dir html-map))))
        page-html (hiccup/html (base-html page-opts))
        html-file (fs/file (:public-dir page-opts) (:html-filename page-opts))]
    (logger/log (str "Writing HTML to public-dir: " html-file))
    (fs/create-dirs (fs/parent html-file))
    (spit html-file page-html)))
