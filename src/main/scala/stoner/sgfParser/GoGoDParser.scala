package stoner.sgfParser

import java.io.File

object GoGoDParser {
  
  val GO_GO_D_DIR = "src/main/resources/GoGoD/Database/"
  
  def recursiveListFiles(f: File = new File(GO_GO_D_DIR)): Array[File] = {
    val these = f.listFiles
	these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }//end def recursiveListFiles(f: File): Array[File]
  
}//end object GoGoDParser

//31337