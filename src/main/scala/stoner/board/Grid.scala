package stoner.board

trait Grid {
  
  type Side = Int
   
  final val WHITE : Side = -1
  final val BLACK : Side = 1
  final val EMPTY : Side = 0
  
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
  
  val boardDimension : BoardDimension
  
  def get(pos: Position) : Side
  
  def get(column : Dimension, row : Dimension) : Side = get(Position(column, row))
  
}//end trait Grid

//31337