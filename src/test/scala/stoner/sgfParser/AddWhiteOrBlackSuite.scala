package stoner.sgfParser

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import stoner.board.{BLACK,WHITE,STANDARD_COLUMN,StandardBoard}

import stoner.sgfParser.AddWhiteOrBlack.{ADD_BLACK_PROPERTY, ADD_WHITE_PROPERTY}

@RunWith(classOf[JUnitRunner])
class AddWhiteOrBlackSuite extends FunSuite {
//  
//  val upperLeftAdd = "[aa]"
//  val upperRightAdd = "[sa]"
//    
//  val emptyStandardBoard = new StandardBoard
//  
//  test("Single Black Adder") {
//    
//    val testBoard = 
//      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
//                                              ADD_BLACK_PROPERTY + upperLeftAdd)
//    
//    assert(testBoard.grid.sum == BLACK.toInt)
//    assert(testBoard.grid.filter(_ == BLACK).length == 1)
//    assert(testBoard.getPosValue((0,0)) == BLACK)
//    
//  }//end test("Single Stone Adder")
//  
//  test("Single White Adder") {
//    val testBoard = 
//      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
//                                              ADD_WHITE_PROPERTY + upperLeftAdd)
//   
//    assert(testBoard.grid.sum == WHITE.toInt)
//    assert(testBoard.grid.filter(_ == WHITE).length == 1)
//    assert(testBoard.getPosValue((0,0)) == WHITE)
//  }
//  
//  test("Multi Black Adder") {
//    val testBoard = 
//      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
//                                              ADD_BLACK_PROPERTY + upperLeftAdd + upperRightAdd)
//    
//    assert(testBoard.grid.sum == BLACK.toInt + BLACK.toInt)
//    assert(testBoard.grid.filter(_ == BLACK).length == 2)
//    assert(testBoard.getPosValue((0,0)) == BLACK)
//    assert(testBoard.getPosValue(((STANDARD_COLUMN - 1).toByte),0) == BLACK)
//    
//  }//end test("Multi Stone (Single Color) Adder")
//
//  test("Multi White Adder") {
//    val testBoard = 
//      AddWhiteOrBlack.applyAddStoneStrToBoard(emptyStandardBoard, 
//                                              ADD_WHITE_PROPERTY + upperLeftAdd + upperRightAdd)
//    
//    assert(testBoard.grid.sum == WHITE.toInt + WHITE.toInt)
//    assert(testBoard.grid.filter(_ == WHITE).length == 2)
//    assert(testBoard.getPosValue((0,0)) == WHITE)
//    assert(testBoard.getPosValue(((STANDARD_COLUMN - 1).toByte),0) == WHITE)
//  }
  
}//end class AddWhiteOrBlackSuite