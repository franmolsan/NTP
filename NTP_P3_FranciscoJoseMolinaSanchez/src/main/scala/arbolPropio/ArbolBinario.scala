package arbolPropio

import arbolPropio.ArbolBinario.profundidad

/**
 * Interfaz generica para el árbol
 *
 * @tparam A
 */
sealed trait ArbolBinario[+A] {
  def estaVacio: Boolean
  def hayHueco: Boolean
  def add[A](a: A): ArbolBinario[A]
}

/**
 * Objeto para definir árbol binario vacío
 */
case object ArbolVacio extends ArbolBinario[Nothing] {
  def estaVacio = true
  def hayHueco = true
  def add[A](nuevoElemento: A): ArbolBinario[A] = {
    Cons(nuevoElemento, ArbolVacio, ArbolVacio)
  }
}

/**
 * Clase para definir cada nodo del árbol
 * @param valor elemento del tipo A que almacena el nodo
 * @param izq el nodo hijo por la izquierda
 * @param dcha el nodo hijo por la derecha
 * @tparam A
 */
case class Cons[A](valor : A, izq: ArbolBinario[A], dcha: ArbolBinario[A]) extends ArbolBinario[A] {
  def estaVacio = false
  def hayHueco = izq.estaVacio || dcha.estaVacio
  override def toString = valor + " " + izq.toString + " " + dcha.toString

  def add[A](nuevoElemento: A): ArbolBinario[A] = {
    if (izq.estaVacio) Cons(valor,izq.add(nuevoElemento),dcha).asInstanceOf[Cons[A]]
    else if (dcha.estaVacio) Cons(valor,izq,dcha.add(nuevoElemento)).asInstanceOf[Cons[A]]
    else if(dcha.hayHueco && !izq.hayHueco) Cons(valor,izq,dcha.add(nuevoElemento)).asInstanceOf[Cons[A]]
    else Cons(valor,izq.add(nuevoElemento),dcha).asInstanceOf[Cons[A]]
  }
}

object ArbolBinario extends App{

  /**
   * Metodo para permitir crear árboles sin usar new
   * @param elementos para crear el árbol
   * @tparam A
   * @return
   */
  def apply[A](elementos : A*) : ArbolBinario[A] = {

    /**
     * Función interna para formar el árbol,
     * añadiendo nuevos nodos al padre
     * @param nodoPadre
     * @param elementosRestantes
     * @return
     */
    @annotation.tailrec
    def go (nodoPadre: ArbolBinario[A], elementosRestantes: A*) : ArbolBinario[A] = {

      // si no nos quedan elementos por añadir, devolvemos el árbol formado
      if(elementosRestantes.isEmpty) nodoPadre

      // si nos quedan elementos, continuar la recursividad
      else go(nodoPadre.add(elementosRestantes.head),elementosRestantes.tail:_*)
    }

    // desencadenar recursividad
    go(ArbolVacio,elementos:_*)
  }

  def recorridoInOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case Cons(valor,izq,dcha) => {
        recorridoInOrden(izq) +
        valor.toString +
        recorridoInOrden(dcha)
      }
      case _ => ""
    }
  }

  def recorridoPreOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case Cons(valor,izq,dcha) => {
        valor.toString +
        recorridoPreOrden(izq) + recorridoPreOrden(dcha)
      }
      case _ => ""
    }
  }

  def recorridoPosOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case Cons(valor,izq,dcha) => {
        recorridoPosOrden(izq) + recorridoPosOrden(dcha) + valor.toString
      }
      case _ => ""
    }
  }

  def recorridoAnchura[A](arbol : ArbolBinario[A]) : Unit = {

    def go(nodoActual: ArbolBinario[A], profActual: Int, profObjetivo: Int, izquierdaRecorrida: Boolean): Unit ={

      //println("profundidad actual: " + profActual + " prof objetivo: " +profObjetivo + " izqu recorrida: " + izquierdaRecorrida)
      //println("nodo actual: " + nodoActual)

      def listaNodo(nodo : ArbolBinario[A]) = {
        nodo match {
          case Cons(valor,izq,dcha) => print(valor.toString)
          case _ => print("")
        }
      }

      if(profActual == profObjetivo){

        //println("estoy en profundidad objetivo ")

        nodoActual match {
          case Cons(valor,izq,dcha) => {
            if(izquierdaRecorrida) {
              //println("recorro derecha ")
              listaNodo(dcha)
              go(arbol,0,profObjetivo+1,false)
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
          case Cons(valor,izq,dcha) => {

            if (izquierdaRecorrida){
              go(dcha,profActual+1,profObjetivo,izquierdaRecorrida)
            }
            else {
              go(izq,profActual+1,profObjetivo,izquierdaRecorrida)
            }
          }
          case _ =>

        }
      }
    }

    go(arbol,0,0,false)

  }

  def profundidad[A](arbolBinario: ArbolBinario[A]) : Int = {

    /**
     * Función interna para calcular la profundidad de un árbol
     * @param arbolActual
     * @param profActual
     * @return la profundidad del árbol
     */
    @annotation.tailrec
    def go (arbolActual: ArbolBinario[A], profActual: Int): Int = {
      arbolActual match {

        // si el árbol está vacío, es porque ya hemos terminado
        // sin embargo, justo antes hemos aumentado el acumulador
        // por lo que tenemos que disminuirlo para devolver la profundidad correcta
        case ArbolVacio => profActual-1

        // si el árbol no está vacío, aumentar el acumulador
        case Cons(valor, izq, dcha) =>
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

  val arbol = ArbolBinario(1,2,3,4,5,6,7,8)
  println(arbol)
  println(profundidad(arbol))

  var arbol2 = ArbolBinario(2,4,5,6,456,9456,863)
  println(arbol2)
  println(profundidad(arbol2))

  println("print inorden " +  recorridoInOrden(arbol))
  println("print posorden " + recorridoPosOrden(arbol))
  println("print preorden " + recorridoPreOrden(arbol))

  recorridoAnchura(arbol)
}
