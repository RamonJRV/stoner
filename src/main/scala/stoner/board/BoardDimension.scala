package stoner.board

object BoardDimension {
  final val STANDARD_BOARD_DIM : BoardDimension = BoardDimension(STANDARD_COLUMNS,STANDARD_ROWS)
}

case class BoardDimension(val column : Dimension, val row : Dimension)