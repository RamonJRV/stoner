package stoner.board

object StoneAdder {
  
  def applyMovesToBoard(board : Board, moves : IndexedSeq[Move]) : Board =
    moves.foldLeft(board)(_ + _)

}