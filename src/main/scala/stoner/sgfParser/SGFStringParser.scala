package stoner.sgfParser

import stoner.board.{Side, StateTransition, WHITE, BLACK, EMPTY, PosFlip,Move,Game,Board}

import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.Traversable
import scala.io.Source

object SGFStringParser {
  
  val sideMap = Map('B' -> BLACK, 'W' -> WHITE)
  
  def parseLines(fileName : String) : Option[Game] = 
    parseLines(Source.fromFile(fileName).getLines.toList)
  
  def parseLines(lines : Traversable[String]) : Option[Game] = {
    if (!lines.head.startsWith("(;SZ[19]")) None
    else {
      val st_o = new ArrayBuffer[StateTransition]
      var winner_o = EMPTY
      
      for(line <- lines.drop(1)) {
        
        if(line.startsWith("RE")) {
          if(line(3) == 'B') winner_o = BLACK
          else if(line(3) == 'W') winner_o = WHITE
        }//end if(line.startsWith("RE"))
        else if(line.startsWith("AB")) {
          for(s <- line.drop(3).sliding(2, 4)) {
            st_o.append(PosFlip(PositionTranslator.strRepToPos(s), BLACK))
          }
        }//end else if(line.startsWith("AB"))
        else if(line.startsWith("AW")) {
          for(s <- line.drop(3).sliding(2, 4)) {
            st_o.append(PosFlip(PositionTranslator.strRepToPos(s), WHITE))
          }
        }//end else if(line.startsWith("AW"))
        else if(line.startsWith(";")) {
          val moves = line.split(';').filter(_.size > 0)
          st_o ++= moves.map((m : String) => Move(PositionTranslator.strRepToPos(m.substring(2,4)),
                                                  sideMap(m(0))))
        }//end else if(line.startsWith(";"))
      }//end for(line <- lines.tail)
      
      Some(Game(new Board(st_o), winner_o))
    }
  }//end def parseLines(lines : List[String])

}//end object SGFStringParser

//31337