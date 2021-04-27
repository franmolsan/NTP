package busquedas

object busquedasGenericas {

  def busquedaBinaria [A] (collection : Array[A], aBuscar : A)( criterio : (A,A) => Boolean) : Int = {

    @annotation.tailrec
    def go(limiteInferior : Int, limiteSuperior: Int) : Int = {

      // no hemos encontrado el elemento
      // por tanto, devolvemos -1
      if (limiteInferior > limiteSuperior) -1

      // obtenemos la mitad del subArray que estamos considerando
      val mitad = (limiteSuperior + limiteInferior)/2

      // si el elemento de la mitad es el que buscamos, devolvemos el índice
      if (collection(mitad) == aBuscar)  mitad

      // si el elemento de la mitad es mayor (el criterio es >)
      // aumentamos los límites, por lo que en la siguiente ejecución
      // solo consideraremos el subarray con los elementos más grandes que el de la mitad
      else if (criterio(collection(mitad),aBuscar)) go(limiteInferior,mitad-1)

      // si el elemento es menor, nos quedamos con el subarray
      // de elementos más pequeños que el elemento de la mitad
      else go(mitad+1, limiteSuperior)
    }

  go(0, collection.length)

  }

  /**
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={
    val arrayBusqueda : Array[Int] = Array.range(1,11)
    val posEncontrado = busquedaBinaria(arrayBusqueda,10)(_>_)

    println("Buscamos el numero 10 en el vector 1-10 ")
    println("Posición metodo búsqueda binaria: " + posEncontrado)
    println("Posicion metodo estándar " + arrayBusqueda.indexOf(10))
  }
}
