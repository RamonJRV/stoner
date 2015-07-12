package stoner.translate

import scala.collection.LinearSeq

import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.{Vector, DenseVector,SparseVector}

import stoner.board.{Game,Side, BLACK, EMPTY, Grid, StateTransition, Move,Position, Rotation}

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
    val gridArrays = game.board.grids.tail.map(_.flattenNumeric[Double].toArray).toArray
    
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
   * and the features are the board positions created after a move by the 
   * labeled player.
   * 
   * @see sideToLabel, Grid.flattenSparkVector
   */
  def gameToWinnerLabeledPoints(game : Game) : IndexedSeq[LabeledPoint] = {
    
    def isMove(t : StateTransition) = t match {
      case Move(_,_) => true
      case _ => false
    }
        
    val gridAndMove = 
      Rotation.applyAllRotations(game).flatMap((g : Game) => g.board.grids.drop(1)
                                                                          .zip(game.board.transitions)
                                                                          .filter(x => isMove(x._2)))
                                      
    def transToLabel(t : StateTransition) = t match {
      case Move(p,s) => if(s == game.winner) 1.0 else 0.0
      case _ => 0.0
    }
    
    gridAndMove.map(x => LabeledPoint(transToLabel(x._2), x._1.flattenSparkVector))
  }//end def gameToLabeledPoints(game : Game) : Array[LabeledPoint]
  
  /**
   * Converts a sequence of games into a Seq of org.apache.spark LabeledPoints 
   * where the label is the winner of the game.  This sequence is useful for 
   * passing to a SparkContext to parallelize into an RDD.
   * 
   * @param the games to be converted into a sequence of LabeledPoint values
   * 
   * @return A sequence of LabelPoint where the label is the winner of each game
   * and the features are the board positions created after a move by the 
   * labeled player.
   * 
   * @see sideToLabel, Grid.flattenSparkVector
   */
  def gameToWinnerLabeledPoints(games : IndexedSeq[Game]) : IndexedSeq[LabeledPoint] = 
    games.par.map(gameToWinnerLabeledPoints).flatten.toIndexedSeq
  
  /**
   * Converts a game into a Seq of LabeledPoints created with: 
   * 
   *     label : the next move represented by a Double that is within 0.0 
   *             and col*row.  Note: the values are whole numbers and therefore
   *             can work with multiclass classification.
   *     features : the current positions with 1.0 being the value of "my" 
   *                pieces, 0.0 being empty positions, and -1.0 being the value
   *                of the oponents pieces.
   * 
   * @param the game to be converted into a sequence of LabeledPoint values
   * 
   * @return A sequence of LabeledPoint where the label is the next move given
   * the Grid as a feature represented by the Vector.
   * 
   * @todo FIXME - unit testing, manual testing appears successful.
   */
  def gameToMoveLabeledPoints(game : Game) : LinearSeq[LabeledPoint] = {
    
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
    
    def gridToFeature(side : Side, grid : Grid) = {
      grid.iteratePositions.map(grid.get).map { s =>
        if(s == side) 1.0
        else if(s == side.opposite) -1.0
        else 0.0
      }.toArray
    }
    
    
    gridAndMove.map(x => (transToPosition(x._2), x._1))
               .map(x => LabeledPoint(x._1.pos.toLabelClass(), new DenseVector(gridToFeature(x._1.side, x._2))))
  }//end def gameToLabeledPoints(game : Game) : Array[LabeledPoint]
  
}//end object GameToData

//31337