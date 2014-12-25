package sgfParser.Board

case class StandardBoard(dimension : BoardDimension = (STANDARD_COLUMN,STANDARD_ROW), 
                         board : BoardRepr) extends BoardSpec

//31337