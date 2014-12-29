package stoner.board

import scala.annotation.tailrec
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.Range

/**
 * The Board (I mean Borg) is responsible for maintaining and updating the 
 * sequence of Grids that represent a game.  Boards are responsible for 
 * "interpreting" Moves into a Grid transition (delta).   
 */
trait Board {
  
  val grid : Grid
  
  def +(move : Move) : Board
  
  /**
   * Identifies the Position of all stones that are part of the same group as
   * the stone at the given position.
   * @param pos The Position holding the stone
   * @return The Positions (including the pos parameter) of all stones that are
   * the same side as the stone at pos if pos is occupied, empty Set otherwise.
   */
  def identifyGroup(pos: Position) : Set[Position] = {
    
    val side = grid.get(pos)
    
    //if (side == EMPTY) Set[Position]() 
    //else {
      @tailrec
      def idGroupRec(posToSearch: Set[Position],acc: Set[Position]) : Set[Position] = {
        if(posToSearch.isEmpty) acc
        else {
          val h = posToSearch.head
          val t = posToSearch.tail
          
          def goodNeighbor(p: Position) : Boolean =
            grid.get(p) == side && !acc.contains(p) //state farm
            
          idGroupRec(grid.getNeighbors(h).filter(goodNeighbor) ++ t,
                     acc + h)
        }//end else to if(posToSearch.isEmpty)
      }//end def idGroupRec(pos: Position, side: Side, acc: Set[Position])
    
      idGroupRec(HashSet[Position](pos), new HashSet[Position]())
    //}//end else to if (side == EMPTY)
  }//end def findGroup(pos: Position, side: Side)
  
  def liberties(pos : Position) : Set[Position] = 
    grid.getNeighbors(pos).filter((p: Position) => grid.get(p) == EMPTY)
  
  def countLiberties(pos: Position) : Set[Position] = {
    identifyGroup(pos).flatMap(liberties)
  }//end def countLiberties(pos: Position) : Set[Position]
  
  def isAlive(pos: Position) : Boolean = liberties(pos).size > 0
  
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, grid.boardDimension.row)
      } yield Range(0, grid.boardDimension.column).map((c: Int) => posToChar(grid.get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait Board

//31337
