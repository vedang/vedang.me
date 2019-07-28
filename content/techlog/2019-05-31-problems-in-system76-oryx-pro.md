+++
title = "Problems in my brand new System76 Oryx Pro laptop"
author = ["Vedang Manerikar"]
date = 2019-05-31
tags = ["linux", "popos", "system76"]
categories = ["laptops"]
draft = true
toc = true
+++

## Emacs is unusable. {#emacs-is-unusable-dot}

-   the cursor constantly moves to the end of the first line on screen
    for no apparent reason.
    -   I think this might have to do with the 'Disable while typing'
        setting of the touchpad. A pause in typing re-enables the
        touchpad and this causes some sort of mouse event. Since the
        mouse pointer is in the top right corner of the screen in Emacs
        for me, this may be why the cursor keeps jumping to the end of
        the first line on screen.
        -   This theory was proven wrong multiple times while I was typing
            this sentence, since the cursor kept jumping to the top even
            though the setting is disabled right now.
    -   I'm convinced the reason that the cursor moves to the end of the
        first line on screen has to do with the position of the
        mouse-pointer. I confirmed this by changing
        \`mouse-avoidance-mode' to \`animate' (from \`banish'). This stops
        the pointer from being moved to the top right corner on key
        press in Emacs. Now, the cursor does not move to the end of the
        first line like it used to, but to some other random place
        (wherever the mouse pointer happened to be last)
-   repeatedly performing certain key-bindings (like C-n) causes a
    buffer menu to pop up. I have no idea why this happens and how to
    reproduce it / stop it.
    -   Once the menu shows up, repeatedly pressing the same key-binding
        causes it to go away after some time. (This may be true only of
        C-n, which is what triggers this for me the most.)
-   **Update** : Both problems above are due to the awkward placing of
    the touchpad on the System 76 laptops. This touchpad is sensitive
    and registers "ghost clicks" during typing. I confirmed this by
    disabling my touchpad. The problem went away. This is an
    acceptable workaround for me as I don't need the mouse when I'm in
    Emacs (and disabling/enabling this is very straightforward)


## The space key is weird. {#the-space-key-is-weird-dot}

-   More than any other key on the laptop, the space key needs a solid
    thump for it to register. This is especially irritating when
    typing out passphrases, since there is no visible feedback.
-   **Update** : Over time, I've gotten used to this and no longer need
    to worry about whether my keypress has registered or not.


## Power management and suspend. {#power-management-and-suspend-dot}

-   Years of using the Mac have trained me to just shut the laptop lid
    when I'm done working. However, with Oryx, there is no predictable
    way to tell whether the battery will continue to be drained or not
    once the lid is down. I've left a fully charged laptop, come back
    to it 4 hours later to find that it was completely drained (to the
    point that it does not start unless you plug it into a power
    source). I have no clue why this happens.
    -   **Update** : Apparently this is famous in the world of System76 as
        the 'suspend black screen of death' bug. :( These are the kind
        of things due to which 'year of the Linux desktop' is a broken
        promise.
-   **Update** : Suspend works okayish, it just drains the battery
    faster than I would have expected. I guess I'll have to learn to
    live with this (but it seriously hampers the use of this laptop as
    a carry-around device, which is something I'm grumpy about)


### The Power button is weird. {#the-power-button-is-weird-dot}

-   I've gotten into the habit of shutting down my laptop when I'm
    unsure about battery and want to definitely conserve it (example
    when I'm traveling). There is no immediate feedback on pressing
    the power button. Due to this, it's hard to know if the keypress
    has registered or not. This is a small gripe in the big picture,
    but it gets annoying fast.
