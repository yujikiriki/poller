package co.s4n.poller.actors

import akka.actor.{ Actor, OneForOneStrategy, Scheduler, Props }
import co.s4n.poller.{ Begin, ReportDone, Check, BuildingReport }
import java.util.Calendar
import co.s4n.poller.infrastructure.acl.ReportGenerationService
import co.s4n.poller.infrastructure.acl.EmailService
import akka.util.duration._
import akka.actor.SupervisorStrategy._
import co.s4n.poller.infrastructure.persistence.PollerCollectionDataServices._
import java.lang.Long._
import akka.actor.ActorLogging

class Poller extends Actor with  ActorLogging {
  val checker = context.actorOf( Props[CollectionChecker], name = "CollectionChecker" )
  
  def receive = {
    case begin: Begin => {  
      log.info( "Begin..." )
      scheduleACollectionCheck( begin.collName, begin.format, begin.jasperTemplate, begin.email )
    }
    case buildingReport: BuildingReport => {
      log.info( "Building report..." )
      scheduleACollectionCheck( buildingReport.collName, buildingReport.format, buildingReport.jasperTemplate, buildingReport.email )
    }
    case reportDone: ReportDone => {
      log.info( "Begin the report generation..." )
      val generatedReportPath = ReportGenerationService.generate( reportDone.collName, reportDone.format, reportDone.jasperTemplate )
      log.info( "Sending email..." )
      EmailService.send( reportDone.email, generatedReportPath )
      log.info( "Deleting the report collection..." )
      removeReportCollection( reportDone.collName )
      log.info( "Done!" )
    }
  }
   
  /**
   * Send a new Check message to the CollectionChecker actor. This method only schedule the Check once.
   */
  def scheduleACollectionCheck( collName: String, format: String, jasperTemplate: String, email: String ): Unit = 
    context.system.scheduler.scheduleOnce( 10 seconds ) {
      checker ! new Check( collName, format, jasperTemplate, email )
    }
  
  override val supervisorStrategy = OneForOneStrategy( maxNrOfRetries = 3, withinTimeRange = 100 seconds ) {
    case _: java.lang.Exception => {
      log.error( "Error no controlado en CollectionChecker" )
      Stop
    }
  }
}