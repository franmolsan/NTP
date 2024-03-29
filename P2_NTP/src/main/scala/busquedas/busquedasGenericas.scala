package busquedas

import scala.math.sqrt

object busquedasGenericas {

  /**
   * Función genérica que implementa la búsqueda binaria
   * @param collection El array ordenado en el que queremos realizar la búsqueda
   * @param aBuscar El elemento que queremos buscar
   * @param criterio Debe ser >
   * @tparam A
   * @return El índice en el que se encuentra el elemento. Si no está en la colección, devuelve -1.
   */
  def busquedaBinaria [A] (collection : Array[A], aBuscar : A)(criterio : (A,A) => Boolean) : Int = {

    if (collection.isEmpty) return -1

    @annotation.tailrec
    def go(limiteInferior : Int, limiteSuperior: Int) : Int = {

      // no hemos encontrado el elemento
      // por tanto, devolvemos -1
      if (limiteInferior > limiteSuperior) return -1

      if (limiteInferior == limiteSuperior && collection(limiteInferior) == aBuscar) return limiteInferior

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

  go(0, collection.length-1)

  }

  /**
   * Función genérica que implementa la búsqueda a saltos
   * @param collection El array ordenado en el que queremos realizar la búsqueda
   * @param aBuscar El elemento que queremos buscar
   * @param criterio Debe ser >
   * @tparam A
   * @return El índice en el que se encuentra el elemento. Si no está en la colección, devuelve -1.
   */
  def busquedaASaltos [A] (collection : Array[A], aBuscar : A)(criterio : (A,A) => Boolean) : Int = {

    if (collection.isEmpty) return -1

    // el tamaño del bloque se obtiene como la raíz cuadrada del tamaño de la colección
    // redondeamos a tipo Int
    val tamBloque = sqrt(collection.length).toInt

    /**
     * Función interna que analiza el array por bloques
     * @param limiteSuperiorBloque el índice que marca el límite de cada bloque
     * @return El índice en el que se encuentra el elemento buscado. Si no está en la colección, devuelve -1.
     * */
    @annotation.tailrec
    def go(limiteSuperiorBloque : Int) : Int = {

      val elementoFinalBloque = collection(limiteSuperiorBloque)

      // el elemento final es el que buscamos, por lo que devolvemos su índice
      if (elementoFinalBloque == aBuscar) limiteSuperiorBloque

      // si el elemento buscado es menor que el elemento final del bloque
      // estamos en el bloque "correcto" por lo que
      // realizamos una búsqueda lineal del bloque
       else if (criterio(elementoFinalBloque,aBuscar))
        busquedaLinealBloque(limiteSuperiorBloque-tamBloque+1, limiteSuperiorBloque)

      // si el elemento buscado es mayor que el elemento final del bloque
      // y no estamos al final de la colección
      // pasamos de bloque
      else if (criterio(aBuscar,elementoFinalBloque)
        && !(limiteSuperiorBloque == collection.length-1)) {

        // si no hemos llegado al final del array
        // calculamos el límite del siguiente bloque
        // teniendo en cuenta que no debemos pasarnos de la longitud del array
        val limiteSiguienteBloque =
          if (limiteSuperiorBloque+tamBloque > collection.length-1) collection.length-1
          else limiteSuperiorBloque+tamBloque

        // llamamos recursivamente para analizar el siguiente bloque
        go(limiteSiguienteBloque)
      }
      else -1
    }

    /**
     * Función interna para realizar una búsqueda lineal de un bloque
     * Comprueba cada elemento del bloque, para ver si es el elemento buscado.
     * @param limiteInferior El índice que va indicando el límite inferior del bloque.
     *                       Va aumentando con cada llamada recursiva,
     *                       ya que indica el elemento del bloque que comprobamos
     * @param limiteSuperior El índice que marca el límite superior del bloque.
     *                       Pararemos las llamadas recursivas si llegamos a este límite
     *                       y no hemos encontrado el elemento que buscábamos.
     * @return El índice en el que se ha encontrado el elemento buscado. Si no existe el elemento en la colección,
     *         devolver -1.
     */
    @annotation.tailrec
    def busquedaLinealBloque(limiteInferior : Int, limiteSuperior: Int) : Int = {

        if (limiteInferior > limiteSuperior) return -1

        // el elemento a comprobar es el que está en el límite inferior del bloque
        val elementoAComprobar = collection(limiteInferior)

        // si el elemento a comprobar era el que buscábamos, devolver el índice
        if (elementoAComprobar == aBuscar) limiteInferior

        // si no es el elemento que buscamos, seguir analizando
        else  busquedaLinealBloque(limiteInferior+1, limiteSuperior)

    }

     go(tamBloque-1)
  }

  /**
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={

    val aBuscar: Int = 15;
    val arrayBusqueda : Array[Int] =  Array.range(1,20)
    val posEncontradoBinaria = busquedaBinaria(arrayBusqueda,aBuscar)(_>_)
    val posEncontradoASaltos = busquedaASaltos(arrayBusqueda,aBuscar)(_>_)

    println(s"Buscamos el número $aBuscar en el vector 1-20 ")
    println("Posición método búsqueda binaria: " + posEncontradoBinaria)
    println("Posicion método estándar " + arrayBusqueda.indexOf(aBuscar))
    println("Posición método búsqueda a saltos " + posEncontradoASaltos)
  }
}
