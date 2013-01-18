package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoDB
import co.s4n.poller.infrastructure.Logging

object PollerCollectionDataServices extends MongoDBLoan with Logging {
  val conn  = MongoConnection( mongoDbURL )
  
  def isReportDone( aCollName: String ): Boolean = {
    val coll: MongoCollection = conn( databaseName )( aCollName )
    val doc = MongoDBObject( "tipoDocumento" -> "done" )
    if( 1 == coll.findOne( doc ).size ) {
      coll.remove( doc )
      log.debug( "Documento con estado done eliminado" )
      true
    }
    else        
      false
  }
  
  def removeReportCollection( aCollName: String ): Unit = {
    val coll: MongoCollection = conn( databaseName )( aCollName )
    coll.drop
    log.debug( "Colleccion con el reporte eliminada" )
  }
}
