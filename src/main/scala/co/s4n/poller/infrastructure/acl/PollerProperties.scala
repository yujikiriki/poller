package co.s4n.poller.infrastructure.acl

import com.typesafe.config.ConfigFactory

object PollerProperties {
  private val conf = ConfigFactory.load()
  val reporteRutaSalidaPDF: String = conf.getString( "PollerSystem.JasperConfiguration.reporteRutaSalidaPDF" )
  val reporteRutaSalidaCSV: String = conf.getString( "PollerSystem.JasperConfiguration.reporteRutaSalidaCSV" )
  val jasperTemplatePath: String = conf.getString( "PollerSystem.JasperConfiguration.jasperTemplatePath" )
  val mongoDbURL: String = conf.getString( "PollerSystem.DatabaseConfiguration.mongodb.ip" )
  val mongoDbPort: String = conf.getString( "PollerSystem.DatabaseConfiguration.mongodb.port" )
  val databaseName: String = conf.getString( "PollerSystem.DatabaseConfiguration.name" )
}