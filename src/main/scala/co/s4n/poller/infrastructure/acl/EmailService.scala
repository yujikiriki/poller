package co.s4n.poller.infrastructure.acl

import co.s4n.mail.SenderEmail
import co.s4n.mail.ConfigMail
import co.s4n.mail.Email
import org.apache.poi.util.StringUtil
import co.s4n.poller.infrastructure.Logging
import co.s4n.poller.infrastructure.acl.PollerProperties._

object EmailService extends Logging {
  
  def send( emailAddress: String, url: String ) = {
    val email = Option( emailAddress )
    log.debug( "email = " + email )
    if( email.isDefined && email.get != "" ) {
      val destinatarios = new java.util.ArrayList[String]
      destinatarios.add( emailAddress )
      val emailSender = new SenderEmail( buildMailConfig )
      emailSender.send( new Email( destinatarios,  "El reporte ya puede ser obtenido: " + wrapReportLocation( url ), "[Certifactura] Reporte" ) )
    }
  }
  
  private def wrapReportLocation( url: String ): String = {
    "http://" + emailServerName + "/reportes/" + url
  }
  
  private def buildMailConfig( ): ConfigMail = {
    new ConfigMail( "demo@certifactura.com", "bb7r65ZA", "SI", "smtp.gmail.com", "465" )    
  }
}