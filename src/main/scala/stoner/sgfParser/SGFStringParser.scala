package stoner.sgfParser

import stoner.board.{Side, StateTransition, WHITE, BLACK, EMPTY, PosFlip,Move,Game,Board}

import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.Traversable
import scala.collection.Map

import scala.io.{Source,Codec}
import java.io.File

import java.nio.charset.CodingErrorAction

object SGFStringParser {
  
  //some files are utf8 and some are iso8859
  //this code lets the Source.fromFile work with either.
  implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  
  val sideMap = Map(('B',BLACK), ('W',WHITE))
    
  /**
   * Parses the lines of a file into a Game.
   * 
   * @param fileName the name of a file to open, read, and parse.
   * 
   * @return A Some(Game) if the file was parsable, None otherwise.
   */
  def parseLines(fileName : String) : Option[Game] = 
    parseLines(Source.fromFile(fileName).getLines.toList)
    
  
  /**
   * Parses the lines of a file into a Game.
   * 
   * @param file the File to open, read, and parse.
   * 
   * @return A Some(Game) if the file was parsable, None otherwise.
   */
  def parseLines(file : File) : Option[Game] = 
    parseLines(Source.fromFile(file).getLines.toList)
  
  /**
   * Parses a sequence of Strings into a Game.
   * 
   * @param lines the Strings to parse.
   * 
   * @return A Some(Game) if the Strings were parsable, None otherwise.
   */
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
      
      Some(Game(new Board(st_o.toList), winner_o))
    }//end else to if (!lines.head.startsWith("(;SZ[19]"))
  }//end def parseLines(lines : List[String])

}//end object SGFStringParser

//31337