package paquetes

import scala.collection.mutable

object Importar extends App{

  // se crea objeto sin hacer uso de import
  val d1 = new java.util.Date

  // se puede hacer importación parcial
  import java.util
  val d2 = new util.Date

  // importar contenido completo de un paquete
  import collection.mutable._

  val obj1 = new ArrayBuffer[String]
  obj1 += "Hola"
  obj1 += "adios"

  import collection.mutable.{Queue, ArrayBuffer}
  val q1 = new Queue

  // importacion asignando alias a la clase
  import collection.immutable.{Map => MapaInmutable}
  val m1  = MapaInmutable(2->3, 8->5)

  // importar clases y ocultar algunas de ellas
  // en concreto la clase Random la ocultamos (no importamos)
  import java.util.{Random => _, _}
  // val generador = new Random
  val arrayList = new ArrayList[Int]


}
