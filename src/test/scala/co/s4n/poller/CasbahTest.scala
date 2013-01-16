package co.s4n.poller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.mongodb.casbah.Imports._

@RunWith( classOf[JUnitRunner] )
class CasbahTest extends FunSuite {
  
  ignore( "Connection to MongoDB" ) {
    val coll = MongoConnection( "192.168.1.29" )( "certifacturadb" )( "ReporteDocumentosClienteSIN_IDENTIFICACION_EXTERIOR_PLATAFORMA" )
    val doc = MongoDBObject( "tipoDocumento" -> "done" )
    1 === coll.findOne( doc ).size
  }
}