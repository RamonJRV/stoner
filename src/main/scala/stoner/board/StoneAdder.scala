package stoner.board

object StoneAdder {
  
  def applyMovesToBoard(board : BoardSpec, moves : IndexedSeq[Move]) : BoardSpec =
    moves.foldLeft(board)(_ + _)

}