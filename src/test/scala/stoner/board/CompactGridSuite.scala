package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import CompactGrid.{setPointValueInBucket,EMPTY_VALUE,WHITE_VALUE,BLACK_VALUE,
                    Bucket, BITS_PER_POINT, POINTS_PER_BUCKET}

@RunWith(classOf[JUnitRunner])
class CompactGridSuite extends FunSuite {
  
  val origin = Position(0,0)
  
  test("setPointValueInBucket on various indices and values") {
    
    //set empty on all indices of a 0000... bucket
    for ( p <- Range(0, CompactGrid.POINTS_PER_BUCKET)) {
      //set the second index to empty
	  assert(0 == setPointValueInBucket(EMPTY_VALUE << p*BITS_PER_POINT,p, EMPTY))
	  assert(0 == setPointValueInBucket(BLACK_VALUE << p*BITS_PER_POINT,p, EMPTY))
	  assert(0 == setPointValueInBucket(WHITE_VALUE << p*BITS_PER_POINT,p, EMPTY))  
    }
	
	//set various indices to EMPTY after prefilled
	var b : Bucket = 15 //15 = 000...1111
	assert(12 == setPointValueInBucket(b, 0, EMPTY)) //12 = 000..1100
    assert(3 == setPointValueInBucket(b, 1, EMPTY)) // 3 = 000..0011
    
    //set the first point with second point prefilled
    b = 12 // 12 = 0000..1100
    assert(12 == setPointValueInBucket(b, 0, EMPTY))// 12 = 0000..1100
    assert(13 == setPointValueInBucket(b, 0, WHITE)) // 13 = 000..1101
    assert(14 == setPointValueInBucket(b, 0, BLACK))//14 = 000..1110
    
    //set the second point with first point prefilled
    b = 3 // 3 = 000...0011
    assert( 3 == setPointValueInBucket(b, 1, EMPTY)) // 3 = 000...0011
    assert( 7 == setPointValueInBucket(b, 1, WHITE))//7 = 000...0111
    assert(11 == setPointValueInBucket(b, 1, BLACK))//11 = 000..1011
  }//end test("setPointValueInBucket on various indices and values")

  val emptyGrid : CompactGrid = new CompactGrid
  
  test("getIndex on various point values") {
    //test the first column
    for(r <- Range(0, STANDARD_ROWS)) {
      assert(r == emptyGrid.getIndex(Position(0,r)))
    }
    
    //test the top row
    for(c <- Range(0, STANDARD_COLUMNS)) {
      assert(STANDARD_ROWS*c == emptyGrid.getIndex(Position(c,0)))
    }
    
    //test the diagonal
    for(d <- Range(0, STANDARD_ROWS)) {
      assert(d*20 == emptyGrid.getIndex(Position(d,d)))  
    }
    
  }//end test("getIndex on various point values")
  
  test("getBucketIndex on various points") {
    for(p <- Range(0,POINTS_PER_BUCKET)) {
      assert(0 == emptyGrid.getBucketIndex(Position(0,p)))
    }
    
    val bucketsPerGrid = 
      emptyGrid.boardDimension.column * emptyGrid.boardDimension.row / POINTS_PER_BUCKET
    
    for(b <- Range(0, bucketsPerGrid)) {
      assert(b == emptyGrid.getBucketIndex(Position(0,b*POINTS_PER_BUCKET)))
    }
  }//end test("getBucketIndex on various points")
  
  test("getPointIndex on various point values") {
    for(p <- Range(0, POINTS_PER_BUCKET)) {
      assert(p == emptyGrid.getPointIndex(Position(0,p)))
    }
    
    assert(0 == emptyGrid.getPointIndex(Position(0,POINTS_PER_BUCKET)))
    assert(1 == emptyGrid.getPointIndex(Position(0,POINTS_PER_BUCKET+1)))
  }//end test("getPointIndex on various point values")
  
  test("set on various points") {
    var pos = Position(0,0)
    assert(emptyGrid.set(pos, WHITE).get(pos) == WHITE)
    assert(emptyGrid.set(pos, BLACK).get(pos) == BLACK)
    
    pos = Position(0,1)
    assert(emptyGrid.set(pos, WHITE).get(pos) == WHITE)
    assert(emptyGrid.set(pos, BLACK).get(pos) == BLACK)
  }//end test("set on various points")
  
  test("flatten of a grid") {
    assert(emptyGrid.flatten.length == 
           emptyGrid.boardDimension.column*emptyGrid.boardDimension.row)
           
    assert(emptyGrid.set(Position(0,0), EMPTY).flatten(0) == EMPTY)
    assert(emptyGrid.set(Position(0,0), WHITE).flatten(0) == WHITE)
    assert(emptyGrid.set(Position(0,0), BLACK).flatten(0) == BLACK)
  }//end test("flatten of a grid")
  
