package introduccion

class Usuario

// la propia declaración de la clase es el constructor!!
class Usuario2 {
  val nombre = "administrador"
  val prompt = s"<$nombre>"
}

// la declaración de la clase es el constructor!!
class Usuario3 (nombreUsuario : String) {
  val nombre = nombreUsuario
  val prompt = s"<$nombre>"

  // declaración del método toString
  override def  toString : String = s"Usuario ($nombre)"

  // mensaje para indicar creación
  println("Creación de objeto de la clase Usuario3")

}

// declaración más adecuada de Usuario
// le ponemos val (o var) nombre al argumento, asi que se convierte en un dato miembro de la clase
class Usuario4(val nombre : String){
  val prompt = s"<$nombre>"

  // declaración del método toString
  override def  toString : String = s"Usuario ($nombre)"

  // mensaje para indicar creación
  println("Creación de objeto de la clase Usuario4")
}

object introduccion extends App{
  // se crea un objeto de la clase Usuario
  val usuario1 = new Usuario

  // se comprueba que el objeto creado se considera instaca de AnyRef
  val esAnyRef = usuario1.isInstanceOf[AnyRef]
  println("Es anyRef " + esAnyRef)

  val usuario2 = new Usuario2
  println("nombre de usuario2 " + usuario2.nombre)
  println("prompt de usuario2 " + usuario2.prompt)
  println(usuario2)

  val usuario3 = new Usuario3("analista")
  println(usuario3)


  val usuarios = List(new Usuario4("administador"),
                      new Usuario4("analista"),
                      new Usuario4("gestorBBDD"),
                      new Usuario4("programador")
  )

  val longitudes = usuarios.map(usuario => usuario.nombre.length)
  println("longitudes de nombres " + longitudes.mkString(" - "))

  val longitudes2 = usuarios map (_.nombre.size)
  println("longitudes 2 de nombres " + longitudes2.mkString(" - "))

  val ordenador = usuarios.sortBy(_.nombre)
  println("ordenados " + ordenador.mkString(" - "))
}
