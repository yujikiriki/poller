package co.s4n.poller.infrastructure

sealed trait BusinessLogicException extends Exception  
sealed trait TechnicalException extends RuntimeException

case class DBConnectionException( message: String ) extends TechnicalException 
case class ThirdPartyLibraryException( message: String ) extends TechnicalException