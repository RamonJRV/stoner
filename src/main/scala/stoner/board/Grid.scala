package stoner.board

import scala.annotation.tailrec

import scala.collection.immutable.HashSet
import scala.collection.TraversableLike
import scala.collection.GenTraversableOnce
import java.util.Arrays

import org.apache.spark.mllib.linalg.{Vector, DenseVector, SparseVector}

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
  
  def apply(column : Dimension, row : Dimension) = get(column,row)
  
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
  def set(pos : Position, side : Side) : Grid
  
  def set(pf: PosFlip) : Grid = pf match {case PosFlip(p,s) => set(p,s)}
  
  def set(pfs : GenTraversableOnce[PosFlip]) : Grid = (this /: pfs)(_ set _)
  
  
  def setCapturedBlack(captured: Int) : Grid
  def setCapturedWhite(captured: Int) : Grid
  
  /**
   * Determines whether or not the given Position is legally within the 
   * dimensions of the grid.
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
   * Returns a Set of Positions that represent the liberties of the stone at 
   * the given position.  
   * 
   * @param pos The Position of one stone in a group.
   * 
   * @return A Set of Positions representing the liberties of a stone at 
   * Position pos, an empty Set if the group has no liberties.
   *   
   */
  def liberties(pos : Position) : Set[Position] = 
    getNeighbors(pos).filter((p) => get(p) == EMPTY)
    
  /**
   * Identifies the Position of all stones that are part of the same group as
   * the stone at the given position.
   * @param pos The Position holding the stone
   * @return The Positions (including the pos parameter) of all stones that are
   * the same side as the stone at pos if pos is occupied, empty Set otherwise.
   */
  def identifyGroup(pos: Position) : Set[Position] = {
    
    val side = get(pos)
    
    @tailrec
    def idGroupRec(posToSearch: Set[Position],
                   acc: Set[Position]) : Set[Position]= {
      if(posToSearch.isEmpty) acc
      else {
    	val h = posToSearch.head
    	val t = posToSearch.tail

    	//state farm
    	def goodNeighbor(p: Position) = get(p) == side && !acc.contains(p) 

    	idGroupRec(getNeighbors(h).filter(goodNeighbor) ++ t,acc + h)
      }//end else to if(posToSearch.isEmpty)
    }//end def idGroupRec(pos: Position, side: Side, acc: Set[Position])

    idGroupRec(HashSet[Position](pos), new HashSet[Position]())
      
  }//end def findGroup(pos: Position, side: Side)
  
  /**
   * Counts the number of liberties of the group associated with the stone 
   * at the given Position.
   * 
   * @param pos The Position of one stone in a group
   * 
   * @return The Set of Positions representing the liberties of the group 
   * associated with the stone at pos.  An empty Set if the group has no
   * liberties.
   * 
   */
  def groupLiberties(pos: Position) : Set[Position] = {
    identifyGroup(pos).flatMap(liberties)
  }//end def countLiberties(pos: Position) : Set[Position]
  
  /**Determines whether or not the group associated with the stone at Position
   * pos is alive, e.g. has at least one liberty.
   * 
   *  ("While I thought that I was learning how to live, I have been learning
   *  how to die" - Benjamin Franklin).
   *  
   *  @param pos The Position of one stone in a group
   *  
   *  @return True if the group associated with the stone at pos is alive, 
   *   false otherwise.
   *   
   */
  def isAlive(pos: Position) : Boolean = !groupLiberties(pos).isEmpty

  /**
   * Flattens the internal representation of the grid into a 1-D array of Sides.
   */
  def flatten : Array[Side] = 
    (for(c <- Range(0,boardDimension.column) ; 
         r <- Range(0,boardDimension.row))
      yield get(c,r)).toArray
        
      
  def flattenNumeric : Array[Double] =
    (for(c <- Range(0,boardDimension.column) ; 
         r <- Range(0,boardDimension.row))
      yield get(c,r).toDouble).toArray
      
  def flattenSparkVector : Vector = {
    
    val a : Array[Double] = flattenNumeric
    
    //use a sparse matrix if less than 10% of the position are filled
    if (a.count(_ != 0.0) < a.length / 10) {
      val arrWithInd = a.zipWithIndex.filter(_._1 != 0.0)
      new SparseVector(a.length, arrWithInd.map(_._2), arrWithInd.map(_._1))
    }
    else {
      new DenseVector(flattenNumeric)
    }
      
    
  }//end def flattenSparkVector : Vector
    
  /**
   * Provides a "deep" hashCode of the grid, i.e. based on contents.
   * 
   * @return A hash value that is based on the contents of the grid.  The 
   * collision probability of two disimilar grids is 1/2^32.
   */
  @Override
  override def hashCode = Arrays.hashCode(flatten)
  
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
  
  /**
   * Prints a pretty representation of the Board.
   */
  override def toString  = {
    val lines = 
      for {
        r <- Range(0, boardDimension.row)
      } yield Range(0,boardDimension.column).map((c: Int) => posToChar(get(c,r))).mkString(" ")
      
    lines.mkString("\n")
  }
  
}//end trait Grid

//31337