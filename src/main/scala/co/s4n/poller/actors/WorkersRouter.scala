package co.s4n.poller.actors

import akka.actor.{Actor, Props, OneForOneStrategy, ActorLogging }
import scala.concurrent.duration._
import akka.actor.SupervisorStrategy._
import co.s4n.poller.Begin
import akka.routing.RoundRobinRouter
import scala.language.postfixOps

class WorkersRouter extends Actor with ActorLogging {
 
  val worker = context.actorOf( Props[Poller].withDispatcher( "workerDispatcher" ), name = "Poller" )
  
  def receive = {
    case begin: Begin => worker ! begin
  }
  
  override val supervisorStrategy = OneForOneStrategy( maxNrOfRetries = 3, withinTimeRange = 100 seconds ) {
    case _: net.sf.jasperreports.engine.JRRuntimeException => {
	  log.error( "Jasper error" )
	  Stop
    }
    case _: Exception => {
	  log.error( "Error no controlado en WorkersRouter" )
	  Stop
    }
  }
}