---
title: "Tinylog: Mbsync: Error: Channel <x> Is Locked"
author: ["Vedang Manerikar"]
date: 2017-10-11T19:22:00
aliases: ["/techlog/2017-10-11-mbsync-channel-is-locked/", "/blog/mbsync-channel-is-locked/"]
slug: "mbsync-channel-is-locked"
tags: ["mbsync"]
draft: false
---

<div class="ox-neuron-main">
<div class="ox-neuron-article">
<h1 class="ox-neuron-article-heading">Tinylog: Mbsync: Error: Channel <x> Is Locked</h1>
<div class="ox-neuron-article-contents">
You may come across the following error when running `mbsync` :

```text
Error: channel :<channel-name>-remote:<folder>-:<channel-name>-local:<folder> is locked
```

This happens when there is another `mbsync` instance running in parallel and it has taken a lock on the particular folder. In my case, I am running `mbsync` via a cron-job, and it tends to leave dead instances around if the laptop sleeps during a run. Kill all instances of `mbsync` and restart the process and you should be fine!

</div>
</div>
</div>
