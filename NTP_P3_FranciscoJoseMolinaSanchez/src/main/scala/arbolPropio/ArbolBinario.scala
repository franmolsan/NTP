package arbolPropio
/**
 * Interfaz generica para el árbol
 *
 * @tparam A
 */
sealed trait ArbolBinario[+A] {
  def estaVacio: Boolean
  def add[A](a: A): ArbolBinario[A]
}

/**
 * Objeto para definir árbol binario vacío
 */
case object ArbolVacio extends ArbolBinario[Nothing] {
  def estaVacio = true
  def add[A](nuevoElemento: A): ArbolBinario[A] = {
    println("ejecuto el add de arbol vacio")
    Cons(nuevoElemento, ArbolVacio, ArbolVacio)
  }
}

/**
 * Clase para definir cada nodo del árbol
 * @param valor elemento del tipo A que almacena el nodo
 * @param padre el padre del nodo del árbol
 * @param izq el nodo hijo por la izquierda
 * @param dcha el nodo hijo por la derecha
 * @tparam A
 */
case class Cons[A](valor : A, izq: ArbolBinario[A], dcha: ArbolBinario[A]) extends ArbolBinario[A] {
  def estaVacio = false
  override def toString = valor + " " + izq.toString + " " + dcha.toString
  def add[A](nuevoElemento: A): ArbolBinario[A] = {
    println("ejecuto el add de Cons")
    izq match {
      case ArbolVacio => izq.add(nuevoElemento)
      case _ => {
        println("matcho dcha")
        dcha match {
          case ArbolVacio => dcha.add(nuevoElemento)
          case _ => izq.add(nuevoElemento)
        }
      }
    }
  }
}

object ArbolBinario extends App{

  def apply[A](elementos : A*) : ArbolBinario[A] = {

    def go (nodoPadre: ArbolBinario[A], elementosRestantes: A*) : ArbolBinario[A] ={
      println("elementos restantes: " + elementosRestantes)
      println("arbol actual:")
      if(elementosRestantes.isEmpty) nodoPadre
      else {
        go(nodoPadre.add(elementos.head),elementosRestantes.tail:_*)
//        nodoPadre match {
//          case Cons(valor,izq,dcha) =>
//            add(elementos(elementoActual))
//          case ArbolVacio => ArbolVacio.add(elementos(elementoActual))
        //}
        // Cons(elementos(elementoActual),nodoPadre,go(nodoPadre,2*elementoActual+1),go(nodoPadre,2*elementoActual+2))
      }
    }

    //val nodoInicial = Cons(elementos(0),Nil,Nil,Nil)
    //Cons(elementos(0),Nil,go(nodoInicial,1),go(nodoInicial,2))
    go(ArbolVacio,elementos:_*)
  }

/*  def mostrarInOrden[A](nodo : ArbolBinario[A]) : String = {
    nodo match {
      case Cons(valor,padre,izq,dcha) => {
        mostrarInOrden(izq) +
        valor.toString +
        mostrarInOrden(dcha)
      }
    }
  }*/

  //mostrarInOrden(ArbolBinario(1,2,3))

  println(ArbolBinario(1,2,3).toString)
}
