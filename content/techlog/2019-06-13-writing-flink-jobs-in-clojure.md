+++
title = "Writing Flink Jobs in Clojure"
author = ["Vedang Manerikar"]
date = 2019-06-13
tags = ["flink", "clojure", "streaming"]
categories = ["programming"]
draft = true
toc = true
+++

## 4e3f68a \* Add concrete type info to generic Flink interfaces {#4e3f68a-add-concrete-type-info-to-generic-flink-interfaces}

Java to the rescue!

Add a Java class to be a simple data-structure to hold our
word-count data.

Define an interface in Java which applies concrete types to generic
Flink interfaces.

Reify'ing this **now** has all the type information needed by Flink!
Clojure code for the win, with a little help from Java.


## b997c9c \* Modify code to use a single reified object {#b997c9c-modify-code-to-use-a-single-reified-object}

Since we are reifying the same interface, we might as well do it
just once, and reuse that object in the various chained stages of
the Flink pipeline


## 8f7fcc0 \* Convert the WriteIntoKafka example into Clojure {#8f7fcc0-convert-the-writeintokafka-example-into-clojure}

This one has an important and slightly discouraging discovery. We
implement our own source here which keeps returning new strings
every second. We need to implement the \`SourceFunction\` interface
for this.

Initially, I thought that the previous approach of implementing a
concrete Interface which extends \`SourceFunction<String>\` would be
enough, but this is not true. This is because of the way that the
two methods of the \`SourceFunction\` Interface interact with each
other. The \`run\` method starts a possible infinite loop, and the
\`cancel\` method stops the loop started by the \`run\` method. This
requires us to mutate state held within the Class, but without
using any of the Clojure state management methods like atom, ref
etc. This is because these are not \`Serializable\`. I don't know how
to do this from Clojure, and I'm 99% sure that it is not possible.

Due to this, I had to write a class in Java which implemented the
\`SourceFunction\` interface.


## bab6c3a \* Convert the socket-window wordcounter to a defrec {#bab6c3a-convert-the-socket-window-wordcounter-to-a-defrec}

Using a `defrecord` allows me to specify the `:load-ns` option,
which is very useful. It ensures that the entire namespace where
the defrecord is defined gets loaded when I use the record anywhere
in my code. This helps avoid errors like:

-   Attempting to call unbound fn:
    \#'poclink.event-stream-processor/json-str->event
