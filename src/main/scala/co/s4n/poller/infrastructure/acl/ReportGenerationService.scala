package co.s4n.poller.infrastructure.acl

import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos

object ReportGenerationService {
  private val exportador = new ExportadorDocumentos
  exportador.setRutaObjetoJasper( "/home/yuji/Desktop/ReporteDocumentosCliente.jasper" )
  exportador.setMongoURI( "mongodb://192.168.1.29:27017/certifacturadb" )
  
  def generate( collName: String, format: String ) = {
    exportador.setColeccion( collName )
    generateForTheSpecifiedFormat( format )
  }
  
  private def generateForTheSpecifiedFormat( format: String ) = format match {
    case "zip" => println( "zip" )
    case "csv" => generateCSV 
    case "pdf" => generatePDF
  }
  
  private def generateCSV = {
    println( "Generando CSV..." )
    exportador.setRutaSalida( "/home/yuji/Desktop/prueba.csv" )
    exportador.exportarDocumentoACsv    
  }
  
  private def generatePDF = {
    println( "Generando PDF..." )
    exportador.setRutaSalida( "/home/yuji/Desktop/prueba.pdf" )
    exportador.exportarDocumentoAPDF
  } 
}