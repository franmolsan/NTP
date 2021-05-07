package objetos


// objeto compañero a la clase ConexionBaseDatos
object ConexionBaseDatos {
  private val url = "jdbv::/locaclhost"
  private val usuario = "admin"
  private val password = "1234"

  // metodo apply para la creacion de objetos
  def apply() = new ConexionBaseDatos
}

class ConexionBaseDatos {
  private val propiedades = Map(
    "url" -> ConexionBaseDatos.url,
    "usuario" -> ConexionBaseDatos.usuario,
    "password" -> ConexionBaseDatos.password
  )

  println("Creado nuevo objeto " + propiedades("url"))
}

object EjemploObjeto2 extends App{
  val conexion1 = ConexionBaseDatos()
  val conexion2 = ConexionBaseDatos()
}
