package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StandardBoardSuite extends FunSuite {
  val emptyGrid = new CompactGrid
  val origin = Position(0,0)

  
  test("identifyGroup works on empty point") {
    //0 size groups
    var board = new StandardBoard(emptyGrid)
    assert(board.identifyGroup(origin).size == 0)
  }
    
  test("identifyGroup works on groups size 1") {
    //1 size groups at origin
    var board = new StandardBoard(emptyGrid.set(origin, BLACK))
    assert(board.identifyGroup(origin).size == 1)
    
    board = new StandardBoard(emptyGrid.set(origin, WHITE))
    assert(board.identifyGroup(origin).size == 1)
    
    //1 size groups in middle of board
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK))
    assert(board.identifyGroup(Position(4,2)) == 1)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), WHITE))
    assert(board.identifyGroup(Position(4,2)) == 1)
    
    //1 size groups with opponent neighbor
    board = new StandardBoard(emptyGrid.set(Position(0,0), BLACK)
                                       .set(Position(0,1), WHITE))
    assert(board.identifyGroup(Position(0,0)) == 1)
    assert(board.identifyGroup(Position(0,1)) == 1)
  }//end test("identifyGroup works on groups size 1")
    
  test("identifyGroup works on groups size 2") {
    
    var board = new StandardBoard(emptyGrid.set(Position(0,0), BLACK)
                                           .set(Position(0,1), BLACK))
    assert(board.identifyGroup(Position(0,0)) == 2)
    assert(board.identifyGroup(Position(0,1)) == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(4,3), BLACK))
    assert(board.identifyGroup(Position(4,2)) == 2)
    assert(board.identifyGroup(Position(4,3)) == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(0,0), WHITE)
                                       .set(Position(0,1), WHITE))
    assert(board.identifyGroup(Position(0,0)) == 2)
    assert(board.identifyGroup(Position(0,1)) == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), WHITE)
                                       .set(Position(4,3), WHITE))
    assert(board.identifyGroup(Position(4,2)) == 2)
    assert(board.identifyGroup(Position(4,3)) == 2)
    
    //2 size groups with neighbor
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(4,3), BLACK)
                                       .set(Position(4,1), WHITE))
    assert(board.identifyGroup(Position(4,2)) == 2)
    assert(board.identifyGroup(Position(4,3)) == 2)
    assert(board.identifyGroup(Position(4,2)) == 1)
  }//end test("identifyGroup works on group size 2")
  
  test("identifyGroup works on groups size 3") {
    //straight group
    var board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                           .set(Position(4,3), BLACK)
                                           .set(Position(4,4), BLACK))
    assert(board.identifyGroup(Position(4,2)) == 3)
    assert(board.identifyGroup(Position(4,3)) == 3)
    assert(board.identifyGroup(Position(4,4)) == 3)
    
    //bent group
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(3,2), BLACK)
                                       .set(Position(4,3), BLACK))
    assert(board.identifyGroup(Position(4,2)) == 3)
    assert(board.identifyGroup(Position(3,2)) == 3)
    assert(board.identifyGroup(Position(4,3)) == 3)
    
    // bent group with opponent
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(3,2), BLACK)
                                       .set(Position(4,3), BLACK)
                                       .set(Position(3,3), WHITE))
    assert(board.identifyGroup(Position(4,2)) == 3)
    assert(board.identifyGroup(Position(3,2)) == 3)
    assert(board.identifyGroup(Position(4,3)) == 3)
    assert(board.identifyGroup(Position(3,3)) == 1)
  }//end test("identifyGroup works on group size 3")
  
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
