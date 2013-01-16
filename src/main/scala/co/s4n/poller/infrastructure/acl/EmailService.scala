package co.s4n.poller.infrastructure.acl

import co.s4n.mail.SenderEmail
import co.s4n.mail.ConfigMail
import co.s4n.mail.Email
import org.apache.poi.util.StringUtil


object EmailService {
  
  def send( emailAddress: String ) = {
    val email = Option( emailAddress )
    
    if( email.isDefined && email.get != "" ) {
      val destinatarios = new java.util.ArrayList[String]
      destinatarios.add( emailAddress )
      val emailSender = new SenderEmail( buildMailConfig )
      emailSender.send( new Email( destinatarios, "Hola mundo" , "Test" ) )
    }
  }
  
  private def buildMailConfig( ): ConfigMail = {
    new ConfigMail( "demo@certifactura.com", "bb7r65ZA", "SI", "smtp.gmail.com", "465" )    
  }
}