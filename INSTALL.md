INSTALL
=======
After following the below steps there should be a functional Go game research environment.

Prerequisites
-------------

1. Scala (2.10.4)
2. sbt (0.13.7)
3. apache spark cluster (1.4.0) - "spark-shell" application necessary for research scripts to run.
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

To use the code within apache spark run the following comand at the command line:

    spark-shell --jars target/scala-2.10/stoner.jar

Data Set
--------
Buy and unzip the [GoGoD](http://gogodonline.co.uk/) dataset .zip file in the stoner/src/main/resources directory.  Not strictly necessary, but all of the examples in the [wiki](https://github.com/RJRyV/stoner/wiki) will assume that those files exist.

Optional (ScalaIDE)
-------------------
1. Import the git repo you cloned in "Steps": File --> Import --> Git --> Projects from Git --> Existing local repository --> Add --> /the/directory/you/checked/stoner/out/to.
2. Switch the compiler to 2.10.4: Project --> Properties --> Scala Installation : 2.10.4.
2. If you get a bunch of "cross-compilation blah blah blah" errors follow the instructions [here](http://scala-ide.org/docs/current-user-doc/faq/index.html).

Next Steps
----------
See the [Getting Started](https://github.com/RJRyV/stoner/wiki/Getting-Started) section of the [wiki](https://github.com/RJRyV/stoner/wiki) to learn about the stoner research environment.
