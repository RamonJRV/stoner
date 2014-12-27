package stoner.board

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import CompactGrid.{setPointValueInBucket,EMPTY_VALUE,WHITE_VALUE,BLACK_VALUE,Bucket, BITS_PER_POINT}

@RunWith(classOf[JUnitRunner])
class CompactGridSuite extends FunSuite {
  
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
    
  }//end test("getIndex on various point values")
  
}//end class CompactGridSuite extends FunSuite