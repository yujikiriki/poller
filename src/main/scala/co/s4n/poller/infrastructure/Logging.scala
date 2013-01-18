package co.s4n.poller.infrastructure

import org.slf4j.LoggerFactory

trait Logging {
  val loggerName = this.getClass
  lazy val log = LoggerFactory.getLogger( loggerName )
}
