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

// las clases case ya están definidas
// por tanto podemos implementar el objeto Lista
object Lista {

  /**
   * Metodo para permitir crear listas sin usar new
   * @param elementos secuencia de elementos a incluir en la lista
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : Lista[A] = {

    // si no nos quedan elementos por añadir a la lista,
    // devolvemos una lista vacía que indica el final
    if(elementos.length == 0) Nil

    // si quedan elementos a añadir, llamar a Cons
    // que creará el nodo de la lista con el elemento correspondiente
    // y seguir llamando recursivamente a Apply, para crear la cola
    else Cons(elementos.head, Lista(elementos.tail:_*))
  }

  /**
   * Obtiene la longitud de una lista
   * @param lista
   * @tparam A
   * @return
   */
  def longitud[A](lista : Lista[A]) : Int = {

    /**
     * Función interna para calcular la longitud de una lista
     * @param listaActual la lista que queda por procesar
     * @param longActual la longitud de la lista que ya hemos procesado
     * @return la longitud total de la lista
     */
    @annotation.tailrec
    def go(listaActual: Lista[A], longActual: Int): Int = {
      listaActual match {
        // si estamos al final de la lista,
        // devolvemos la longitud que teníamos acumulada
        case Nil => longActual
        // si nos quedan elementos por procesar,
        // llamar recursivamente a go y aumentar la longitud acumulada
        case Cons(cabeza,cola) => go(cola,longActual+1)
      }
    }

    // llamada inicial para desencadenar la recursividad
    go (lista,0)
  }

  /**
   * Metodo para sumar los valores de una lista de enteros
   * @param enteros
   * @return
   */
  def sumaEnteros(enteros : Lista[Int]) : Double = {

    /**
     * Función interna para calcular la suma
     * de todos los elementos de una lista de enteros
     * @param enterosRestantes la lista de enteros que no hemos sumado aún
     * @param sumaActual el acumulador donde guardamos la suma calculada hasta el momento
     * @return la suma de todos los elementos de una lista de enteros
     */
    @annotation.tailrec
    def go(enterosRestantes: Lista[Int], sumaActual: Double): Double = {
      enterosRestantes match {
        // si estamos al final de la lista,
        // devolver la suma actual, que será la suma de todos
        case Nil => sumaActual
        // si no estamos al final de la lista,
        // llamar recursivamente a go pasandole la cola como lista restante
        // y sumar el elemento de la lista con la suma acumulada
        case Cons(cabeza,cola) => go(cola,sumaActual+cabeza)
      }
    }

    // llamada inicial para desencadenar la recursividad
    go (enteros,0)
  }

  /**
   * Metodo para multiplicar los valores de una lista de enteros
   * @param enteros
   * @return
   */
  def productoEnteros(enteros : Lista[Int]) : Double = {

    /**
     * Función interna para calcular el producto
     * de todos los elementos de una lista de enteros
     * @param enterosRestantes la lista de los enteros cuyo producto no hemos procesado aún
     * @param productoActual  el acumulador donde guardamos el producto calculado hasta el momento
     * @return el producto de todos los elementos de una lista de enteros
     */
    @annotation.tailrec
    def go(enterosRestantes: Lista[Int], productoActual: Double): Double = {
      enterosRestantes match {
        // si estamos al final de la lista,
        // devolver el producto actual, que será el producto de todos los elementos
        case Nil => productoActual
        // si no estamos al final de la lista,
        // llamar recursivamente a go pasandole la cola como lista restante
        // y seguir multiplicando
        case Cons(cabeza,cola) => go(cola,productoActual*cabeza)
      }
    }

    // llamada inicial para desencadenar la recursividad
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

    lista match {
      // si la lista está vacía, devolver elemento neutro
      case Nil => neutro
      // si la lista no está vacía
      case Cons(cabeza,cola) =>
        // si la longitud de la cola es 0, es porque estamos en el último elemento
        // en ese caso, aplicar la función
        if(longitud(cola) == 0) funcion(cabeza,neutro)
        // si no estamos en el último elemento, debemos llegar a él
        // por lo que continuamos la recursión.
        // Devolvemos el resultado de aplicar la función
        // al elemento de la lista y al resultado de aplicar la función a los elementos de la cola
        else funcion(cabeza,foldRight(cola,neutro)(funcion))
    }

  }

