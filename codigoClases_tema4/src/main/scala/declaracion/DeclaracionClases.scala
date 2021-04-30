package declaracion


class ClaseGenerica[A](lista : List[A]) extends Iterable[A] {
  override def iterator: Iterator[A] = lista.iterator
}

object DeclaracionClases extends App {
  val obj1: ClaseGenerica[String] = new ClaseGenerica(List("hola", "adios", "pepe", "amigo"))

  // gracias al iterador puedo utilizar las características de la progr funcional
  obj1 foreach println

  def funcion(cadena :String): String = cadena.reverse
  val inversion = obj1 map funcion

  println("inversion " + inversion.mkString(" : "))

  val primero: String = obj1.head
  val resto = obj1.tail

  // se crea un objeto para almacenar valores de tipo double
  val obj2: ClaseGenerica[Double] = new ClaseGenerica(List(1.5,2.5, 8, 9.4))
}
