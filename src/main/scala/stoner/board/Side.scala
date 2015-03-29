package stoner.board

sealed trait Side {

  def opposite : Side
  
  def toChar   : Char
  def toInt    : Int
  def toDouble : Double = toInt.toDouble
  
}//end trait Side

case object WHITE extends Side {
  override val opposite = BLACK
  
  override val toChar = 'W'
  override val toInt = -1
  
}//end case object WHITE extends Side

case object BLACK extends Side {
  override val opposite = WHITE
  
  override val toChar = 'B'
  override val toInt = 1
}//end case object BLACK extends Side

case object EMPTY extends Side {
  override val opposite = EMPTY
  
  override val toChar = '*'
  override val toInt = 0
}
