package co.s4n.poller.infrastructure.acl

import co.s4n.mail.SenderEmail
import co.s4n.mail.ConfigMail
import co.s4n.mail.Email


object EmailService {
  def send = {
    println( "=> Enviar correo electrónico" ) 

    val destinatarios = new java.util.ArrayList[String]
    destinatarios.add( "hansen.gonzalez@gmail.com" )
    
    val emailSender = new SenderEmail( buildMailConfig )
    emailSender.send( new Email( destinatarios, "Hola mundo" , "Test" ) )
  }
  
  private def buildMailConfig( ): ConfigMail = {
    new ConfigMail( "demo@certifactura.com", "bb7r65ZA", "SI", "smtp.gmail.com", "465" )    
  }
}