package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StandardBoardSuite extends FunSuite {
  
  val emptyGrid = new CompactGrid
  val origin = Position(0,0)
  
  def emptyBoard(grid: Grid) : StandardBoard = new StandardBoard(grid)
  
  /**
   * @todo TODO - switch all of the new StandardBoard statements to emptyBoard
   */
  test("identifyGroup works on groups size 1") {
    //1 size groups at origin
    var board = new StandardBoard(emptyGrid.set(origin, BLACK))
    assert(board.identifyGroup(origin).size == 1)
    
    board = new StandardBoard(emptyGrid.set(origin, WHITE))
    assert(board.identifyGroup(origin).size == 1)
    
    //1 size groups in middle of board
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK))
    assert(board.identifyGroup(Position(4,2)).size == 1)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), WHITE))
    assert(board.identifyGroup(Position(4,2)).size == 1)
    
    //1 size groups with opponent neighbor
    board = new StandardBoard(emptyGrid.set(Position(0,0), BLACK)
                                       .set(Position(0,1), WHITE))
    assert(board.identifyGroup(Position(0,0)).size == 1)
    assert(board.identifyGroup(Position(0,1)).size == 1)
  }//end test("identifyGroup works on groups size 1")
    
  test("identifyGroup works on groups size 2") {
    
    var board = new StandardBoard(emptyGrid.set(Position(0,0), BLACK)
                                           .set(Position(0,1), BLACK))
    assert(board.identifyGroup(Position(0,0)).size == 2)
    assert(board.identifyGroup(Position(0,1)).size == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(4,3), BLACK))
    assert(board.identifyGroup(Position(4,2)).size == 2)
    assert(board.identifyGroup(Position(4,3)).size == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(0,0), WHITE)
                                       .set(Position(0,1), WHITE))
    assert(board.identifyGroup(Position(0,0)).size == 2)
    assert(board.identifyGroup(Position(0,1)).size == 2)
    
    board = new StandardBoard(emptyGrid.set(Position(4,2), WHITE)
                                       .set(Position(4,3), WHITE))
    assert(board.identifyGroup(Position(4,2)).size == 2)
    assert(board.identifyGroup(Position(4,3)).size == 2)
    
    //2 size groups with neighbor
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(4,3), BLACK)
                                       .set(Position(4,1), WHITE))
    assert(board.identifyGroup(Position(4,2)).size == 2)
    assert(board.identifyGroup(Position(4,3)).size == 2)
    assert(board.identifyGroup(Position(4,1)).size == 1)
  }//end test("identifyGroup works on group size 2")
  
  test("identifyGroup works on groups size 3") {
    //straight group
    var board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                           .set(Position(4,3), BLACK)
                                           .set(Position(4,4), BLACK))
    assert(board.identifyGroup(Position(4,2)).size == 3)
    assert(board.identifyGroup(Position(4,3)).size == 3)
    assert(board.identifyGroup(Position(4,4)).size == 3)
    
    //bent group
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(3,2), BLACK)
                                       .set(Position(4,3), BLACK))
    assert(board.identifyGroup(Position(4,2)).size == 3)
    assert(board.identifyGroup(Position(3,2)).size == 3)
    assert(board.identifyGroup(Position(4,3)).size == 3)
    
    // bent group with opponent
    board = new StandardBoard(emptyGrid.set(Position(4,2), BLACK)
                                       .set(Position(3,2), BLACK)
                                       .set(Position(4,3), BLACK)
                                       .set(Position(3,3), WHITE))
    assert(board.identifyGroup(Position(4,2)).size == 3)
    assert(board.identifyGroup(Position(3,2)).size == 3)
    assert(board.identifyGroup(Position(4,3)).size == 3)
    assert(board.identifyGroup(Position(3,3)).size == 1)
  }//end test("identifyGroup works on group size 3")
  
  
  
  test("Liberties for a single stone in various Positions") {
    //single stone in the corner
    var board = emptyBoard(emptyGrid.set(origin, BLACK))
    assert(board.liberties(origin).size == 2)
    assert(board.liberties(origin).contains(Position(0,1)) &&
           board.liberties(origin).contains(Position(1,0)))
           
    board = emptyBoard(emptyGrid.set(origin, WHITE))
    assert(board.liberties(origin).size == 2)
    assert(board.liberties(origin).contains(Position(0,1)) &&
           board.liberties(origin).contains(Position(1,0)))
           
    //single stone on the edge
    var p = Position(0,1)
    board = emptyBoard(emptyGrid.set(p, BLACK))
    assert(board.liberties(p).size == 3)
    assert(board.liberties(p).contains(Position(0,0)) && 
           board.liberties(p).contains(Position(1,1)) &&
           board.liberties(p).contains(Position(0,2)))
           
    board = emptyBoard(emptyGrid.set(p, WHITE))
    assert(board.liberties(p).size == 3)
    assert(board.liberties(p).contains(Position(0,0)) && 
           board.liberties(p).contains(Position(1,1)) &&
           board.liberties(p).contains(Position(0,2)))
           
    //single stone in the middle of the board
    p = Position(1,1)
    board = emptyBoard(emptyGrid.set(p, BLACK))
    assert(board.liberties(p).size == 4)
    assert(board.liberties(p).contains(Position(1,0)) && 
           board.liberties(p).contains(Position(0,1)) &&
           board.liberties(p).contains(Position(1,2)) && 
           board.liberties(p).contains(Position(2,1)))
           
    p = Position(1,1)
    board = emptyBoard(emptyGrid.set(p, WHITE))
    assert(board.liberties(p).size == 4)
    assert(board.liberties(p).contains(Position(1,0)) && 
           board.liberties(p).contains(Position(0,1)) &&
           board.liberties(p).contains(Position(1,2)) && 
           board.liberties(p).contains(Position(2,1)))
          
  }//end test("Liberties for a single stone in various Positions")
  
  test("Liberties for a single stone with opponent") {
    var board = emptyBoard(emptyGrid.set(origin, BLACK)
                                    .set(Position(1,0), WHITE))
                                    
    assert(board.liberties(origin).size == 1)
    assert(board.liberties(origin).contains(Position(0,1)))
    
    board = emptyBoard(emptyGrid.set(origin, BLACK)
                                .set(Position(0,1), WHITE))
                                    
    assert(board.liberties(origin).size == 1)
    assert(board.liberties(origin).contains(Position(1,0)))
    
    
  }//end test("Liberties for a single stone with opponent")
  
  
  test("Liberties for two stones in various Positions") {
    //FIXME - finish test  
  }//end test("Liberties for two stones in various Positions")
  
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
