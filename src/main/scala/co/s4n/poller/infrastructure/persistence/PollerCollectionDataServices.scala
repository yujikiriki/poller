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
      coll.findOne( doc ) match {
        case Some( foundDoc ) => {
       	  coll.remove( foundDoc )
          true  
        }
        case None => false
      }
    }
    loan( f )
  }
  
  def removeReportCollection( aCollName: String ): Unit = {
    def f( conn: MongoConnection ): Unit = {
      val coll: MongoCollection = conn( databaseName )( aCollName )
      coll.drop
    }
    loan( f )
  }
}
