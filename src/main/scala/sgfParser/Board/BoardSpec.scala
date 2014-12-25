package sgfParser.Board

trait BoardSpec {
  
  final val WHITE_OCCUPIED : Byte = -1
  final val BLACK_OCCUPIED : Byte = 1
  final val EMPTY          : Byte = 0
  
  def dimension : BoardDimension
  
  def board : BoardRepr
  
  def generateEmptyBoard(dim: BoardDimension): BoardRepr = Array.ofDim[Byte](dim._1*dim._2)

}//end trait BoardSpec

//31337
