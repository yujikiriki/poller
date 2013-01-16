package co.s4n.poller.infrastructure.acl

import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos
import co.s4n.poller.infrastructure.acl.PollerProperties._
import java.util.UUID

object ReportGenerationService {
  private val exportador = new ExportadorDocumentos
  
  def generate( collName: String, format: String, jasperTemplate: String ): Unit = {
    exportador.setMongoURI( "mongodb://" + mongoDbURL + ":" + mongoDbPort + "/" + databaseName )
    exportador.setRutaObjetoJasper( jasperTemplatePath + jasperTemplate + ".jasper" )
    exportador.setColeccion( collName )
    generateForTheSpecifiedFormat( collName, format )
  }
  
  private def generateForTheSpecifiedFormat( collName: String, format: String ) = {
    format match {
      case "zip" => println( "zip" )
      case "csv" => generateCSV( collName )
      case "pdf" => generatePDF( collName )
    }
  }
  
  private def generateCSV( collName: String ) = {
    exportador.setRutaSalida( reporteRutaSalidaCSV + collName + ".csv" )
    exportador.exportarDocumentoACsv    
  }
  
  private def generatePDF( collName: String ) = {
    exportador.setRutaSalida( reporteRutaSalidaPDF + collName + ".pdf" )
    exportador.exportarDocumentoAPDF
  } 
}