name := "cryptoprotocols-scala"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.5.6"

libraryDependencies += "org.scala-lang" % "scala-actors" % "2.11.8"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.6"

libraryDependencies += "commons-codec" % "commons-codec" % "1.2"

libraryDependencies += "org.bouncycastle" % "bcprov-jdk16" % "1.45"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

