package stoner.board

object Rotation {
  
  type rotation = (Position,BoardDimension) => Position
  
  val allRotations = Array[rotation](identity,horizontal, vertical, diagonal)
  
  def identity(p : Position, bd : BoardDimension) = p
  
  def horizontal(p : Position, bd : BoardDimension) = 
    Position(bd.column-1-p.column, p.row)

  def vertical(p : Position, bd : BoardDimension) = 
    Position(p.column, bd.row-1-p.row)
        
  def diagonal(p : Position, bd : BoardDimension) =
    Position(bd.column-1-p.column, bd.row-1-p.row)
  
  def rotateState(r : rotation)(st : StateTransition, dim : BoardDimension) = st match {
    case PosFlip(p,s) => PosFlip(r(p, dim),s)
    case Move(p,s) => Move(r(p,dim), s)
  }
    
  def rotate(r : rotation)(b : Board) = 
    new Board(b.transitions.map((st : StateTransition) => rotateState(r)(st,b.boardDimension)),
              b.boardDimension)
        
  def applyAllRotations(board : Board) : Array[Board] = 
    allRotations.map((r: rotation) => rotate(r)(board))
    
  def applyAllRotations(game : Game) :  Array[Game] = 
    applyAllRotations(game.board).map(Game(_, game.winner))
}//end object Rotation

//31337