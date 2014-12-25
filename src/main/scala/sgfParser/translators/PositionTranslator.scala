package sgfParser.translators

import sgfParser.Board.{Dimension,Position}

import scala.collection.immutable.SortedMap

object PositionTranslator {
  
  final val zeroChar: Int  = 'a'.toInt
  
  def charToDimension(char : Char) : Dimension = (char.toInt - zeroChar).toByte
  
  def strRepToPos(str : IndexedSeq[Char]) : Position = (charToDimension(str(0)), charToDimension(str(1)))

}//end object PositionTranslator

//31337