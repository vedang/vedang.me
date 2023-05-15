---
title: "Gitlab Private Pages + Running Docker containers in Gitlab CI/CD Pipelines"
author: ["Vedang Manerikar"]
date: 2022-08-23T10:19:00
slug: "gitlab-private-pages-docker"
tags: ["cicd"]
draft: false
---

## Private pages on Gitlab {#private-pages-on-gitlab}

Did you know that [Gitlab Pages](https://docs.gitlab.com/ee/user/project/pages/) supports private / auth-based static sites? Basically, you add people you want to share your site with as Project Members to the repository. Gitlab then enforces Gitlab Login on the website, so you can only see the content if you are logged into Gitlab (and have been given access to the repository).

This is a very cool way to host private static sites freely (vs, for example, self-hosting + HTTP Basic Auth).


## Moving from Github Actions to Gitlab CI/CD Pipelines (+ running Docker containers in CI/CD scripts) {#moving-from-github-actions-to-gitlab-ci-cd-pipelines--plus-running-docker-containers-in-ci-cd-scripts}

It's ridiculously hard to Google for the right way to run a dockerized tool in CI/CD environments.

Github Actions makes this a nobrainer because their images (example: `ubuntu-latest`) have `docker` clients and servers baked in already. So you just write your command (say `docker run hello-world`) directly into your YAML file and you are done.

Gitlab CI/CD does not have this. They expect you to search + use images from Dockerhub / Gitlab Container Registry. To make matters worse, it isn't enough to use the official Docker in Docker image (which is `docker:latest` btw). Using this image only gives you the `docker` client. You still need to run the `docker` server separately. If you don't, your pipeline will fail with the following error:

```text
docker: error during connect: Post "http://docker:2375/v1.24/containers/create": dial tcp: lookup docker on 169.254.169.254:53: no such host.
```

To run the server, you need to understand the [Services](https://docs.gitlab.com/ee/ci/services/) concept of Gitlab CI/CD. In short, this is a way to run services that your main job might need to access when it runs. You can use it, for example, to run database instances that your test job would connect to. In our case, we need a docker daemon service, which is provided by the `docker:dind` image.

Once you have this, then your entire pipeline will finally run. It took me a frustratingly long time to figure it all out. Here is what my final `.gitlab-ci.yml` file looks like:

```yaml
workflow:
  rules:
    - if: $CI_COMMIT_BRANCH

before_script:
  - mkdir -p .neuron/output && touch .neuron/output/.nojekyll

pages:
  image: docker:latest
  services:
    - docker:dind
  script:
    - docker run -v $PWD/content:/notes sridca/neuron neuron gen --pretty-urls
    - cp -R content/.neuron/output public
  artifacts:
    paths:
      - public
  rules:
    - if: $CI_COMMIT_BRANCH == "master"
```

The `pages` job-name is special. Gitlab understands that it is meant for hosting, and expects HTML / assets in a top-level directory called `public`.

I hope this helps someone else waste less time.
