package stoner.sgfParser

import scala.io.Source

import java.io.File

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import stoner.board.EMPTY 

@RunWith(classOf[JUnitRunner])
class sgfParserSuite extends FunSuite {
  
  def filenameToLines(f: String) = Source.fromFile(f).getLines.toList
  
  val topDir = "src/main/resources/GoGoD/Database/"
  
  val testList = filenameToLines(topDir + "0196-1699/1698-12-21a.sgf")
    
  val testListNoWinner = filenameToLines(topDir + "0196-1699/0196-00-00.sgf")
    
  
  test("Test List is Valid") {
    assert(testList.size > 0)
    
    assert(testList.head == "(;SZ[19]FF[3]")
  }//end test("Test List is Valid")
  
  test("Game Parsing of test list") {
    val game = SGFStringParser.parseLines(testList)
    
    assert(game.isDefined)
  }//end test("Game Parsing of test list")
  
  test("Game Parsing of Unknown Winner") {
    val game = SGFStringParser.parseLines(testListNoWinner)
    
    assert(game.isDefined)
    assert(game.get.winner == EMPTY)
  }//end test("Game Parsing of Unknown Winner")
  
  
}//end class sgfParserSuite

//31337
