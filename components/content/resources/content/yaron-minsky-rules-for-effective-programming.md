---
title: "Yaron Minsky: Rules for Effective Programming"
author: ["Vedang Manerikar"]
date: 2023-05-26T19:42:00
slug: "yaron-minsky-rules-for-effective-programming"
tags: ["programming", "wisdom"]
categories: ["notes"]
draft: false
---

<div class="ox-neuron-main">
<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Yaron Minsky: Rules for Effective Programming</h1>
<div class="ox-neuron-article-contents">
_Snippet from [Effective ML](https://www.youtube.com/watch?v=-J8YyfrSwTk)_

1.  **Favour Readers Over Writers**: Readers drive clarity and simplicity. Always write code such that it's easy to read
2.  **Create Uniform Interfaces**: Uniform interfaces help set expectations for how the code/data can be treated. They make it easier to jump into new code.
3.  **Make Illegal States Unrepresentable**: Think about the invariants of your data, and design datastructures so that your data cannot enter into an illegal state.
4.  **Code for Exhaustiveness**: Prefer explicit case matching over implicit default cases. A default case is a bug waiting to happen when the shape of the data changes.
5.  **Open Few Modules**: Or in the more general case, use namespaced functions to improve readability. OCaml has a nifty feature where you can use namespaced functions in a local scope! This improves the readability even more than having to require the namespace for the whole file.
6.  **Make Common Errors Obvious**: Make it explicit in the function name that this function can throw an exception. Then the caller knows to expect / handle a possible error.
7.  **Avoid Boilerplate**: Reduces code readability, increases chances of subtle bugs.
8.  **Avoid Complex Type Hackery**: The enemy of correctness is complexity. And complex types are complex.
9.  **Do not be puritanical about purity**: Remember that side-effects are the only way you actually change anything in the real world.

</div>
</div>
</div>
