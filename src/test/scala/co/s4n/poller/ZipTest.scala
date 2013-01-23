package co.s4n.poller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.FunSuite
import co.s4n.poller.infrastructure.acl.ZipFileService

@RunWith( classOf[JUnitRunner] )
class ZipTest extends FunSuite {

  test( "Zip multiple files" ) {
    val fileList: List[String] = List( "/home/yuji/Desktop/1.txt", "/home/yuji/Desktop/2.txt", "/home/yuji/Desktop/3.txt", "/home/yuji/Desktop/4.txt", "/home/yuji/Desktop/5.txt" )
    ZipFileService.zipFile( "/home/yuji/Desktop/test.zip" , fileList )
  }
}