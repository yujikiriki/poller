package co.s4n.poller.actors

import akka.actor.Actor
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import co.s4n.poller.{ ReportDone, Check, BuildingReport }
import co.s4n.poller.ReportDone

class CollectionChecker( dbURL: String, dbName: String, aCollName: String, aFormat: String ) extends Actor {  
  val collName: String = aCollName
  val format: String = aFormat
  val mongoColl = MongoConnection( dbURL )( dbName )( aCollName )
  
  def receive = {
    case check: Check => checkCollection
  }
  
  /**
   * Looks for a Document in the Collection thats has a "done" attribute 
   */
  def checkCollection = {
    val doc = MongoDBObject( "tipoDocumento" -> "done" )
    
    /* TODO: Should be wrapper inside a Loan Pattern */
    try {
      if( 1 == mongoColl.findOne( doc ).size ) {
        /* Remove the document with the done flag so it does not appear in the report */
        mongoColl.remove( doc )
        sender ! new ReportDone( collName, format )
      }
      else {
        sender ! new BuildingReport( collName, format )
      }
    } catch {
      case e: java.io.IOException => println( "IOException" )
      case e: java.net.ConnectException => println( "ConnectException" )
      case e: java.lang.Exception => println( "Exception" )
    }
  }
  
}