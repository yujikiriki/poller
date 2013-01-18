package co.s4n.poller.infrastructure.acl

import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos
import co.s4n.poller.infrastructure.acl.PollerProperties._
import java.util.UUID
import co.s4n.poller.infrastructure.acl.ZipFileService._
import co.s4n.poller.infrastructure.Logging

object ReportGenerationService extends Logging {
  private val exportador = new ExportadorDocumentos

  def generate( collName: String, format: String, jasperTemplate: String ): String = {
    val mongoLocation = "mongodb://" + mongoDbURL + ":" + mongoDbPort + "/" + databaseName
    log.debug( "mongoLocation = " + mongoLocation )
    exportador.setMongoURI( mongoLocation )
    val jasper = jasperTemplatePath + jasperTemplate + ".jasper"
    log.debug( jasper )
    exportador.setRutaObjetoJasper( jasper )
    exportador.setColeccion( collName )
    generateForTheSpecifiedFormat( collName, format )
  }

  private def generateForTheSpecifiedFormat( collName: String, format: String ): String = {
    format match {
      case "csv" => generateCSV( collName )
      case "pdf" => generatePDF( collName )
      case "zip" => generateZipFile( collName )
    }
  }

  private def generateCSV( collName: String ): String = {
    val path: String = reporteRutaSalidaCSV + collName + ".csv"
    exportador.setRutaSalida( path )
    exportador.exportarDocumentoACsv
    log.debug( "CSV file generated to: " + path )
    collName + ".csv"
  }

  private def generatePDF( collName: String ): String = {
    val path: String = reporteRutaSalidaPDF + collName + ".pdf"
    exportador.setRutaSalida( path )
    exportador.exportarDocumentoAPDF
    log.debug( "PDF file generated to: " + path )
    collName + ".pdf"
  }

  private def generateZipFile( collName: String ): String = {
    val csv: String = generateCSV( collName )
    val pdf: String = generatePDF( collName )
    val path = reporteRutaSalidaPDF + collName + ".zip"
    zipFile( path, List( reporteRutaSalidaCSV + csv, reporteRutaSalidaPDF + pdf ) )
    log.debug( "Zip file generated to: " + path )
    collName + ".zip"
  }
}