package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoDB
import co.s4n.poller.infrastructure.Logging
import scala.util.{Try}
import com.mongodb.DBObject

object PollerCollectionDataServices extends MongoDBLoan with Logging {
  
  def isReportDone( aCollName: String ): Boolean = {
    def f( conn: MongoConnection ): Boolean = {
      val coll: MongoCollection = conn( databaseName )( aCollName )
      val doc: DBObject = MongoDBObject( "tipoDocumento" -> "done" )
      if( 1 == coll.findOne( doc ).size ) {
        coll.remove( doc )
        log.debug( "Documento con estado 'done' eliminado" )
        true
      }
      false
    }
    loan( f )
  }
  
  def removeReportCollection( aCollName: String ): Boolean = {
    def f( conn: MongoConnection ): Boolean = {
      val coll: MongoCollection = conn( databaseName )( aCollName )
      coll.drop
      conn.close
      true
    }
    loan( f )
  }
}
