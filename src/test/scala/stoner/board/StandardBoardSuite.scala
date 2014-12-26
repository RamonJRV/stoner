package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class StandardBoardSuite extends FunSuite {
  val blackCornerPos : Position = (0,0)
  val blackCornerMove : Move = Move(BLACK, blackCornerPos)
  
  val whiteCornerPos : Position = ((STANDARD_COLUMN - 1).toByte,0.toByte)
  val whiteCornerMove : Move = Move(WHITE, whiteCornerPos)
  
  val emptyStandardBoard = new StandardBoard
  
  test("New standard board is correct size") {
    assert(emptyStandardBoard.board.length == STANDARD_COLUMN.toInt * STANDARD_ROW.toInt)
  }
  
  test("New Board has no positions") {
    assert(emptyStandardBoard.board.sum == 0)
  }
  
  test("Single Move creates Single Stone Position") {
    
    var testBoard : BoardSpec = emptyStandardBoard + blackCornerMove
    assert(testBoard.board.sum == BLACK.toInt)
    assert(testBoard.board.filter(_ == BLACK).length == 1)
    assert(testBoard.getPosValue(blackCornerPos) == BLACK)
    
    testBoard = emptyStandardBoard + whiteCornerMove
    assert(testBoard.board.sum == WHITE.toInt)
    assert(testBoard.board.filter(_ == WHITE).length == 1)
    assert(testBoard.getPosValue(whiteCornerPos) == WHITE)
    
  }
  
  test("Two moves creates Two Stone Position") {
    val testBoard = emptyStandardBoard + blackCornerMove + whiteCornerMove
    
    assert(testBoard.board.sum == 0)
    assert(testBoard.board.filter(_ == BLACK).length == 1)
    assert(testBoard.board.filter(_ == WHITE).length == 1)
    
    assert(testBoard.getPosValue(blackCornerPos) == BLACK)
    assert(testBoard.getPosValue(whiteCornerPos) == WHITE)
  }

}//end object BoardAndMoveSuite extends FunSuite

//31337
