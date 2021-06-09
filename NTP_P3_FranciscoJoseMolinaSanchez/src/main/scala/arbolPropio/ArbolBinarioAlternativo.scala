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
  override def toString = valor + " " + izq.toString + " " + dcha.toString

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

object ArbolBinarioAlternativo extends App{

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

  def recorridoInOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {
    nodo match {
      case Nodo(valor,izq,dcha) => {
        recorridoInOrden(izq) +
        valor.toString +
        recorridoInOrden(dcha)
      }
      case _ => ""
    }
  }

  def recorridoPreOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {
    nodo match {
      case Nodo(valor,izq,dcha) => {
        valor.toString +
        recorridoPreOrden(izq) + recorridoPreOrden(dcha)
      }
      case _ => ""
    }
  }

  def recorridoPosOrden[A](nodo : ArbolBinarioAlternativo[A]) : String = {
    nodo match {
      case Nodo(valor,izq,dcha) => {
        recorridoPosOrden(izq) + recorridoPosOrden(dcha) + valor.toString
      }
      case _ => ""
    }
  }

  def recorridoAnchura[A](arbol : ArbolBinarioAlternativo[A]) : Unit = {

    def go(nodoActual: ArbolBinarioAlternativo[A], profActual: Int, profObjetivo: Int, izquierdaRecorrida: Boolean): Unit ={

      //println("profundidad actual: " + profActual + " prof objetivo: " +profObjetivo + " izqu recorrida: " + izquierdaRecorrida)
      //println("nodo actual: " + nodoActual)

      def listaNodo(nodo : ArbolBinarioAlternativo[A]) = {
        nodo match {
          case Nodo(valor,izq,dcha) => print(valor.toString)
          case _ => print("")
        }
      }

      if(profActual == profObjetivo){

        //println("estoy en profundidad objetivo ")

        nodoActual match {
          case Nodo(valor,izq,dcha) => {
            if(izquierdaRecorrida) {
              //println("recorro derecha ")
              listaNodo(dcha)
               //go(arbol,0,profObjetivo,false)
            }
            else {
              //println("recorro izquierda ")
              listaNodo(izq)
              go(arbol,0,profObjetivo,true)
            }
          }
          case _ =>

        }
      }
      else {
        //println("NO estoy en profundidad objetivo ")
        nodoActual match {
          case Nodo(valor,izq,dcha) => {

            go(izq,profActual+1,profObjetivo,izquierdaRecorrida)
            go(dcha,profActual+1,profObjetivo,izquierdaRecorrida)

            go(arbol,0,profObjetivo+1, false)

          }
          case _ =>

        }
      }
    }

    go(arbol,0,0,false)

  }


  def recorridoAnchuraCola[A](arbol : ArbolBinarioAlternativo[A]) : String = {

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
            go(listaNodos.tail,cadena+valor.toString)
          }
        }
      }
    }

    // desencadenar recursividad
    go(ListBuffer(arbol),"")
  }

  def size[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    arbolBinario match {
      case ArbolVacio => 0
      case Nodo(valor,izq,dcha) => 1 + size(izq) + size(dcha)
    }
  }

  def profundidad[A](arbolBinario: ArbolBinarioAlternativo[A]) : Int = {

    /**
     * Función interna para calcular la profundidad de un árbol
     * @param arbolActual
     * @param profActual
     * @return la profundidad del árbol
     */
    @annotation.tailrec
    def go (arbolActual: ArbolBinarioAlternativo[A], profActual: Int): Int = {
      arbolActual match {

        // si el árbol está vacío, es porque ya hemos terminado
        // sin embargo, justo antes hemos aumentado el acumulador
        // por lo que tenemos que disminuirlo para devolver la profundidad correcta
        case ArbolVacio => profActual-1

        // si el árbol no está vacío, aumentar el acumulador
        case Nodo(valor, izq, dcha) =>
          go(izq,profActual+1)
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

  def sumarValoresHojas(arbolBinario: ArbolBinarioAlternativo[Int]) : Double = {

    arbolBinario match {
      case Nodo(valor, izq, dcha) => {
        if(izq.estaVacio && dcha.estaVacio) valor
        else sumarValoresHojas(izq) + sumarValoresHojas(dcha)
      }
      case _ => 0
    }
  }

  def aplicarFuncionHojas[A, B](arbolBinario: ArbolBinarioAlternativo[A], neutro: A)(funcion : (A, A) => A) : A = {

    arbolBinario match {
      case Nodo(valor, izq, dcha) => {
        if(izq.estaVacio && dcha.estaVacio) valor
        else funcion(aplicarFuncionHojas(izq,neutro)(funcion),aplicarFuncionHojas(dcha,neutro)(funcion))
      }
      case _ => neutro
    }
  }

  val arbol = ArbolBinarioAlternativo(1,2,3,4,5,6,7,8)
  println(arbol)
  println(profundidad(arbol))

  var arbol2 = ArbolBinarioAlternativo(2,4,5,6,456,9456,863)
  println(arbol2)
  println(profundidad(arbol2))

  println("print inorden " +  recorridoInOrden(arbol))
  println("print posorden " + recorridoPosOrden(arbol))
  println("print preorden " + recorridoPreOrden(arbol))

  println(recorridoAnchuraCola(arbol))
  println("tamaño del arbol: " + size(arbol))

  println("suma valores de las hojas " + sumarValoresHojas(arbol))

  println("aplicar multiplicacion a hojas " + aplicarFuncionHojas(arbol,1)(_*_))
}
