package seriesRecurrentes

object seriesRecurrentes {


  /**
   * Función genérica para generar una serie
   * @param generadorSiguiente la función que especifica como se obtiene el siguiente término de la serie
   * @param serie array con los elementos de la serie generados hasta el momento
   * @param tope límite en el que se para de generar la serie
   * @return la serie, en forma de array
   */
  def calcularSerie (generadorSiguiente: (Int, Int) => Int)(n: Int, primero: Int, segundo: Int) : Int = {

    /**
     * Función interna tail recursive
     * Gestiona el acumulador de la recursividad, por lo que
     * permite que la función general ("generadorSerie") tenga una interfaz más natural
     * @param serie
     * @param acum
     * @return
     */
    @annotation.tailrec
    def go (n: Int, previo: Int, actual: Int): Int ={

      // si hemos calculado el elemento que nos han pedido, devolverlo
      if (n == 0) actual
      // si no, seguir la recursividad.
      else {
        // generamos el siguiente término pasándole los dos anteriores
        val nuevoDato : Int = generadorSiguiente(previo,actual)

        // recursividad, aumentando el acumulador y añadiendo el térrmino generado a la serie
        go(n-1,actual,nuevoDato)
      }
    }

    //desencadenar la recursividad
    go(n,segundo,primero)
  }

  /**
   * Función específica para generar la serie de Fibonacci
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param tope Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   * @return la serie de Fibonacci en forma de Array.
   */
  def serieFibonacci (tope : Int = 10) : Array[Int] = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 0
    val segundoTermino = 1
    val serie : Array[Int] = Array(primerTermino,segundoTermino)

    // en este caso, el término siguiente se obtiene mediante la suma de los dos anteriores
    val funcionFibonacci : (Int, Int) => Int = _+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente (la suma)
    val generadorFibonacci: (Array[Int], Int) => Array[Int] = generadorSerie(funcionFibonacci)_

    // llamamos a la función específica que genera la serie de Fibonacci
    generadorFibonacci(serie, tope)
  }

  /**
   * Función específica para generar la serie de Lucas
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param tope Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   * @return la serie de Lucas en forma de Array.
   */
  def serieLucas (tope : Int = 10) : Array[Int] = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 1
    val serie : Array[Int] = Array(primerTermino,segundoTermino)

    // en este caso, el término siguiente se obtiene mediante la suma de los dos anteriores
    val funcionLucas : (Int, Int) => Int = _+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente (la suma)
    val generadorLucas = generadorSerie(funcionLucas)_

    // llamamos a la función específica que genera la serie de Lucas
    generadorLucas(serie, tope)
  }

  /**
   * Función específica para generar la serie de Pell
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param tope Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   * @return la serie de Pell en forma de Array.
   */
  def seriePell (tope : Int = 10) : Array[Int] = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 6
    val serie : Array[Int] = Array(primerTermino,segundoTermino)

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionPell : (Int, Int) => Int = _+2*_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorPell = generadorSerie(funcionPell)_

    // llamamos a la función específica que genera la serie de Pell
    generadorPell(serie, tope)
  }

  /**
   * Función específica para generar la serie de Pell-Lucas
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param tope Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   * @return la serie de Pell-Lucas en forma de Array.
   */
  def seriePellLucas (tope : Int = 10) : Array[Int] = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 2
    val serie : Array[Int] = Array(primerTermino,segundoTermino)

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionPellLucas : (Int, Int) => Int = _+2*_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorPellLucas = generadorSerie(funcionPellLucas)_

    // llamamos a la función específica que genera la serie de Pell-Lucas
    generadorPellLucas(serie, tope)
  }

  /**
   * Función específica para generar la serie de Jacobsthal
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param tope Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   * @return la serie de Jacobsthal en forma de Array.
   */
  def serieJacobsthal (tope : Int = 10) : Array[Int] = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 0
    val segundoTermino = 1
    val serie : Array[Int] = Array(primerTermino,segundoTermino)

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionJacobsthal : (Int, Int) => Int = 2*_+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorJacobsthal = generadorSerie(funcionJacobsthal)_

    // llamamos a la función específica que genera la serie de Pell
    generadorJacobsthal(serie, tope)
  }

  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={
    println("Series definidas de forma recurrente")
    println("Fibonacci: " + serieFibonacci(15).mkString(" "))
    println("Lucas: " + serieLucas(15).mkString(" "))
    println("Pell: " + seriePell(15).mkString(" "))
    println("Pell-Lucas: " + seriePellLucas(15).mkString(" "))
    println("Jacobsthal: " + serieJacobsthal(15).mkString(" "))
  }
}
