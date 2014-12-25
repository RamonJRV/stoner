package stoner.board


case class StandardBoard(board : BoardRepr = new Array[Byte](STANDARD_POINT_COUNT)) extends BoardSpec {
  
  override def dimension : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW)

  override def +(move : Move) : BoardSpec = 
    StandardBoard(board.updated(posToIndex(move.position), move.side))
}

//31337