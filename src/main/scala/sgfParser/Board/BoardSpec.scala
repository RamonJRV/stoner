package sgfParser.Board

trait BoardSpec {
  
  def dimension : BoardDimension
  
  def board : BoardRepr
  
  def generateEmptyBoard(dim: BoardDimension): BoardRepr = Array.ofDim[Byte](dim._1*dim._2)

  def +(move : Move) : Option[BoardSpec] = { 
    val index = posToIndex(move.position)
    
    if (index > board.length || 
        board(index) != EMPTY) 
      None
    else 
      Some(new StandardBoard(dimension, board.updated(index, move.side)))
  }//end def +(move : Move) : Option[BoardSpec]
  
  def posToIndex(position : Position) = 
    position._1.toInt*dimension._1.toInt + position._2.toInt
}//end trait BoardSpec

//31337