  /**
   * Suma mediante foldRight
   * @param listaEnteros
   * @return
   */
  def sumaFoldRight(listaEnteros : Lista[Int]) : Double = {
    // llamada a foldright pasando la suma como función a aplicar
    foldRight(listaEnteros,0.0)(_+_)
  }

  /**
   * Producto mediante foldRight
   * @param listaEnteros
   * @return
   */
  def productoFoldRight(listaEnteros : Lista[Int]) : Double = {
    // llamada a foldright pasando el producto como función a aplicar
    foldRight(listaEnteros,1.0)(_*_)
  }

  /**
   * Reemplaza la cabeza por nuevo valor. Se asume que si la lista esta vacia
   * se devuelve una lista con el nuevo elemento
   *
   * @param lista
   * @param cabezaNueva
   * @tparam A
   * @return
   */
  def asignarCabeza[A](lista : Lista[A], cabezaNueva : A) : Lista[A] = {
    lista match {
      // si la lista está vacía, devolvemos una lista
      // con el elemento que nos han pasado como nueva cabeza
      case Nil => Lista(cabezaNueva)
      // si la lista no está vacía,
      // devolver la lista con la misma cola pero cambiando la cabeza
      // por la nueva
      case Cons(cabeza,cola) => Cons(cabezaNueva,cola)
    }
  }

  /**
   * Elimina el elemento cabeza de la lista
   * @param lista
   * @tparam A
   * @return
   */
  def tail[A](lista : Lista[A]): Lista[A] = {
    lista match {
      // si la lista está vacía
      // devolvemos una lista vacía también
      case Nil => Nil
      // si la lista no está vacía, devolvemos la cola
      case Cons(cabeza,cola) => cola
    }
  }

  /**
   * Elimina los n primeros elementos de una lista
   * @param lista lista con la que trabajar
   * @param n numero de elementos a eliminar
   * @tparam A tipo de datos
   * @return
   */
  def eliminar[A](lista : Lista[A], n: Int) : Lista[A] = {
    // si hemos llegado a n, devolver la lista como nos haya quedado
    if (n == 0) lista
    // si no hemos llegado a n
    else lista match {
      // si la lista está vacía, devolverla
      case Nil => lista
      // si la lista no está vacía,
      // llamamos recurisvamente a eliminar,
      // pasándole la cola y disminuyendo n en 1
      case Cons(cabeza,cola) => eliminar(cola,n-1)
    }
  }

