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

object ArbolBinario extends App{

  /**
   * Metodo para permitir crear árboles sin usar new
   * @param elementos para crear el árbol
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : ArbolBinario[A] = {

    def go(idActual: String, elementosRestantes: A*): ArbolBinario[A] = {
      if (elementosRestantes.size == 1) NodoHoja(elementosRestantes.head)
      else {
        val listaDividida =  elementosRestantes.splitAt( (elementosRestantes.size+1)/2 )
        NodoInterno(idActual , go(idActual+"-izq",listaDividida._1:_*), go(idActual+"-dcha",listaDividida._2:_*))
      }
    }

    if(elementos.size > 0) go("raiz",elementos:_*)
    else NodoVacio
  }

  def recorridoInOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(id,hijoIzq,hijoDcha) => {
        recorridoInOrden(hijoIzq) + id + " " +
          recorridoInOrden(hijoDcha)
      }
      case NodoHoja(valor) => valor.toString + " "
      case _ => ""
    }
  }

  def recorridoPreOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(id, hijoIzq,hijoDcha) => {
        id + " " +
        recorridoPreOrden(hijoIzq) +
        recorridoPreOrden(hijoDcha)
      }
      case NodoHoja(valor) => valor.toString + " "
      case _ => ""
    }
  }

  def recorridoPosOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case NodoInterno(id, hijoIzq,hijoDcha) => {
        recorridoPosOrden(hijoIzq) + recorridoPosOrden(hijoDcha) + id + " "
      }
      case NodoHoja(valor) => valor.toString + " "
      case _ => ""
    }
  }

  def recorridoAnchura[A](arbol : ArbolBinario[A]) : String = {

    /**
     * Función interna para recorrer el árbol e ir formando
     * la cadena con el recorrido en anchura
     * @param listaNodos
     * @param cadena
     * @return
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

  def numTotalNodos[A](arbolBinario: ArbolBinario[A]) : Int = {

    arbolBinario match {
      case NodoHoja(valor) => 1
      case NodoInterno(id,izq,dcha) => 1 + numTotalNodos(izq) + numTotalNodos(dcha)
      case _ => 0
    }
  }

  def numNodosHoja[A](arbolBinario: ArbolBinario[A]) : Int = {

    arbolBinario match {
      case NodoHoja(valor) => 1
      case NodoInterno(id,izq,dcha) =>  numNodosHoja(izq) + numNodosHoja(dcha)
      case _ => 0
    }
  }

  def numNodosInternos[A](arbolBinario: ArbolBinario[A]) : Int = {

    arbolBinario match {
      case NodoHoja(valor) => 0
      case NodoInterno(id,izq,dcha) => 1 + numNodosInternos(izq) + numNodosInternos(dcha)
      case _ => 0
    }
  }

  def profundidad[A](arbolBinario: ArbolBinario[A]) : Int = {

    /**
     * Función interna para calcular la profundidad de un árbol
     * @param arbolActual
     * @param profActual
     * @return la profundidad del árbol
     */
    def go (arbolActual: ArbolBinario[A], profActual: Int): Int = {
      arbolActual match {

        // si el árbol está vacío, es porque ya hemos terminado
        // sin embargo, justo antes hemos aumentado el acumulador
        // por lo que tenemos que disminuirlo para devolver la profundidad correcta
        case NodoHoja(valor) => profActual

        // si el árbol no está vacío, aumentar el acumulador
        case NodoInterno(id, hijoIzq, hijoDcha) =>
          val profIzq = go(hijoIzq,profActual+1)
          val profDcha = go(hijoDcha,profActual+1)

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

  def sumarValoresHojas(arbolBinario: ArbolBinario[Int]) : Double = {
    aplicarFuncionHojas(arbolBinario,0)(_+_)
  }

  def aplicarFuncionHojas[A, B](arbolBinario: ArbolBinario[A], neutro: A)(funcion : (A, A) => A) : A = {

    arbolBinario match {
      case NodoInterno(id, izq, dcha) =>
         funcion(aplicarFuncionHojas(izq,neutro)(funcion),aplicarFuncionHojas(dcha,neutro)(funcion))
      case NodoHoja(valor) => valor
      case _ => neutro
    }
  }

  def unirArboles[A](arbol1: ArbolBinario[A], arbol2: ArbolBinario[A]): ArbolBinario[A] = {

    arbol1 match {
      case NodoVacio => arbol2
      case _ => arbol2 match {
        case NodoVacio => arbol1
        case _ => NodoInterno("nuevaRaiz",arbol1,arbol2)
      }
    }
  }

  def mostrarArbol[A](arbolBinario: ArbolBinario[A]) = {

    def go(nodoActual: ArbolBinario[A]): Unit = {

      nodoActual match {
        case NodoInterno(id,hijoIzq,hijoDcha) => {
          println(id +  "├── " + go(hijoIzq))
          println(" └──" +  go(hijoDcha))
        }
        case NodoHoja(valor) => print(valor.toString)
        case _ => ""
      }
    }

    go(arbolBinario)
  }

  val arbol = ArbolBinario(1,2,3,4,5,6,7,8)
  println("Árbol: " + arbol)
  println("Recorrido inorden: " +  recorridoInOrden(arbol))
  println("Recorrido posorden: " + recorridoPosOrden(arbol))
  println("Recorrido preorden: " + recorridoPreOrden(arbol))
  println("Recorrido en anchura: " + recorridoAnchura(arbol))

  println("Número de nodos: " + numTotalNodos(arbol))
  println("Número de nodos internos: " + numNodosInternos(arbol))
  println("Número de nodos hoja: " + numNodosHoja(arbol))

  println("suma valores de las hojas " + sumarValoresHojas(arbol))

  println("aplicar multiplicacion a hojas " + aplicarFuncionHojas(arbol,1)(_*_))

  val arbol2 = ArbolBinario(1,2,3)
  println("árbol 2: " + arbol2)

  val arbol3 = unirArboles(arbol,arbol2)

  println(arbol3)

  println("recorrido anchura árbol1 + árbol2: "+ recorridoAnchura(arbol3))

  println(profundidad(arbol3))

  println(mostrarArbol(arbol))
}