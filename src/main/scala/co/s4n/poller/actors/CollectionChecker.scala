package co.s4n.poller.actors

import akka.actor.Actor
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import co.s4n.poller.{ ReportDone, Check, BuildingReport }
import co.s4n.poller.ReportDone
import co.s4n.poller.infrastructure.acl.PollerProperties._

class CollectionChecker( aCollName: String ) extends Actor {  
  val mongoColl = MongoConnection( mongoDbURL )( databaseName )( aCollName )
  
  def receive = {
    case check: Check => checkCollection( check )
  }
  
  /**
   * Looks for a Document in the Collection thats has a "done" attribute 
   */
  def checkCollection( check: Check ) = {
    val doc = MongoDBObject( "tipoDocumento" -> "done" )
    
    /* TODO: Should be wrapper inside a Loan Pattern */
    try {
      if( 1 == mongoColl.findOne( doc ).size ) {
        /* Remove the document with the done flag so it does not appear in the report */
        mongoColl.remove( doc )
        sender ! new ReportDone( check.collName, check.format, check.jasperTemplate, check.email )
      }
      else {
        sender ! new BuildingReport( check.collName, check.format, check.jasperTemplate, check.email )
      }
    } catch {
      case e: java.io.IOException => println( "IOException" )
      case e: java.net.ConnectException => println( "ConnectException" )
      case e: java.lang.Exception => println( "Exception" )
    }
  }
  
}