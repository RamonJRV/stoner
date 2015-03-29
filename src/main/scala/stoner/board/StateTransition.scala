package stoner.board

sealed trait StateTransition {
  val pos: Position
  val side: Side
}//end trait StateTransition

/**
 * A PosFlip is similar to a move except that the various rules associated with
 * a move are not checked; e.g. a PosFlip cannot kill an opponents group and a
 * PosFlip can occur at a ko position.
 * 
 * @param pos The Position to flip the side of
 * @param side The new Side to apply to the grid at pos
 * 
 * @return A Grid representing the state of the grid after the specified flip
 * of Position pos.
 */
case class PosFlip(val pos : Position, val side : Side) extends StateTransition {
  
  //def +(grid : Grid) = grid.set(pos, EMPTY).set(pos, side)
  
}//end case class PosFlip(val pos : Position, val side : Side)

case class Move(pos: Position,side: Side) extends StateTransition

//31337