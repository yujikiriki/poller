package co.s4n.poller.actors

import akka.actor.Actor
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import co.s4n.poller.{ ReportDone, Check, BuildingReport }
import co.s4n.poller.ReportDone
import co.s4n.poller.infrastructure.acl.PollerProperties._
import co.s4n.poller.infrastructure.persistence.PollerCollectionDataServices._
import spray.util.SprayActorLogging

class CollectionChecker( ) extends Actor with SprayActorLogging {  
  
  def receive = {
    case check: Check => checkCollection( check )
  }
  
  /**
   * Looks for a Document in the Collection thats has a "done" attribute 
   */
  def checkCollection( check: Check ): Unit =
    if( isReportDone( check.collName ) )
      sender ! new ReportDone( check.collName, check.format, check.jasperTemplate, check.email )
    else
      sender ! new BuildingReport( check.collName, check.format, check.jasperTemplate, check.email )
}