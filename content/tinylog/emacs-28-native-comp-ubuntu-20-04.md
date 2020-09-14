+++
title = "Compiling and Running Emacs 28 from the native-comp feature branch (Ubuntu 20.04)."
author = ["Vedang Manerikar"]
lastmod = 2020-08-23T12:35:23+05:30
tags = ["emacs"]
categories = ["emacs"]
draft = false
creator = "Emacs 28.0.50 (Org mode 9.3.7 + ox-hugo)"
+++

{{< tweet 1297429392835010565>}}


## The gist of it, building Emacs from Source {#the-gist-of-it-building-emacs-from-source}

```shell-script
git clone https://git.savannah.gnu.org/git/emacs.git
cd emacs
./autogen.sh
./configure --with-nativecomp
# If everything works
make
# Test the binary
src/emacs -Q
# Install the binary
sudo make install
# If you want to re-build from scratch
make distclean
```


## Problems during Installation {#problems-during-installation}


### Configure fails because libgccjit fails the smoketest {#configure-fails-because-libgccjit-fails-the-smoketest}

-   Ensure that libgccjit is installed and at the latest version (at
    this point in time, the latest version is 10)
-   Ensure that gcc is at the same version as libgccjit (also 10)
-   Install the Ubuntu toolchain PPA for the latest versions of gcc and libgccjit.
    -   <https://launchpad.net/~ubuntu-toolchain-r/+archive/ubuntu/ppa>
-   After updating / installing the latest gcc, libgccjit-\* packages,
    you will need to use update-alternatives to ensure that the correct
    tools are being picked.

    ```shell-script
    sudo update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-10 10
    sudo update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-10 10
    sudo update-alternatives --install /usr/bin/g++ g++ /usr/bin/g++-9 9
    sudo update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-9 9
    sudo update-alternatives --config gcc
    sudo update-alternatives --config g++
    ```
-   Ensure that gcc-10 is selected, so that you don't see the 'failed
    smoke test' error message from libgccjit.


### Configure fails because various libraries don't exist on your system. {#configure-fails-because-various-libraries-don-t-exist-on-your-system-dot}

-   Emacs needs a number of different libraries during compilation. You
    should look for an install the `-dev` version of any such libraries
    that are missing. (Eg: `libxpm-dev`, `libgif-dev`, `libtiff-dev`)


## Problems Post Installation {#problems-post-installation}


### You need to generate native compiled files for all your emacs-lisp code {#you-need-to-generate-native-compiled-files-for-all-your-emacs-lisp-code}

-   Add this to your init.el

    ```emacs-lisp
    (if (and (fboundp 'native-comp-available-p)
             (native-comp-available-p))
        (setq comp-deferred-compilation t)
      (message "Native complation is *not* available"))
    ```
-   The message in the above code will also tell you whether the Native
    Compilation binary is correctly built.
-   As a one-time run, you can also execute the following code after  `M-x ielm`

    ```emacs-lisp
    (native-compile-async "/home/<yourname>/.emacs.d" t)
    ```
-   This will create all the necessary `.eln` files for you.


### You need to clean install your packages, preferably the latest versions of the packages {#you-need-to-clean-install-your-packages-preferably-the-latest-versions-of-the-packages}

-   Best to just reinstall all the packages you depend on, to ensure
    that they get compiled properly.


### Start and profit, huge speed boosts. {#start-and-profit-huge-speed-boosts-dot}

-   Very little configuration code broke for me (only one `isearch`
    modification broke, which used `substitute-key-definition` and
    substituted a function which took optional arguments with another
    function which took 0 arguments. This seems to not work within
    native compilation.
