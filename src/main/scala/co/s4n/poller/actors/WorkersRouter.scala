package co.s4n.poller.actors

import akka.actor.{Actor, Props, OneForOneStrategy}
import akka.util.duration._
import akka.actor.SupervisorStrategy._
import co.s4n.poller.Begin
import akka.routing.RoundRobinRouter

class WorkersRouter extends Actor {
  
  override val supervisorStrategy = OneForOneStrategy( maxNrOfRetries = 3, withinTimeRange = 100 seconds ) {
    case _: java.io.IOException => {
      println( "No hubo conexion a la base de datos" )
      Restart
    }
    case _: Exception => {
      println( "Error nuevo y no controlado" )
      Stop
    }
  }

  val worker = context.actorOf( Props[Poller].withDispatcher( "workerDispatcher" ), name = "Poller" )
  
  def receive = {
    case begin: Begin => worker ! begin
  }
}