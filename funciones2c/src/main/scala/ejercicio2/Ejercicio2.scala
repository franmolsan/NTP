package ejercicio2

object Ejercicio2 extends App{
  def ordenado[A](as : Array[A])(criterio: (A,A) => Boolean) : Boolean = {
    def go (indice : Int) : Boolean = {
      // caso base : última comprobacion
      if (indice == as.length -2) criterio(as(indice), as(indice+1))
      // se comprueba si se cumple la condicion con los varlores actuales
      else if(!criterio(as(indice),as(indice+1))) false
      // si se cumple, hay que seguir comprobando
      else go (indice+1)
     }

    // incio del proceso
    go (0)
  }

  val array1 : Array[Int] = Array(1,5,35,57,98,123,215)
  var res = ordenado(array1)(_<_)
  println(res)
  res = ordenado(array1)(_>_)
  println(res)
}
