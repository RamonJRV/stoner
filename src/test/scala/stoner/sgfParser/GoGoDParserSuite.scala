package stoner.sgfParser


import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GoGoDParserSuite extends FunSuite {
  
  test("Recursive List files") {
    
    val files = GoGoDParser.recursiveListFiles()
    
    assert(files.length > 0)
  }//end test("Recursive List files")
}//end class GoGoDParserSuite extends FunSuite

//31337