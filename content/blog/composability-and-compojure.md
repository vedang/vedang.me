+++
title = "Composability and Compojure"
author = ["Vedang Manerikar"]
date = 2012-02-23T23:58:00+05:30
aliases = ["/techlog/2012-02-23-composability-and-compojure/"]
lastmod = 2020-08-22T18:04:32+05:30
tags = ["programming", "ring", "compojure", "clojure"]
categories = ["programming"]
draft = false
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
+++

_Caveat_: This post needs some rudimentary knowledge of Compojure.
Compojure is a web framework for Clojure, and if you don't understand
what that means, then you should probably head over to the
[Compojure docs](https://github.com/weavejester/compojure/wiki).

Compojure exposes us to a beautifully <span class="underline">composable</span> abstraction, and
this post is an attempt to show why that is a great thing.

<!--more-->

In order to understand the Compojure framework, let us write a small
`cello world` app. The code snippets in this post are a means to
explain a concept, they may not work as-is. The full, working code is
available at [this](https://gist.github.com/1893532/) gist. Okay then, let's get started.

Here is what the basic routes function would look like:

```clojure
(cc/defroutes main-routes
  (cc/GET "/" [] (fn [req]
                   (rur/response "<h1>Cello World</h1>")))
  (cc/GET "/bye/" [] (fn [req]
                       (rur/response "<h1>Goodbye World</h1>")))
  (route/not-found "<h1>Page not found</h1>"))
```

Compojure uses Ring to handle requests and responses. These terms
(request/response) don't mean 'objects' (to those of you from the OOP
world) of any kind, they are just hash-maps used to <span class="underline">represent</span> the
idea. They are data, and as such, can be manipulated in any way we
want. Ring has a simple philosophy: write functions which accept a
request and return a response. Such functions are called <span class="underline">handlers</span>.

The `cc/GET` helper macro - and it's ilk - asks the user for a
request-method (specified by the GET in the name `cc/GET`), a route
(string representing uri), and a handler(H1). The macro becomes a
handler (H2) which returns the result of `(H1 req)` if the route and
the method of the incoming request match the specified route and
request-method, otherwise it returns nil. This is our first
introduction to composability in Compojure. We have a macro that takes
a handler and gives us another handler. As long as we are dealing in
handlers, composability allows us to ignore any implementation
complexity.

The `cc/defroutes` macro takes a name and a list of handlers and
returns a handler (H3) which runs all the handlers in the list on the
request until one of them returns a non-nil value, else it returns
nil. Finally, it binds the name to the handler so that we can call it.
Simple, isn't it?

So if you think you've understood it so far, tell me if adding this
route to our main routes will work or not:

```clojure
(cc/GET "/hello*" [] (cc/defroutes hello-routes
                       (cc/GET "/hello/name/" []
                               (fn [req]
                                 (rur/response "<h1>Cello Vedang</h1>")))
                       (cc/GET "/hello/city/" []
                               (fn [req]
                                 (rur/response "<h1>Cello from Pune!</h1>")))))
```

Well, yes! `cc/defroutes` gives us a handler[^fn:1], and that's really
all `cc/GET` cares about!

Having an abstraction of this form allows us to do many things easily,
knowing that stuff <span class="underline">just works</span>. For example, let us write some
<span class="underline">middleware</span>. What is middleware? Middleware modifies the incoming
request or outgoing response in some way that makes us happy. How does
this fit into our abstraction? - As a function that takes a
handler(H1) and, wait for it, returns another handler(H2). Boom!
Confused? Here is what a middleware function looks like:

```clojure
(defn verify-secret
  "Verify that secret-key has been sent as a parameter s in the request"
  [handler]
  (fn [request]
    (if (= "s=please" (:query-string request))
      (handler request)
      {:status 403
       :body "You don't know the secret word"})))
```

This function gets a handler(H1). It doesn't know or care what that
handler is going to do to the request. It returns a new handler(H2)
which does the following: it checks to see if the incoming request
knows that the secret word s is "please". If it does, great. Execute
H1 on the request and call it a day. Otherwise, return a nil - meaning
the request in not valid. Now H2 could go through as many other
middleware functions as we want, all of them agnostic of any other
middleware functions. Each middleware will return a modified
handler(H3, H4, ... Hn), and we will run the final handler on the
request.

Here is what the final code would look like:

```clojure
;; Define some Hello routes
(cc/defroutes hello-routes
  (cc/GET "/hello/name/" []
          (fn [req]
            (rur/response "<h1>Cello Vedang</h1>")))
  (cc/GET "/hello/city/" []
          (fn [req]
            (rur/response "<h1>Cello from Pune!</h1>"))))

;; Our main routes function.
(cc/defroutes main-routes*
  (cc/GET "/" [] (fn [req]
                   (rur/response "<h1>Cello World</h1>")))
  (cc/GET "/bye/" [] (fn [req]
                       (rur/response "<h1>Goodbye World</h1>")))
  (cc/GET "/hello*" [] hello-routes)
  (route/not-found "<h1>Page not found</h1>"))

;; A middleware function
(defn verify-secret
  "Verify that secret-key has been sent as a parameter s in the request"
  [handler]
  (fn [request]
    (if (= "s=please" (:query-string request))
      (handler request)
      {:status 403
       :body "You don't know the secret word"})))

;;; Wrap my main routes in middleware
(def main-routes (-> #'main-routes*
                     verify-secret))

;;; Start app with main routes
(run-jetty #'main-routes {:port port :join? false})
```

`main-routes*` is a handler which matches the incoming uris to ones we
support, `verify-secret` will make sure that the incoming requests
know the secret word. We can go a really long way with functions that
take a request and return a response. Compojure gives us a great DSL
to deal with the web. It's composability facilitates building elegant
systems and frameworks.


## Footnotes: {#footnotes}

[^fn:1]: : Actually, we got lucky in this case. `cc/defroutes` is a macro. When we say `(cc/defroutes name & handlers)` the code is replaced to become `(def name handlerfn)`. Luckily for us though, def returns the variable which was just defined, and it works out okay in the end. The aim was to show composability in action, not to espouse a coding style. Never do this in actual code.
