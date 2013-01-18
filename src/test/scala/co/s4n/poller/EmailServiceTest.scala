package co.s4n.poller

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import co.s4n.poller.infrastructure.acl.EmailService

@RunWith( classOf[JUnitRunner] )
class EmailServiceTest extends FunSuite {
	
  test( "Send an email if the address is not empty" ) {
    EmailService.send( "yujikiriki@s4n.co", "cadenaDePrueba" )
  }
}