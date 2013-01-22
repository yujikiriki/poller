package co.s4n.poller.infrastructure.persistence

import com.mongodb.casbah.{MongoConnection, MongoCollection}
import co.s4n.poller.infrastructure.acl.PollerProperties._
import co.s4n.poller.infrastructure.Logging
import scala.util.{ Try, Success, Failure }
import co.s4n.poller.infrastructure.DBConnectionException

trait MongoDBLoan extends Logging { 

  def loan[T]( f: MongoConnection => T ): T = Try( MongoConnection( mongoDbURL ) ) match {
    case Success( conn ) => {
      val result: T = f( conn )
      conn.close
      result
    }
    case Failure( ex ) => {
      log.error( ex.getMessage )
      throw new DBConnectionException( "No se pudo conectar a MongoDB. ¿La URL es correcta? URL = " + mongoDbURL  )
    }
  }
  
}