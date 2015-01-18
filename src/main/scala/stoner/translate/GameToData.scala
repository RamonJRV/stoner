package stoner.translate

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import stoner.board.{Game,BLACK}

object GameToData {

  def gameToArrays(game : Game) : (Array[Array[Float]], Array[Float]) = {
    val gridArrays = game.board.grids.tail.map(_.flattenNumeric).toArray
    
    val winner = 
      if (game.winner == BLACK) 1.0f
      else 0.0f
      
    (gridArrays, Array.fill(gridArrays.length)(winner))
  }//end def gameToArrays(game : Game)
  
  
}//end object GameToData

//31337