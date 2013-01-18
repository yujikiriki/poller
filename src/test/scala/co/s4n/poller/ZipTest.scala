package co.s4n.poller

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.FunSuite
import co.s4n.poller.infrastructure.acl.ZipFileService

@RunWith( classOf[JUnitRunner] )
class ZipTest extends FunSuite {

  ignore( "Zip multiple files" ) {
    val fileList: List[String] = List( "/home/yuji/Desktop/5.todo", "/home/yuji/Desktop/4.todo", "/home/yuji/Desktop/3.todo", "/home/yuji/Desktop/2.todo", "/home/yuji/Desktop/Poller.todo" )
    ZipFileService.zipFile( "/home/yuji/Desktop/test.zip" , fileList )
  }
  
}