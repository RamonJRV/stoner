name := "stoner"

version := "0.199"

scalaVersion := "2.11.4"

resolvers += "TypeSafe Repository" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.2"

//logLevel := Level.Debug

scalacOptions += "-deprecation"
