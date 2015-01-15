package stoner.sgfParser

import scala.io.Source

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class sgfParserSuite extends FunSuite {
  
  println(System.getProperty("user.dir"))
  val testList = Source.fromFile("src/main/resources/GoGoD/Database/0196-1699/1698-12-21a.sgf").getLines.toList

  test("Test List is Valid") {
    assert(testList.size > 0)
    
    assert(testList.head == "(;SZ[19]FF[3]")
  }//end test("Test List is Valid")
  
  test("Game Parsing of test list") {
    val game = SGFStringParser.parseLines(testList)
    
    assert(game.isDefined)
  }
}//end class sgfParserSuite

//31337
