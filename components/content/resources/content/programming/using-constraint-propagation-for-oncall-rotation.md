---
title: "Using Constraint Propagation to solve the team Oncall rotation"
author: ["Vedang Manerikar"]
slug: "using-constraint-propagation-for-oncall-rotation"
tags: ["constraint-propagation", "clojure"]
draft: true
---

<div class="ox-neuron-main">
<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Using Constraint Propagation to solve the team Oncall rotation</h1>
<div class="ox-neuron-article-contents">

## Notation {#notation}

-   Weeks of the calendar are represented by numbers
    -   1 to 52 for every week
-   One run of the program produces one schedule, starting from the next week (after the last provided week in the input schedule)
-   Each person is represented by a map of properties:
    -   "name": name of the person,
    -   "id": a keyword representing the person,
    -   "in-next-rotation?": will be true for people who are to be considered in the next rotation,
    -   "prev-rotation-week": will be a number representing the week when the person was oncall. This can be empty for someone who was not present in the previous rotation but would be present in the next one.
    -   "leaves": leaves planned by the person. Represented as an array of tuples, each tuple representing the week that the person is on leave, and whether this is a soft/hard constraint on the scheduling. If the person is on leave for longer than one day in the week, it becomes a hard constraint against scheduling. If the person is on leave for only one day in the week, it is a soft constraint.
-   The output schedule is map of person-id -&gt; oncall week number


## Hard constraints {#hard-constraints}

-   A person cannot be oncall when they are on leave for more than one day in the given week.


## Soft constraints {#soft-constraints}

-   The next oncall rotation for any person should be as far away from their previous rotation as possible.
-   A person should not be oncall if they have even a single leave planned in the week.

</div>
</div>
</div>
