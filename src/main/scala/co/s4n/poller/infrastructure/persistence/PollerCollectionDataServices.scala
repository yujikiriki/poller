package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoDB

object PollerCollectionDataServices extends MongoDBLoan {
  
  def isReportDone( aCollName: String ): Boolean = {
    def statement( conn: MongoConnection ): Boolean = {
      val coll: MongoCollection = conn( databaseName )( aCollName )
      val doc = MongoDBObject( "tipoDocumento" -> "done" )
      if( 1 == coll.findOne( doc ).size ) {
        coll.remove( doc )
        true
      }
      false
    }
    loan( statement ).asInstanceOf[Boolean]
  }
  
  def removeReportCollection( aCollName: String ): Unit = {
    def statement( conn: MongoConnection ) : Unit = {
      val coll: MongoCollection = conn( databaseName )( aCollName )
      coll.drop
    }
    loan( statement )
  }
}