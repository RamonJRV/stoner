package stoner.board

import java.util.Arrays

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
    
}//end object CompactGrid

case class CompactGrid(val boardDimension : BoardDimension = BoardDimension.STANDARD_BOARD_DIM, 
                       val capturedBlack : Int = 0,
                       val capturedWhite : Int = 0,
                       val gridArray : CompactGrid.GridRepr = CompactGrid.generateGridArray(BoardDimension.STANDARD_BOARD_DIM)) extends Grid {
  
  /**Gets the 1-D point index from a 2-D Position.
   * 
   * @param pos : The Position to get the 1-D for
   * @return The numerical index corresponding to the given Position.  The 
   *  return value is in the set {0,1,...,boardDimension.Column*boardDimension.Row-1}
   */
  protected[board] def getIndex(pos : Position) : Int = 
    boardDimension.row * pos.column + pos.row
  
  /**Gets the index within gridArray holding the Bucket storing the given 
   * 2-D Position.
   * 
   * @param pos : The Position to get the Bucket index for
   * @return The index for the Bucket at gridArray[i] which cotains the given 
   *  Position.  The return value is in the set 
   *  {0,1,...,boardDimension.Column*boardDimension.Row/POINTS_PER_BUCKET-1}
   */
  protected[board] def getBucketIndex(pos : Position) : Int = 
    getIndex(pos) / CompactGrid.POINTS_PER_BUCKET
  
  /**Gets the point index within a the Bucket storing the given 2-D Position.
   * 
   * @param pos : The Position to get the Bucket index for
   * @return The point index for the Bucket storing the given Position.  The
   *  return value is in the set {0,1,...,POINTS_PER_BUCKET}
   * 
   */
  protected[board] def getPointIndex(pos : Position) : Int = {
    getIndex(pos) - getBucketIndex(pos)*CompactGrid.POINTS_PER_BUCKET
  }
  
  @Override
  override def get(pos : Position) : Side = {
    val bucket = gridArray(getBucketIndex(pos))
    
    val pointIndex = getPointIndex(pos)
    
    val pointVal = 
      ((bucket << ((CompactGrid.POINTS_PER_BUCKET-pointIndex -1)*CompactGrid.BITS_PER_POINT))
        >> (CompactGrid.POINTS_PER_BUCKET-1)*CompactGrid.BITS_PER_POINT)
        
    if(pointVal == CompactGrid.EMPTY_VALUE)
      EMPTY
    else if(pointVal == CompactGrid.WHITE_VALUE)
      WHITE
    else
      BLACK
  }//end def get(pos : Position) : Side
    
  @Override
  override def set(pos : Position, side : Side) : CompactGrid = {
     
    val newBucketValue =   
      CompactGrid.setPointValueInBucket(gridArray(getBucketIndex(pos)),
                                        getPointIndex(pos),
                                        side)
                                        
    new CompactGrid(boardDimension,
                    capturedBlack,
                    capturedWhite,
                    gridArray.updated(getBucketIndex(pos), newBucketValue))
  }//end def set(pos : Position, side : Side) : CompactGrid

  /**
   * Provides a "deep" hashCode of the grid, i.e. based on contents.
   * @return A hash value that is based on the contents of the grid.  The 
   * collision probability of two disimilar grids is 1/2^32.
   */
  @Override
  override def hashCode : Int = Arrays.hashCode(gridArray)
  
}//end case class CompactGrid extends Grid

//31337