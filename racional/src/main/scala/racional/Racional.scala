package racional


/**
 * Clase para representar numeros racionales (n/d)
 * @param n numerador
 * @param d denominador
 */
class Racional(n: Int, d: Int){

  // se compureba que se puede crear el objeto
  require(d != 0)

  private val mcd = maximoComunDivisor(n,d)

  // se crean datos miembro para numerador y denominador
  val numerador : Int = n/mcd
  val denominador : Int = d/mcd

  /**
   * constructor auxiliar para crear racionales de tipo
   * n/1
   * @param n
   */
  def this(n: Int) = this(n,1)

  /**
   * overide to string
   * @return
   */
  override def toString: String = numerador + "/" + denominador

  /**
   * método para sumar 2 racionales
   * @param otro
   * @return
   */
  def + (otro : Racional): Racional = {
    new Racional(numerador*otro.denominador + otro.numerador*denominador,
      denominador*otro.denominador)
  }

  def < (otro: Racional): Boolean = {
    this.numerador * otro.denominador < otro.numerador*this.denominador
  }

  def maximo(otro: Racional): Racional = {
    if(this < otro) otro else this
  }

  /**
   * cálculo del máximo común divisor
   * @param a
   * @param b
   * @return
   */
  private def maximoComunDivisor(a: Int, b: Int): Int = {
    if (b == 0) a
    else maximoComunDivisor(b, a%b)
  }


}


object Racional extends App{
  val obj1 = new Racional(2,2)
  val obj2 = new Racional (4,3)
  println("obj1: " + obj1)
  println("obj2: " + obj2)
  println("suma :" + (obj1 + obj2))

  println("comprobacion de menor " + (obj1 < obj2))

  val obj3 = new Racional(5)
  println("obj3: " + obj3)
}
