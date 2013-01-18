package co.s4n.poller.infrastructure.acl
import java.io.{ BufferedInputStream, FileInputStream, FileOutputStream }
import java.util.zip.{ ZipEntry, ZipOutputStream }

object ZipFileService {

  def zipFile( out: String, files: Iterable[String] ) = {
    val zip = new ZipOutputStream( new FileOutputStream( out ) )
    files.foreach { name =>
      zip.putNextEntry( new ZipEntry( name ) )
      val in = new BufferedInputStream( new FileInputStream( name ) )
      var b = in.read()
      while ( b > -1 ) {
        zip.write( b )
        b = in.read()
      }
      in.close()
      zip.closeEntry()
    }
    zip.close()
  }
}