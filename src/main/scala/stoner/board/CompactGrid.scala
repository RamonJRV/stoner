package stoner.board

object CompactGrid {
  
  type Bucket  = Int
  type GridRepr = Array[Bucket]
  
  final val BITS_PER_POINT : Int = 2
  final val BUCKET_SIZE : Int = 32 // I can't find a scala Int.bitSize type function
  final val POINTS_PER_BUCKET : Int = BUCKET_SIZE / BITS_PER_POINT

  //00
  final val EMPTY_VALUE : Int = 0
  //01
  final val WHITE_VALUE : Int = 1
  //10
  final val BLACK_VALUE : Int = 2
  
  def gridSizeToGridReprSize(c : Dimension, r : Dimension) : Int =
    (c*r)*BITS_PER_POINT/POINTS_PER_BUCKET + 1
  
  
    
  def generateGridArray(dim : BoardDimension) : Array[Int] = dim match {
  	case BoardDimension(c,r) => {
	  new Array[Int](gridSizeToGridReprSize(c,r))
  	}
  }//end def generateGridArray(dim : BoardDimension) : Array[Byte]

  /** Sets the value of a single point within a bucket.  Note the only legal
   *  transitions are:
   *  EMPTY -> BLACK
   *  EMPTY -> WHITE
   *  WHITE -> EMPTY
   *  BLACK -> EMPTY
   *  
   *  @param bucket : The bucket contianing the point to set the new value to
   *  @param index : the index of the point to change
   *  @param side : the new value of the point
   *  
   *  @return A bucket with the specified point position transitioned to the 
   *  new side
   */
  def setPointValueInBucket(bucket : CompactGrid.Bucket, 
                            index : Int, 
                            side: Side) : CompactGrid.Bucket = 
    if (side == EMPTY)
      //3 = 1* 2^1 + 1*2^0 = 000...11
      // (3 << (index * BITS_PER_POINT) = 00..11..000
      // ~(3 << (index * BITS_PER_POINT) = 11..00..111
      bucket & (~(3 << (index * BITS_PER_POINT)))
    else if (side == WHITE) 
      // WHITE_VALUE = 000...01
      // WHITE_VALUE << (index * BITS_PER_POINT) = 00...01..00
      bucket | (WHITE_VALUE << (index * BITS_PER_POINT)) 
    else
      // BLACK_VALUE = 000...11
      // BLACK_VALUE << (index * BITS_PER_POINT) = 00...11..00
      bucket | (BLACK_VALUE << (index * BITS_PER_POINT))
    
}//end object CompactGridUtils

case class CompactGrid(val boardDimension : BoardDimension = BoardDimension.STANDARD_BOARD_DIM, 
                       val gridArray : CompactGrid.GridRepr = CompactGrid.generateGridArray(BoardDimension.STANDARD_BOARD_DIM)) extends Grid {
  
  protected[board] def getIndex(pos : Position) : Int = boardDimension.row * pos.column + pos.row
  
  def getBucket(pos : Position) : Int = 
    getIndex(pos)*CompactGrid.BITS_PER_POINT / CompactGrid.POINTS_PER_BUCKET
  
  def getBucketIndex(pos : Position) =
    getIndex(pos) - getBucket(pos) * CompactGrid.POINTS_PER_BUCKET
    
  def get(pos : Position) : Side = {
    //index is which linear index 0 -> (19x19-1)
    val index = getIndex(pos)
    
    //bucket is which integer the index falls in
    val bucket = getBucket(pos)
      
    //bucket index is which point in the bucket, 2 bits per point
    val bucketIndex = getBucketIndex(pos)
    
    val pointValue = 
      (gridArray(bucket) << (CompactGrid.POINTS_PER_BUCKET*CompactGrid.BITS_PER_POINT-bucketIndex*CompactGrid.BITS_PER_POINT -1)) >> (CompactGrid.POINTS_PER_BUCKET*CompactGrid.BITS_PER_POINT  - 1) 
          
    if (pointValue == CompactGrid.EMPTY_VALUE)
      EMPTY
    else if (pointValue == CompactGrid.WHITE_VALUE )
      WHITE
    else
      BLACK  
  }//end def get(pos : Position) : Side
  
  
  
  def set(pos : Position, side : Side) : CompactGrid = {

    val updatedValue : Int = 
      if (side == BLACK) {
        var allOnes : Int = 0 
        allOnes = ~(allOnes & 0)
        CompactGrid.BLACK_VALUE
      }
      else if (side == WHITE) {
        CompactGrid.WHITE_VALUE
      }
      else {
        CompactGrid.EMPTY_VALUE   
      }
      //CompactGrid.EMPTY_VALUE) << getBucketIndex(pos)*CompactGrid.BITS_PER_POINT 
        
      //val updatedValue : Int = gridArray(getBucket(pos))  //FIXME - I need to do the slider math
    
    CompactGrid(boardDimension, gridArray.updated(getBucket(pos), updatedValue))
  }//end def set(pos : Position, side : Side) : CompactGrid

}//end case class CompactGrid extends Grid

