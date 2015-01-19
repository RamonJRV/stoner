INSTALL
=======
Currently there is no "production" level install.  There is only a build of the source tree.  

Prerequisites
-------------

1. Scala (2.10.4)
2. sbt (0.13.7)
3. apache spark cluster (1.2.0)
4. Optional: ScalaIDE


Steps
-----
From a unix-ish command line:

    git clone git@github.com:RJRyV/stoner.git
    cd stoner
    sbt
    
Then in sbt:
    
    compile
    test
    package

Optionally for ScalaIDE (in sbt):

    eclipse

Data Set
--------
Buy and unzip the [GoGoD](http://gogodonline.co.uk/) dataset .zip file in the stoner/src/main/resources directory.

Optional (ScalaIDE)
-------------------
1. Import the git repo you cloned in "Steps": File --> Import --> Git --> Projects from Git --> Existing local repository --> Add --> /the/directory/you/checked/stoner/out/to.
2. Switch the compiler to 2.10.4: Project --> Properties --> Scala Installation : 2.10.4.
2. If you get a bunch of "cross-compilation blah blah blah" errors follow the instructions [here](http://scala-ide.org/docs/current-user-doc/faq/index.html).