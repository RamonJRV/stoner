package stoner.sgfParser

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import stoner.board.{BLACK,WHITE,StandardBoard}

import stoner.sgfParser.AddWhiteOrBlack.{ADD_BLACK_PROPERTY, ADD_WHITE_PROPERTY}

@RunWith(classOf[JUnitRunner])
class AddWhiteOrBlackSuite extends FunSuite {
  
  val singleStoneAdd = "[aa]"
  
  val emptyStandardBoard = new StandardBoard
  
  test("Single Stone Adder") {
    
    var testBoard = 
      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
                                              ADD_BLACK_PROPERTY + singleStoneAdd)
    
    assert(testBoard.board.sum == BLACK.toInt)
    assert(testBoard.board.filter(_ == BLACK).length == 1)
    assert(testBoard.getPosValue((0,0)) == BLACK)
    
    testBoard = 
      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
                                              ADD_WHITE_PROPERTY + singleStoneAdd)
   
    assert(testBoard.board.sum == WHITE.toInt)
    assert(testBoard.board.filter(_ == WHITE).length == 1)
    assert(testBoard.getPosValue((0,0)) == WHITE)
  }//end test("Single Stone Adder")

}//end class AddWhiteOrBlackSuite