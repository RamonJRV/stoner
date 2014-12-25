name := "stoner"

version := "0.1"

scalaVersion := "2.11.4"

resolvers += "TypeSafe Repository" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.2"

//logLevel := Level.Debug

scalacOptions += "-deprecation"
