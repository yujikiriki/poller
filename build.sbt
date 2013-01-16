import AssemblyKeys._

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "xml", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", "commons", "collections", xs @ _*) => MergeStrategy.last
    case PathList("org", "w3c", "dom", xs @ _*) => MergeStrategy.last
    case ".project" => MergeStrategy.discard
    case ".classpath" => MergeStrategy.discard
    case "jasperreports_extension.properties" => MergeStrategy.first
    case x => old(x)
  }
}

test in assembly := {}

jarName in assembly := "poller.jar"

name := "Poller"

version := "0.1"

scalaVersion := "2.9.2"

scalacOptions ++= Seq( "-deprecation", "-unchecked", "-Ydependent-method-types" )

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "spray repo" at "http://repo.spray.io"
)

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0.4",
	"com.chuusai" %% "shapeless" % "1.2.3",
	"io.spray" % "spray-http" % "1.0-M7",
	"io.spray" % "spray-httpx" % "1.0-M7",
	"io.spray" % "spray-util" % "1.0-M7",
	"io.spray" % "spray-io" % "1.0-M7",
	"io.spray" % "spray-can" % "1.0-M7",
	"io.spray" % "spray-routing" % "1.0-M7",
	"io.spray" % "spray-json" % "1.2.2" cross CrossVersion.full,
	"junit" % "junit" % "4.10" % "test",
	"org.scalatest" %% "scalatest" % "1.8" % "test",
	"com.jayway.restassured" % "rest-assured" %  "1.7.1" % "test",
	"commons-beanutils" % "commons-beanutils" % "1.8.3",
	"commons-collections" % "commons-collections" % "3.2.1",
	"commons-digester" % "commons-digester" % "2.1",
	"commons-logging" % "commons-logging" % "1.1.1",
	"com.lowagie" % "itext" % "2.1.7",
	"net.sf.jasperreports" % "jasperreports" % "4.7.0",
	"commons-pool" % "commons-pool" % "1.6",
	"log4j" % "log4j" % "1.2.16",	
	"org.apache.poi" % "poi" % "3.9",
	"com.mongodb.casbah" % "casbah_2.9.0-1" % "2.1.5.0",
	"org.slf4j" % "slf4j-nop" % "1.6.0"            
)
