+++
title = "Problems in my brand new System76 Oryx Pro laptop"
author = ["Vedang Manerikar"]
date = 2019-05-31T22:51:00+05:30
lastmod = 2020-03-25T22:54:54+05:30
tags = ["laptops", "system76", "popos", "linux"]
categories = ["tools"]
draft = false
creator = "Emacs 26.1 (Org mode 9.3.2 + ox-hugo)"
toc = true
+++

## **Important Note** {#important-note}

I'm enjoying using a Linux laptop after years of development on the
Macbook, and this post is more a list of issues I personally faced
on moving to Linux after 7 years of development on the Macbook. I'd
written a similar thing when I'd moved to the Macbook for the first
time. Hopefully, this will help someone else moving to this
particular laptop. This is not a "look, linux sux" post. The Oryx
Pro is a great machine, and is a far better experience than the
overall Linux experience I remember from 2012.


## Emacs is unusable due to constant movement of the cursor. {#emacs-is-unusable-due-to-constant-movement-of-the-cursor-dot}

-   The cursor constantly moves to the end of the first line on screen
    for no apparent reason.
    -   I'm convinced the reason that the cursor moves to the end of the
        first line on screen has to do with the position of the
        mouse-pointer. I confirmed this by changing \`mouse-avoidance-mode'
        to \`animate' (from \`banish'). This stops the pointer from being
        moved to the top right corner on key press in Emacs. Now, the
        cursor does not move to the end of the first line like it used to,
        but to some other random place (wherever the mouse pointer
        happened to be last)
-   Repeatedly performing certain key-bindings (like C-n) causes a
    buffer menu to pop up. I have no idea why this happens and how to
    reproduce it / stop it.
    -   Once the menu shows up, repeatedly pressing the same key-binding
        causes it to go away after some time. (This may be true only of
        C-n, which is what triggers this for me the most.)
-   **Update** : Both problems above are due to the awkward placing of the
    touchpad on the System 76 laptops. This touchpad is sensitive and
    registers "ghost clicks" during typing. I confirmed this by
    disabling my touchpad. The problem went away. This is an acceptable
    workaround for me as I don't need the mouse when I'm in Emacs (and
    disabling/enabling the touchpad is straightforward using `xinput`)
    -   `xinput list` will list all the available input devices. Look for
        your touchpad and note the id of the device.
    -   `xinput disable <id>` will disable the device.
    -   `xinput enable <id>` will enable the device.


## The space key is weird. {#the-space-key-is-weird-dot}

-   More than any other key on the laptop, the space key needs a solid
    thump for it to register. This is especially irritating when typing
    out passphrases, since there is no visible feedback.
-   **Update** : Over time, I've gotten used to this and no longer need to
    worry about whether my keypress has registered or not.


## Power management and suspend. {#power-management-and-suspend-dot}


### No "just works" for Suspend. {#no-just-works-for-suspend-dot}

-   Years of using the Mac have trained me to just shut the laptop lid
    when I'm done working. However, with Oryx, there is no predictable
    way to tell whether the battery will continue to be drained or not
    once the lid is down. I've left a fully charged laptop, come back to
    it 4 hours later to find that it was completely drained (to the
    point that it does not start unless you plug it into a power
    source). I have no clue why this happens.
    -   **Update** : Apparently this is famous in the world of System76 as
        the 'suspend black screen of death' bug. :( These are the kind of
        things due to which 'year of the Linux desktop' is a broken
        promise.
-   **Update 2** : Suspend works okayish, it just drains the battery
    faster than I would have expected. I guess I'll have to learn to
    live with this (but it seriously hampers the use of this laptop as a
    carry-around device, which is something I'm grumpy about).
-   **Update 3** : Prolonged use of this laptop really makes me appreciate
    the incredible power management of the Macbook. I love Oryx as a
    development environment, but the Macbook's power management is in a
    whole different class.


### The Power button is weird. {#the-power-button-is-weird-dot}

-   I've gotten into the habit of shutting down my laptop when I'm
    unsure about battery and want to definitely conserve it (example
    when I'm traveling). There is no immediate feedback on pressing the
    power button. Due to this, it's hard to know if the keypress has
    registered or not. This is a small gripe in the big picture, but it
    gets annoying fast.
