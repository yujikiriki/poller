package co.s4n.poller

import spray.can.server.SprayCanHttpServerApp
import akka.actor.{Props,ActorSystem}
import co.s4n.poller.actors.WorkersRouter
import akka.routing.RoundRobinRouter
import com.typesafe.config.ConfigFactory
import java.net.InetAddress

object Main extends App with SprayCanHttpServerApp {
  val machineIp = InetAddress.getLocalHost.getHostAddress
  machineIp match {
    case "127.0.0.1" => printIPErrorMessage
    case  _ => loadSystem
  }
  
  def printIPErrorMessage() = {
    println( "Error: No se puede obtener una IP correcta debido a que el hostname \"" 
        + InetAddress.getLocalHost.getHostName() 
        + "\" resuelve a la IP \"" + machineIp 
        + "\". Por favor modifique el archivo /etc/hosts asignandole al hostname la IP valida del servidor." )
  }
  
  def loadSystem() = {
    /* Load the custom configuration file */
	val akkaSystem = ActorSystem( "PollerSystem", ConfigFactory.load().getConfig( "PollerSystem" ) )
	/* Create only one worker router (actorOf) */
	val router = akkaSystem.actorOf( Props[WorkersRouter].withRouter( RoundRobinRouter( nrOfInstances = 10 ) ), name = "WorkerRouter" )
	val handler = akkaSystem.actorOf( Props[PollerServiceActor] )
	newHttpServer(handler) ! Bind(interface = machineIp, port = 7474 )
  }
}