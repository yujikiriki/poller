package co.s4n.poller.web

import akka.actor.{ Actor, actorRef2Scala, ActorLogging }
import spray.routing.HttpService
import spray.routing.Directive.pimpApply
import spray.httpx.SprayJsonSupport._
import spray.http.StatusCodes._
import spray.routing.directives.CompletionMagnet._
import co.s4n.poller.BeginMessageJsonProtocol._
import co.s4n.poller.Begin
import akka.event.Logging

class PollerServiceActor extends Actor with ActorLogging with HttpService {
  
  val actorRefFactory = context  
  def receive = runRoute( route )
  
  val router = actorRefFactory.actorFor( "/user/WorkerRouter" )  
  val route = {
	  path( "tasks" ) {
		  post {
			  entity( as[Begin] ) { begin =>
			  log.info( "INFO - Resquest received: " + begin )
			  router ! begin
			  complete( Created, "" )
			  }
		  } ~
		  get {
			  complete( "PONG" )
		  }
	  }
  }  
  
  

}
