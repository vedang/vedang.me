---
title: "Switch Statements in Python"
tags:
- python
date: 2011-08-07T13:21:21+05:30
---

Today I went through some old Python code and noticed this pattern
through out:

{{< highlight python >}}

response, data = somefunc()
if response == "this":
    do_this_with(data)
elif response == "that":
    do_that_with(data)
elif response == "huh":
    duh(data)
;; lots more elifs.
else:
    prevent_horrible_crash(data)

{{< /highlight >}}

This code should ideally have been a switch-case, but Python does not
support a `switch` statement.
<!--more-->

Proponents of OOP believe that `switch` is *bad* - second only to
`goto`. This is not strictly true - both goto and switch can be used
elegantly and with great effect. Goto, for example, is great for
undoing stacked changes and switch's fall-through behavior allows
nicely for 'do things according to the stage I'm at'. However, if
you're doing OOP, consider using polymorphism instead.

I'm not doing OOP, so polymorphism does not apply to me, but I was
looking for a way to optimize this code. I was on a code-cleanup
spree, in the "flow", and I thought, "Why don't I use maps to do
this?". Brilliant! It was a really neat idea. Later I found out that
this is the accepted way of doing switch-case in Python and I'd done
nothing special. So anyway, the code now looked like this:

{{< highlight python >}}

response_map = {"this": do_this_with,
                "that": do_that_with,
                "huh": duh}
response_map.get(response, prevent_horrible_crash)(data)

{{< /highlight >}}

And that would have been that, had I not suddenly developed a
conscience. I had replaced perfectly working, mostly readable code
with some other code. What if my map solution was slower? What if it
was _much slower_? I'd done a sizable amount of refactoring, and I did
not relish throwing it away. I needed to test my solution, so I wrote
some sample code:

{{< gist vedang 1130352 >}}

The results were disheartening. My replacement code was slower -
though only just.

{{< highlight bash >}}

$ python switch-speed.py
if - 2.08906793594
map - 2.88215684891

{{< /highlight >}}

I tweaked the code a little and moved the creation of the map outside
of the `switch_map` function. Python can access local variables faster
than it can access global variables, but I figured a global dict would
still beat the cost of creating the dict every time.

{{< highlight bash >}}

$ python switch-speed.py
if -  2.08955693245
map -  2.00381493568

{{< /highlight >}}

I breathed a sigh of relief. There was a lesson to be learnt here -
test/profile _before_ you optimize!


#### References and articles:

*  More information about switch statements smell - [c2.com](http://c2.com/cgi/wiki?SwitchStatementsSmell)
*  Goto is not evil - [kerneltrap.org](http://kerneltrap.org/node/553/2131)
