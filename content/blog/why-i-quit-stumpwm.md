+++
title = "Why I Quit StumpWM"
author = ["Vedang Manerikar"]
date = 2011-08-04T13:41:00+05:30
aliases = ["/techlog/2011-08-04-why-i-quit-stumpwm/"]
lastmod = 2020-08-22T18:04:34+05:30
tags = ["tools", "window-managers", "stumpwm"]
categories = ["tools"]
draft = false
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
toc = true
+++

After using [StumpWM](<http://www.nongnu.org/stumpwm/>) for nearly a
year, I'm finally quitting and moving back to Gnome. I came to love
StumpWM's minimalistic approach, and this isn't an easy decision. So
why am I doing it?

<!--more-->


## Pinky Hell {#pinky-hell}

I am (was) a heavy user of Emacs, Tmux and StumpWM. These programs
rely heavily on the `Ctrl` key. This means that the `Ctrl` key and
my left pinky finger are pretty much destroyed at this point. With
the spectre of RSI looming over my head, StumpWM is the easiest
tool to get rid off. I might have less screen-space now, but my
fingers will thank me for it.


## What you gain in Functionality, you lose in Integration {#what-you-gain-in-functionality-you-lose-in-integration}

StumpWM is infinitely configurable. If you have the patience, you
can get it to do things that are simply impossible with other
Windows Managers. For example, there is a `mpd.lisp` module inside
the contrib directory which converts StumpWM into a MPD client. A
<span class="underline">full-fledged client</span>. This means that all music operations are
entirely unobtrusive and <span class="underline">always</span> at your beck and call. MPD with
StumpWM has without a doubt been the best music setup I've ever
used.

However, I missed Gnome's integration with Ubuntu - Ubuntu One,
Gwibber, Pidgin, event notifications. Even if I <span class="underline">could</span> configure
StumpWM for these things, there was too much effort involved.


## Dead processes, High temperatures {#dead-processes-high-temperatures}

<span class="underline">Disclaimer</span> : Everything I'm about to say now is conjecture.
There is no proof that StumpWM is responsible for any of this.

At 5 days uptime, my system load was touching 2, and my system
temperature was at 65 degrees C.
[KK](http://about.me/kiran%5Fkulkarni/) had a similar number of
programs running on his system - this is a guy who uses
full-blown [compiz](http://www.compiz.org/) effects - his system
temperature was 36 degrees and load average was around 1.15.
Furthermore, he had a 48 day uptime. KK gleefully told me that it
had to be StumpWM that was the problem! He was kidding, but some
inquiry around the office showed that my laptop was much worse
than the other guys on Ubuntu.

I also found that Chrome was leaving behind loads of defunct
processes on my machine. There were 41 defunct chromium-browser
processes when I decided to reboot the laptop and do some
monitoring. Within an hour of the reboot, I had 7 defunct
processes, CPU temperature was at 45 degrees and the load average
was hovering around 1.8. That was the proverbial straw that broke
the camel's back. I switched back to Gnome. Later in the day, I
thought about actually investigating the behavior of my laptop,
but it's been behaving well on Gnome. As I write, I have a day's
uptime, a load average of 1.03 and CPU temperature at 37.5
degrees C. Unscientific, but fine by me.


## How I got over the Withdrawal Symptoms: {#how-i-got-over-the-withdrawal-symptoms}

Of all the StumpWM features, there was just one that I simply could
not do without - 'run-or-raise'. On StumpWM, you can define hot-keys
to behave as follows:

1.  Launch the program if it is not running
2.  Move focus to the program if it is already running.

I use one Emacs instance, one terminal instance (tmux'ed) and one
Chrome instance. I <span class="underline">need</span> to be able to access them with minimal
friction. Turns out, a little hackery on compiz is all we need -
`compiz-send.py` is a nifty little script that sends messages to DBus
and interacts with compiz. I can use it along with `xdtool` to
implement my run-or-raise behavior. Here is a look at my
`run-or-raise-emacs.sh` script:

{{< gist vedang 1130303 >}}

You can download `compiz-send` from [here](http://wiki.compiz.org/Plugins/Dbus?action=AttachFile&do=get&target=compiz-send.py).

So there you have it. At the moment, life is back to Gnome+Compiz. If
you know any nifty hacks for Gnome+Compiz, please let me know in the
comments.


## References: {#references}

[wiki.compiz.org](http://wiki.compiz.org/Plugins/Dbus)
