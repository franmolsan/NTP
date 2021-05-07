package seriesRecurrentes

object seriesRecurrentes {

  /**
   * Función genérica para generar una serie
   * @param generadorSiguiente la función que especifica como se obtiene el siguiente término de la serie
   * @param n el término que queremos generar de la serie (empezando desde el 1)
   * @param primero el primer término de la serie
   * @param segundo el segundo término de la serie
   * @return el término n de la serie (empezando desde 1)
   */
  def calcularSerie (generadorSiguiente: (Int, Int) => Int)(n: Int, primero: Int, segundo: Int) : Int = {

    /**
     * Función interna tail recursive
     * @param n Int que nos sirve para parar la recursividad (cuando llegue a 0)
     *          Lo vamos disminuyendo en cada recursión
     * @param previo El término generado en la llamada anterior
     * @param actual El término generado en la llamada actual
     * @return
     */
    @annotation.tailrec
    def go (n: Int, previo: Int, actual: Int): Int ={

      // si hemos calculado el término que nos han pedido, devolverlo
      if (n == 0) actual
      // si no, seguir la recursividad
      else {
        // generamos el siguiente término pasándole los dos anteriores
        val nuevoDato : Int = generadorSiguiente(previo,actual)

        // recursividad, aumentando el acumulador y añadiendo el término generado a la serie
        go(n-1,actual,nuevoDato)
      }
    }

    // desencadenar la recursividad
    // pasamos n-2 porque ya tenemos los dos primeros términos
    if (n > 2){
      go(n-2,primero,segundo)
    }
    // si nos piden el término 2 o el termino 1, no tenemos que calcularlos
    else if (n == 2) segundo
    else if (n == 1) primero

    // si nos piden el término 0 o negativo, devolvemos -1
    // para indicar que no es correcto
    else -1
  }

  /**
   * Función específica para generar la serie de Fibonacci
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param n Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   *          Empieza en 1 (es decir, que el primer elemento de la serie es el 1, no el 0)
   * @return El elemento n de la serie de Fibonacci (si n<1, se devolverá -1)
   */
  def serieFibonacci (n : Int = 10) : Int = {

    // especificamos los dos términos iniciales
    val primerTermino = 0
    val segundoTermino = 1

    // en este caso, el término siguiente se obtiene mediante la suma de los dos anteriores
    val funcionFibonacci : (Int, Int) => Int = _+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente (la suma)
    val generadorFibonacci: (Int,Int, Int) => Int = calcularSerie(funcionFibonacci)_

    // llamamos a la función específica que genera la serie de Fibonacci
    generadorFibonacci(n,primerTermino,segundoTermino)
  }

  /**
   * Función específica para generar la serie de Lucas
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param n Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   *          Empieza en 1 (es decir, que el primer elemento de la serie es el 1, no el 0)
   * @return El elemento n de la serie de Lucas (si n<1, se devolverá -1)
   */
  def serieLucas (n : Int = 10) : Int = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 1

    // en este caso, el término siguiente se obtiene mediante la suma de los dos anteriores
    val funcionLucas : (Int, Int) => Int = _+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente (la suma)
    val generadorLucas = calcularSerie(funcionLucas)_

    // llamamos a la función específica que genera la serie de Lucas
    generadorLucas(n,primerTermino,segundoTermino)
  }

  /**
   * Función específica para generar la serie de Pell
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param n Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   *          Empieza en 1 (es decir, que el primer elemento de la serie es el 1, no el 0)
   * @return El elemento n de la serie de Pell (si n<1, se devolverá -1)
   */
  def seriePell (n : Int = 10) : Int = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 6

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionPell : (Int, Int) => Int = _+2*_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorPell = calcularSerie(funcionPell)_

    // llamamos a la función específica que genera la serie de Pell
    generadorPell(n,primerTermino,segundoTermino)
  }

  /**
   * Función específica para generar la serie de Pell-Lucas
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param n Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   *          Empieza en 1 (es decir, que el primer elemento de la serie es el 1, no el 0)
   * @return El elemento n de la serie de Pell-Lucas (si n<1, se devolverá -1)
   */
  def seriePellLucas (n : Int = 10) : Int = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 2
    val segundoTermino = 2

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionPellLucas : (Int, Int) => Int = _+2*_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorPellLucas = calcularSerie(funcionPellLucas)_

    // llamamos a la función específica que genera la serie de Pell-Lucas
    generadorPellLucas(n,primerTermino,segundoTermino)
  }

  /**
   * Función específica para generar la serie de Jacobsthal
   * Se especifican los términos iniciales
   * Y la función con la que conseguimos los términos sucesivos
   * @param n Límite en el que paramos de generar la serie, tiene como valor por defecto 10
   *          Empieza en 1 (es decir, que el primer elemento de la serie es el 1, no el 0)
   * @return El elemento n de la serie de Jacobsthal (si n<1, se devolverá -1)
   */
  def serieJacobsthal (n : Int = 10) : Int = {

    // generamos la serie con los dos términos iniciales
    val primerTermino = 0
    val segundoTermino = 1

    // función para conseguir el siguiente término a partir de los dos anteriores
    val funcionJacobsthal : (Int, Int) => Int = 2*_+_

    // generamos función específica
    // a partir de la función genérica,
    // especificando la forma de obtener el término siguiente
    val generadorJacobsthal = calcularSerie(funcionJacobsthal)_

    // llamamos a la función específica que genera la serie de Pell
    generadorJacobsthal(n,primerTermino,segundoTermino)
  }

  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={
    println("Series definidas de forma recurrente")

    val n : Int = 5;
    println(s"Termino $n de la serie de Fibonacci: ${serieFibonacci(n)}")
    println(s"Termino $n de la serie de Lucas: ${serieLucas(n)}")
    println(s"Termino $n de la serie de Pell: ${seriePell(n)}")
    println(s"Termino $n de la serie de Pell-Lucas: ${seriePellLucas(n)}")
    println(s"Termino $n de la serie de Jacobsthal: ${serieJacobsthal(n)}")
  }
}
