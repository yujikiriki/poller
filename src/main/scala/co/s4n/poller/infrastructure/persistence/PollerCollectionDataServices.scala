package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoDB

object PollerCollectionDataServices {
  val db: MongoDB = MongoConnection( mongoDbURL )( databaseName )
  
  def isReportDone( aCollName: String ): Boolean = {
    val coll: MongoCollection = db( aCollName )
    val doc = MongoDBObject( "tipoDocumento" -> "done" )
    if( 1 == coll.findOne( doc ).size ) {
      coll.remove( doc )
      true
    }
    false
  }
  
  
}