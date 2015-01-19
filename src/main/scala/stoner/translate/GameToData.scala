package stoner.translate

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.{Vector, DenseVector,SparseVector}

import stoner.board.{Game,Side, BLACK, EMPTY, Grid, StateTransition, Move,Position}

/**
 * Contains useful functions for translating game data to machine learning 
 * compatible data types.
 */
object GameToData {

  /**
   * Converts a Side to a "label".
   * 
   * @param s The Side to convert into a label.
   * 
   * @return The label to be used in machine learning.
   * 
   */
  def sideToLabel(s : Side) = 
    if (s == BLACK) 1.0
    else 0.0
  
  def gameToArrays(game : Game) : (Array[Array[Double]], Array[Double]) = {
    val gridArrays = game.board.grids.tail.map(_.flattenNumeric).toArray
    
    val label = sideToLabel(game.winner)
      
    (gridArrays, Array.fill(gridArrays.length)(label))
  }//end def gameToArrays(game : Game)
  
  
  /**
   * Converts a game into a Seq of org.apache.spark LabeledPoints where
   * the label is the winner of the game.  This sequence is useful for passing 
   * to a SparkContext to parallelize into an RDD.
   * 
   * @param the game to be converted into a sequence of LabelPoint values
   * 
   * @return A sequence of LabelPoint where the label is the winner of the game
   * and the features are the board positions.
   * 
   * @see sideToLabel, Grid.flattenSparkVector
   */
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
  
  /**
   * Converts a game into a Seq of (Move, org.apache.spark.mllib.linalg.Vector).
   * The Move is the next move given a Vector representation of the current
   * move 
   * 
   * @param the game to be converted into a sequence of (Move, Vector)
   * 
   * @return A sequence of (Move, Vector) where the label is the next move given
   * the Grid as a feature represented by the Vector.
   * 
   * @see Grid.flattenSparkVector
   */
  def gameToMoveLabeledPoints(game : Game) : IndexedSeq[(Move,Vector)] = {
    
    def isMove(t : StateTransition) = t match {
      case Move(_,_) => true
      case _ => false
    }
    
    val gridAndMove = game.board.grids.dropRight(1)
                                      .zip(game.board.transitions)
                                      .filter(x => isMove(x._2))
                                      
    def transToPosition(t : StateTransition) = t match {
      case m: Move => m
      case _ => Move(Position(0,0), BLACK)  //never happens because only Moves left
    }
    
    
    
    gridAndMove.map(x => (transToPosition(x._2), x._1.flattenSparkVector))
  }//end def gameToLabeledPoints(game : Game) : Array[LabeledPoint]
  
}//end object GameToData

//31337