object Test {
  val emailAddress: String = ""                   //> emailAddress  : String = ""
  
  val email = Option( emailAddress )              //> email  : Option[String] = Some()
  
  email.isEmpty                                   //> res0: Boolean = false
  email.isDefined                                 //> res1: Boolean = true
  
  email.isDefined && email.get != ""              //> res2: Boolean = false
}