package stoner.sgfParser

import scala.collection.immutable.IndexedSeq
import scala.collection.immutable.Vector

import stoner.board.{BLACK,WHITE,Board,Move,Position}

import PositionTranslator.strRepToPos

import stoner.board.StoneAdder.applyMovesToBoard

object AddWhiteOrBlack {
  
  final val ADD_WHITE_PROPERTY : String = "AW"
  final val ADD_BLACK_PROPERTY : String = "AB"

  def addWhiteStone(str : String) : Move = Move(WHITE, strRepToPos(str))
  def addBlackStone(str : String) : Move = Move(BLACK, strRepToPos(str))
    
  
  private def splitPosSeq(str: String) : IndexedSeq[String] =
    str.drop(3).sliding(2, 4).toIndexedSeq
    
  
  def addStoneParser(str: String) : IndexedSeq[Move] = {
    if (str.startsWith(ADD_WHITE_PROPERTY))
      splitPosSeq(str).map(addWhiteStone)
    else if(str.startsWith(ADD_BLACK_PROPERTY))
      splitPosSeq(str).map(addBlackStone)
    else 
      Vector[Move]()
  }

  def applyAddStoneStrToBoard(board : Board, str : String) : Board = 
    applyMovesToBoard(board, addStoneParser(str))
}//end object StoneAdder

//31337