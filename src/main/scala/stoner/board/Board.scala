package stoner.board

import scala.annotation.tailrec
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.Range

/**
 * The Board (I mean Borg) is responsible for maintaining and update the 
 * sequence of Grids that represent a game.  Boards are responsible for 
 * "interpreting" Moves into a Grid transition (delta).   
 */
trait Board {
  
  val grid : Grid
  
  def +(move : Move) : Board
  
  /**
   * Identifies the position of all stones that are part of the same group as
   * the stone at the given position.
   * @param pos The Position holding the stone
   * @return The Positions (including the pos parameter) of all stones that are
   * the same side as the stone as pos if pos is occupied, empty Set otherwise.
   * @todo Optimize the internal recursive function to be tail recursive.
   */
  def identifyGroup(pos: Position) : Set[Position] = {
    
    val side = grid.get(pos)
    
    if (side == EMPTY) Set[Position]() 
    
    def identifyGroupRec(pos: Position, side: Side, acc: Set[Position]) : Set[Position] = {
      if (acc contains pos) acc
      else {
        Set[Position](pos) | 
        {for(n <- grid.getNeighbors(pos) if (grid.get(n) == side)) 
          yield identifyGroupRec(n, side, acc + pos)}.flatten
      }
    }//end def identifyGroupRec(pos: Position, side: Side, acc: Set[Position])
    
    identifyGroupRec(pos, side, new HashSet[Position]())
  }//end def findGroup(pos: Position, side: Side)
  
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, grid.boardDimension.row)
      } yield Range(0, grid.boardDimension.column).map((c: Int) => posToChar(grid.get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait BoardSpec

//31337
