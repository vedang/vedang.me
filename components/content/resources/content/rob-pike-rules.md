---
title: "Rob Pike: Rules of Programming"
author: ["Vedang Manerikar"]
date: 2018-06-12T10:05:00
aliases: ["/notes/rob-pike-rules/"]
slug: "rob-pike-rules"
tags: ["wisdom", "programming"]
categories: ["notes"]
draft: false
---

<div class="ox-neuron-main">
<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Rob Pike: Rules of Programming</h1>
<div class="ox-neuron-article-contents">
_Snippet from: [Notes on C Programming](https://www.lysator.liu.se/c/pikestyle.html)_

-   **No Speed Hacks** : You can't tell where a program is going to spend its time. Bottlenecks occur in surprising places, so don't try to second guess and put in a speed hack until you've proven that's where the bottleneck is.

-   **Measure before tuning** : Don't tune for speed until you've measured, and even then don't unless one part of the code overwhelms the rest.

-   **No Fancy Algorithms** : Fancy algorithms are slow when n is small, and n is usually small. Fancy algorithms have big constants. Until you know that n is frequently going to be big, don't get fancy. (Even if n does get big, use Rule 2 first.)

-   **Use Simple Data Structures** : Fancy algorithms are buggier than simple ones, and they're much harder to implement. Use simple algorithms as well as simple data structures.

-   **Data Dominates** : If you've chosen the right data structures and organized things well, the algorithms will almost always be self-evident. Data structures, not algorithms, are central to programming.

</div>
</div>
</div>
