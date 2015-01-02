stoner (as in [Go stone](http://en.wikipedia.org/wiki/Go_equipment#Stones), you [hippy](http://i.ytimg.com/vi/CWxgfTMLtc0/maxresdefault.jpg))
==================================
A research project to develop an [automated](http://en.wikipedia.org/wiki/Artificial_intelligence) [Go](http://en.wikipedia.org/wiki/Go_(game)) player utilizing machine learning techniques rather than typical tree descent searches.  The primary language will be [Scala](http://www.scala-lang.org).

![](https://lh3.googleusercontent.com/_B1UBiW-spfA/TPMDzWlZQpI/AAAAAAAACRs/UNc7Qflkf9w/TNG%2B54%2BBooby%2BTrap%2BData%2B-%2BWesley.jpg)

Problem Formulation
-------------------
The problem I wish to solve is as follows:

* Given a Go board position **P**, without accompanying game history except for illegal moves due to ko, find the move **M** that maximizes the probability of a resulting game win.

I got 99 problems but optimal move estimator given current position state representation ain't one! (after I finish this project)

Techniques
----------
Instead of writing another run-of-the-mill monte carlo game tree descent search player I want to focus on recent research in machine learning (see section: Prior Art).  

Some research threads I want to focus on are:

1. Most recent research in the ML game player field has focused on training a computer player to predict an "expert" player's next move, e.g. don't create a smart game player just make one that mimics a smart player.  I,however, want to create a player that predicts which side will win given a position.  To turn this oracle into a game player: search over all legal moves **{M}** for a position **P**, and predict the opponents probability of winning given the position resulting from each move (**P** + **m_i**), then choose the move that results in the lowest probability of winning for the opponent.  I think NNs and SVMs would work well.

2. Using ML predictors as a pruners for regular monte carlo tree searchers.

3. Break up the game into move count stages. First 2% - 10% database lookup, 10% - 80% use ML, final 20% use regular monte cristo (carlo).   

4. Exploit AWS cluster deployment to create different "grades" of player.  If you want to turn the dial up to 11 you can distribute the player across several AWS nodes (possibly using [EMR](http://aws.amazon.com/elasticmapreduce/)).  Or, for all the n00bs, just have the player deploy to cores on the local host.  This would allow for limitless player strength expansion simply by adding hardware: can I get a skynet up in this piece!!!


![](http://cdn.screenrant.com/wp-content/uploads/terminator-5-release-date-new-trilogy.jpg)


Language
--------
Why functional programming you ask??? 

![](http://imgs.xkcd.com/comics/functional.png)

Because I'm trying to make the '60s cool again (free love man).  Seriously: because I want a whole lotta cuncurrency (see section below) and I think Scala gets me there the quickest.

Also, I think recursive functions will greatly simplify things like [dragon](https://github.com/RJRyV/stoner/blob/master/src/main/scala/stoner/board/Board.scala), [super-ko](https://github.com/RJRyV/stoner/wiki/Why-Scala-is-Perfect-For-Go), & death identification.

Concurrency Models
------------------
Let's make some concurrency jambalaya: all kinds of spices and flavors mixed together.  I would like to use:

1. data parallelism: use cuDNN for GPU Neural networks.
2. actor thread model: [Akka](http://akka.io) (god bless you) actors for multi-core & multi-chip parallelism.
3. lambda architecture: Did somebody say "jump on the bandwagon"?  I wanna use [Spark](https://spark.apache.org) to distribute some ML work.

Prior Art
---------
My automata sensei are:

1. Christopher Clark, Amos Storkey [Teaching Deep Convolutional Neural Networks to Play Go](http://arxiv.org/abs/1412.3409).  2014
2. Arpad Rimmel, Olivier Teytaud, Chang-Shing Lee, Shi-Jim Yen, Mei-Hui Wang, et al.. [Current Frontiers in Computer Go](https://hal.inria.fr/inria-00544622/PDF/ct.pdf). IEEE Transactions on Computational Intelligence and Artificial Intelligence in Games, IEEE, 2010, in press. <inria-00544622>
3. Martin Wistuba, Lars Schmidt-Thieme [Move Prediction in Go - Modelling Feature Interactions Using Latent Factors](http://www.ismll.uni-hildesheim.de/pub/pdfs/wistuba_et_al_KI_2013.pdf)

Training Data Set
-----------------
*"Life can only be understood backwards; but it must be lived forwards." - Kierkegaard*

The primary data set for training and evaluation will be the [GoGoD](http://gogodonline.co.uk/) dataset.  It is freakin' awesome, the earliest game is from 196 AD!  Unfortunately, as with most things in life, it ain't free (see INSTALL.md).



-- A 31337 Production -- 
