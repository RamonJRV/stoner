package stoner.sgfParser

import scala.io.Source
import scala.io.BufferedSource

import java.io.File

import stoner.board.Game

object GoGoDParser {
  
  val GO_GO_D_DIR = "src/main/resources/GoGoD/Database/"
  
  val GO_GO_D_FILE = new File(GO_GO_D_DIR)
  
  val SGF_FILE_POSTFIX = ".sgf"
  
  /**
   * Recursively list all of the files below the given file.
   * 
   * @param file The File to look for files underneath.
   * 
   * All of the non-directory Files below file.
   */
  def recursiveListFiles(file: File = GO_GO_D_FILE): Array[File] = {
    val these = file.listFiles
    
    if(these == null)
      Array(file)
    else 
	  these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }//end def recursiveListFiles(f: File): Array[File]
  
  /**
   * Finds all of the .sgf files beneath file.
   * 
   * @param file the File containing .sgf files.
   * 
   * All of the .sgf files below file.
   */
  def getSGFFiles(file: File = GO_GO_D_FILE): Array[File] = 
    recursiveListFiles(file).filter(_.getAbsolutePath.endsWith(SGF_FILE_POSTFIX))
  
  /**
   * Parses all of the .sgf files below fileName into Games.
   * 
   * @param fileName the name of a File containing .sgf files.
   * 
   * @return the Games that were able to be parsed from .sgf files below file.
   * 
   */
//  def getGamesFromSGFFile(fileName : String): Array[Game] =
//    getGamesFromSGFFile(new File(fileName))
    
  /**
   * Parses all of the .sgf files below file into Games.
   * 
   * @param file the File containing .sgf files.
   * 
   * @return the Games that were able to be parsed from .sgf files below file.
   * 
   */
  def getGamesFromSGFFile(file : File): Array[Game] = {
    getSGFFiles(file).par.map(SGFStringParser.parseLines(_)).flatten.toArray
  }//end def getGamesFromSGFFiles(files : Array[File] = getSGFFiles)
  
  
}//end object GoGoDParser

//31337