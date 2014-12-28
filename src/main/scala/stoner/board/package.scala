package stoner

package object board {
  
  type Side = Int
  
  final val WHITE : Side = -1
  final val BLACK : Side = 1
  final val EMPTY : Side = 0
  
  def otherSide(side : Side) = side * -1
  
  def posToChar(pos : Side) = {
    if (pos == WHITE) 'W'
    else if (pos == BLACK) 'B'
    else '*'
  }
  
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
    
  final val STANDARD_COLUMNS : Column = 19
  final val STANDARD_ROWS : Row = 19
  
  final val STANDARD_POINT_COUNT = STANDARD_COLUMNS.toInt*STANDARD_ROWS.toInt

  
}// package object Board

//31337