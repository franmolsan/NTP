package construccion


class PuntoAleatorio {
  val x = {
    println("Asignación de x")
    util.Random.nextDouble
  }

  // lazy: se hace solo cuando es imprescinible
  lazy val y = {
    println("Asignación de y")
    util.Random.nextDouble
  }
}
object PruebaLazy extends App{

  // se crea un objeto de la clase PuntoAleatorio
  val p1 = new PuntoAleatorio

  // mostrar las coordenadas de p1:
  println("coordenadas de p1: " + p1.x + ", " + p1.y)
}
