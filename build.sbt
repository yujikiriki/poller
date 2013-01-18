
import AssemblyKeys._

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "xml", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", "commons", "collections", xs @ _*) => MergeStrategy.last
    case PathList("org", "w3c", "dom", xs @ _*) => MergeStrategy.last
    case "jasperreports_extension.properties" => MergeStrategy.last    
    case ".project" => MergeStrategy.discard
    case ".classpath" => MergeStrategy.discard
    case "defaultconfig.properties" => MergeStrategy.discard
    case x => old(x)
  }
}

test in assembly := {}

jarName in assembly := "poller.jar"

name := "Poller"

version := "0.1"

scalaVersion := "2.9.2"

scalacOptions ++= Seq( "-deprecation", "-unchecked", "-Ydependent-method-types" )

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "spray repo" at "http://repo.spray.io"
)

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0.4",
	"com.typesafe.akka" % "akka-slf4j" % "2.0.4",
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
	"com.lowagie" % "itext" % "2.1.7",
	"net.sf.jasperreports" % "jasperreports" % "4.7.0",
	"org.apache.poi" % "poi" % "3.9",
	"com.mongodb.casbah" % "casbah_2.9.0-1" % "2.1.5.0"	,
	"ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime"
)
