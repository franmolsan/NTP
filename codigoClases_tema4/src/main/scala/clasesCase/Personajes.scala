package clasesCase

case class Personaje(nombre: String, heroe: Boolean)

object Personajes extends App{
  val personaje1 = Personaje("Gollum", false)
  val personaje2 = Personaje("Gandalf", true)
  val personaje3 = Personaje("Gandalf", true)

  // se comprueba que los datos miembro son val
  // la siguiente sentencia no se puede ejecutar:
  // personaje2.heroe = true

  // las clases CASE tienen un método equals que compara
  // dato miembro a dato miembro
  val comparacion1 = (personaje1 == personaje2)
  println("comparacion1 " + comparacion1)

  val comparacion2 = (personaje2 == personaje3)
  println("comparacion2 " + comparacion2)

  // posibildad de uso toString
  println(personaje1)

  // puedo usar compiar para crear copias en las que varia un dato miembro
  val personaje4 = personaje1.copy("Sauron")
  println(personaje4)

  personaje4.heroe;
  // se dispone de un metodo unapply (contrario a apply): despieza el objeto
  // y compone una tupla
  val resultado: Option[(String, Boolean)] = Personaje.unapply(personaje4)
  println(resultado)
}
