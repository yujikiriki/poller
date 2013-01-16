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
      println( "Begin..." )
      scheduleACollectionCheck( begin.collName, begin.format, begin.jasperTemplate, begin.email )
    }
    case buildingReport: BuildingReport => {
      println( "Building report..." )
      scheduleACollectionCheck( buildingReport.collName, buildingReport.format, buildingReport.jasperTemplate, buildingReport.email )
    }
    case reportDone: ReportDone => {
      ReportGenerationService.generate( reportDone.collName, reportDone.format, reportDone.jasperTemplate )
      println( "Sending email..." )
      EmailService.send( reportDone.email )
      println( "Done..." )
    }
  }
   
  /**
   * Send a new Check message to the CollectionChecker actor. This method only schedule the Check once.
   */
  def scheduleACollectionCheck( collName: String, format: String, jasperTemplate: String, email: String ) = context.system.scheduler.scheduleOnce( 50 milliseconds ) {
    val checker = context.actorOf( Props( new CollectionChecker ( collName ) ) )
    checker ! new Check( collName, format, jasperTemplate, email )
  }
  
}