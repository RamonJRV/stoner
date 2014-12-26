package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class BoardAndMoveSuite extends FunSuite {
  
  test("New standard board is correct size") {
    val testBoard = new StandardBoard
    assert(testBoard.board.length == STANDARD_COLUMN.toInt * STANDARD_ROW.toInt)
  }
  
  test("New Board has no positions") {
    assert((new StandardBoard).board.sum == 0)
  }

}//end object BoardAndMoveSuite extends FunSuite

//31337
