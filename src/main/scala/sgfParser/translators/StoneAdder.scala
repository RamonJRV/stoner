package sgfParser.translators

import scala.collection.immutable.IndexedSeq
import scala.collection.immutable.Vector

import sgfParser.Board.{BLACK,WHITE}
import sgfParser.Board.BoardSpec
import sgfParser.Board.Move
import sgfParser.Board.Position

import sgfParser.translators.PositionTranslator.strRepToPos

object StoneAdder {
  
  final val ADD_WHITE_PROPERTY : String = "AW"
  final val ADD_BLACK_PROPERTY : String = "AB"

  def addWhiteStone(str : String) : Move = Move(WHITE, strRepToPos(str))
  def addBlackStone(str : String) : Move = Move(BLACK, strRepToPos(str))
    
  //FIXME - ugly implementation
  private def splitPosSeq(str: String) : IndexedSeq[String] =
    str.drop(3).sliding(2, 4).toIndexedSeq
    //str.drop(2).tail.dropRight(1).split("""\]\[""").toIndexedSeq
  
  def addStoneParser(str: String) : IndexedSeq[Move] = {
    if (str.startsWith(ADD_WHITE_PROPERTY))
      splitPosSeq(str).map(addWhiteStone)
    else if(str.startsWith(ADD_BLACK_PROPERTY))
      splitPosSeq(str).map(addBlackStone)
    else 
      Vector[Move]()
  }

  def applyMovesToBoard(board : BoardSpec, moves : IndexedSeq[Move]) : BoardSpec =
    moves.foldLeft(board)(_ + _)
  
  def applyAddStoneStrToBoard(board : BoardSpec, str : String) : BoardSpec = 
    applyMovesToBoard(board, addStoneParser(str))
}//end object StoneAdder

//31337