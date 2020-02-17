+++
title = "Lazy Weekend Reading: A love letter to Clojure"
author = ["Vedang Manerikar"]
date = 2019-10-13T00:00:00+05:30
tags = ["clojure", "software", "programming"]
categories = ["notes"]
draft = false
toc = true
+++

I had no idea that _Gene Kim_, author of three books that have
garnered wide-spread public acclaim, was doing Clojure programming.
(They are DevOps Handbook, Phoenix Project, and Accelerate -- we've
ordered them recently for the Helpshift Library). He published [a
love-letter to Clojure](https://itrevolution.com/love-letter-to-clojure-part-1/) this weekend, and I think it is an absolute
must-read for **all programmers** (whether you write Clojure or not).
The blog-post covers the following areas:


## General software philosophy {#general-software-philosophy}

-   Choose tools that are good to **think** with
-   Most bugs are due to programmers not understanding all the possible
    states their code may execute in. **Make state and it's mutation
    explicit**. Put it in a specific place and mutate it with a small set
    of specific functions.
-   **Conventions matter**. They help readability of code, which is critical.
-   **Make systems simple to change**. Don't "trap yourself in a system of
    work where you can no longer solve real business problems easily.
    Instead, you’re forced to merely solve puzzles all day, trying to
    figure out how to make your small change, obstructed by your
    complected system".
-   Developer productivity is enhanced when they follow the ideals
    listed below. Invisible structures around us either help or hinder
    in this regard.
    -   The first ideal: **Locality and Simplicity**
    -   The second ideal: **Focus, Flow and Joy**
    -   The third ideal: **Improvement of Daily Work**
    -   The fourth ideal: **Psychological Safety**
    -   The fifth ideal: **Customer Focus**
-   "Practice as a transcendental experience" and "Practice as hard work
    and perseverance" are both needed for developers to level up.
-   Bugs can be introduced by not understanding programming language
    constructs (eg: in Ruby, strings are mutated if you use the <<
    operator).


## Specific Programming Practices {#specific-programming-practices}

-   **Pure functions are trivial to test** -- build input, look at output.
    If it's hard to write a test for your code, that's a sign that it is
    complicated. Try to simplify it by pulling pure functions out of it
    and writing tests for those.
-   **Push side-effects (impure functions) to the edge of the program** /
    piece of code that you are writing. Not practicing this leads to
    code becoming increasingly untestable and difficult to change.
    -   example of good vs bad way:

        ```clojure

        ;; good way: steps are composed together, which each compiler phase
        ;; indepdendently executable and testable.

        (-> (tokenize-source-files!)
            (generate-abstract-syntax-tree)
            (generate-intermediate-representation)
            (generate-assembly-instructions)
            (write-assembly-output-files!))

        ;; bad way: all the intermediate steps buried inside other functions,
        ;; no longer reachable or inspectable.

        tokenize-source-files-and-generate-ir-and-generate-assembly();
        ```
-   Pushing I/O to edges reduces the need for mocks and stubs


## The Joy of Clojure {#the-joy-of-clojure}

-   Ease of writing short, beautiful code that just works.
-   Simpler syntax frees your brain to think about the problem you want
    to solve.
-   The REPL experience enables developer to stay focused and in flow
    state. The developer experiences joy in REPL driven development.
-   Clojure is a high-level language that gets out of your way. "A
    programming language is low-level when its programs require
    attention to the irrelevant." - Alan Perlis


## Other notes {#other-notes}

-   The brightest days of infrastructure, operations and security are
    still ahead of us. They involve providing platforms to developers
    that hide the complexity of these things and allow the developer to
    focus on the business problem they want to solve.


## Links to other superb resources for more reading {#links-to-other-superb-resources-for-more-reading}

-   Some great blog-posts:
    -   Bryan Cantrell: [A love letter to Rust](https://twitter.com/bcantrill/status/1042180114199375872) (which inspired this post)
    -   John Carmack: [The power of functional programming concepts in C++](http://www.gamasutra.com/view/news/169296/Indepth%5C%5FFunctional%5C%5Fprogramming%5C%5Fin%5C%5FC.php)
    -   Shu Uesugi and Julien Benchetrit: [An Introduction to React in 2019 (For People Who Know Just Enough jQuery To Get By)](https://medium.freecodecamp.org/react-introduction-for-people-who-know-just-enough-jquery-to-get-by-2019-version-28a4b4316d1a)
    -   Uncle Bob Martin: [Why Clojure?](https://blog.cleancoder.com/uncle-bob/2019/08/22/WhyClojure.html)
-   Some great videos:
    -   John Carmack: [Rewriting Wolfenstein 3D using functional programming techniques](https://youtu.be/1PhArSujR%5FA)
    -   Rich Hickey: [Simple Made Easy](https://www.infoq.com/presentations/Simple-Made-Easy/)
    -   Dr. Mihaly Csikszentmihalyi: [TED talk on the flow state](https://www.ted.com/talks/mihaly%5Fcsikszentmihalyi%5Fon%5Fflow?language=en)
    -   Brian Goetz: [Stewardship of the Java ecosystem](https://youtu.be/2y5Pv4yN0b0)
    -   Rafal Dittwald: [Solving problems the Clojure way](https://youtu.be/vK1DazRK%5Fa0)
    -   Bruce Hauman: [Talking about figwheel for Clojurescript development](https://youtu.be/j-kj2qwJa%5FE)
    -   Sean Corfield: [Using the REPL to debug a problem in core.memoize](https://www.youtube.com/embed/UFY2rd05W2g)
    -   Tiago Luchini: [Writing declarative domain models](https://youtu.be/EDojA%5FfahvM)
    -   David Nolen: [Rethinking Identity](https://www.youtube.com/watch?v=77b47P8EpfA)
-   Some great books:
    -   Michael Nygard: `Release it!`
    -   Chas Emerick, Brian Carper, Christophe Grand: `Clojure Programming`
    -   Russ Olsen: `Getting Clojure`
    -   Brian Goetz: `Java Concurrency in Practice`
    -   Martin Fowler: `Refactoring: Improving the design of existing code`
    -   Dr. Mihaly Csikszentmihalyi: `Flow: The psychology of optimal experience`
    -   Daniel Higgenbottom: `Clojure for the brave and true`
    -   Carin Meier: `Living Clojure`
    -   Mark Schwartz: `Seat at the table: IT leadership in the age of agility`
-   Clojure tutorials:
    -   Eric Normand: [Purely Functional](https://purelyfunctional.tv)
    -   Arne Brasseur: [Lambda Island](https://lambdaisland.com)
