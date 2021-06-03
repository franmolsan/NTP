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
    Cons(nuevoElemento, ArbolVacio, ArbolVacio, 0)
  }
}

/**
 * Clase para definir cada nodo del árbol
 * @param valor elemento del tipo A que almacena el nodo
 * @param izq el nodo hijo por la izquierda
 * @param dcha el nodo hijo por la derecha
 * @tparam A
 */
case class Cons[A](valor : A, izq: ArbolBinario[A], dcha: ArbolBinario[A], dprof: Int) extends ArbolBinario[A] {
  def estaVacio = false
  override def toString = valor + " " + izq.toString + " " + dcha.toString

  def add[A](nuevoElemento: A): ArbolBinario[A] = {

    /*if(izq.estaVacio) Cons(valor ,izq.add(nuevoElemento),dcha, profundidad+1).asInstanceOf[Cons[A]]
    else if (dcha.estaVacio) Cons(valor,izq,dcha.add(nuevoElemento), profundidad+1 ).asInstanceOf[Cons[A]]
    else {
      izq match {
        case Cons(valorIzq,izqIzq,dchaIzq,profIzq) =>
          dcha match {
            case Cons(valorDcha,izqDcha,dchaDcha,profDcha) =>
              if ((profIzq - profDcha) > 1) Cons(valor,izq,dcha.add(nuevoElemento), profundidad+1).asInstanceOf[Cons[A]]
              else Cons(valor ,izq.add(nuevoElemento),dcha, profundidad+1).asInstanceOf[Cons[A]]
          }
      }
    }*/
    println("arbol actual: " + this)
    println("elemento actual " + this.valor)
    println("d-prof nodo actual: " + dprof)

    if (dprof >= 1){
      izq match {
        case ArbolVacio => dprof+1
        case _ => dprof
      }
      Cons(valor, izq, dcha.add(nuevoElemento), dprof-1).asInstanceOf[Cons[A]]
    }
    else {
      val nuevaProf = izq match {
        case ArbolVacio => dprof+1
        case _ => dprof
      }
      Cons(valor, izq.add(nuevoElemento), dcha, dprof+1).asInstanceOf[Cons[A]]
    }

/*    izq match {
      case ArbolVacio => Cons(valor, izq.add(nuevoElemento), dcha, dprof+1).asInstanceOf[Cons[A]]
      case Cons(valorI, izqI, dchaI, profI) =>{

        println("profundidad hijo izq: " + profI)

        dcha match {
          case ArbolVacio => Cons(valor, izq, dcha.add(nuevoElemento), dprof-1).asInstanceOf[Cons[A]]
          case Cons(valorD, izqD, dchaD, profD) => {

            println("profundidad hijo dcho: " + profD)

            if ((profI - profD) > 1) Cons(valor, izq, dcha.add(nuevoElemento), dprof-1).asInstanceOf[Cons[A]]
            else Cons(valor, izq.add(nuevoElemento), dcha,dprof+1).asInstanceOf[Cons[A]]

          }
        }
      }
    }*/

  }
}

object ArbolBinario extends App{

  def apply[A](elementos : A*) : ArbolBinario[A] = {

    def go (nodoPadre: ArbolBinario[A], elementosRestantes: A*) : ArbolBinario[A] ={

      if(elementosRestantes.isEmpty) nodoPadre
      else {
        go(nodoPadre.add(elementosRestantes.head),elementosRestantes.tail:_*)
//        nodoPadre match {
//         case Cons(valor,izq,dcha, prof) => nodoPadre.add()
//    case ArbolVacio => ArbolVacio.add(elementos(elementoActual))
//        }

      }
    }

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

   println(ArbolBinario(1,2,3,4,5,6,7,8).toString)

  // println(ArbolVacio.add(1).add(2).add(3).toString)
}
