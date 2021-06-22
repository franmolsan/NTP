package arbolPropio

import scala.collection.mutable.ListBuffer

/**
 * Interfaz generica para la lista
 *
 * @tparam A
 */
sealed trait ArbolBinario[+A]

/**
 * Clase para representar los nodos internos del árbol
 * Estos nodos no almacenan valor
 * @param hijoIzq
 * @param hijoDcha
 * @tparam A
 */
case class NodoInterno[+A](id: String, hijoIzq: ArbolBinario[A], hijoDcha: ArbolBinario[A]) extends ArbolBinario[A]

/**
 * Clase para representar los nodos hoja del árbol
 * Estos nodos sí almacenan un valor
 * @param valor
 * @tparam A
 */
case class NodoHoja[+A](valor: A) extends ArbolBinario[A]

case object NodoVacio extends ArbolBinario[Nothing]

object ArbolBinario{

  /**
   * Metodo para permitir crear árboles sin usar new
   * @param elementos para crear el árbol
   * @tparam A
   * @return árbol binario equilibrado
   */
  def apply[A](elementos : A*) : ArbolBinario[A] = {

    /**
     * Función interna para crear árboles
     * @param idActual string identificador del nodo interno actual
     * @param elementosRestantes
     * @return árbol binario equilibrado
     */
    def go(idActual: String, elementosRestantes: A*): ArbolBinario[A] = {

      // si solo nos queda un elemento restante, devolver
      // un nodo hoja con el valor del elemento
      if (elementosRestantes.size == 1) NodoHoja(elementosRestantes.head)

      // si nos queda más de un elemento restante
      else {
        // dividimos la lista por la mitad (+1 para redondear hacia arriba)
        // para construir dos árboles equilibrados,
        // cada uno con la mitad de elementos
        val listaDividida =  elementosRestantes.splitAt( (elementosRestantes.size+1)/2 )

        // devolvemos un nodo interno
        // cuyos hijos son los árboles que se formarán
        // a partir de las mitades izquierda y derecha de la lista
        // para cada hijo le añadimos a su id un sufijo
        // indicando si es el hijo izquierdo o derecho
        NodoInterno(idActual , go(idActual+"-izq",listaDividida._1:_*), go(idActual+"-dcha",listaDividida._2:_*))
      }
    }

    // si nos han pasado elementos, desencadenamos la recursión
    if(elementos.size > 0) go("raiz",elementos:_*)

    // si no nos han pasado elementos, devolvemos el nodo vacío
    else NodoVacio
  }

  /**
   * Método para realizar el recorrido en profundidad inorden
   * (la raíz se visita tras recorrer el hijo izquierdo
   * y antes de recorrer el derecho)
   * @param nodo
   * @tparam A
   * @return
   */
  def recorridoInOrden[A](nodo : ArbolBinario[A]) : String = {

    // comprobamos el nodo actual
    nodo match {

      // si es un nodo interno
      // recorrer el hijo izquierdo,
      // luego el id del nodo interno
      // y finalmente recorrer el hijo derecho
      case NodoInterno(id,hijoIzq,hijoDcha) => {
        recorridoInOrden(hijoIzq) + id + " " +
          recorridoInOrden(hijoDcha)
      }

      // si es un nodo hoja, devolver su valor
      case NodoHoja(valor) => valor.toString + " "

      // en otro caso, (p.ej. nodo vacío) devolver cadena vacía
      case _ => ""
    }
  }

  /**
   * Método para realizar el recorrido en profundidad preorden
   * (la raíz se visita primero, luego el hijo izquierdo
   * y finalmente el hijo derecho)
   * @param nodo
   * @tparam A
   * @return
   */
  def recorridoPreOrden[A](nodo : ArbolBinario[A]) : String = {

    // comprobamos el nodo actual
    nodo match {

      // si es un nodo interno
      // devolver el id del nodo interno,
      // luego recorrer el hijo izquierdo
      // y finalmente recorrer el hijo derecho
      case NodoInterno(id, hijoIzq,hijoDcha) => {
        id + " " +
        recorridoPreOrden(hijoIzq) +
        recorridoPreOrden(hijoDcha)
      }

      // si es un nodo hoja, devolver su valor
      case NodoHoja(valor) => valor.toString + " "

      // en otro caso, (p.ej. nodo vacío) devolver cadena vacía
      case _ => ""
    }
  }

