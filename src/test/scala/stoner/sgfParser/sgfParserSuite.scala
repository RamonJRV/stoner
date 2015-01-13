package stoner.sgfParser

import scala.io.Source

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class sgfParserSuite extends FunSuite {
  
  val testList = Source.fromFile("/Users/ramonromeroyvigil/Documents/experimentalData/go/GoGoD/Database/0196-1699/1698-12-21a.sgf").toList

  test("Test List is Valid") {
    assert(testList.size > 0)
  }//end test("Test List is Valid")
}//end class sgfParserSuite

//31337
