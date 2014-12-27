 package stoner.board


class StandardBoard(grid_i : Grid = new CompactGrid) extends Board {
    
  override def grid = grid_i
   
  //FIXME
  override def +(move : Move) : Board = this
    
}

//31337