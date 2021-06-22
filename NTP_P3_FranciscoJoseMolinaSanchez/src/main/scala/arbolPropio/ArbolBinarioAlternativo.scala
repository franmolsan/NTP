package arbolPropio

import scala.collection.mutable.ListBuffer

/**
 * Interfaz generica para el árbol
 *
 * @tparam A
 */
sealed trait ArbolBinarioAlternativo[+A] {
  def estaVacio: Boolean
  def hayHueco: Boolean
  def add[A](a: A): ArbolBinarioAlternativo[A]
}

/**
 * Objeto para definir árbol binario vacío
 */
case object ArbolVacio extends ArbolBinarioAlternativo[Nothing] {
  def estaVacio = true
  def hayHueco = true
  def add[A](nuevoElemento: A): ArbolBinarioAlternativo[A] = {
    Nodo(nuevoElemento, ArbolVacio, ArbolVacio)
  }
}

/**
 * Clase para definir cada nodo del árbol
 * @param valor elemento del tipo A que almacena el nodo
 * @param izq el nodo hijo por la izquierda
 * @param dcha el nodo hijo por la derecha
 * @tparam A
 */
case class Nodo[A](valor : A, izq: ArbolBinarioAlternativo[A], dcha: ArbolBinarioAlternativo[A]) extends ArbolBinarioAlternativo[A] {
  def estaVacio = false
  def hayHueco = izq.estaVacio || dcha.estaVacio

  /**
   * Función para insertar un nuevo elmemento en el árbol
   * de forma que esté balanceado
   * @param nuevoElemento
   * @tparam A
   * @return
   */
  def add[A](nuevoElemento: A): ArbolBinarioAlternativo[A] = {
    // primero comprobamos si algún hijo está vacío e intentamos insertar en él
    // priorizando la izquierda
    if (izq.estaVacio) Nodo(valor,izq.add(nuevoElemento),dcha).asInstanceOf[Nodo[A]]
    else if (dcha.estaVacio) Nodo(valor,izq,dcha.add(nuevoElemento)).asInstanceOf[Nodo[A]]

    // si tiene los dos hijos
    // comprobamos si alguno de ellos tiene un "hueco" libre para insertar un nodo
    // igualmente priorizamos la izquierda
    // es decir, solo insertamos en la derecha cuando la izquierda esté ocupada
    else if(dcha.hayHueco && !izq.hayHueco) Nodo(valor,izq,dcha.add(nuevoElemento)).asInstanceOf[Nodo[A]]
    else Nodo(valor,izq.add(nuevoElemento),dcha).asInstanceOf[Nodo[A]]
  }
}

object ArbolBinarioAlternativo{

  /**
   * Metodo para permitir crear árboles sin usar new
   * @param elementos para crear el árbol
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : ArbolBinarioAlternativo[A] = {

    /**
     * Función interna para formar el árbol,
     * añadiendo nuevos nodos al padre
     * @param nodoPadre
     * @param elementosRestantes
     * @return
     */
    @annotation.tailrec
    def go (nodoPadre: ArbolBinarioAlternativo[A], elementosRestantes: A*) : ArbolBinarioAlternativo[A] = {

      // si no nos quedan elementos por añadir, devolvemos el árbol formado
      if(elementosRestantes.isEmpty) nodoPadre

      // si nos quedan elementos, continuar la recursividad
      else go(nodoPadre.add(elementosRestantes.head),elementosRestantes.tail:_*)
    }

