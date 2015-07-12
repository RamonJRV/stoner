package stoner.board

import stoner.board.BoardDimension.STANDARD_BOARD_DIM

case class Position(column : Int, row : Int) {
  
  def toLabelClass(boardDim : BoardDimension = STANDARD_BOARD_DIM) =
    (column*boardDim.row + row).toDouble
  
}//end case class Position(column : Int, row : Int)

//31337