package stoner.board

object BoardDimension {
  final val STANDARD_BOARD_DIM : BoardDimension = BoardDimension(STANDARD_COLUMN,STANDARD_ROW)
}

case class BoardDimension(val column : Dimension, val row : Dimension)