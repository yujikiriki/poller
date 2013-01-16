package co.s4n.poller.actors

import akka.util.duration._
import akka.actor.{ Actor, OneForOneStrategy }
import co.s4n.poller.{ Begin, ReportDone, Check, BuildingReport }
import java.lang.Long._
import java.util.Calendar
import akka.actor.Scheduler
import akka.actor.Props
import co.s4n.poller.infrastructure.acl.ReportGenerationService
import co.s4n.poller.infrastructure.acl.EmailService
import akka.actor.SupervisorStrategy._

class Poller extends Actor {
  val checker = context.actorOf( Props[CollectionChecker], name = "CollectionChecker" )
  
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
  def scheduleACollectionCheck( collName: String, format: String, jasperTemplate: String, email: String ) = 
    context.system.scheduler.scheduleOnce( 10 seconds ) {
      checker ! new Check( collName, format, jasperTemplate, email )
    }
  
  override val supervisorStrategy = OneForOneStrategy( maxNrOfRetries = 3, withinTimeRange = 100 seconds ) {
    case _: java.lang.NullPointerException => {
      println( "Error no controlado en CollectionChecker" )
      Stop
    }
  }
}