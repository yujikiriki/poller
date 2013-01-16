package co.s4n.poller.infrastructure.acl

import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos
import co.s4n.poller.infrastructure.acl.PollerProperties._
import java.util.UUID

object ReportGenerationService {
  private val exportador = new ExportadorDocumentos
  
  def generate( collName: String, format: String, jasperTemplate: String ) = {
    exportador.setMongoURI( "mongodb://" + mongoDbURL + ":" + mongoDbPort + "/" + databaseName )
    exportador.setRutaObjetoJasper( jasperTemplatePath + jasperTemplate + ".jasper" )
    exportador.setColeccion( collName )
    generateForTheSpecifiedFormat( format, collName )
  }
  
  private def generateForTheSpecifiedFormat( collName: String, format: String ) = {
	val uuid = UUID.randomUUID.toString( )
    format match {
      case "zip" => println( "zip" )
      case "csv" => generateCSV( uuid, collName )
      case "pdf" => generatePDF( uuid, collName )
    }
  }
  
  private def generateCSV( uuid: String, collName: String ) = {
    exportador.setRutaSalida( reporteRutaSalidaCSV + "_" + collName + "_" + uuid + ".csv" )
    exportador.exportarDocumentoACsv    
  }
  
  private def generatePDF( uuid: String, collName: String ) = {
    exportador.setRutaSalida( reporteRutaSalidaPDF + "_" + collName + "_" + uuid + ".pdf" )
    exportador.exportarDocumentoAPDF
  } 
}