package ejercicio1

object ejercicio1v1 extends App{
  /**
   * funcion sumatoria entre dos limites
   * @param inf
   * @param sup
   * @return
   */

    // no tail recursive
  def sumatorio(inf : Int, sup: Int) : Int = {
    if (inf > sup) 0
    else inf + sumatorio(inf+1, sup)
  }


  /**
   * Funcion sumatorio con TR
   * @param inf
   * @param sup
   * @return
   */
  def sumatorioTR(inf : Int, sup: Int) : Int = {
    @annotation.tailrec
    def go (inf: Int, sup : Int, acum : Int) : Int = {
      if (inf > sup) acum
      else go (inf+1, sup, acum+inf)
    }

    // desencadenar el proceso
    go(inf, sup, 0)
  }

  val res1 = sumatorio(1,10)
  println(res1)
  val res2 = sumatorioTR(1,10)
  println(res2)
}
