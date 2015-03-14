package stoner.sgfParser


import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import java.io.File

@RunWith(classOf[JUnitRunner])
class GoGoDParserSuite extends FunSuite {

  test("Recursive List files works") {

    val files = GoGoDParser.recursiveListFiles()

    assert(files.length > 0)
  }//end test("Recursive List files")
	
  test("getGamesFromSGFFiles works on 2007") {
    val games = GoGoDParser.getGamesFromSGFFile(GoGoDParser.GO_GO_D_DIR + "2007")
    
    assert(games.length > 0)
  }
    
  test("getGamesFromSGFFiles works on 2013") {
    val games = GoGoDParser.getGamesFromSGFFile(GoGoDParser.GO_GO_D_DIR + "2013")
    
    assert(games.length > 0)
  }//end test("getGamesFromSGFFiles works on 2007")
}//end class GoGoDParserSuite extends FunSuite

//31337