
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

scalaVersion := "2.10.0"

scalacOptions ++= Seq( "-deprecation", "-unchecked", "-feature" )

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo" at "http://repo.spray.io",
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.1.0",
	"com.typesafe.akka" %% "akka-slf4j" % "2.1.0",
	"ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
	"com.chuusai" %% "shapeless" % "1.2.3",
	"io.spray" % "spray-http" % "1.1-M7",
	"io.spray" % "spray-httpx" % "1.1-M7",
	"io.spray" % "spray-util" % "1.1-M7",
	"io.spray" % "spray-io" % "1.1-M7",
	"io.spray" % "spray-can" % "1.1-M7",
	"io.spray" % "spray-routing" % "1.1-M7",
	"io.spray" %% "spray-json" % "1.2.3",
	"org.parboiled" % "parboiled-core" % "1.1.4",
	"com.github.tmingos" % "casbah_2.10" % "2.5.0-SNAPSHOT",
	"junit" % "junit" % "4.10" % "test",
	"org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
	"com.jayway.restassured" % "rest-assured" %  "1.7.2" % "test",
	"com.lowagie" % "itext" % "2.1.7",
	"net.sf.jasperreports" % "jasperreports" % "4.7.0",
	"org.apache.poi" % "poi" % "3.9"
)
