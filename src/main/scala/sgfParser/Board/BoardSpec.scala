package sgfParser.Board

trait BoardSpec {
  
  def dimension : BoardDimension
  
  def board : BoardRepr
  
  def generateEmptyBoard(dim: BoardDimension): BoardRepr = Array.ofDim[Byte](dim._1*dim._2)

  def +(move : Move) : Option[BoardSpec] = 
    if (move.position._1.toInt * move.position._2.toInt > board.length || 
        board(posToIndex(move.position)) != EMPTY) 
      None
    else 
      Some(new StandardBoard(dimension, board.updated(posToIndex(move.position), move.side)))
  
  def posToIndex(position : Position) = 
    position._1.toInt*dimension._1.toInt + position._2.toInt
}//end trait BoardSpec

//31337
