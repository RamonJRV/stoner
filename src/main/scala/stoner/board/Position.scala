package stoner.board

import stoner.board.BoardDimension.STANDARD_BOARD_DIM

object Position {
  def labelClassToPos(label : Double, boardDim : BoardDimension) = {
    val p = label.toInt
    
    val c = p / boardDim.row
    val r = p - c*boardDim.row
    
    Position(c,r)
  }
}

case class Position(column : Int, row : Int) {
  
  def toLabelClass(boardDim : BoardDimension = STANDARD_BOARD_DIM) =
    (column*boardDim.row + row).toDouble
  
}//end case class Position(column : Int, row : Int)

//31337