package stoner.board

import scala.annotation.tailrec

import scala.collection.GenTraversableOnce
import scala.collection.immutable.Set
import scala.collection.immutable.HashSet
import scala.Range

object Board {
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
   *  
   */
  protected[board] def killAMotherfucker(m: Move, grid: Grid) : Set[Position] = m match {
     case Move(p,s) => {
       val hypoGrd = grid.set(p,s)
       
       grid.getNeighbors(p)
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
   * 
   */
  protected[board] def setStoneWithKill(move : Move, grid: Grid) : Grid = {
     val fallenSoldiers = killAMotherfucker(move,grid).flatMap(grid.identifyGroup)
     val bodyCount = fallenSoldiers.size
     
     val capturedBlack = 
       if(move.side == WHITE) grid.capturedBlack + bodyCount 
       else grid.capturedBlack
     
     val capturedWhite = 
       if(move.side == BLACK) grid.capturedWhite + bodyCount 
       else grid.capturedWhite 
     
     (grid /: fallenSoldiers)(_.set(_, EMPTY)).set(move.pos, move.side) 
                                              .setCapturedBlack(capturedBlack)
                                              .setCapturedWhite(capturedWhite)
     
  }//end override protected[board] def setStonesWithKill(move : Move) : Board

  /**
   * Determines whether or not the specified Move is a suicide move.  A suicide
   * is the placement of a stone that doesn't kill any of its neighbors but
   * has zero liberties after placing the stone on the board.
   * 
   * @param move The move to test "suicide-ality" of 
   * 
   * True if the given move would result in suicide, false otherwise.
   */
  def isSuicide(move : Move, grid : Grid) = 
    setStoneWithKill(move, grid).liberties(move.pos) == 0
  
  def isEmpty(pos : Position, grid : Grid) = grid.get(pos) == EMPTY
  
}//end object Board

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
                                 case m: Move => Board.setStoneWithKill(m,g)})
  }//end val grids =
  
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
   * Determines whether or not the specified Move is a suicide move.  A suicide
   * is the placement of a stone that doesn't kill any of its neighbors but
   * has zero liberties after placing the stone on the board.
   * 
   * @param move The move to test "suicide-ality" of 
   * 
   * True if the given move would result in suicide, false otherwise.
   */
  def isSuicide(move : Move) = 
    Board.setStoneWithKill(move, grids.last).liberties(move.pos) == 0
  
  /**
   * Determines whether or not the given Position is EMPTY.
   * 
   * @param pos The Position to check emptiness for.
   * 
   * @return True if the given Position is empty, false otherwise.
   */
  def isEmpty(pos : Position) = Board.isEmpty(pos, grids.last)
  
  /**
   * Determines if the given move is a legl move according to the rules of Go.
   * 
   * @param move The move the test legality of
   * 
   * True if the given move is legal, false otherwise.
   */
  def isLegalMove(move : Move) =
    (Board.isEmpty(move.pos, grids.last) &&
     !isKo(move) && 
     !Board.isSuicide(move, grids.last))
     
  /**
   * Determines whether or not the given move is a ko move of the game.
   * 
   * @param move The move to decide if it is a ko.
   * 
   * @return True if the given move would be a ko (Japanese style), false 
   *  otherwise.
   *  
   * @todo FIXME - unit testing
   */
  def isKo(move : Move) = 
    grids.exists(_ == Board.setStoneWithKill(move, grids.last))
  
  
  /**
   * Applies the specified Move to the Grid if the move is legal, e.g. it does
   * not represent a ko move and does not set the side of an occupied Position.
   * 
   * @param move The Move to try to apply to the Board
   * 
   * A new Board representing the updated state of the Board after the move if
   * it was legal, None otherwise.
   * 
   * @todo FIXME - unit testing
   */
  def +(move : Move) : Option[Board] = 
     if(isLegalMove(move)) Some(new Board(transitions :+ move, boardDimension))
     else None
  
  
  
  /**
   * Traverses the posManipulationSeq to apply the given PosFlips, from lowest
   * order position to highest, to the internal Grid state.
   * 
   * @param posManipulationSeq A position manipulation sequence specify the 
   *  steps to adjust the grid.
   *  
   * @return A Board with the internal grid representing the changees specificied
   *  in posManipulationSeq.
   *  
   * @todo FIXME - unit testing 
   */
  def setStones(posManipulationSeq: GenTraversableOnce[PosFlip]) : Board = 
     new Board(transitions ++ posManipulationSeq, boardDimension)
  
  /**
   * Affects the given PosFlip onto the internal grid.
   * 
   * @param pf The PosFlip to apply to the grid
   * 
   * @return A Board with the updated grid representation.
   * 
   * @todo FIXME - unit testing
   */
  def setStone(pf : PosFlip) = setStones(Array(pf))
 
  
  /**
   * Prints a pretty representation of the Board.
   */
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, boardDimension.row)
      } yield Range(0,boardDimension.column).map((c: Int) => posToChar(grids.last.get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait Board

//31337