    // desencadenar recursividad
    go(ArbolVacio,elementos:_*)
  }

  /**
   * Método para realizar el recorrido en profundidad inorden
   * (la raíz se visita tras recorrer el hijo izquierdo
   * y antes de recorrer el derecho)
   * @param nodo
   * @tparam A
   * @return
   */
  def recorridoInOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {

    // comprobamos el nodo actual
    nodo match {

      // recorrer el hijo izquierdo,
      // luego el valor del nodo actual
      // y finalmente recorrer el hijo derecho
      case Nodo(valor,izq,dcha) => {
        recorridoInOrden(izq) +
        valor.toString + " " +
        recorridoInOrden(dcha)
      }

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
  def recorridoPreOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {

    // comprobamos el nodo actual
    nodo match {

      // devolver el valor del nodo actual,
      // luego recorrer el hijo izquierdo
      // y finalmente recorrer el hijo derecho
      case Nodo(valor,izq,dcha) => {
        valor.toString + " " +
        recorridoPreOrden(izq) + recorridoPreOrden(dcha)
      }

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
  def recorridoPosOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {

    // comprobamos el nodo actual
    nodo match {

      // recorrer el hijo izquierdo,
      // luego recorrer el hijo derecho
      // y finalmente devolver el valor del nodo actual
      case Nodo(valor,izq,dcha) => {
        recorridoPosOrden(izq) + recorridoPosOrden(dcha) + valor.toString + " "
      }

      // en otro caso, (p.ej. nodo vacío) devolver cadena vacía
      case _ => ""
    }
  }

  /**
   * Método para realizar el recorrido en anchura del árbol
   * @param arbol
   * @tparam A
   * @return
   */
  def recorridoAnchura[A](arbol : ArbolBinarioAlternativo[A]) : String = {

    /**
     * Función interna para recorrer el árbol e ir formando
     * la cadena con el recorrido en anchura
     * @param listaNodos
     * @param cadena
     * @return
     */
    def go(listaNodos: ListBuffer[ArbolBinarioAlternativo[A]], cadena: String): String = {

      // cuando no queden nodos que recorrer,
      // devolvemos la cadena formada
      if (listaNodos.isEmpty) cadena

      // si quedan nodos
      else {
        // comprobamos el nodo que hay en la cabeza de la lista
        val nodoActual = listaNodos.head
        nodoActual match {
          case Nodo(valor,izq,dcha) =>{

            // introducir en la cola sus hijos
            if (!izq.estaVacio) listaNodos += izq
            if (!dcha.estaVacio) listaNodos += dcha

            // llamar recursivamente a la función,
            // eliminando el nodo procesado (con tail)
            // y añadiendo a la cadena el valor actual del nodo
            go(listaNodos.tail,cadena+valor.toString+" ")
          }
          case _ => ""
        }
      }
    }

    // desencadenar recursividad
    go(ListBuffer(arbol),"")
  }

  /**
   * Método para calcular el número total de nodos de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numTotalNodos[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    // comprobamos el nodo actual
    arbolBinario match {

      // si es vacío, no sumamos nada (0)
      case ArbolVacio => 0

      // si no es vacío, sumamos 1 y recorremos los hijos
      case Nodo(valor,izq,dcha) => 1 + numTotalNodos(izq) + numTotalNodos(dcha)
    }
  }

  /**
   * Método para calcular el número de nodos hoja de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numNodosHoja[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    // comprobamos el nodo actual
    arbolBinario match {

      // si es vacío, no sumamos nada (0)
      case ArbolVacio => 0

      // si no es vacío
      case Nodo(valor,izq,dcha) =>

        // si es una hoja, devolvemos 1
        if (izq.estaVacio && dcha.estaVacio) 1

        // si no es hoja, seguimos recorriendo los hijos
        else numNodosHoja(izq) + numNodosHoja(dcha)
    }
  }

  /**
   * Método para calcular el número de nodos internos de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def numNodosInternos[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    // comprobamos el nodo actual
    arbolBinario match {

      // si es vacío, no sumamos nada (0)
      case ArbolVacio => 0

      // si no es vacío
      case Nodo(valor,izq,dcha) =>

        // si es una hoja, devolvemos 0
        if (izq.estaVacio && dcha.estaVacio) 0

        // si no es una hoja, sumamos 1 y seguimos recorriendo los hijos
        else 1 + numNodosInternos(izq) + numNodosInternos(dcha)
    }
  }

  /**
   * Método para calcular la profundidad de un árbol
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def profundidad[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    /**
     * Función interna para calcular la profundidad de un árbol
     * @param arbolActual
     * @param profActual
     * @return la profundidad del árbol
     */
    def go (arbolActual: ArbolBinarioAlternativo[A], profActual: Int): Int = {
      arbolActual match {

        // si el árbol está vacío, es porque ya hemos terminado
        // sin embargo, justo antes hemos aumentado el acumulador
        // por lo que tenemos que disminuirlo para devolver la profundidad correcta
        case ArbolVacio => profActual-1

        // si el árbol no está vacío, aumentar el acumulador
        case Nodo(valor, izq, dcha) =>

          // calcular profundidad de sus hijos
          val profIzq = go(izq,profActual+1)
          val profDcha = go(dcha,profActual+1)
          // devolver la mayor profundidad (la de su hijo izquierdo o la de su hijo derecho).
          if(profIzq > profDcha) profIzq else profDcha
      }
    }

    // comporbar el árbol que nos han pasado
    arbolBinario match {
      // si está vacío, directamente devolvemos que la profundidad es -1
      case ArbolVacio => 0
      // si no está vacío, calculamos la profundidad
      case _ => go(arbolBinario,0)
    }
  }

  /**
   * Método para calcular la suma de los valores de las hojas del árbol
   * @param arbolBinario
   * @return
   */
  def sumarValoresHojas(arbolBinario: ArbolBinarioAlternativo[Int]) : Double = {
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
  def aplicarFuncionHojas[A, B](arbolBinario: ArbolBinarioAlternativo[A], neutro: A)(funcion : (A, A) => A) : A = {

    // para cada nodo
    arbolBinario match {

      case Nodo(valor, izq, dcha) => {
        // si es un nodo hoja, devolver su valor
        if(izq.estaVacio && dcha.estaVacio) valor

        // si es nodo interno, continuar la recursividad por sus hijos
        // y devolver el resultado de aplicar la función al valor devuelto por la función en sus hijos
        else funcion(aplicarFuncionHojas(izq,neutro)(funcion),aplicarFuncionHojas(dcha,neutro)(funcion))
      }

      // en otro caso, devolver el elemento neutro
      case _ => neutro
    }
  }

  /**
   *
   * @param arbolBinario
   * @tparam A
   * @return
   */
  def stringArbol[A](arbolBinario: ArbolBinarioAlternativo[A]) : String = {
    /**
     * Función interna para recorrer el árbol (preorden)
     * y mostrar por pantalla cada nodo
     * @param nodoActual
     */
    def go(nodoActual: ArbolBinarioAlternativo[A]): String = {

      // comprobamos el nodo actual
      nodoActual match {

        // si no es un nodo vacío, imprimir su valor
        // y seguir procesando sus hijos, recursivamente
        case Nodo(valor,hijoIzq,hijoDcha) => {
          var respuesta = "Valor nodo: " + valor + "\n"
          if(!hijoIzq.estaVacio) respuesta += "Hijo izquierdo (" + go(hijoIzq) + ")"
          if(!hijoDcha.estaVacio) respuesta += "\nHijo derecho (" + go(hijoDcha) + ")"

          respuesta
        }
        case _ => ""
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
    val arbol = ArbolBinarioAlternativo()
    println("Árbol" + arbol)
    println(stringArbol(arbol))
    println("Profundidad del árbol: " + profundidad(arbol))

    println("Recorrido inorden " +  recorridoInOrden(arbol))
    println("Recorrido posorden " + recorridoPosOrden(arbol))
    println("Recorrido preorden " + recorridoPreOrden(arbol))

    println("Recorrido anchura " + recorridoAnchura(arbol))
    println("Tamaño del arbol: " + numTotalNodos(arbol))
    println("Número de hojas del arbol: " + numNodosHoja(arbol))

    println("Suma valores de las hojas " + sumarValoresHojas(arbol))

    println("Aplicar multiplicacion a hojas " + aplicarFuncionHojas(arbol,1)(_*_))
  }

}
