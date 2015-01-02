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
    
  /**A sequence of intermediate grid states from the initial empty board to
   * the most current state: grids.last.
   */
  val grids = {
    val zero : Grid = (new CompactGrid(boardDimension))
    
     transitions.scanLeft(zero)((g,t) => t match {
                                  case PosFlip(p,s) => g.set(p,s)
                                  case m: Move => setStoneWithKill(m)})
  }
  
  /**
   * "And you will know my name is the Lord when I lay my vengeance upon you!"
   * - Pulp Fiction
   * 
   * Identifies if the Move m takes the last liberty of the group connected
   * to any of m's neighbors.
   * 
   * @param m The Move that may cause a death of its neighbor(s).
   * 
   * @return The Set of Positions that represent the stones that have been
   *  killed as a result of the placement of Move m. 
   */
  protected[board] def killAMotherfucker(m: Move) : Set[Position] = m match {
     case Move(p,s) => {
       val hypoGrd = grids.last.set(p,s)
       
       grids.last.getNeighbors(p)
                 .filter((p) => hypoGrd.get(p) == otherSide(s))
                 .filter((p) => !hypoGrd.isAlive(p))
     }
   }//end protected[board] def killAMotherfucker(m: Move) : Set[Position] 
  
  /**
   * Sets the stone at the Position specified by move and affect any deaths that
   * are caused as a result.
   * 
   * @param move The Move to apply to the grid.
   * 
   * @return The Grid representing the state change (placement + death) caused
   * by move.
   */
  protected[board] def setStoneWithKill(move : Move) : Grid = {
     val fallenSoldiers = killAMotherfucker(move).flatMap(grids.last.identifyGroup)
     val bodyCount = fallenSoldiers.size
     
     val capturedBlack = 
       if(move.side == WHITE) grids.last.capturedBlack + bodyCount else grids.last.capturedBlack
     
     val capturedWhite = 
       if(move.side == BLACK) grids.last.capturedWhite + bodyCount else grids.last.capturedWhite 
    
     val zero = grids.last
     
     (zero /: fallenSoldiers)(_.set(_, EMPTY)).set(move.pos, move.side) 
     
  }//end override protected[board] def setStonesWithKill(move : Move) : Board
  
  /**
   * Applies the specified PosFlip to the Grid irregardless of whether or not
   * the Position was previously occupied.  DOES NOT affect any killing of 
   * adjoining groups.
   * 
   * @param pf The PosFlip to apply to the Board.
   * 
   * @return A Board representing the new state after apply pf to the current
   * state.
   * 
   * @see +(move: Move) 
   */
  def +(pf : PosFlip) : Board = new Board(transitions :+ pf, boardDimension)
  
  /**
   * Applies the specified Move to the Grid if the move is legal, e.g. it does
   * not represent a ko move and does not set the side of an occupied Position.
   * 
   * @param move The Move to try to apply to the Board
   * 
   * A new Board representing the updated state of the Board after the move if
   * it was legal, None otherwise.
   */
  def +(move : Move) : Option[Board] = 
     if(isKo(move) || grids.last.get(move.pos) != EMPTY) None
     else Some(new Board(transitions :+ move, boardDimension))
  
  /**
   * Determines whether or not the given move is a ko move of the game.
   * 
   * @param move The move to decide if it is a ko.
   * 
   * @return True if the given move would be a ko (Japanese style), false 
   *  otherwise.
   */
  def isKo(move : Move) : Boolean = 
    !grids.find(_ == setStoneWithKill(move)).isEmpty
  
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