  test("hashCode of a grid") {
    assert((new CompactGrid).hashCode == (new CompactGrid).hashCode)
    
    //check for collision of similar grids
    assert((new CompactGrid).set(Position(0,0), EMPTY) == 
           (new CompactGrid).set(Position(0,0), EMPTY))
    
    assert((new CompactGrid).set(Position(0,0), WHITE) == 
           (new CompactGrid).set(Position(0,0), WHITE))
           
    assert((new CompactGrid).set(Position(0,0), BLACK) == 
           (new CompactGrid).set(Position(0,0), BLACK))
           
    assert((new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), EMPTY) == 
           (new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), EMPTY))
           
    assert((new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), WHITE) == 
           (new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), WHITE))
           
    assert((new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), BLACK) == 
           (new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), BLACK))
    
    assert((new CompactGrid).set(Position(0,0), WHITE).set(Position(0,1), WHITE) == 
           (new CompactGrid).set(Position(0,0), WHITE).set(Position(0,1), WHITE))
           
    assert((new CompactGrid).set(Position(0,0), WHITE).set(Position(0,1), BLACK) == 
           (new CompactGrid).set(Position(0,0), WHITE).set(Position(0,1), BLACK))
           
    //check for non-collision of non-similar grids
    assert((new CompactGrid).set(Position(0,0), EMPTY) != 
           (new CompactGrid).set(Position(0,0), WHITE))
    
    assert((new CompactGrid).set(Position(0,0), EMPTY) != 
           (new CompactGrid).set(Position(0,0), BLACK))
           
    assert((new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), BLACK) != 
           (new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), EMPTY))
           
    assert((new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), BLACK) != 
           (new CompactGrid).set(Position(0,0), EMPTY).set(Position(0,1), WHITE))       
          
  }//end test("hashCode of a grid")
  
 
    test("identifyGroup works on groups size 1") {
    var grid = emptyGrid.set(origin,BLACK)
    
    //1 size groups at origin
   
    assert(grid.set(PosFlip(origin, BLACK)).identifyGroup(origin).size == 1)
    
    assert(grid.set(PosFlip(origin, WHITE)).identifyGroup(origin).size == 1)
    
    //1 size groups in middle of grid
    assert(emptyGrid.set(Position(4,2), BLACK).identifyGroup(Position(4,2)).size == 1)
    
    assert(emptyGrid.set(Position(4,2), WHITE).identifyGroup(Position(4,2)).size == 1)
    
    //1 size groups with opponent neighbor
    grid = emptyGrid.set(Position(0,0), BLACK)
                    .set(Position(0,1), WHITE)
    assert(grid.identifyGroup(Position(0,0)).size == 1)
    assert(grid.identifyGroup(Position(0,1)).size == 1)
  }//end test("identifyGroup works on groups size 1")
  
  test("identifyGroup works on groups size 2") {
    
    var grid = emptyGrid.set(Position(0,0), BLACK)
                        .set(Position(0,1), BLACK)
                        
    assert(grid.identifyGroup(Position(0,0)).size == 2)
    assert(grid.identifyGroup(Position(0,1)).size == 2)
    
    grid = emptyGrid.set(Position(4,2), BLACK)
                    .set(Position(4,3), BLACK)
                    
    assert(grid.identifyGroup(Position(4,2)).size == 2)
    assert(grid.identifyGroup(Position(4,3)).size == 2)
    
    grid = emptyGrid.set(Position(0,0), WHITE)
                    .set(Position(0,1), WHITE)
    assert(grid.identifyGroup(Position(0,0)).size == 2)
    assert(grid.identifyGroup(Position(0,1)).size == 2)
    
    grid = emptyGrid.set(Position(4,2), WHITE)
                    .set(Position(4,3), WHITE)
    assert(grid.identifyGroup(Position(4,2)).size == 2)
    assert(grid.identifyGroup(Position(4,3)).size == 2)
    
    //2 size groups with neighbor
    grid = emptyGrid.set(Position(4,2), BLACK)
                    .set(Position(4,3), BLACK)
                    .set(Position(4,1), WHITE)
    assert(grid.identifyGroup(Position(4,2)).size == 2)
    assert(grid.identifyGroup(Position(4,3)).size == 2)
    assert(grid.identifyGroup(Position(4,1)).size == 1)
  }//end test("identifyGroup works on group size 2")
  
  test("identifyGroup works on groups size 3") {
    //straight group
    var grid = emptyGrid.set(Position(4,2), BLACK)
                        .set(Position(4,3), BLACK)
                        .set(Position(4,4), BLACK)
    assert(grid.identifyGroup(Position(4,2)).size == 3)
    assert(grid.identifyGroup(Position(4,3)).size == 3)
    assert(grid.identifyGroup(Position(4,4)).size == 3)
    
    //bent group
    grid = emptyGrid.set(Position(4,2), BLACK)
                    .set(Position(3,2), BLACK)
                    .set(Position(4,3), BLACK)
    assert(grid.identifyGroup(Position(4,2)).size == 3)
    assert(grid.identifyGroup(Position(3,2)).size == 3)
    assert(grid.identifyGroup(Position(4,3)).size == 3)
    
    // bent group with opponent
    grid = emptyGrid.set(Position(4,2), BLACK)
                    .set(Position(3,2), BLACK)
                    .set(Position(4,3), BLACK)
                    .set(Position(3,3), WHITE)
    assert(grid.identifyGroup(Position(4,2)).size == 3)
    assert(grid.identifyGroup(Position(3,2)).size == 3)
    assert(grid.identifyGroup(Position(4,3)).size == 3)
    assert(grid.identifyGroup(Position(3,3)).size == 1)
  }//end test("identifyGroup works on group size 3")
  
  test("Liberties for a single stone in various Positions") {
    //single stone in the corner
    var grid = emptyGrid.set(origin, BLACK)
    assert(grid.liberties(origin).size == 2)
    assert(grid.liberties(origin).contains(Position(0,1)) &&
           grid.liberties(origin).contains(Position(1,0)))
           
    grid = emptyGrid.set(origin, WHITE)
    assert(grid.liberties(origin).size == 2)
    assert(grid.liberties(origin).contains(Position(0,1)) &&
           grid.liberties(origin).contains(Position(1,0)))
           
    //single stone on the edge
    var p = Position(0,1)
    grid = emptyGrid.set(p, BLACK)
    assert(grid.liberties(p).size == 3)
    assert(grid.liberties(p).contains(Position(0,0)) && 
           grid.liberties(p).contains(Position(1,1)) &&
           grid.liberties(p).contains(Position(0,2)))
           
    grid = emptyGrid.set(p, WHITE)
    assert(grid.liberties(p).size == 3)
    assert(grid.liberties(p).contains(Position(0,0)) && 
           grid.liberties(p).contains(Position(1,1)) &&
           grid.liberties(p).contains(Position(0,2)))
           
    //single stone in the middle of the grid
    p = Position(1,1)
    grid = emptyGrid.set(p, BLACK)
    assert(grid.liberties(p).size == 4)
    assert(grid.liberties(p).contains(Position(1,0)) && 
           grid.liberties(p).contains(Position(0,1)) &&
           grid.liberties(p).contains(Position(1,2)) && 
           grid.liberties(p).contains(Position(2,1)))
           
    p = Position(1,1)
    grid = emptyGrid.set(p, WHITE)
    assert(grid.liberties(p).size == 4)
    assert(grid.liberties(p).contains(Position(1,0)) && 
           grid.liberties(p).contains(Position(0,1)) &&
           grid.liberties(p).contains(Position(1,2)) && 
           grid.liberties(p).contains(Position(2,1)))
          
  }//end test("Liberties for a single stone in various Positions")
  
  test("Liberties for a single stone with opponent") {
    var grid = emptyGrid.set(origin, BLACK)
                        .set(Position(1,0), WHITE)
                                    
    assert(grid.liberties(origin).size == 1)
    assert(grid.liberties(origin).contains(Position(0,1)))
    
    grid = emptyGrid.set(origin, BLACK)
                    .set(Position(0,1), WHITE)
                                    
    assert(grid.liberties(origin).size == 1)
    assert(grid.liberties(origin).contains(Position(1,0)))
    
    grid = emptyGrid.set(origin,BLACK)
                    .set(Position(0,1), WHITE)
                    .set(Position(1,0), WHITE)
                                
    assert(grid.liberties(origin).size == 0)
    
    grid = emptyGrid.set(origin, WHITE)
                    .set(Position(1,0), BLACK)
                                    
    assert(grid.liberties(origin).size == 1)
    assert(grid.liberties(origin).contains(Position(0,1)))
           
    //... I'm fairly certain WHITE would pass all the same tests as BLACK
  }//end test("Liberties for a single stone with opponent")
  
  test("Liberties for two stones in various Positions") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                                PosFlip(Position(0,1),BLACK)))
                                
    //println(b.toString)
    //println(b.liberties(Position(0,0)).toString)
    assert(g.liberties(Position(0,0)).contains(Position(1,0)))
    assert(g.liberties(Position(0,1)).contains(Position(1,1)))
    assert(g.liberties(Position(0,1)).contains(Position(0,2)))
    
    g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                            PosFlip(Position(0,1),WHITE)))
                                            
    assert(g.liberties(Position(0,0)).contains(Position(1,0)))
    assert(g.liberties(Position(0,1)).contains(Position(1,1)))
    assert(g.liberties(Position(0,1)).contains(Position(0,2)))
  }//end test("Liberties for two stones in various Positions")
  
  test("Liberties for a three stone bent group") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                                PosFlip(Position(0,1),BLACK),
                                PosFlip(Position(1,0),BLACK)))
                                                
    assert(g.liberties(Position(0,0)).isEmpty)
    
    assert(g.liberties(Position(0,1)).contains(Position(0,2)))
    assert(g.liberties(Position(0,1)).contains(Position(1,1)))
    
    assert(g.liberties(Position(1,0)).contains(Position(2,0)))
    assert(g.liberties(Position(1,0)).contains(Position(1,1)))
    
  }//end test("Liberties for a three stone bent group")
  
  test("groupLiberties on one stone") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK)))
    
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(0,1)))
    
    g = emptyGrid.set(Array(PosFlip(Position(1,0),BLACK)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(0,0)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(2,0)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,1)))
    
    g = emptyGrid.set(Array(PosFlip(Position(1,1),BLACK)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(0,1)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,2)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(2,1)))
    
    g = emptyGrid.set(Array(PosFlip(Position(1,1),WHITE)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,0)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(0,1)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,2)))
    assert(g.groupLiberties(Position(0,0)).contains(Position(2,1)))
    
  }//end test("groupLiberties on one stone")

  test("group liberties on two stones") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                                PosFlip(Position(0,1),BLACK)))
                                                
    assert(g.groupLiberties(Position(0,0)).contains(Position(0,2)) &&
           g.groupLiberties(Position(0,1)).contains(Position(0,2)))
           
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,1)) &&
           g.groupLiberties(Position(0,1)).contains(Position(1,1)))
           
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,0)) &&
           g.groupLiberties(Position(0,1)).contains(Position(1,0)))
  }//end test("group liberties on two stones")
  
  test("group liberties on two stones with oponent") {
    val g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                                PosFlip(Position(0,1),BLACK),
                                PosFlip(Position(0,2),WHITE)))
                                                
    assert(!g.groupLiberties(Position(0,0)).contains(Position(0,2)) &&
           !g.groupLiberties(Position(0,1)).contains(Position(0,2)))
           
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,1)) &&
           g.groupLiberties(Position(0,1)).contains(Position(1,1)))
           
    assert(g.groupLiberties(Position(0,0)).contains(Position(1,0)) &&
           g.groupLiberties(Position(0,1)).contains(Position(1,0)))
           
    assert(g.groupLiberties(Position(0,2)).contains(Position(1,2)))
    assert(g.groupLiberties(Position(0,2)).contains(Position(0,3)))
  }//end test("group liberties on two stones")
  
  test("isAlive one stone 0 - 2 opponents") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK)))
    
    assert(g.isAlive(Position(0,0)))
    
    g = g.set(Array(PosFlip(Position(0,1),WHITE)))
    assert(g.isAlive(Position(0,0)))
    
    g = g.set(Array(PosFlip(Position(1,0),WHITE)))
    assert(!g.isAlive(Position(0,0)))
  }//end test("isAlive one stone 0 - 2 opponents")
  
  test("isAlive two stones 0 - 3 opponent") {
    var g = emptyGrid.set(Array(PosFlip(Position(0,0),BLACK),
                                PosFlip(Position(0,1),BLACK)))
                                                        
    assert(g.isAlive(Position(0,0)) && g.isAlive(Position(0,1)))
    
    g = g.set(PosFlip(Position(0,2), WHITE))
    assert(g.isAlive(Position(0,0)) && g.isAlive(Position(0,1)))
    
    g = g.set(PosFlip(Position(1,1), WHITE))
    assert(g.isAlive(Position(0,0)) && g.isAlive(Position(0,1)))
    
    g = g.set(PosFlip(Position(1,0), WHITE))
    assert(!g.isAlive(Position(0,0)) && !g.isAlive(Position(0,1)))
    
  }//end test("isAlive two stones 0 -4 opponent")
  
  test("I wanted to create an eye :)") {
    // WWW
    //WBBBW
    //WB*BW
    //WBBBW
    // WWW
    
    var g = emptyGrid.set(Array(PosFlip(Position(0,1), WHITE),
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

    g = g.set(PosFlip(Position(2,2),WHITE))
    assert(!g.isAlive(Position(1,1)))
    
  }//end test("I wanted to create an eye :)")

}//end class CompactGridSuite extends FunSuite

//31337