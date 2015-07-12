package stoner.research

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Helper functions for creating spark configurations on the local host.
 */
object LocalConf {
  
  val localMaster = "local[*]"

  val DEFAULT_NAME = "stoner"
  
  def createConf : SparkConf = createConf(DEFAULT_NAME)
  
  def createConf(name : String) = new SparkConf().setMaster(localMaster)
                                                 .setAppName(name)
  
  def createContext = new SparkContext(createConf)
                                                 
}//end object LocalConf

//31337