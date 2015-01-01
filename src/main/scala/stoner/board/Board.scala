package stoner.board

import scala.annotation.tailrec

import scala.collection.GenTraversableOnce
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.Range

/**
 * The Board (I mean Borg) is responsible for maintaining and updating the 
 * sequence of Grids that represent a game.  Boards are responsible for 
 * "interpreting" Moves into a Grid transition (delta).   
 */
class Board(val transitions : IndexedSeq[StateTransition] = Array[StateTransition](),
            val boardDimension : BoardDimension = BoardDimension.STANDARD_BOARD_DIM) {
    
  val grids : Array[Grid] = {
    val zero : Grid = (new CompactGrid(boardDimension))
    
     transitions.scanLeft(zero)((g,t) => t match {
                                  case PosFlip(p,s) => g.set(p,s)
                                  case m: Move => setStoneWithKill(m)}).toArray
  }
  
  
  protected[board] def killAMotherfucker(m: Move) : Set[Position] = m match {
     case Move(p,s) => {
       val hypoGrd = grids.last.set(p,s)
       
       grids.last.getNeighbors(p)
                 .filter((p) => hypoGrd.get(p) == otherSide(s))
                 .filter((p) => !hypoGrd.isAlive(p))
     }
   }//end protected[board] def killAMotherfucker(m: Move) : Set[Position] 
  
  /**
   * @todo document this function
   * @todo FIXME - unit testing
   */
  protected[board] def setStoneWithKill(move : Move) : Grid = {
     val fallenSoldiers = killAMotherfucker(move).flatMap(grids.last.identifyGroup)
     val bodyCount = fallenSoldiers.size
     
     val capturedBlack = 
       if(move.side == WHITE) grids.last.capturedBlack + bodyCount else grids.last.capturedBlack
     
     val capturedWhite = 
       if(move.side == BLACK) grids.last.capturedWhite + bodyCount else grids.last.capturedWhite 
    
     val zero = grids.last
     
     //i try very hard not to let my code go beyond 80 chars per line but the 
     //below expression was too beautiful to break up
     (zero /: fallenSoldiers)((g:Grid, p: Position) => g.set(p, EMPTY)).set(move.pos, move.side) 
  }//end override protected[board] def setStonesWithKill(move : Move) : Board
  
  def +(pf : PosFlip) : Board = new Board(transitions :+ pf, boardDimension)
  
  /**
   * @todo document this function
   * @todo FIXME - unit testing
   */
  def +(move : Move) : Option[Board] = 
     if(isKo(move) || grids.last.get(move.pos) != EMPTY) None
     else Some(new Board(transitions :+ move, boardDimension))
  
  /**
   * @todo document this function
   * @todo FIXME - unit testing
   */
  def isKo(move : Move) : Boolean =             
    !grids.find(_ == grids.last.set(move.pos, move.side)).isEmpty
  
  /**
   * Traverses the posManipulationSeq to apply the given PosFlips, from lowest
   * order position to highest, to the internal Grid state.
   * 
   * @param posManipulationSeq A position manipulation sequence specify the 
   *  steps to adjust the grid.
   *  
   * @return A Board with the internal grid representing the changees specificied
   *  in posManipulationSeq. 
   */
  def setStones(posManipulationSeq: GenTraversableOnce[PosFlip]) : Board = 
     new Board(transitions ++ posManipulationSeq, boardDimension)
  
  /**
   * Affects the given PosFlip onto the internal grid.
   * 
   * @param pf The PosFlip to apply to the grid
   * 
   * @return A Board with the updated grid representation.
   */
  def setStone(pf : PosFlip) = setStones(Array(pf))
 
  
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, boardDimension.row)
      } yield Range(0,boardDimension.column).map((c: Int) => posToChar(grids.last.get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait Board

//31337
