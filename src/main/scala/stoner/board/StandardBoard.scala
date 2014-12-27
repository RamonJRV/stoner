 package stoner.board


class StandardBoard(grid_i : GridRepr = new Array[Byte](STANDARD_POINT_COUNT)) extends Board {
  
  override val grid = grid_i
   
  override def dimension : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW)

  override def +(move : Move) : Board = 
    new StandardBoard(grid.updated(posToIndex(move.position), move.side))
}

//31337