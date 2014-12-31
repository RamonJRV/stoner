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
    
    board = emptyBoard(emptyGrid.set(origin,BLACK)
                                .set(Position(0,1), WHITE)
                                .set(Position(1,0), WHITE))
                                
    assert(board.liberties(origin).size == 0)
    
    board = emptyBoard(emptyGrid.set(origin, WHITE)
                                    .set(Position(1,0), BLACK))
                                    
    assert(board.liberties(origin).size == 1)
    assert(board.liberties(origin).contains(Position(0,1)))
           
    //... I'm fairly certain WHITE would pass all the same tests as BLACK
  }//end test("Liberties for a single stone with opponent")
  
  test("Liberties for two stones in various Positions") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                                PosFlip(Position(0,1),BLACK)))
    //println(b.toString)
    //println(b.liberties(Position(0,0)).toString)
    assert(b.liberties(Position(0,0)).contains(Position(1,0)))
    assert(b.liberties(Position(0,1)).contains(Position(1,1)))
    assert(b.liberties(Position(0,1)).contains(Position(0,2)))
    
    b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                            PosFlip(Position(0,1),WHITE)))
                                            
    assert(b.liberties(Position(0,0)).contains(Position(1,0)))
    assert(b.liberties(Position(0,1)).contains(Position(1,1)))
    assert(b.liberties(Position(0,1)).contains(Position(0,2)))
  }//end test("Liberties for two stones in various Positions")
  
  test("Liberties for a three stone bent group") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                                PosFlip(Position(0,1),BLACK),
                                                PosFlip(Position(1,0),BLACK)))
                                                
    assert(b.liberties(Position(0,0)).isEmpty)
    
    assert(b.liberties(Position(0,1)).contains(Position(0,2)))
    assert(b.liberties(Position(0,1)).contains(Position(1,1)))
    
    assert(b.liberties(Position(1,0)).contains(Position(2,0)))
    assert(b.liberties(Position(1,0)).contains(Position(1,1)))
    
  }//end test("Liberties for a three stone bent group")
  
  test("groupLiberties on one stone") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK)))
    
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(0,1)))
    
    b = (new StandardBoard).setStones(Array(PosFlip(Position(1,0),BLACK)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(0,0)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(2,0)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,1)))
    
    b = (new StandardBoard).setStones(Array(PosFlip(Position(1,1),BLACK)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(0,1)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,2)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(2,1)))
    
    b = (new StandardBoard).setStones(Array(PosFlip(Position(1,1),WHITE)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(0,1)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,2)))
    assert(b.groupLiberties(Position(0,0)).contains(Position(2,1)))
    
  }//end test("groupLiberties on one stone")

  test("group liberties on two stones") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                                PosFlip(Position(0,1),BLACK)))
                                                
    assert(b.groupLiberties(Position(0,0)).contains(Position(0,2)) &&
           b.groupLiberties(Position(0,1)).contains(Position(0,2)))
           
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,1)) &&
           b.groupLiberties(Position(0,1)).contains(Position(1,1)))
           
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,0)) &&
           b.groupLiberties(Position(0,1)).contains(Position(1,0)))
  }//end test("group liberties on two stones")
  
  test("group liberties on two stones with oponent") {
    val b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                                PosFlip(Position(0,1),BLACK),
                                                PosFlip(Position(0,2),WHITE)))
                                                
    assert(!b.groupLiberties(Position(0,0)).contains(Position(0,2)) &&
           !b.groupLiberties(Position(0,1)).contains(Position(0,2)))
           
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,1)) &&
           b.groupLiberties(Position(0,1)).contains(Position(1,1)))
           
    assert(b.groupLiberties(Position(0,0)).contains(Position(1,0)) &&
           b.groupLiberties(Position(0,1)).contains(Position(1,0)))
           
    assert(b.groupLiberties(Position(0,2)).contains(Position(1,2)))
    assert(b.groupLiberties(Position(0,2)).contains(Position(0,3)))
  }//end test("group liberties on two stones")
  
  test("isAlive one stone 0 - 2 opponents") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK)))
    
    assert(b.isAlive(Position(0,0)))
    
    b = b.setStones(Array(PosFlip(Position(0,1),WHITE)))
    assert(b.isAlive(Position(0,0)))
    
    b = b.setStones(Array(PosFlip(Position(1,0),WHITE)))
    assert(!b.isAlive(Position(0,0)))
  }//end test("isAlive one stone 0 - 2 opponents")
  
  test("isAlive two stones 0 - 3 opponent") {
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,0),BLACK),
                                                PosFlip(Position(0,1),BLACK)))
                                                        
    assert(b.isAlive(Position(0,0)) && b.isAlive(Position(0,1)))
    
    b = b.setStone(PosFlip(Position(0,2), WHITE))
    assert(b.isAlive(Position(0,0)) && b.isAlive(Position(0,1)))
    
    b = b.setStone(PosFlip(Position(1,1), WHITE))
    assert(b.isAlive(Position(0,0)) && b.isAlive(Position(0,1)))
    
    b = b.setStone(PosFlip(Position(1,0), WHITE))
    assert(!b.isAlive(Position(0,0)) && !b.isAlive(Position(0,1)))
    
  }//end test("isAlive two stones 0 -4 opponent")
  
  test("I wanted to create an eye :)") {
    // WWW
    //WBBBW
    //WB*BW
    //WBBBW
    // WWW
    
    var b = (new StandardBoard).setStones(Array(PosFlip(Position(0,1), WHITE),
                                                PosFlip(Position(0,2), WHITE),
                                                PosFlip(Position(0,3), WHITE),
                                                PosFlip(Position(1,0), WHITE),
                                                PosFlip(Position(1,1), BLACK),
                                                PosFlip(Position(1,2), BLACK),
                                                PosFlip(Position(1,3), BLACK),
                                                PosFlip(Position(1,4), WHITE),
                                                PosFlip(Position(2,0), WHITE),
                                                PosFlip(Position(2,1), BLACK),
                                                PosFlip(Position(2,3), BLACK),
                                                PosFlip(Position(2,4), WHITE),
                                                PosFlip(Position(3,0), WHITE),
                                                PosFlip(Position(3,1), BLACK),
                                                PosFlip(Position(3,2), BLACK),
                                                PosFlip(Position(3,3), BLACK),
                                                PosFlip(Position(3,4), WHITE),
                                                PosFlip(Position(4,1), WHITE),
                                                PosFlip(Position(4,2), WHITE),
                                                PosFlip(Position(4,3), WHITE)))
                                                
    b = b.setStone(PosFlip(Position(2,2),WHITE))
    assert(!b.isAlive(Position(1,1)))
    
  }//end test("I wanted to create an eye :)")
  
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
