+++
title = "Plain Text Gantt Charts"
author = ["Vedang Manerikar"]
date = 2019-04-06
tags = ["gantt", "emacs", "org-mode", "mermaidjs"]
categories = ["yakshaving"]
draft = true
toc = true
featured_image = "/images/pgm-gantt.jpg"
+++

## Points {#points}

-   Gantt charts are a very effective planning tool
-   Everyone ends up on gantt charts:
    -   manually maintain an excel sheet
    -   use professional (and expensive) tool for all or part of your
        project planning: MS Project, Project Plan 365.
-   Wouldn't it be great to have this in plain text?
    -   mermaidjs
    -   ^ Great tool. Easy to write plain text gantt charts and see the
        visualization.
    -   Where do we store this information? I want it to be a part of
        the day to day work I'm doing.
-   Enter org-mode
    -   Incredible tool for personal planning, note taking, task tracking
    -   Missing visualization tools, but has all the tools to hook things together.
    -   Let's get to work connecting org-mode and mermaidjs
-   What we get
    -   Add source blocks anywhere in your org-file, as you would normally do.
    -   All the data is available for you to read within context.
    -   Dealing with dates in Org is a breeze.
-   What are the pain points?
    -   Tangle the org-file, Process the generated file, Evaluate the processed file
        -   Too many manual steps
    -   Adding new sections means you have to remember to add the
        section to the tangle block in the correct order.
-   What is missing
    -   Being able to add a leave for 2 days in the middle of a planned
        project and have relevant plans automatically adjust. Need to
        check if tools support this.

<!--more-->

Gantt Charts are the best.
