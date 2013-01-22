package co.s4n.poller

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith( classOf[JUnitRunner] )
class PollerCollectionDataServicesTest extends FunSuite {
  import co.s4n.poller.infrastructure.persistence.PollerCollectionDataServices._
  
  test( "Check if the report is done" ) {
	  assert( isReportDone( "" ) )
  }
  
  test( "Delete the report collection from the database" ) {
    
  }

}