name := "Poller"

version := "0.1"

scalaVersion := "2.9.2"

scalacOptions ++= Seq( "-deprecation", "-unchecked" )

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.10" % "test",
	"org.scalatest" %% "scalatest" % "1.8" % "test",
	"com.typesafe.akka" % "akka-actor" % "2.0.4",
	"com.typesafe.akka" % "akka-remote" % "2.0.4",
	"com.typesafe.akka" % "akka-slf4j" % "2.0.4",
	"com.typesafe.akka" % "akka-testkit" % "2.0.4",	
	"io.spray" % "spray-util" % "1.0-M7",
	"io.spray" % "spray-io" % "1.0-M7",
	"io.spray" % "spray-http" % "1.0-M7",
	"io.spray" % "spray-can" % "1.0-M7",
	"io.spray" % "spray-routing" % "1.0-M7"
)
