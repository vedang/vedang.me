+++
title = "L Peter Deutsch: The Fallacies of Distributed Computing"
author = ["Vedang Manerikar"]
lastmod = 2020-08-22T18:11:52+05:30
tags = ["wisdom", "distributed-systems"]
categories = ["wisdom"]
draft = false
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
+++

_Snippet from: [The Wikipedia Article](https://en.wikipedia.org/wiki/Fallacies%5Fof%5Fdistributed%5Fcomputing), along with my own notes_

The network is reliable
: Think about error-handling on network
    failures. Use the following patterns: Timeouts, Retries, Circuit
    breakers, Graceful Degradation.


Latency is zero
: Think about bandwidth requirements and packet
    loss. Use the following patterns: Small payloads, Compression,
    Asynchronous Communication, Perceived Progress, Timeouts, Graceful
    Degradation.


Bandwidth is infinite
: The same as above. Think explicitly about
    bottlenecks in Network traffic. Use the following patterns: Caching,
    Etags.


The network is secure
: Think about encryption, compliance, data
    risk. Understand the trade-offs with speed and simplicity.
    Prioritize security over the network for any sensitive data.


Topology doesn't change
: Think about local state, tolerance for
    staleness, co-ordination overheads, recovery mechanisms


There is one administrator
: Remember to test the end-to-end flow
    and think through production components not controlled by you.


Transport cost is zero
: Think through data transfer costs in cloud
    environments.


The network is homogeneous
: Think about connection drops,
    reconnects, client timeouts. Use the following patterns:
    Idempotency, Eventual Consistency


Plan for the fallacies in your benchmarks and testing.
