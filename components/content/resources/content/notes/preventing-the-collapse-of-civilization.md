---
title: "Lazy Weekend Viewing: Preventing the collapse of civilization"
author: ["Vedang Manerikar"]
date: 2020-04-12T18:20:00
aliases: ["/blog/preventing-the-collapse-of-civilization/"]
slug: "preventing-the-collapse-of-civilization"
tags: ["technology", "history", "programming"]
draft: false
syndication:
  twitter: 1249325252594642944
---

<div class="ox-neuron-main">
<div class="ox-neuron-toc">
<div class="ox-neuron-toc-contents">
<div class="ox-neuron-toc-heading">Table of Contents</div>
<div class="ox-neuron-toc-items">
- [Summary](#summary)
- [Technology degrades over time](#technology-degrades-over-time)
- [Thesis: Software Robustness is in decline right now](#thesis-software-robustness-is-in-decline-right-now)
- [Thesis: Programmer Productivity is in decline right now](#thesis-programmer-productivity-is-in-decline-right-now)
- [If Robustness and Productivity are both declining, Software cannot be advancing](#if-robustness-and-productivity-are-both-declining-software-cannot-be-advancing)
- [Complexity accelerates knowledge loss](#complexity-accelerates-knowledge-loss)
- [Resources:](#resources)</div>
</div>
</div>

<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Lazy Weekend Viewing: Preventing the collapse of civilization</h1>
<div class="ox-neuron-article-contents">
"[Preventing the Collapse of Civilization](https://www.youtube.com/watch?v=pW-SOdj4Kkk)" is an excellent talk by Jonathan Blow (of Braid fame) at DevGAMM 2019. It's inspiring and I recommend you make the time to see it. My notes below:


## Summary {#summary}

-   It is a myth that technology only advances. We've often seen significant backward slides in tech. (Lots of examples to illustrate this point)
-   Software is in decline. Both software robustness and programmer productivity is declining. (Lots of examples to illustrate this point)
-   We need to fight complexity and strive for simplicity in every step if we want to battle degradation and loss of capability.


## Technology degrades over time {#technology-degrades-over-time}

-   Unless people make intentional effort to avoid this.
    -   We have this idea of technology marching forward, but it goes backward all the time.
    -   We've forgotten lots of tech over time when empires have fallen. (excellent examples in the talk)
    -   Entire inter-connected thriving global system collapsed in 1177 BC. (end of the Bronze Age)
-   It takes a lot of energy to communicate from generation to generation and there are losses inevitably. This has happened to us with Microprocessors already. (Interview with Bob Colwell, Chief Microprocessor Architect, Intel illustrating this point)


## Thesis: Software Robustness is in decline right now {#thesis-software-robustness-is-in-decline-right-now}

-   It's hard to recognize a slow collapse, the fall of the Roman Empire took 300 years.
-   Software has been free riding on hardware, so it looks like we are doing okay.
-   Software is having small local technological improvements (like machine learning) while suffering from degradation is other areas (like creating efficient apps)
-   We don't expect software to work anymore, and this has been happening for so long that it's become the new normal.
    -   "Just restart it!"
    -   Jon: "I'm just going to note down / take a screenshot every time software I use has an obvious bug or unintuitive / incorrect behavior." ~ collected loads of examples in just a few days.
    -   Try counting for yourself and you will be surprised by how many bugs there are, where the system just doesn't do the thing it's supposed to.
-   If your laptop itself is less than 3 nines uptime, nothing running on it will be 5 nines. 5 nines was a standard that we were selling in the 90s, but it's completely infeasible today.
-   If you haven't seen an entire industry produce robust software in a decade, what makes you think they can do it at all?


## Thesis: Programmer Productivity is in decline right now {#thesis-programmer-productivity-is-in-decline-right-now}

-   Programmers: "Working on a higher level of abstraction lets me be smart! Save Effort!"
-   You start out by being right and then you extrapolate it too far into the wrong territory.
-   The flip side of higher lever of abstraction is the loss of capability altogether.
-   Productivity per programmer is dropping, not increasing. This is easy to look at in terms of innovation in Products across multiple companies vs number of engineers they employ.
    -   Interview with Ken Thompson talking about writing Unix in 3 weeks to illustrate this point.


## If Robustness and Productivity are both declining, Software cannot be advancing {#if-robustness-and-productivity-are-both-declining-software-cannot-be-advancing}

-   "You cannot just"
    -   Copy a program from one computer to another
        -   The installer is for getting around incompatibilities added at the OS layer. The OS removes capabilities like compatibility.
    -   Statically link
    -   Draw a pixel on a screen
    -   Write a shader
    -   Compile a program without a manifest
    -   Run an unsigned program
-   All of these things add friction, bugs, engineering time, headspace. They keep us from thinking about and doing interesting things.


## Complexity accelerates knowledge loss {#complexity-accelerates-knowledge-loss}

-   An example of complication: Language Server Protocol
    -   A complicated, slow way to do libraries.
    -   It's a hassle to write plugins (made up problem). So instead, just talk to a language server over a socket.
    -   There is nothing special in looking up the location of X. It's just an API, and what you are saying is we should architect APIs like this. Which is crazy.
    -   If you have to author something for this system, you now have to author and debug for a distributed system, where state is not located in any central place.
    -   You are basically converting your fast system into a slow distributed debugging hell.
-   Is there a point of reasonable complexity, and if so are we past it? How would we know?
    -   Example of how complexity will impact the gaming industry: First there won't be people to make competing Game engines, then there won't be people good enough to maintain the existing systems.
    -   In the bronze age, reading and writing were not widespread, so they were fragile.
        -   Today, almost nobody knows what is happening on a CPU/GPU, that skill is fragile.
-   Complication accelerates Knowledge Loss
    -   More to know, so you only end up knowing a small percentage.
    -   Deep knowledge replaced by trivia
    -   Good information drowned by noise.
        -   The harder it is to understand something, the more noise / bad answers around it.


## Resources: {#resources}

-   Videos recommended by Jonathan Blow:
    -   Casey Muratori: [The Thirty Million Line Problem](https://www.youtube.com/watch?v=kZRE7HIO3vk)
    -   Samo Burja: [Civilization: Institutions, Knowledge and the Future](https://www.youtube.com/watch?v=OiNmTVThNEY)
    -   Eric Cline: [1177 BC: The Year Civilization Collapsed](https://www.youtube.com/watch?v=hyry8mgXiTk)

</div>
</div>
</div>