  /**
   * Método para realizar el recorrido en profundidad posorden
   * (primero se visita el hijo izquierdo, luego el derecho
   * y finalmente la raíz)
   * @param nodo
   * @tparam A
   * @return
   */
  def recorridoPosOrden[A](nodo : ArbolBinario[A]) : String = {

    nodo match {

      // si es un nodo interno
      // recorrer el hijo izquierdo,
      // luego recorrer el hijo derecho
      // y finalmente devolver el id del nodo interno
      case NodoInterno(id, hijoIzq,hijoDcha) => {
        recorridoPosOrden(hijoIzq) + recorridoPosOrden(hijoDcha) + id + " "
      }

      // si es un nodo hoja, devolver su valor
      case NodoHoja(valor) => valor.toString + " "

      // en otro caso, (p.ej. nodo vacío) devolver cadena vacía
      case _ => ""
    }
  }

  /**
   * Método para realizar el recorrido en anchura del árbol
   * @tparam A
   * @return
   */
  def recorridoAnchura[A](arbol : ArbolBinario[A]) : String = {

    /**
     * Función interna para recorrer el árbol e ir formando
     * la cadena con el recorrido en anchura
     * @param listaNodos
     * @param cadena la cadena formada hasta el momento
     * @return cadena con el recorrido en anchura del árbol
     */
    def go(listaNodos: ListBuffer[ArbolBinario[A]], cadena: String): String = {

      // cuando no queden nodos que recorrer,
      // devolvemos la cadena formada
      if (listaNodos.isEmpty) cadena

      // si quedan nodos
      else {
        // comprobamos el nodo que hay en la cabeza de la lista
        val nodoActual = listaNodos.head
        nodoActual match {

          // si es un nodo interno
          case NodoInterno(id, izq, dcha) => {

            // introducir sus hijos en la cola
            listaNodos += izq
            listaNodos += dcha

            // llamar recursivamente a la función,
            // eliminando el nodo procesado (con tail)
            // y añadiendo a la cadena el valor actual del nodo
            go(listaNodos.tail, cadena + id + " ")
          }

          // si es un nodo hoja, no es necesario procesar sus hijos, solo su valor
          case NodoHoja(valor) => go(listaNodos.tail, cadena + valor.toString + " ")
          case _ => ""
        }
      }
    }

    // desencadenar recursividad
    go(ListBuffer(arbol),"")
  }


  /**
   * Método que devuelve el número total de nodos del árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numTotalNodos[A](arbolBinario: ArbolBinario[A]) : Int = {

    // para cada nodo
    arbolBinario match {
      // si es hoja, devolvemos 1
      case NodoHoja(valor) => 1
      // si es interno, sumamos 1 (del propio nodo) + el número de nodos del hijo izquierdo
      // + el número de nodos del hijo derecho
      case NodoInterno(id,izq,dcha) => 1 + numTotalNodos(izq) + numTotalNodos(dcha)
      // otro caso (arbol vacío), devolvemos 0
      case _ => 0
    }
  }

  /**
   * Método para calcular el número de nodos hoja de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numNodosHoja[A](arbolBinario: ArbolBinario[A]) : Int = {

    // para cada nodo
    arbolBinario match {
      // si es hoja, devolvemos 1
      case NodoHoja(valor) => 1
      // si es interno, seguimos analizando sus hijos
      case NodoInterno(id,izq,dcha) =>  numNodosHoja(izq) + numNodosHoja(dcha)
      // otro caso (arbol vacío), devolvemos 0
      case _ => 0
    }
  }

  /**
   * Método para calcular el número de nodos internos de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numNodosInternos[A](arbolBinario: ArbolBinario[A]) : Int = {

    // para cada nodo
    arbolBinario match {
      // si es hoja, devolvemos 0
      case NodoHoja(valor) => 0
      // si es interno, sumamos 1 y seguimos analizanod sus hijos
      case NodoInterno(id,izq,dcha) => 1 + numNodosInternos(izq) + numNodosInternos(dcha)
      // otro caso (arbol vacío), devolvemos 0
      case _ => 0
    }
  }

  /**
   * Método para calcular la profundidad de un árbol binario
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def profundidad[A](arbolBinario: ArbolBinario[A]) : Int = {

    /**
     * Función interna para calcular la profundidad de un árbol
     * @param arbolActual
     * @param profActual acumulador
     * @return la profundidad del árbol
     */
    def go (arbolActual: ArbolBinario[A], profActual: Int): Int = {
      arbolActual match {

        // si llegamos a un nodo hoja, devolvemos la profundidad calculada
        case NodoHoja(valor) => profActual

        // si llegamos a un nodo interno
        case NodoInterno(id, hijoIzq, hijoDcha) =>
          // calcular profundidad de sus hijos
          val profIzq = go(hijoIzq,profActual+1)
          val profDcha = go(hijoDcha,profActual+1)
          // devolver la mayor profundidad (la de su hijo izquierdo o la de su hijo derecho).
          if(profIzq > profDcha) profIzq else profDcha
      }
    }

