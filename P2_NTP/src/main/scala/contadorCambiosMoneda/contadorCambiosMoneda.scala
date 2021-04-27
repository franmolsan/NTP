package contadorCambiosMoneda

import scala.collection.mutable.ListBuffer

object contadorCambiosMoneda {

  /**
   * Función para listar todas las formas posibles de devolver una cierta cantidad
   * con unos tipos de monedas determinados
   * @param cantidad el valor a devolver
   * @param monedas lista de tipos de monedas posibles
   * @return Una lista con todos los cambios posibles (cada cambio es a su vez una lista con las monedas utilizadas)
   */
  def listarCambiosPosibles(cantidad : Int, monedas : List[Int]) : List[List[Int]] = {

    /**
     * Función interna para calcular los cambios, con TR
     * @param monedasDisponibles Lista de los tipos de monedas que podemos utilizar para dar el cambio
     * @param monedasNoUsadas Lista de los tipos de monedas que todavía no hemos utilizado
     * @param listaCambios Lista de los cambios que llevamos hasta el momento
     * @return Lista con todos los cambios calculados
     */
    @annotation.tailrec
    def go(monedasDisponibles : List[Int], monedasNoUsadas : List[Int], listaCambios : List[List[Int]]): List[List[Int]] = {
      // generar cambios con los tipos de moneda disponibles
      val nuevaListaCambios = generarCambiosConMonedas(cantidad, monedasDisponibles, listaCambios)

      // si todavía nos quedan tipos de moneda por utilizar, continuar la recursión
      if (monedasNoUsadas.nonEmpty)
        go (monedasDisponibles ::: List(monedasNoUsadas.head), monedasNoUsadas.tail, nuevaListaCambios)

      // si no nos quedan tipos de monedas por utilizar, hemos terminado
      // por lo que devolvemos la lista última de cambios generada
      else nuevaListaCambios
    }

    // desencadenar recursividad
    // empezamos solo con la primera moneda disponible, y el resto como no usadas
    val listaFinal = go(List(monedas.head), monedas.tail, List(List()))

    // quitar el primer elemento de la lista, que está vacío (lo hemos pasado en la línea anterior así)
    if (listaFinal.nonEmpty) listaFinal.tail
    else listaFinal
  }


  /**
   * Función que genera los cambios posibles con unos tipos de moneda dados
   * @param cantidad La cantidad cuyo cambio queremos devolver
   * @param monedas Los tipos de moneda que tenemos disponibles
   * @param listaCambios la lista de cambios que hemos calculado hasta el momento
   * @return Lista con todos los cambios calculados
   */
  @annotation.tailrec
  def generarCambiosConMonedas(cantidad : Int, monedas : List[Int], listaCambios :  List[List[Int]]) : List[List[Int]] = {

    // generamos un cambio con unos tipos de monedas
    val unCambio : List[Int] = generarUnCambio(cantidad, monedas, List())

    // actualizamos la lista de los cambios:
    val nuevaListaCambios =
    // si el cambio generado es bueno (no es -1 y no lo hemos generado antes)
    // lo guardamos en la lista
    if (!unCambio.contains(-1) && !listaCambios.contains(unCambio))
       listaCambios:::List(unCambio)
    else  listaCambios // si el cambio es malo, no lo añadimos a la lista

    // si tenemos más tipos de monedas con las que probar, continuar la recursión
    // en lugar de pasar todas las monedas, pasamos la "cola", es decir, todas las monedas menos la primera
    if (monedas.size > 1) generarCambiosConMonedas(cantidad,monedas.tail,nuevaListaCambios)
    // si no nos quedan tipos de monedas con las que probar, parar la recursión y devolver la lista de cambios
    else nuevaListaCambios
  }

  /**
   * Función que genera un único cambio
   * @param restante La cantidad que queda por devolver
   * @param monedasPosibles List de las monedas que puede utilizar para dar el cambio.
   *                        Primero utiliza la primera moneda de la lista
   *                        por lo que si cambia la primera moneda, da cambios diferentes
   * @param cambioActual  Lista con el cambio que lleva hasta el momento
   * @return  El cambio que haya generado. Si no ha encontrado un cambio posible, devuelve una lista con -1
   */
  @annotation.tailrec
  def generarUnCambio(restante: Int, monedasPosibles: List[Int], cambioActual: List[Int]): List[Int] = {

    // si no tenemos que dar más cambio, devolver el cambio que llevamos
    if (restante == 0) cambioActual

    // si tenemos que dar más cambio
    else {

      // nos quedamos solo con los tipos de moneda útiles
      // es decir, aquellos que tienen un valor menor o igual que es valor que nos queda por devolver
      val tiposUtiles = monedasPosibles.filter(_<=restante)

      // si no podemos utilizar ninguna moneda, devolver una lista con el valor -1
      // para indicar que no hemos podido dar el cambio
      // por tanto, no se añadirá a la lista de cambios posibles
      if (tiposUtiles.isEmpty) List(-1)

      // podemos utilizar alguna moneda
      else {
        // definimos una moneda preferente, que sera la primera moneda que nos han pasado
        val monedaPref =
            // si no hemos usado todavia la primera moneda que nos han pasado, usarla
           if (!cambioActual.contains(monedasPosibles.head)) tiposUtiles.head
           // si no, utilizamos la más grande que podamos
           else tiposUtiles.last
        generarUnCambio(restante-monedaPref, tiposUtiles ,cambioActual:::List(monedaPref))
      }
    }
  }


  /**
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={
    val listaPosiblesCambios = listarCambiosPosibles(4,List(1,2,4,6))

    println("Lista de cambios posibles para el valor 4, con monedas de tipo 1,2,4 y 6")
    println(listaPosiblesCambios.mkString("\n"))
  }

}
