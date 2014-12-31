package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PosFlipSuite extends FunSuite {
  
  val emptyGrid = new CompactGrid
  
  test("PosFlip + on single board") {
    
    assert((new PosFlip(Position(0,0), BLACK) + emptyGrid).get(0,0) == BLACK)
    assert((new PosFlip(Position(0,0), WHITE) + emptyGrid).get(0,0) == WHITE)
    
  }//end test("PosFlip + on single board")
  
}//end class PosFlipSuite extends FunSuite

//31337