  /**
   * Elimina elementos mientra se cumple la condicion pasada como
   * argumento
   * @param lista lista con la que trabajar
   * @param criterio predicado a considerar para continuar con el borrado
   * @tparam A tipo de datos a usar
   * @return
   */
  def eliminarMientras[A](lista : Lista[A], criterio: A => Boolean) : Lista[A] = {
    lista match {
        // si la lista está vacía, devolverla
      case Nil => lista
        // si la lista no está vacía
      case Cons(cabeza,cola) =>
        // si se cumple el criterio, llamar recursivamente a eliminarMientras
        // pasándole la cola (se ha eliminado el elemento de la cabeza)
        if (criterio(cabeza)) eliminarMientras(cola,criterio)
        // si no se cumple el cirterio, devolver la lista
        else lista
    }

  }
  /**
   * Elimina el ultimo elemento de la lista. Aqui no se pueden compartir
   * datos en los objetos y hay que generar una nueva lista copiando
   * datos
   * @param lista lista con la que trabajar
   * @tparam A tipo de datos de la lista
   * @return
   */
  def eliminarUltimo[A](lista : Lista[A]) : Lista[A] = {

    /**
     * Función interna para procesar la lista
     * @param listaRestante la lista que nos queda por procesar
     * @param listaActual la lista que ya hemos procesado
     * @tparam A
     * @return la lista que nos han pasado inicialmente con todos los elementos menos el último
     */
    @annotation.tailrec
    def go[A](listaRestante : Lista[A], listaActual: Lista[A]) : Lista[A] = {
      listaRestante match {
          // si la lista es vacía, devolverla
        case Nil => listaRestante
          // si la lista no está vacía
        case Cons(cabeza,cola) =>
          // si la longitud de la cola es 0, es porque estamos en el último elemento
          // entonces, devolver la listaActual
          // (que contiene todos los elementos menos este último)
          if (longitud(cola) == 0) listaActual
          // si no estamos en el último elemento,
          // añadir a la lista actual el elemento que hemos procesado
          // y llamar a go para seguir procesando la cola
          else  go(cola, concatenar(listaActual,Lista(cabeza)))
      }
    }

    // llamada para desencadenar la recursión
    go(lista, Nil)
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
      // si la lista está vacía, devolver neutro
      case Nil => neutro
      // si quedan elementos en la lista, llamar a foldLeft
      // pasando como argumentos la cola, ya que tenemos que procesarla,
      // y como neutro el resultado de aplicar la función al elemento actual de la lista
      // de esta forma, neutro va actuando como acumulador
      // y se devolverá cuando llegemos al final
      case Cons(cabeza,cola) => foldLeft(cola,funcion(neutro,cabeza))(funcion)
    }
  }


  /**
   * Devuelve la lista que recibe por parámetro en forma de string
   * Para poder imprimirla con formato
   * @param lista
   * @tparam A
   * @return
   */
  def stringLista[A](lista : Lista[A]) : String = {

    @annotation.tailrec
    def go (listaRestante: Lista[A], stringActual: String): String = {
      listaRestante match {
        case Nil => stringActual + "Nil"
        // si quedan elementos en la lista
        case Cons(cabeza,cola) => go(cola,stringActual + cabeza + " -> ")
      }
    }

    // desencadenar recursividad
    go(lista,"")
  }

  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val Lista1: Lista[Int] = Lista(1,2,3)
    println("Lista: " + stringLista(Lista1))
    println("Longitud: " + longitud(Lista1))
    println("Suma: " + sumaEnteros(Lista1))
    println("Producto: " + productoEnteros(Lista1))

    val Lista2 : Lista[Int] = Lista(4,5,6)
    println("Lista 2: " + stringLista(Lista2))

    val ListaConcatenada = concatenar(Lista1,Lista2)
    println("Lista Concatenada: " + stringLista(ListaConcatenada))

    println(stringLista(concatenar(concatenar(Lista("a","b"),Lista("c", "d")),Lista2)))

    println("FoldLeft suma de lista " + Lista1 +": " + foldLeft(Lista1, 0)(_+_))
    println("FoldRight suma de lista " + Lista1 +": " + foldRight(Lista1, 0)(_+_))

    println("suma FoldRight: " + sumaFoldRight(ListaConcatenada))
    println("producto FoldRight: " + productoFoldRight(ListaConcatenada))
    println("suma normal: " + sumaEnteros(ListaConcatenada))
    println("producto normal: " + productoEnteros(ListaConcatenada))

    println(asignarCabeza(Lista1,83))
    println("Tail de la lista " + Lista1 + " : " + tail(Lista1))

    println("Eliminar 2 primeros elementos de lista " + Lista1 + " : " + eliminar(Lista1,2))
    println("Eliminar 3 primeros elementos de lista " + Lista1 + " : " + eliminar(Lista1,3))
    println("Eliminar 4 primeros elementos de lista " + Lista1 + " : " + eliminar(Lista1,4))

    val predicadoCondicion: (Int) => Boolean = (num) => num < 3

    println("elminar mientras elemento < 3: " + stringLista(eliminarMientras(Lista1, predicadoCondicion)))

    println("eliminar ultimo: " + stringLista(eliminarUltimo(Lista1)))

  }
}
