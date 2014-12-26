package stoner.board

import scala.Range

trait BoardSpec {
  
  def dimension : BoardDimension
  
  def board : BoardRepr
  
  def generateEmptyBoard(dim: BoardDimension): BoardRepr = Array.ofDim[Byte](dim._1*dim._2)

  def +(move : Move) : BoardSpec 
  
  protected def posToIndex(position : Position) = 
    position._1.toInt*dimension._1.toInt + position._2.toInt
    
  def getPosValue(position : Position) : Side = board(posToIndex(position))
  
  def getPosValue(col: Dimension, row: Dimension) : Side = getPosValue((col,row))
  
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, dimension._2)
      } yield Range(0, dimension._1).map((c: Int) => posToChar(getPosValue(c.toByte,r.toByte))).mkString(" ")
      
    lines.mkString("\n")
  }
}//end trait BoardSpec

//31337
