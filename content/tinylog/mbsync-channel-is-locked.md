+++
title = "Tinylog: Mbsync: Error: Channel <x> Is Locked"
author = ["Vedang Manerikar"]
date = 2017-10-11T19:22:00+05:30
aliases = ["/techlog/2017-10-11-mbsync-channel-is-locked/", "/blog/mbsync-channel-is-locked/"]
lastmod = 2020-08-22T18:04:34+05:30
tags = ["mbsync"]
categories = ["tinylog"]
draft = false
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
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
