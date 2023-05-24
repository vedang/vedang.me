---
title: "Lazy Weekend Viewing: GOJEK's 10x Engineer - Truth or Myth?"
author: ["Vedang Manerikar"]
date: 2019-07-20T07:39:00
aliases: ["/weblog/2019-07-20-gojek-10x-engineering/", "/blog/gojek-10x-engineering/"]
slug: "gojek-10x-engineering"
tags: ["engineering-management", "gojek", "culture"]
draft: false
featured_image: "blog/gojek-10x-engineering/gofigure6.jpg"
---

<div class="ox-neuron-main">
<div class="ox-neuron-toc">
<div class="ox-neuron-toc-contents">
<div class="ox-neuron-toc-heading">Table of Contents</div>
<div class="ox-neuron-toc-items">
- [Summary](#summary)
- [Introductions](#introductions)
- [How do you get so much done with so few engineers](#how-do-you-get-so-much-done-with-so-few-engineers)
- [Collaboration, Communication and Team Size](#collaboration-communication-and-team-size)
- [What about "number of pods"? Will infinite teams of 5 scale infinitely?](#what-about-number-of-pods-will-infinite-teams-of-5-scale-infinitely)
- [Complexity of organizations](#complexity-of-organizations)
- [Monolith to SOA](#monolith-to-soa)
- [Relationship as a service AKA How politics enters your system](#relationship-as-a-service-aka-how-politics-enters-your-system)
- [Is there such a thing as a 10x engineer?](#is-there-such-a-thing-as-a-10x-engineer)
- [Good practices in coding](#good-practices-in-coding)
- [Differences between good engineers and 10x engineers](#differences-between-good-engineers-and-10x-engineers)
- [How do you spot red flags in a new hire?](#how-do-you-spot-red-flags-in-a-new-hire)
- [Onboarding / Engineering bootcamp](#onboarding-engineering-bootcamp)
- [Gojek culture](#gojek-culture)</div>
</div>
</div>

<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Lazy Weekend Viewing: GOJEK's 10x Engineer - Truth or Myth?</h1>
<div class="ox-neuron-article-contents">

## Summary {#summary}

-   Engineering quality is paramount. Focus on [clean and clear code](https://dave.cheney.net/2019/07/09/clear-is-better-than-clever). Code is the primary medium of communication for any engineer. Write beautiful code and hold people to high standards.
-   Adding head count has vast hidden costs and often brings down output. The reason for this is the exponential increase in communication required to align everyone to common goals.
-   Similarly, pods cannot scale unless they can own small, independent components.
-   Monoliths are normal and good when the company is small. Refactoring the monolithic model into different components allows us to scale pods and org.
-   Carve monoliths only when it hits critical mass. Then, identify the pain and pull it out into it's own pod.
-   "Relationship as a service" or "Please get this done for me over and above other stuff on your plate"
    -   "Traffic congestion" (A team which is too busy) and structural failure can lead to the impression that the system is bureaucratic and political.
-   Criteria when hiring Engineers
    -   Computer science (Can computers understand your code?)
        -   Ability to grasp large complex systems
        -   Understanding of implications of design choices on that system.
    -   Software Engineering (Can humans understand your code?)
        -   How well do you communicate through your code? This is the metric that enables building **good** systems.
        -   The hygiene you show in code is the hygiene you will enforce on others.
    -   Good behavioral traits (Can you grow?)
        -   Curious
        -   Wants to learn
        -   Can accept feedback
        -   "Strong opinions, Weakly held."
        -   "Pride without attachment."
-   Find the full video [here](https://www.youtube.com/watch?v=He0XBBfCEVk)!
-   The rest of this post is detailed notes on the video

<!--more-->


## Introductions {#introductions}

-   CEO: Nadiem Makarim
-   CTO: Niranjan Paranjape
-   Tech Recruitment Head: Sidu Ponnappa
-   C42 Engineering: Sequoia SWAT Team for solving tech problems
    -   They were the rare "other Clojure shop" in India in 2010.
-   Common consulting milestone for them was "the Client just tried to poach me". When Gojek tried this with Niranjan, they sold the entire company to Gojek.
-   Today, Gojek does $9 BILLION in transactions with 300 engineers.


## How do you get so much done with so few engineers {#how-do-you-get-so-much-done-with-so-few-engineers}

-   Adding headcount does not increase output.
-   With every engineer you add, you actually slow down work (exponentially)
-   A good finance person will challenge you on every item of spend because they understand that that's how they lose money.
-   Similarly, a good engineering leader will challenge you on every addition of headcount. The hidden cost is enormous and impacts ability to deliver quickly and at quality.


## Collaboration, Communication and Team Size {#collaboration-communication-and-team-size}

-   Every person you add exponentially increases the combinatorics of communication you need in order to agree on what exactly it is that you are building.
-   **Code is communication between humans. Keep it clean and simple.**
-   Upto 4 people, productivity grows linearly with addition of people. Beyond that it starts plateauing and dipping.
    -   The person who observed this started slicing and dicing teams so that at any point in time a subsystem was being worked on by no more than 4 people.
    -   By [Conway's Law](https://goodroot.ca/post/2018/10/13/practicality-metaphysics-conways-law/), he was forcing system architecture to be set up in a certain way.
-   We chose to map out our systems based on what a 5 person team could do.


## What about "number of pods"? Will infinite teams of 5 scale infinitely? {#what-about-number-of-pods-will-infinite-teams-of-5-scale-infinitely}

-   As an organization, we find similar diminishing returns with growing the number of pods that we have.
-   The company's ability to add pods is also limited by ability of the pods to understand the underlying system.
-   If your pods are independent (have to collaborate only once in 2 to 4 weeks), then you are in a manageable situation.
-   Multiple pods working on the same problem leads to pushing for local goals (evolving the system to maximize your objectives based on limited understanding)


## Complexity of organizations {#complexity-of-organizations}

-   "Don't slow me down" from CEO to engineering
    -   Philosopher poet engineers
    -   Engineering had to educate the CEO on the bad effects of not on-boarding new comers fast enough
-   Adding Kafka to our stack allowed us to scale
    -   Helped us decouple communication
    -   Tap into our data, you don't need to ask me anymore.
-   **Refactoring the monolithic model into different components allowed us to put more people on the independent teams.**


## Monolith to SOA {#monolith-to-soa}

-   We first pulled out the allocation system
    -   matching driver to customer
-   Next we pulled out Go Pay
    -   This allowed us to staff the team of Go Pay without them having to understand the entire monolith.
-   **Identify a problem, pull it out, establish a pod around it.**
-   CTO was pushing CEO:
    -   Before we move into this new thing, we need to get rid of the monolith first.
    -   The choice they made was **never stall growth**. This meant hanging on to the monolith for longer than they would have liked, and refactoring it in bits and pieces rather than in one single effort.
-   When starting out, it makes complete sense to have a monolith.
-   When you carve a system out of a monolith, eventually that will become a monolith.
    -   Now you need to split it again.
    -   **Identify when the monolith reaches critical mass, and then refactor your team and code-base in lockstep.**


## Relationship as a service AKA How politics enters your system {#relationship-as-a-service-aka-how-politics-enters-your-system}

-   I need something from your system, so I'm going to lean on our relationship to unblock me and ignore your own priorities.
-   "Hey I really need this done fast, because X"
-   This will grow until you are completely busy trying to unblock other people and are unable to focus on any of your own priorities.
-   Now you have a bureaucracy. The person with the back-channel to the blocked system gets to jump the queue. This inherently does not scale because people without the relationship back-channel get more and more frustrated.
-   "Our organization is political." I don't have privileged access, therefore that other guy must be master politician.
-   **The actual underlying problem is traffic congestion. If you are trying to do your best, you are going to use every resource at your disposal, including your relationships, to get work done**
-   **Even in the most well-intentioned and non-egotistical organizations, structural failure leads to the perception of favoritism**


## Is there such a thing as a 10x engineer? {#is-there-such-a-thing-as-a-10x-engineer}

-   **As system complexity grows, for you to meaningfully contribute, you need to know the system!**
-   Criteria to select engineers
    -   Computer science (can computers understand your code?)
        -   Ability to grasp large complex systems
        -   Understanding of implications of design choices on that system.
    -   Software Engineering (can humans understand your code?)
        -   How well do you communicate through your code?
        -   Communication problems are the hard blockers on building good systems.
        -   Well written code provides contextual logic and [clarity](https://dave.cheney.net/2019/07/09/clear-is-better-than-clever).
    -   Good behavioral traits:
        -   curious
        -   wants to learn
        -   reads books
        -   can accept feedback
        -   ^ This is the kind of person who can grow.


## Good practices in coding {#good-practices-in-coding}

-   Every code-base comes with a glossary
-   Terms have specific meaning, and naming is critical in the dividends it pays off in the future.
-   Every engineer at every level has to take a written test
-   We are not looking at whether you can solve the complex problem. We are judging how you express yourself. You are a programmer, and your code should speak well for you.
-   **Hygiene becomes more and more important as a programmer becomes more and more senior. The hygiene you show in code is the hygiene you will enforce on others.**
-   High standards are important.


## Differences between good engineers and 10x engineers {#differences-between-good-engineers-and-10x-engineers}

-   10x **outcome** (not output)
-   As you grow your organization, individual outcome is less important. Team outcome is what you need to measure.
-   Engineering is completely a team sport.
-   You can have deeply capable and extremely competent individual contributors who like to work alone. As long as their code fits beautifully with other systems, they are multiplying everyone's capacity.


## How do you spot red flags in a new hire? {#how-do-you-spot-red-flags-in-a-new-hire}

-   Every good engineer is highly opinionated. A good engineer has strong opinions weakly held.
    -   You should be able to change your opinion on being presented facts.
-   If you are unable to deal with criticism of your code, that's a smell.
    -   We ask junior people to interview senior engineers. This shows the grace they have when dealing with others.
-   We routinely wind up in situations where you have young people owning complex systems. Senior engineers need to be hired to now deal with these systems, and they will be on-boarded by juniors. They should be able to deal with this fact.
-   CTO: "When I lay out a design, I expect people to give me rational criticism so that I can learn from them."
-   If you had to hire someone with deep experience vs great behavioral foundations, what would you choose?
    -   It Depends!
    -   Sometimes, you need to bring deep experience to the table to build something out and learn from the guru.
    -   Behavioral traits means that someone is teachable. Outside the Valley, this is extremely important because of the lack of access to experienced engineering.


## Onboarding / Engineering bootcamp {#onboarding-engineering-bootcamp}

-   Structured decision making
-   Basic coding hygiene
-   Collaboration
    -   How do you engage with someone else to meaningfully decide reasonably quickly what something in code should be.
-   Heavy focus on coding during the bootcamp.
-   We bring in coaches from **every team** and teach. (regardless of hire). This helps us build empathy, which is critical for future collaboration.
-   Pride without attachment.
    -   Pride allows you to focus on creating beautiful code.
    -   Attachment means you will stand in the way of the evolution of your beautiful baby. You need to let it go and let others change it.
    -   If you look back at code you wrote 6 months back and don't notice how bad you were, you aren't growing.
-   Repeat the same problem every Monday throughout the whole bootcamp. Ask them to review every week and see if they find that feeling of progress.


## Gojek culture {#gojek-culture}

-   Even the most high-performing team has to deal with intense time pressure.
-   What makes or breaks teams under pressure?
    -   Show them the impact that they are making.
    -   You don't need to push engineers, you need to give them the rush! External force will not drive the maker as much as the impact of work will.
    -   The value you create is not proportional to the time you put in, it's proportional to the state of your mind in that time you put in.
        -   If you are burned out, or your team mate is burned out then there is going to be a huge negative spiral.
    -   To harness creativity, you need balance and psychological safety. The hours that you put in, you need to be in a state of flow. This requires you to take short breaks after days of intense activity.
-   You can have a 10x engineer amidst you, but you may never know it because you did not give them sufficient leeway to unleash their art.

</div>
</div>
</div>
