package co.s4n.poller

import spray.json.DefaultJsonProtocol
import spray.httpx.unmarshalling.{Unmarshaller, pimpHttpEntity}
import spray.util._
import spray.http._
import MediaTypes._
import spray.json.JsonFormat
import spray.httpx.SprayJsonSupport._

case class Message( collName: String, format: String, jasperTemplate: String, email: String )
case class Begin( collName: String, format: String, jasperTemplate: String, email: String )
case class ReportDone( collName: String, format: String, jasperTemplate: String, email: String )
case class Check( collName: String, format: String, jasperTemplate: String, email: String )
case class BuildingReport( collName: String, format: String, jasperTemplate: String, email: String )

object BeginMessageJsonProtocol extends DefaultJsonProtocol {
  implicit val taskUnmarshaller = jsonFormat( Begin, "collName", "format", "jasperTemplate", "email" )
}