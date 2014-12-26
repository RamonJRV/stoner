 package stoner.board


case class StandardBoard(grid : GridRepr = new Array[Byte](STANDARD_POINT_COUNT)) extends Board {
  
  override def dimension : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW)

  override def +(move : Move) : Board = 
    StandardBoard(grid.updated(posToIndex(move.position), move.side))
}

//31337