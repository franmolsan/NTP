import scala.collection.View
import scala.math.sqrt

object NumerosPrimos extends App{

  /**
   * Comprobación sobre si un numero es primo
   * @param numero
   * @return
   */
  def esPrimo(numero : Int) : Boolean = {

    !(2 to sqrt(numero).toInt).exists(x => numero % x == 0)
  }


  /**
   * Obtiene el n-esimo numero primo en un rango
   * @param inf
   * @param sup
   * @param n
   * @return
   */
  def enesimoPrimoEnRango(inf: Int, sup: Int, n: Int) = {
    val secuenciaPrimos: Seq[Int] = (inf to sup).filter(x => esPrimo(x))
    secuenciaPrimos(n)
  }

  /**
   * Metodo recursivo para encontrar el enesimo primo en un rango
   * @param inf
   * @param sup
   * @param n
   * @return
   */
  def enesimoPrimoEnRangoRecursivo(inf: Int, sup: Int, n: Int): Int = {

    if (esPrimo(inf)){
      if (n==1) inf
      else enesimoPrimoEnRangoRecursivo(inf+1,sup,n-1)
    }
    else enesimoPrimoEnRangoRecursivo(inf+1,sup,n)
  }


  /**
   * Construir un rango perezoso mediente el uso de la clase LazyList
   * Es más eficiente porque se evalua solo cuando se hace falta
   * Forma típica de construir listas
   * @param inf
   * @param sup
   * @return
   */
  def rangoPerezoso(inf: Int, sup:Int) : LazyList[Int] = {
    if(inf > sup) LazyList.empty
    else LazyList.cons(inf,rangoPerezoso(inf+1,sup))
  }

  /**
   * Usando rangos perezosos
   * @param inf
   * @param sup
   * @param n
   * @return
   */
  def enesimoPrimoEnRangoPerezoso(inf: Int, sup: Int, n:Int) = {
    rangoPerezoso(inf,sup).filter(x => esPrimo(x))(n)
  }

  /**
   * Metodo de obtencion del eneismo primo usando view
   * @param inf
   * @param sup
   * @param n
   * @return
   */
  def enesimoPrimoEnRangoView(inf: Int, sup: Int, n:Int): Int ={
    val resultado: View[Int] = (inf to sup).view.filter(x => esPrimo(x))
    resultado.drop(n-1).head // quitar los n-1 primeros valores y obtener la cabeza, que es n
  }
}
