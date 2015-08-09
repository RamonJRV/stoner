package stoner

/**
 * The board package contains the primary representations of a Go game.  
 * 
 * The very bottom of the representation is a Side which can be one of three
 * values: Black, White, or Empty.
 * 
 * A Grid is a storage mechanism for the 2-D Array of sides and the number of
 * stones taken by each side.
 * 
 * A Board contains a sequence of Grids which is the progression of the
 * game.  
 * 
 * And finally a Game is simply a Board and a declared winner.
 */
package object board {
    
  type Dimension = Int
  type Column = Dimension
  type Row = Dimension
    
  final val STANDARD_COLUMNS : Column = 19
  final val STANDARD_ROWS : Row = 19
  
  final val STANDARD_POINT_COUNT = STANDARD_COLUMNS.toInt*STANDARD_ROWS.toInt
  
}// package object board

//31337