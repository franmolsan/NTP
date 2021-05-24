package listaPropia

/**
 * Interfaz generica para la lista
 * @tparam A
 */
sealed trait Lista[+A]

/**
 * Objeto para definir lista vacia
 */
case object Nil extends Lista[Nothing]

/**
 * Clase para definir la lista como compuesta por elemento inicial
 * (cabeza) y resto (cola)
 * @param cabeza
 * @param cola
 * @tparam A
 */
case class Cons[+A](cabeza : A, cola : Lista[A]) extends Lista[A]

object Lista extends App {

  /**
   * Metodo para permitir crear listas sin usar new
   * @param elementos secuencia de elementos a incluir en la lista
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : Lista[A] = {

    if(elementos.length == 0) Nil
    else Cons(elementos.head, Lista(elementos.tail:_*))
  }

  /**
   * Obtiene la longitud de una lista
   * @param lista
   * @tparam A
   * @return
   */
  def longitud[A](lista : Lista[A]) : Int = {

    @annotation.tailrec
    def go(listaActual: Lista[A], longActual: Int): Int = {
      listaActual match {
        case Nil => longActual
        case Cons(cabeza,cola) => go(cola,longActual+1)
      }
    }
    go (lista,0)
  }

  /**
   * Metodo para sumar los valores de una lista de enteros
   * @param enteros
   * @return
   */
  def sumaEnteros(enteros : Lista[Int]) : Double = {

    @annotation.tailrec
    def go(enterosRestantes: Lista[Int], sumaActual: Double): Double = {
      enterosRestantes match {
        case Nil => sumaActual
        case Cons(cabeza,cola) => go(cola,sumaActual+cabeza)
      }
    }
    go (enteros,0)
  }

  /**
   * Metodo para multiplicar los valores de una lista de enteros
   * @param enteros
   * @return
   */
  def productoEnteros(enteros : Lista[Int]) : Double = {

    @annotation.tailrec
    def go(enterosRestantes: Lista[Int], productoActual: Double): Double = {
      enterosRestantes match {
        case Nil => productoActual
        case Cons(cabeza,cola) => go(cola,productoActual*cabeza)
      }
    }
    go (enteros,1)
  }

  /**
   * Metodo para agregar el contenido de dos listas
   * @param lista1
   * @param lista2
   * @tparam A
   * @return
   */
  def concatenar[A](lista1: Lista[A], lista2: Lista[A]): Lista[A] = {

    // procesamos la primera lista
    lista1 match {
      // cuando no queden elementos, añadimos la lista2 (que está bien formada)
      case Nil => lista2
      // si quedan elementos, seguir construyendo la lista concatenada
      case Cons(cabeza,cola) => Cons(cabeza,concatenar(cola,lista2))
    }

  }

  /**
   * Funcion de utilidad para aplicar una funcion de forma sucesiva a los
   * elementos de la lista con asociatividad por la derecha
   * @param lista
   * @param neutro
   * @param funcion
   * @tparam A
   * @tparam B
   * @return
   */
  def foldRight[A, B](lista : Lista[A], neutro : B)(funcion : (A, B) => B): B = {

    println("recibo lista: " + lista)
    println("recibo neutro: " +  neutro)

    if (longitud(lista)==0) neutro
    else {
      lista match {
        case Cons(cabeza, cola) => foldRight(cola, funcion(cabeza, neutro))(funcion)
      }
    }
  }

  /**
   * Suma mediante foldRight
   * @param listaEnteros
   * @return
   */
  def sumaFoldRight(listaEnteros : Lista[Int]) : Double = ???

  /**
   * Producto mediante foldRight
   * @param listaEnteros
   * @return
   */
  def productoFoldRight(listaEnteros : Lista[Int]) : Double = ???

  /**
   * Reemplaza la cabeza por nuevo valor. Se asume que si la lista esta vacia
   * se devuelve una lista con el nuevo elemento
   *
   * @param lista
   * @param cabezaNueva
   * @tparam A
   * @return
   */
  def asignarCabeza[A](lista : Lista[A], cabezaNueva : A) : Lista[A] = ???

  /**
   * Elimina el elemento cabeza de la lista
   * @param lista
   * @tparam A
   * @return
   */
  def tail[A](lista : Lista[A]): Lista[A] = ???

  /**
   * Elimina los n primeros elementos de una lista
   * @param lista lista con la que trabajar
   * @param n numero de elementos a eliminar
   * @tparam A tipo de datos
   * @return
   */
  def eliminar[A](lista : Lista[A], n: Int) : Lista[A] = ???

  /**
   * Elimina elementos mientra se cumple la condicion pasada como
   * argumento
   * @param lista lista con la que trabajar
   * @param criterio predicado a considerar para continuar con el borrado
   * @tparam A tipo de datos a usar
   * @return
   */
  def eliminarMientras[A](lista : Lista[A], criterio: A => Boolean) : Lista[A] = ???
  /**
   * Elimina el ultimo elemento de la lista. Aqui no se pueden compartir
   * datos en los objetos y hay que generar una nueva lista copiando
   * datos
   * @param lista lista con la que trabajar
   * @tparam A tipo de datos de la lista
   * @return
   */
  def eliminarUltimo[A](lista : Lista[A]) : Lista[A] = {
    ???
  }

  /**
   * foldLeft con recursividad tipo tail
   * @param lista lista con la que trabajar
   * @param neutro elemento neutro
   * @param funcion funcion a aplicar
   * @tparam A parametros de tipo de elementos de la lista
   * @tparam B parametro de tipo del elemento neutro
   * @return
   */

  @annotation.tailrec
  def foldLeft[A, B](lista : Lista[A], neutro: B)(funcion : (B, A) => B): B = {
    lista match {
      case Nil => neutro
      case Cons(cabeza,cola) => foldLeft(cola,funcion(neutro,cabeza))(funcion)
    }
  }

  val Lista1: Lista[Int] = Lista(1,2,3)
  println("Lista: " + Lista1)
  println("Longitud: " + longitud(Lista1))
  println("Suma: " + sumaEnteros(Lista1))
  println("Producto: " + productoEnteros(Lista1))

  val Lista2 : Lista[Int] = Lista(4,5,6)
  val ListaConcatenada = concatenar(Lista1,Lista2)
  println("ListaConcatenada: " + ListaConcatenada)

  println(concatenar(concatenar(Lista("a","b"),Lista("c", "d")),Lista2))

  println("FoldLeft suma de lista " + Lista1 +": " + foldLeft(Lista1, 0)(_+_))
  println("FoldRight suma de lista " + Lista1 +": " + foldRight(Lista1, 0)(_+_))

}
