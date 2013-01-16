package co.s4n.poller.infrastructure.acl

import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos
import co.s4n.poller.infrastructure.acl.PollerProperties._

object ReportGenerationService {
  private val exportador = new ExportadorDocumentos
  
  def generate( collName: String, format: String, jasperTemplate: String ) = {
    exportador.setMongoURI( "mongodb://" + mongoDbURL + ":" + mongoDbPort + "/" + databaseName )
    exportador.setRutaObjetoJasper( jasperTemplatePath + jasperTemplate + ".jasper" )
    exportador.setColeccion( collName )
    generateForTheSpecifiedFormat( format )
  }
  
  private def generateForTheSpecifiedFormat( format: String ) = format match {
    case "zip" => println( "zip" )
    case "csv" => generateCSV 
    case "pdf" => generatePDF
  }
  
  private def generateCSV = {
    exportador.setRutaSalida( reporteRutaSalidaCSV )
    exportador.exportarDocumentoACsv    
  }
  
  private def generatePDF = {
    exportador.setRutaSalida( reporteRutaSalidaPDF )
    exportador.exportarDocumentoAPDF
  } 
}