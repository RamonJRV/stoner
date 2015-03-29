package stoner

package object board {
    
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
    
  final val STANDARD_COLUMNS : Column = 19
  final val STANDARD_ROWS : Row = 19
  
  final val STANDARD_POINT_COUNT = STANDARD_COLUMNS.toInt*STANDARD_ROWS.toInt
  
}// package object board

//31337