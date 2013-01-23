package co.s4n.poller.infrastructure.acl
import java.io.{ BufferedInputStream, FileInputStream, FileOutputStream }
import java.util.zip.{ ZipEntry, ZipOutputStream }
import java.io.File
import scala.util.{ Try, Success, Failure }
import co.s4n.poller.infrastructure.Logging

object ZipFileService extends Logging {

  def zipFile( out: String, files: List[String] ) = {
    try {
      val zip = new ZipOutputStream( new FileOutputStream( out ) )
      files.foreach { name =>
        val file: File = new File( name )
        zip.putNextEntry( new ZipEntry( file.getName ) )
        val in = new BufferedInputStream( new FileInputStream( name ) )
        var b = in.read
        while ( b > -1 ) {
          zip.write( b )
          b = in.read()
        }
        in.close
        zip.closeEntry
      }
      zip.close
      /* If the zip file is created, delete the source */
      deleteFiles( files )
    } catch {
      case e: Exception => { 
        println( e )
        log.error( e.getMessage )
      }
    }    
  }
  
  private def deleteFiles( paths: List[String] ): Unit = {
    paths.foreach( path => {
      val file = Try( new File( path ) )
      file match {
        case Success( f ) => f.delete
        case Failure( ex ) => { 
          println( ex.getMessage )
          log.error( ex.getMessage )
        }
      }
    })
  }
}