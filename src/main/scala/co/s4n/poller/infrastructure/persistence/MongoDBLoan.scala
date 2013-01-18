package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import co.s4n.poller.infrastructure.Logging

trait MongoDBLoan extends Logging {
  
  /**
   * Loan call for MongoDB
   */
  def loan( f: MongoConnection => AnyVal ) = {
    var conn: MongoConnection = null
    try {
      conn = MongoConnection( mongoDbURL )
      /* Call the function */
      f( conn )
    } catch {
      case e: Exception => {
        e.printStackTrace
        log.error( e.getMessage( ) ) 
      }
    } finally {
      conn.close
    }
  }
}