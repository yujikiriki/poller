package co.s4n.poller.web
import akka.actor.Actor
import spray.routing.HttpService
import co.s4n.poller.BeginMessageJsonProtocol._
import spray.httpx.SprayJsonSupport._
import spray.http.StatusCodes._
import akka.actor.actorRef2Scala
import co.s4n.poller.Begin
import spray.routing.Directive.pimpApply
import spray.routing.directives.CompletionMagnet.fromObject
import spray.routing.directives.CompletionMagnet.fromStatusObject

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
	      println( "Request received: " + begin )
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
