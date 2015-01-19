package stoner.translate

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.{Vector, DenseVector,SparseVector}

import stoner.board.{Game,Side, BLACK, EMPTY, Grid, StateTransition, Move}

object GameToData {

  def sideToLabel(s : Side) = 
    if (s == BLACK) 1.0
    else 0.0
  
  def gameToArrays(game : Game) : (Array[Array[Double]], Array[Double]) = {
    val gridArrays = game.board.grids.tail.map(_.flattenNumeric).toArray
    
    val label = sideToLabel(game.winner)
      
    (gridArrays, Array.fill(gridArrays.length)(label))
  }//end def gameToArrays(game : Game)
  
  
  def gameToWinnerLabeledPoints(game : Game) : IndexedSeq[LabeledPoint] = {
    
    def isMove(t : StateTransition) = t match {
      case Move(_,_) => true
      case _ => false
    }
    
    val gridAndMove = game.board.grids.dropRight(1)
                                      .zip(game.board.transitions)
                                      .filter(x => isMove(x._2))
                                      
    def transToLabel(t : StateTransition) = t match {
      case Move(p,s) => if(s == game.winner) 1.0 else 0.0
      case _ => 0.0
    }
    
    
    
    gridAndMove.map(x => LabeledPoint(transToLabel(x._2), x._1.flattenSparkVector))
  }//end def gameToLabeledPoints(game : Game) : Array[LabeledPoint]
  
}//end object GameToData

//31337