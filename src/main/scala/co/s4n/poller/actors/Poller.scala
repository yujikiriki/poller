package co.s4n.poller.actors

import akka.util.duration._
import akka.actor.Actor
import co.s4n.poller.{ Begin, ReportDone, Check, BuildingReport }
import java.lang.Long._
import java.util.Calendar
import akka.actor.Scheduler
import akka.actor.Props
import co.s4n.poller.infrastructure.acl.ReportGenerationService
import co.s4n.poller.infrastructure.acl.EmailService

class Poller extends Actor {
  
  def receive = {
    case begin: Begin => {
      scheduleACollectionCheck( begin.collName, begin.format )
    }
    case buildingReport: BuildingReport => { 
      scheduleACollectionCheck( buildingReport.collName, buildingReport.format )
    }
    case reportDone: ReportDone => {
      ReportGenerationService.generate( reportDone.collName, reportDone.format )
      EmailService.send
    }
  }
   
  /**
   * Send a new Check message to the CollectionChecker actor. This method only schedule the Check once.
   */
  def scheduleACollectionCheck( collName: String, format: String ) = context.system.scheduler.scheduleOnce( 50 milliseconds ) {
    val checker = context.actorOf( Props( new CollectionChecker (
      "192.168.1.29",
      "certifacturadb", 
      collName,
      format ) ) )
    
    checker ! new Check( collName, format )
  }
  
}