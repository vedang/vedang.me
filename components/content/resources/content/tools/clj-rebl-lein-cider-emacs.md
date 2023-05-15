---
title: "How to use the Clojure REBL with Leiningen, Cider and Emacs"
author: ["Vedang Manerikar"]
date: 2020-10-28T23:18:00
slug: "clj-rebl-lein-cider-emacs"
tags: ["clojure", "cognitect", "rebl", "leiningen", "cider", "emacs"]
draft: false
---

**Note**: This set of instructions only work on **Clojure 1.10+**, **Lein 2.9+** and **JDK 11+**. Also, REBL requires a license for commercial use.

I didn't find a handy reference to getting started with REBL if your primary workflow is Leiningen/nrepl, so here is the series of things I had to do to get the REBL up and running for myself.

1.  Download the [REBL jar](https://docs.datomic.com/cloud/other-tools/REBL.html) from Cognitect.
2.  Use [lein-localrepo](https://github.com/kumarshantanu/lein-localrepo) to install the REBL jar to your m2.
    ```shell-script
    lein localrepo install rebl-0.9.242/rebl-0.9.242.jar com.cognitect/rebl 0.9.242
    ```
3.  Add the following code to your `profiles.clj` file (code below). This is based on the [REBL installation guide](https://docs.datomic.com/cloud/other-tools/REBL.html#installation) and [Rick Moynihan's nrepl middleware](https://github.com/RickMoynihan/nrebl.middleware):
    ```clojure
    {
     ;; ....
     ;; You other profiles stuff
     ;; ....
     :rebl {:plugins [[cider/cider-nrepl "0.25.3"]
                      [refactor-nrepl "2.5.0"]
                      [nrepl "0.8.2"]]
            :repl-options {:nrepl-middleware [nrebl.middleware/wrap-nrebl]}
            :dependencies [[org.clojure/tools.deps.alpha "0.7.511"]
                           [com.cognitect/rebl "0.9.242"]
                           [org.clojure/core.async "1.3.610"]
                           [cljfmt  "0.6.4"]
                           [rickmoynihan/nrebl.middleware "0.3.1"]
                           [org.openjfx/javafx-fxml "15-ea+6"]
                           [org.openjfx/javafx-controls "15-ea+6"]
                           [org.openjfx/javafx-swing "15-ea+6"]
                           [org.openjfx/javafx-base "15-ea+6"]
                           [org.openjfx/javafx-web "15-ea+6"]]}
     ;; ....
     ;; If you don't have anything in your profiles.clj right now, just
     ;; remove the comments and copy the whole map over.
     }
    ```
4.  Start the REPL with the new profile
    ```shell-script
    lein with-profile +rebl repl :headless
    ```
5.  Connect to the REPL from Emacs. Do this by opening up any Clojure file in your project and executing `M-x cider-connect` in the file.
6.  Run the following commands in the REPL to spin up the REBL.
    ```clojure
    (require '[cognitect.rebl :as rebl])
    (rebl/ui)
    ```
7.  At this point, anything that you evaluate when programming will be sent to your REPL as well as to your REBL and can be inspected from there. Anything you evaluate into the REPL will be sent over to the REBL as well.
8.  Work on the REPL as you normally would. Your work is also being sent into the REBL for fancy viewing when you need it.
