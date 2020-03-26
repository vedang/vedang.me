+++
title = "Lazy Weekend Reading: What is User Story Mapping?"
author = ["Vedang Manerikar"]
lastmod = 2020-03-26T08:00:34+05:30
tags = ["project-management"]
categories = ["notes"]
draft = true
creator = "Emacs 26.1 (Org mode 9.3.2 + ox-hugo)"
+++

This post compiles notes from the following resources by Jeff Patton,
the author of [User Story Mapping](https://www.amazon.in/User-Story-Mapping-Jeff-Patton/dp/935110897X/):

1.  His Youtube video: [Why Documents Fail](https://www.youtube.com/watch?v=a--xbF%5FWGyM)
2.  His blog-post on the topic: [The New User Story Backlog is a Map](https://www.jpattonassociates.com/the-new-backlog/)

I'm reading through the book right now and it's great. These resources
convinced me that the book was worth purchasing. Here are my notes:


## The problem with documents: {#the-problem-with-documents}

-   Specifying in writing does not work well, it's open to
    misinterpretation.
-   Shared documents are not sharing all the information. What is the
    story?

> "Stop sharing documents and tell me the story!" ~ Jeff Patton.


## Stories: {#stories}


### What is a Story? {#what-is-a-story}

> "If you can tell stories about what the software does and generate
> energy and interest and a vision in your listener's mind, then why not
> tell stories before the software does it?"
> ~ Kent Beck.

-   Stories get their name from **how we use them**, not how we write them
    down.
    -   Related: Tell stories, don't just write them down.
-   Talk about the outcome, not just the output.
-   Focus discussion and collaboration around who will use and benefit
    from the thing you are building, and what's going to happen "later"
    after the delivery.
-   The release is not the celebration, the "later" is the celebration.
-   Use simple visualizations to anchor the stories you tell.


### Basics of developing a Story {#basics-of-developing-a-story}

-   **Card**: User writes what they want on a card.
-   **Conversation**: Developer figures out the details of what they need
    to build, by talking to the User.
-   **Confirmation**: Developers may get it wrong, but since it's a
    conversation, they can always clarify their understanding and
    iterate.


## How to have Conversations: {#how-to-have-conversations}

-   When we have a shallow discussion, we may all take away something
    different.
-   The way to detect differences in understanding are by externalizing
    our thinking with words and pictures.
-   When we combine and refine, we arrive at something better.
-   Afterwards, when we say the same thing, we actually mean it.
-   What you record in conversations works like a vacation photo.
    Looking at it helps you remember details that are not in the photo.
    -   The best documents use words and pictures to help us recall our
        conversations. They don't replace the conversations.
-   If you replace a conversation with a document, you've stopped using
    stories.
    -   Start with shared understanding
    -   Spread shared understanding with discussion
    -   Build software equipped with shared understanding
    -   Reading through documents alone cannot make this happen, this is
        where the end result does not match our expectations.


## Story Maps: {#story-maps}


### What is a Story Map? {#what-is-a-story-map}

-   Stories are not enough by themselves.
    -   You can't build things one little thing at a time.
    -   Arranging stories in the order you'll build them doesn't explain
        to others what the system does.
-   You need to tell the whole story of the product and understand the
    whole system!
-   A Story Map helps organize the discussion about the user's
    experience with our product.
    -   Build story maps in small collaborative groups.
    -   Use mapping to break down big stories and to help find small
        viable solutions.
-   Story maps build effective stories, and effective stories connect
    everyone to the purpose of your product.


### How do you build a Story Map? {#how-do-you-build-a-story-map}

-   The top most stories are the "big stories" or "user activities".
-   Smaller stories ("user tasks") are arranged under the big stories in
    a grid form.
-   If the person using the system does one thing after the other, put
    the early thing on the left and the later thing on the right. The
    order in which you'd explain the system to someone else is the
    correct order of activities.
    -   The map should help you tell the story.


### Your Story Map is ready. Now what? {#your-story-map-is-ready-dot-now-what}

-   Once your map is ready, walk through it with a user / stakeholder /
    developer. Does the whole story work? This exercise will help you
    find things that you've missed.
    -   Missed steps.
    -   Pain points and "real problems".
    -   Correct sizing of stories and their place in the map.
-   Once your map is ready, it will show you the backbone of your
    system. This is the list of big things at the top, these are
    essential to your system.
    -   You don't prioritize the backbone, it just "is". The backbone is
        the list of the most basic things that your system needs.
    -   Prioritize the stories hanging down from the backbone. Higher ==
        absolutely necessary. The list of stories placed high describe the
        smallest possible system you could build that would give you
        end-to-end functionality. This is the "skeleton" of your system.
-   Keep the story map displayed and easily viewable at all times. It
    should become the constant point of discussion about the product you
    are building.
-   Your product is built and you want to add a new feature. Now what?
    -   Build a story map for your new feature
