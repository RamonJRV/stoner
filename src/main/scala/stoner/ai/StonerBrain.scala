package stoner.ai

import stoner.board.{Board, Move, Side}

trait StonerBrain {
  
  def bestMove(board: Board) : Option[(Move, Side)]

}//end trait StonerBrain

//31337