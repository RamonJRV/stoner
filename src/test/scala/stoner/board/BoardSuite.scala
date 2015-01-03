package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StandardBoardSuite extends FunSuite {
  
  val emptyGrid = new CompactGrid
  val origin = Position(0,0)
  
  def emptyBoard = new Board
  
  test("Empty Board is still functional") {
    for (c <- Range(0, emptyBoard.boardDimension.column);
         r <- Range(0, emptyBoard.boardDimension.row)) {
      assert(emptyBoard.isEmpty(Position(c,r)))
      assert(!emptyBoard.isSuicide(Move(Position(c,r), BLACK)))
      assert(!emptyBoard.isKo(Move(Position(c,r), BLACK)))
    }
  }//end test("Empty Board is still functional")
  
  test("Board constructor properly places single StateTransition") {
    var b = new Board(Array[StateTransition](PosFlip(origin, BLACK)))
        
    assert(b.grids.last.get(origin) == BLACK)
    
    for (c <- Range(0,b.boardDimension.column);
         r <- Range(0,b.boardDimension.row);
         if (c > 0 || r > 0))  
      assert(b.isEmpty(Position(c,r)))
      
    b = new Board(Array[StateTransition](PosFlip(Position(1,1), BLACK)))
        
    assert(b.grids.last.get(Position(1,1)) == BLACK)
    
    for (c <- Range(0,b.boardDimension.column);
         r <- Range(0,b.boardDimension.row);
         if (c != r))  
      assert(b.isEmpty(Position(c,r)))
      
    b = new Board(Array[StateTransition](PosFlip(origin, WHITE)))
        
    assert(b.grids.last.get(origin) == WHITE)
    
    for (c <- Range(0,b.boardDimension.column);
         r <- Range(0,b.boardDimension.row);
         if (c > 0 || r > 0))  
      assert(b.isEmpty(Position(c,r)))
  }//end test("Constructor properly places stones")
  
  
  test("Board constructor properly places multiple StateTransitions") {
    var b = new Board(Array[StateTransition](Move(origin, BLACK),
                                             Move(Position(0,1),WHITE)))
    assert(b.grids(1).get(origin) == BLACK)
    assert(b.grids(1).get(Position(0,1)) == EMPTY)
    
    assert(b.grids.last.get(origin) == BLACK)
    assert(b.grids.last.get(Position(0,1)) == WHITE)
    
    for (c <- Range(0,b.boardDimension.column);
         r <- Range(0,b.boardDimension.row);
         if (c > 1 || r > 1))  
      assert(b.isEmpty(Position(c,r)))
  }//end test("Board constructor properly places multiple StateTransitions")
  
}//end object BoardAndMoveSuite extends FunSuite

//31337
