+++
title = "Tinylog: Mbsync: Error: Channel <x> Is Locked"
author = ["Vedang Manerikar"]
date = 2017-10-11T19:22:00+05:30
lastmod = 2020-03-25T19:33:55+05:30
tags = ["mbsync"]
categories = ["tinylog"]
draft = false
creator = "Emacs 26.1 (Org mode 9.3.2 + ox-hugo)"
+++

You may come across the following error when running `mbsync` :

```text
Error: channel :<channel-name>-remote:<folder>-:<channel-name>-local:<folder> is locked
```

This happens when there is another `mbsync` instance running in
parallel and it has taken a lock on the particular folder. In my case,
I am running `mbsync` via a cron-job, and it tends to leave dead
instances around if the laptop sleeps during a run. Kill all instances
of `mbsync` and restart the process and you should be fine!
