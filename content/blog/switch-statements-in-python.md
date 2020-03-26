+++
title = "Switch Statements in Python"
author = ["Vedang Manerikar"]
date = 2011-08-07T13:21:00+05:30
lastmod = 2020-03-26T20:55:13+05:30
tags = ["python"]
categories = ["programming"]
draft = false
creator = "Emacs 26.1 (Org mode 9.3.2 + ox-hugo)"
+++

Today I went through some old Python code and noticed this pattern
through out:

```python
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
```

This code should ideally have been a switch-case, but Python does not
support a `switch` statement.

<!--more-->

Proponents of OOP believe that `switch` is **bad** - second only to
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

```python
response_map = {"this": do_this_with,
                "that": do_that_with,
                "huh": duh}
response_map.get(response, prevent_horrible_crash)(data)
```

And that would have been that, had I not suddenly developed a
conscience. I had replaced perfectly working, mostly readable code
with some other code. What if my map solution was slower? What if it
was <span class="underline">much slower</span>? I'd done a sizable amount of refactoring, and I did
not relish throwing it away. I needed to test my solution, so I wrote
some sample code:

```python
from timeit import Timer
from random import randint


def switch_if():
    value = randint(1, 10)
    if value == 1:
        return '1'
    elif value == 2:
        return '2'
    elif value == 3:
        return '3'
    elif value == 4:
        return '4'
    elif value == 5:
        return '5'
    elif value == 6:
        return '6'
    elif value == 7:
        return '7'
    elif value == 8:
        return '8'
    elif value == 9:
        return '9'
    else:
        return '10'


def switch_map():
    value = randint(1, 10)
    smap = {1: '1',
            2: '2',
            3: '3',
            4: '4',
            5: '5',
            6: '6',
            7: '7',
            8: '8',
            9: '9',
            10: '10'}
    return smap[value]


t = Timer(setup='from __main__ import switch_if', stmt='switch_if()')
print "if - ", t.timeit()

t = Timer(setup='from __main__ import switch_map', stmt='switch_map()')
print "map - ", t.timeit()
```

The results were disheartening. My replacement code was slower -
though only just.

```text
$ python switch-speed.py ;=>
;=> if  - 2.08906793594
;=> map - 2.88215684891
```

I tweaked the code a little and moved the creation of the map outside
of the `switch_map` function. Python can access local variables faster
than it can access global variables, but I figured a global dict would
still beat the cost of creating the dict every time.

```text
$ python switch-speed.py ;=>
;=> if  -  2.08955693245
;=> map -  2.00381493568
```

I breathed a sigh of relief. There was a lesson to be learnt here -
test/profile <span class="underline">before</span> you optimize!