    // comporbar el árbol que nos han pasado
    arbolBinario match {
      // si está vacío, directamente devolvemos que la profundidad es 0
      case NodoVacio => 0
      // si no está vacío, calculamos la profundidad
      case _ => go(arbolBinario,0)
    }
  }

  /**
   * Método para calcular la suma de los valores de las hojas del árbol
   * @param arbolBinario
   * @return
   */
  def sumarValoresHojas(arbolBinario: ArbolBinario[Int]) : Double = {
    // internamente llamamos a aplicarFuncionHojas, pasándole la suma como función
    aplicarFuncionHojas(arbolBinario,0)(_+_)
  }

  /**
   * Método para aplicar una función a los valores almacenados en los nodos hoja del árbol
   * @param arbolBinario
   * @param neutro
   * @param funcion
   * @tparam A
   * @tparam B
   * @return
   */
  def aplicarFuncionHojas[A, B](arbolBinario: ArbolBinario[A], neutro: A)(funcion : (A, A) => A) : A = {

    // para cada nodo
    arbolBinario match {

      // si es nodo interno, continuar la recursividad por sus hijos
      // y devolver el resultado de aplicar la función al valor devuelto por la función en sus hijos
      case NodoInterno(id, izq, dcha) =>
         funcion(aplicarFuncionHojas(izq,neutro)(funcion),aplicarFuncionHojas(dcha,neutro)(funcion))

      // si es un nodo hoja, devolver su valor
      case NodoHoja(valor) => valor

      // en otro caso, devolver el elemento neutro
      case _ => neutro
    }
  }

  /**
   * Método para generar un nuevo árbol a partir de otros dos
   * @param arbol1
   * @param arbol2
   * @tparam A
   * @return
   */
  def unirArboles[A](arbol1: ArbolBinario[A], arbol2: ArbolBinario[A]): ArbolBinario[A] = {

    // comprobamos el primer árbol
    arbol1 match {

      // si está vacío, devolvemos el segundo árbol
      case NodoVacio => arbol2

      // si el primer árbol no está vacío,
      // procesamos el segundo árbol
      case _ => arbol2 match {

        // si el segundo árbol está vacío (y el primero no)
        // devolvemos el primero
        case NodoVacio => arbol1

        // si ambos árboles no están vacíos
        // devolvemos un nodo que actúe como nueva raiz
        // el primer árbol es el hijo izquierdo de la nueva raíz
        // el segundo árbol es el hijo derecho de la nueva raíz
        case _ => NodoInterno("nuevaRaiz",arbol1,arbol2)
      }
    }
  }

  /**
   * Función para mostrar los nodos de un árbol
   * (tanto los nodos internos como los hoja)
   * @param arbolBinario
   * @tparam A
   */
  def mostrarArbol[A](arbolBinario: ArbolBinario[A]): Unit = {

    /**
     * Función interna para recorrer el árbol (preorden)
     * y mostrar por pantalla cada nodo
     * @param nodoActual
     */
    def go(nodoActual: ArbolBinario[A]): Unit = {

      // comprobamos el nodo actual
      nodoActual match {

        // si es un nodo interno, imprimir su id
        // y seguir procesando sus hijos, recursivamente
        case NodoInterno(id,hijoIzq,hijoDcha) => {
          println("Nodo Interno " + id)
          go(hijoIzq);
          go(hijoDcha);
        }

        // si es un nodo hoja, imprimir su valor
        case NodoHoja(valor) => println("Nodo hoja, valor " + valor.toString)
      }
    }

    // desencadenar recursividad
    go(arbolBinario)
  }


  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val arbol1 = ArbolBinario(1,2,3,4,5,6,7,8)
    println("Árbol 1: " + arbol1)
    println("Recorrido inorden: " +  recorridoInOrden(arbol1))
    println("Recorrido posorden: " + recorridoPosOrden(arbol1))
    println("Recorrido preorden: " + recorridoPreOrden(arbol1))
    println("Recorrido en anchura: " + recorridoAnchura(arbol1))

    println("Número de nodos: " + numTotalNodos(arbol1))
    println("Número de nodos internos: " + numNodosInternos(arbol1))
    println("Número de nodos hoja: " + numNodosHoja(arbol1))

    println("Suma valores de las hojas " + sumarValoresHojas(arbol1))

    println("Aplicar multiplicacion a hojas " + aplicarFuncionHojas(arbol1,1)(_*_))

    println("Profundidad del Árbol 1 (sin contar la raíz): " + profundidad(arbol1))
    mostrarArbol(arbol1)

    val arbol2 = ArbolBinario(1,2,3)
    println("Árbol 2: " + arbol2)

    val arbol3 = unirArboles(arbol1,arbol2)

    println("Unión del árbol 1 y árbol 2: " + arbol3)
  }

}