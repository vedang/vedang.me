---
title: "L Peter Deutsch: The Fallacies of Distributed Computing"
author: ["Vedang Manerikar"]
date: 2020-08-11T10:15:00
aliases: ["/notes/l-peter-deutsch-the-fallacies-of-distributed-computing"]
slug: "l-peter-deutsch-the-fallacies-of-distributed-computing"
tags: ["distributed-systems", "wisdom"]
categories: ["notes"]
draft: false
---

_Snippet from: [The Wikipedia Article](https://en.wikipedia.org/wiki/Fallacies_of_distributed_computing), along with my own notes_

The network is reliable
: Think about error-handling on network failures. Use the following patterns: Timeouts, Retries, Circuit breakers, Graceful Degradation.


Latency is zero
: Think about bandwidth requirements and packet loss. Use the following patterns: Small payloads, Compression, Asynchronous Communication, Perceived Progress, Timeouts, Graceful Degradation.


Bandwidth is infinite
: The same as above. Think explicitly about bottlenecks in Network traffic. Use the following patterns: Caching, Etags.


The network is secure
: Think about encryption, compliance, data risk. Understand the trade-offs with speed and simplicity. Prioritize security over the network for any sensitive data.


Topology doesn't change
: Think about local state, tolerance for staleness, co-ordination overheads, recovery mechanisms for split brains, inability to reach endpoints.


There is one administrator
: Remember to test the end-to-end flow and think through production components not controlled by you.


Transport cost is zero
: Think through data transfer costs in cloud environments. Monitor payload sizes.


The network is homogeneous
: Think about connection drops, reconnects, client timeouts. Use the following patterns: Idempotency, Eventual Consistency
