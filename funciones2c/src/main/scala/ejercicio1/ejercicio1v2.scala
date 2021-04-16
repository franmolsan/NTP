package ejercicio1

object ejercicio1v2 extends App {

  /**
   * funmcion sumatorio generica
   * @param funcion
   * @param inf
   * @param sup
   * @return
   */
  def sumatorio(funcion : Int => Int, inf : Int, sup : Int) : Int = {
    if (inf > sup) 0
    else funcion(inf) + sumatorio(funcion, inf+1, sup)
  }

  def sumatorioTR(funcion : Int => Int, inf : Int, sup: Int) : Int ={
    @annotation.tailrec
    def go(inf : Int, acum : Int) : Int = {
      if (inf > sup) acum
      else go(inf+1, funcion(inf))
    }

    // desencadenar la recurividad
    go(inf, 0)
  }

  /**
   * funcion identidad
   * @param x
   * @return
   */
  def identidad(x: Int) = x

  /**
   * funcion cuadrado
   * @param x
   * @return
   */
  def cuadrado (x: Int) = x*x


  /**
   * potencia de 2 TR
   * @param x
   * @return
   */
  def potencia2(x: Int): Int = {
    def go(x : Int, acum : Int) : Int = {
      if (x == 0) acum
      else go (x-1, 2*acum)
    }
    //desencadenar
    go(x,1)
  }

  var suma1_10 = sumatorio(identidad, 1,10)
  println("sumatorio 1-10 (debe valer 55): " + suma1_10)

  suma1_10 = sumatorio(cuadrado,1,10)
  println("sumatorio 1-10 de x al cuadrado: " + suma1_10)


  // se generan nuevas funciones
  val sumatorioBasico: (Int, Int) => Int = sumatorio(identidad, _,_)
  suma1_10 = sumatorioBasico(1,10)
  println("suma del 1 al 10 (55): " + suma1_10)

  def sumatorioBasico2 = sumatorio(identidad,_,_)
  println(sumatorioBasico2(1,10))

  // funciones con varias listas de argumentos (currying)
  def sumatorioTRV2(funcion : Int => Int)(inf : Int, sup : Int) : Int = {
    def go(inf: Int, acum : Int) : Int = {
      if (inf > sup) acum
      else go(inf+1, funcion(inf) + acum)
    }

    //desencadenar
    go(inf, 0)
  }

  val sumatorioBasico3 : (Int, Int) => Int = sumatorioTRV2(identidad)
  def sumatorioBasico4 = sumatorioTRV2(identidad)_
}
