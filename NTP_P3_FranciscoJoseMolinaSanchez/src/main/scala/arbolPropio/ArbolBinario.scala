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
case class NodoInterno[+A](hijoIzq: ArbolBinario[A], hijoDcha: ArbolBinario[A]) extends ArbolBinario[A]

/**
 * Clase para representar los nodos hoja del árbol
 * Estos nodos sí almacenan un valor
 * @param valor
 * @tparam A
 */
case class NodoHoja[+A](valor: A) extends ArbolBinario[A]

object ArbolBinario extends App{

  /**
   * Metodo para permitir crear árboles sin usar new
   * @param elementos para crear el árbol
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : ArbolBinario[A] = {

    if (elementos.size == 1) NodoHoja(elementos.head)
    else {
      val listaDividida =  elementos.splitAt( (elementos.size+1)/2 )
      NodoInterno(ArbolBinario(listaDividida._1:_*),ArbolBinario(listaDividida._2:_*))
    }

  }

  def recorridoInOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(hijoIzq,hijoDcha) => {
        recorridoInOrden(hijoIzq) + " Interno " +
          recorridoInOrden(hijoDcha)
      }
        case NodoHoja(valor) => valor.toString + " "
    }
  }

  def recorridoPreOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(hijoIzq,hijoDcha) => {
        "Interno " +
        recorridoPreOrden(hijoIzq) +
        recorridoPreOrden(hijoDcha)
      }
      case NodoHoja(valor) => valor.toString + " "
    }
  }

  def recorridoPosOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(hijoIzq,hijoDcha) => {
        recorridoPosOrden(hijoIzq) + recorridoPosOrden(hijoDcha) + "Interno "
      }
      case NodoHoja(valor) => valor.toString + " "
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

  val arbol = ArbolBinario(1,2,3,4,5,6)
  println(arbol)

  println("print inorden " +  recorridoInOrden(arbol))
  println("print posorden " + recorridoPosOrden(arbol))
  println("print preorden " + recorridoPreOrden(arbol))

/*  println(recorridoAnchuraCola(arbol))
  println("tamaño del arbol: " + size(arbol))

  println("suma valores de las hojas " + sumarValoresHojas(arbol))

  println("aplicar multiplicacion a hojas " + aplicarFuncionHojas(arbol,1)(_*_))*/
}