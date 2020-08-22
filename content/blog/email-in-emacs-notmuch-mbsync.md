+++
title = "Setting up email in Emacs using notmuch and mbsync"
author = ["Vedang Manerikar"]
lastmod = 2020-08-22T18:04:33+05:30
tags = ["tools", "mbsync", "notmuch", "emacs", "email"]
categories = ["tools"]
draft = true
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
+++

## Installing notmuch {#installing-notmuch}

-   sudo apt install notmuch


### Create the notmuch config file {#create-the-notmuch-config-file}


### Load notmuch.el when running emacs {#load-notmuch-dot-el-when-running-emacs}


## Installing mbsync {#installing-mbsync}

-   sudo apt install isync


### Create the mbsync config file {#create-the-mbsync-config-file}


## Installing msmtp {#installing-msmtp}

-   sudo apt install msmtp


### Create the msmtp config file {#create-the-msmtp-config-file}


### Set the sendmail program to msmtp in Emacs {#set-the-sendmail-program-to-msmtp-in-emacs}


## Create a script to sync email and index it. {#create-a-script-to-sync-email-and-index-it-dot}

-   nmgetmail.sh
-   install a cron for this.
