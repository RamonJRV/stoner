package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StandardBoardSuite extends FunSuite {
  
  val emptyGrid = new CompactGrid
  val origin = Position(0,0)
  
  def emptyBoard = new Board
  
    
//  val blackCornerPos : Position = (0,0)
//  val blackCornerMove : Move = Move(BLACK, blackCornerPos)
//  
//  val whiteCornerPos : Position = ((STANDARD_COLUMN - 1).toByte,0.toByte)
//  val whiteCornerMove : Move = Move(WHITE, whiteCornerPos)
//  
//  val emptyStandardBoard = new StandardBoard
//  
//  test("New standard board is correct size") {
//    assert(emptyStandardBoard.grid.length == STANDARD_COLUMN.toInt * STANDARD_ROW.toInt)
//  }
//  
//  test("New Board has no positions") {
//    assert(emptyStandardBoard.grid.sum == 0)
//  }
//  
//  test("Single Move creates Single Stone Position") {
//    
//    var testBoard : Board = emptyStandardBoard + blackCornerMove
//    assert(testBoard.grid.sum == BLACK.toInt)
//    assert(testBoard.grid.filter(_ == BLACK).length == 1)
//    assert(testBoard.getPosValue(blackCornerPos) == BLACK)
//    
//    testBoard = emptyStandardBoard + whiteCornerMove
//    assert(testBoard.grid.sum == WHITE.toInt)
//    assert(testBoard.grid.filter(_ == WHITE).length == 1)
//    assert(testBoard.getPosValue(whiteCornerPos) == WHITE)
//    
//  }
//  
//  test("Two moves creates Two Stone Position") {
//    val testBoard = emptyStandardBoard + blackCornerMove + whiteCornerMove
//    
//    assert(testBoard.grid.sum == 0)
//    assert(testBoard.grid.filter(_ == BLACK).length == 1)
//    assert(testBoard.grid.filter(_ == WHITE).length == 1)
//    
//    assert(testBoard.getPosValue(blackCornerPos) == BLACK)
//    assert(testBoard.getPosValue(whiteCornerPos) == WHITE)
//    
//    println("Should be black in upper left and white in upper right")
//    println(testBoard)
//  }

}//end object BoardAndMoveSuite extends FunSuite

//31337
