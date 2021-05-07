package usoTrait

trait UtilidadesHtml {
  def quitarMarca(entrada : String) = {
    entrada + " eleminadas marcas"
  }
}

trait UtilidadesString {
  def quitarEspacios (entrada: String) = {
    entrada + " espacios eliminados"
  }
}

// herencia mútiple con interfaces (traits)
class Texto(val contenido : String) extends UtilidadesHtml with UtilidadesString {
  def simplificar : String = {
    val paso1 = quitarEspacios(contenido)
    val paso2 = quitarMarca(paso1)
    paso2 // devolver paso2
  }
}

object EjemploTrait extends App{
  val objeto = new Texto("mensaje original del objeto")
  println("simplificado: " + objeto.simplificar)
}
