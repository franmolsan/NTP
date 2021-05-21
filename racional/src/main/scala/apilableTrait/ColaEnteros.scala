package apilableTrait

import scala.collection.mutable.ArrayBuffer

abstract class ColaEnteros {
  def get : Int
  def put(x: Int)
}

class ColaEnterosArray extends ColaEnteros {

  // estructura de almacenamiento de datos
  private val cola = new ArrayBuffer[Int]

  /**
   * devuelve el elemento de cabeza de la cola
   * @return
   */
  def get: Int = cola.remove(0)

  /**
   * agrega nuevo elemento al final de la cola
   * @param x
   */
  def put (x: Int) = {
    cola += x
  }
}


/**
 * Dota a la clase ColaEnteros de la funcionalidad de
 * multiplicar por 2 los elementos a insertar
 */
trait Doblar extends ColaEnteros{
  abstract override def put(x: Int) = {
    println("put en trait doblar - a instertar x: " + x)
    super.put(x*2)
  }
}

/**
 * Dota a la clase ColaEnteros de la capacidad de filtrado de negativos
 */
trait FiltrarNegativos extends ColaEnteros {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) {
      println("put en trait filtrado de negativos - a insertar x: " + x)
      super.put(x)
    }
  }
}

/**
 * Dota a la clase ColaEnteros de la capacidad de filtrado de negativos
 */
trait Incrementar extends ColaEnteros {
  abstract override def put(x: Int): Unit = {
    println("put en trait incrementar - a insertar x: " + x)
    super.put(x+1)
  }
}

// se pueden definir clases específicas mezclando funcionalidad
class ColaDoblando extends ColaEnterosArray with Doblar

object PruebaTraitApilables extends App{
  val cola1 = new ColaDoblando
  cola1.put(2)

  println("valor de cabeza cola1 " + cola1.get)

  // puedo crear objetos mezclando la funcionalidad que me interesa
  // sin necesidad de definir las clases de forma completa
  // aquí, hay una especie de herencia múltiple: mecanismo de linearización
  // se aplica primero el último: filtrar negativos, y luego Incrementar
  val cola2 = new ColaEnterosArray with Incrementar with FiltrarNegativos
  cola2.put(-1)
  cola2.put(0)
  cola2.put(1)

  println("se obtiene cabeza de la pila " + cola2.get)

  val cola3 = new ColaEnterosArray with Incrementar with FiltrarNegativos
  cola3.put(-1)
  cola3.put(0)
  cola3.put(1)
  println("se obtiene cabeza de la pila " + cola3.get)

}

