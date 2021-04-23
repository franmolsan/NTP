package seriesRecurrentes

object seriesRecurrentes {


  /**
   * Función genérica para generar una serie
   * @param generadorSiguiente la función que especifica como se obtiene el siguiente término de la serie
   * @param serie array con los elementos de la serie generados hasta el momento
   * @param tope límite en el que se para de generar la serie
   * @return la serie, en forma de array
   */
  def generadorSerie (generadorSiguiente: (Int, Int) => Int)(serie : Array[Int], tope : Int) : Array[Int] = {

    /**
     * Función interna tail recursive
     * Gestiona el acumulador de la recursividad, por lo que
     * permite que la función general ("generadorSerie") tenga una interfaz más natural
     * @param serie
     * @param acum
     * @return
     */
    @annotation.tailrec
    def go (serie : Array[Int], acum : Int): Array[Int] ={

      // si llegamos al tope, parar la recursividad
      if (acum == tope) serie
      // si no estamos en el tope
      else {
        // generamos el siguiente término pasándole los dos anteriores
        // (que son los dos últimos de la serie)
        val nuevoDato : Int = generadorSiguiente(serie(serie.length-2),serie(serie.length-1))

        // recursividad, aumentando el acumulador y añadiendo el térrmino generado a la serie
        go(serie:+nuevoDato,acum+1)
      }
    }

    //desencadenar la recursividad
    go(serie, 0)
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
