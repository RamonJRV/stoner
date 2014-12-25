package sgfParser

package object Board {
  
  type Side = Byte
  
  final val WHITE_OCCUPIED : Side = -1
  final val BLACK_OCCUPIED : Side = 1
  final val EMPTY          : Side = 0
  
  type Column = Byte
  type Row = Byte
  
  type BoardDimension = (Column,Row)
  type Position = BoardDimension
  
  final val STANDARD_COLUMN : Column = 19
  final val STANDARD_ROW : Row = 19
  final val STANDARD_BOARD_DIM : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW)
  
  type BoardRepr = Array[Byte]
  
  final val STANDARD_EMPTY_BOARD : Array[Byte] = 
    Array.ofDim[Byte](STANDARD_BOARD_DIM._1*STANDARD_BOARD_DIM._2)
  
}// package object Board

//31337