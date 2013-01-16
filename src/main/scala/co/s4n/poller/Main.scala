package co.s4n.poller

import spray.can.server.SprayCanHttpServerApp
import akka.actor.{Props,ActorSystem}
import co.s4n.poller.actors.WorkersRouter
import akka.routing.RoundRobinRouter
import com.typesafe.config.ConfigFactory
import java.net.InetAddress
import co.s4n.poller.web._

object Main extends App with SprayCanHttpServerApp {
  /* Load the custom configuration file */
  val akkaSystem = ActorSystem( "PollerSystem", ConfigFactory.load().getConfig( "PollerSystem" ) )
  /* Create only one worker router (actorOf) */
  val router = akkaSystem.actorOf( Props[WorkersRouter].withRouter( RoundRobinRouter( nrOfInstances = 10 ) ), name = "WorkerRouter" )
  val handler = akkaSystem.actorOf( Props[PollerServiceActor] )
  newHttpServer(handler) ! Bind( interface = "localhost", port = 7474 )
}