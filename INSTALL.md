INSTALL
=======
Currently there is no "production" level install.  There is only a build of the source tree.  

Prerequisites
-------------

1. Scala (2.11.4)
2. sbt (0.13.7)
3. Optional: ScalaIDE

Steps
-----
From a unix-ish command line:

    git clone git@github.com:RJRyV/stoner.git
    cd stoner
    sbt
    
Then in sbt:
    
    compile

Optionally (in sbt):

    eclipse

Data Set
--------
Buy and unzip the [GoGoD](http://gogodonline.co.uk/) dataset .zip file in the stoner/src/main/resources directory.
