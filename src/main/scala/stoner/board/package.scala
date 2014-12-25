package stoner

package object board {
  
  type Side = Byte
  
  final val WHITE : Side = -1
  final val BLACK : Side = 1
  final val EMPTY : Side = 0
  
  def posToChar(pos : Side) = {
    if (pos == WHITE) 'W'
    else if (pos == BLACK) 'B'
    else '*'
  }
  
  type Dimension = Byte
  type Column = Dimension
  type Row = Dimension
  
  type BoardDimension = (Column,Row)
  type Position = BoardDimension
  
  final val STANDARD_COLUMN : Column = 19
  final val STANDARD_ROW : Row = 19
  final val STANDARD_BOARD_DIM : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW)
  final val STANDARD_POINT_COUNT = STANDARD_COLUMN.toInt*STANDARD_ROW.toInt
  
  type BoardRepr = Array[Byte]
  
  final val STANDARD_EMPTY_BOARD : Array[Byte] = 
    Array.ofDim[Byte](STANDARD_BOARD_DIM._1*STANDARD_BOARD_DIM._2)
  
}// package object Board

//31337