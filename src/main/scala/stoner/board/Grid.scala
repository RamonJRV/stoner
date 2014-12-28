package stoner.board

import java.util.Arrays

/** A representation of stones on a Go board and the count of each players'
 *  captured stones.
 * 
 */
trait Grid {
  
  type Side = Int
   
  final val WHITE : Side = -1
  final val BLACK : Side = 1
  final val EMPTY : Side = 0
  
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
  
  val boardDimension : BoardDimension
  
  /**The number of black stones that have been captured.*/
  val capturedBlack : Int
  /**The number of white stones that have been captured.*/
  val capturedWhite : Int
  
  /**
   * Gets the Side stored in the grid for the given Position.
   * 
   * @param pos The position to get the side for
   * @return The side for the given position
   */
  def get(pos: Position) : Side
  
  /**
   * Gets the Side stored in the grid for the given column and row.
   * 
   * @param column The column to get the side for
   * @param row The row to get the side for
   * @return The side for the given column and row combination.
   */
  def get(column : Dimension, row : Dimension) : Side = get(Position(column, row))
  
  /**
   * Sets the side at the given position and returns a new CompactGrid 
   * representing the updated state.
   * 
   * @param pos The position to set the Side for
   * @param side The Side value to set at the given position
   * 
   * @return A new CompactGrid with the state updated at the specified side and
   *  pos
   */
  def set(pos : Position, side : Side) : CompactGrid
  
  /**
   * Determines whether or not the given is legally within the dimensions of 
   * the grid
   * @param pos The Position to evaluate for legality
   * @return True if the given position is within the confines of boardDimension,
   *  false otherwise.
   */
  def isLegalPosition(pos: Position) : Boolean = pos match {
    case Position(col,row) => 
      col >=0 && col < boardDimension.column &&
      row >=0 && row < boardDimension.row
  }//end def isLegalPosition(pos: Position) : Boolean = pos match
  
  protected def iterateAllPossibleNeighbors(pos: Position) : Set[Position] = 
    pos match { case Position(col,row) => Set[Position](Position(col-1, row),
	                                                    Position(col+1, row),
		     		                                    Position(col, row-1),
				                                        Position(col, row+1))
	}
  
  /**
   * Gets the legal neighboring Positions of the given Position
   * @param The Position to get the legal neihbors for
   * @return All of the neighbors of the given Position that are on the grid. 
   */
  def getNeighbors(pos : Position) : Set[Position] = 
    (iterateAllPossibleNeighbors(pos)).filter(isLegalPosition)
  
  /**
   * Flattens the internal representation of the grid into a 1-D array of Sides.
   */
  def flatten : Array[Side] = 
    (for(c <- Range(0,boardDimension.column) ; 
         r <- Range(0,boardDimension.row))
      yield get(c,r)).toArray
  
  /**
   * Provides a "deep" hashCode of the grid, i.e. based on contents.
   * 
   * @return A hash value that is based on the contents of the grid.  The 
   * collision probability of two disimilar grids is 1/2^32.
   */
  @Override
  override def hashCode : Int = Arrays.hashCode(flatten)
  
  /**
   * Provides a "deep" equality check based on the grid.
   * 
   * @return True if the contents of the other grid are equal to the contents
   * of this Grid, false otherwise (more than likely).
   */
  @Override
  override def equals(o: Any) = o match {
    case that: Grid => that.hashCode == hashCode
    case _ => false
  }
  
}//end trait Grid

//31337