package co.s4n.poller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.jayway.restassured.RestAssured._
import org.scalatest.FunSuite
import com.jayway.restassured.matcher.RestAssuredMatchers._
import org.hamcrest.Matchers._
import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import org.scalatest.BeforeAndAfter

@RunWith( classOf[JUnitRunner] )
class PollerServiceTest extends FunSuite with BeforeAndAfter {
  /*
    curl -v -H 'Content-Type:application/json' -d '{ "collName" : "A long string of the collection name", "format" : "pdf|csv|zip" }' http://localhost:7474/tasks 
  */

  before {
    /* RestAssured config */
    baseURI = "http://192.168.1.77"
    port = 7474
  }
  
  test( "Is service alive?" ) {
    RestAssured.expect
      .statusCode( 200 )
      .body( equalTo( "PONG" ) ) 
    .when.get( "/tasks" )
    info( "The service is aliave and happy to serve!!!" )
  }
  
  test( "Generate a report" ) {
    RestAssured
      .given
        .contentType( "application/json" )      
        .body( """{ "collName" : "ReporteDocumentosClienteSIN_IDENTIFICACION_EXTERIOR_PLATAFORMA", "format" : "csv", "jasperTemplate" : "ReporteDocumentosCliente", "email" : "" }""" )
      .expect
        .statusCode( 201 )
      .post( "/tasks" )
  }
  
  ignore( "Verify the generation of a requested report" ) ( pending )
  
  ignore( "Load test usign creation" ) {
    for( i <- 1 to 20 ) {    
	    RestAssured
	     .given
	       .contentType( "application/json" )      
	       .body( "{ \"collName\" : \"A very long string\", \"format\" : \"zip|pdf|csv\" }" )
	     .expect
	       .statusCode( 201 )
	     .post( "/tasks" )
    }
  }
}