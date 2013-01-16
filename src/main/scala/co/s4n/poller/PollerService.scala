package co.s4n.poller

import akka.actor.{Props, Actor}
import spray.routing.HttpService
import spray.routing.Directives
import java.util.Date
import co.s4n.poller.BeginMessageJsonProtocol._
import spray.httpx.SprayJsonSupport._
import co.s4n.poller.actors.WorkersRouter
import akka.routing.RoundRobinRouter
import spray.http.StatusCodes._

class PollerServiceActor extends Actor with PollerService {
  def actorRefFactory = context  
  def receive = runRoute( route )
}

trait PollerService extends HttpService {
  
  /* Reference to the local router */
  val router = actorRefFactory.actorFor( "/user/WorkerRouter" )
  
  val route = {
    path( "tasks" ) {
      post {
	    entity( as[Begin] ) { begin =>
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
