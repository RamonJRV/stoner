package stoner.board

import scala.Range

trait Board {
  
  def grid : Grid
  
  def +(move : Move) : Board
  
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, grid.boardDimension.row)
      } yield Range(0, grid.boardDimension.column).map((c: Int) => posToChar(grid.get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait BoardSpec

//31337
