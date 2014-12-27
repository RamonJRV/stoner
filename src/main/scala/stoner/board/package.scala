package stoner

package object board {
  
  type Side = Int
  
  final val WHITE : Side = -1
  final val BLACK : Side = 1
  final val EMPTY : Side = 0
  
  def posToChar(pos : Side) = {
    if (pos == WHITE) 'W'
    else if (pos == BLACK) 'B'
    else '*'
  }
  
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
  
  //type BoardDimension = (Column,Row)
  
  
  final val STANDARD_COLUMN : Column = 19
  final val STANDARD_ROW : Row = 19
  
  final val STANDARD_POINT_COUNT = STANDARD_COLUMN.toInt*STANDARD_ROW.toInt

  
}// package object Board

//31337