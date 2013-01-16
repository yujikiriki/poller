package co.s4n.poller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.certicamara.certifactura.generador.documentos.ExportadorDocumentos
import org.scalatest.BeforeAndAfter

@RunWith( classOf[JUnitRunner] )
class ExportadorDocumentosTest extends FunSuite with BeforeAndAfter {
  val exportador = new ExportadorDocumentos
  
  before {
    exportador.setRutaObjetoJasper( "/home/yuji/Desktop/ReporteDocumentosCliente.jasper" )
	exportador.setMongoURI( "mongodb://192.168.1.29:27017/certifacturadb" )
	exportador.setColeccion( "50f468759b013918dcfa0da6" )
  }

  ignore( "Generar PDF" ) {
    exportador.setRutaSalida( "/home/yuji/Desktop/prueba.pdf" )    
    exportador.exportarDocumentoAPDF( )    
  }
  
  ignore( "Generar CSV" ) {
    exportador.setRutaSalida( "/home/yuji/Desktop/prueba.csv" )
    exportador.exportarDocumentoACsv( )
  }
}