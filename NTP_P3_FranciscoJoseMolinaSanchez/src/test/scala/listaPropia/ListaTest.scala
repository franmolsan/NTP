package listaPropia

import listaPropia.Lista.{asignarCabeza, concatenar, eliminar, eliminarMientras, eliminarUltimo, foldLeft, longitud, productoEnteros, productoFoldRight, sumaEnteros, sumaFoldRight, tail}
import org.scalatest.funsuite.AnyFunSuite

class ListaTest extends AnyFunSuite{

  // listas a utilizar durante las pruebas
  val ListaEnterosPropia = Lista(1,2,3,4,5)
  val ListaEnterosScala = List(1,2,3,4,5)

  val ListaCharPropia1 = Lista('a','b','c','d')
  val ListaCharPropia2 = Lista('e','f','g','h')

  val ListaCharScala1 = List('a','b','c','d')
  val ListaCharScala2 = List('e','f','g','h')


  // prueba 1
  test("Calculo correcto de la longitud de la lista") {
    assert(longitud(ListaEnterosPropia) == ListaEnterosScala.length)
  }

  // prueba 2
  test("Suma correcta de los elementos de la lista de enteros") {
    assert(sumaEnteros(ListaEnterosPropia) == ListaEnterosScala.sum)
  }

  // prueba 3
  test("Producto correcta de los elementos de la lista de enteros") {
    assert(productoEnteros(ListaEnterosPropia) == ListaEnterosScala.product)
  }

  // prueba 4
  test("Concatenación produce longitud correcta") {
    // comparamos que el resultado de concatenar tiene la misma longitud
    // en ambos casos
    assert(longitud(concatenar(ListaCharPropia1,ListaCharPropia2)) ==
      (ListaCharScala1 ::: ListaCharScala2).length)
  }

  // prueba 5
  test("Concatenación es correcta") {
    // comprobamos si la concatenación produce el resultado
    // que debería
    assert(concatenar(ListaCharPropia1,ListaCharPropia2) ==
    Lista('a','b','c','d','e','f','g','h'))
  }

  // prueba 6
  test("Suma FoldRight es correcta") {
    assert(sumaFoldRight(ListaEnterosPropia) == ListaEnterosScala.foldRight(0)(_+_))
  }

  // prueba 7
  test("Producto FoldRight es correcto") {
    assert(productoFoldRight(ListaEnterosPropia) == ListaEnterosScala.foldRight(1)(_*_))
  }

  // prueba 8
  test("Asignar cabeza es correcto") {
    assert(asignarCabeza(ListaCharPropia1,'e') == Lista('e','b','c','d'))
  }

  // prueba 9
  test("Tail es correcto") {
    assert(tail(ListaCharPropia1) == Lista('b','c','d'))
  }

  // prueba 10
  test("Eliminar n es correcto") {
    assert(eliminar(ListaCharPropia1, 2) == Lista('c','d'))
  }

  // prueba 11
  test("Eliminar mientras es correcto") {
    val condicionEliminar: (Int) => Boolean = (num) => num < 4
    assert(eliminarMientras(ListaEnterosPropia, condicionEliminar) == Lista(4,5))
  }

  // prueba 12
  test("Eliminar ultimo es correcto") {
    assert(eliminarUltimo(ListaEnterosPropia) == Lista(1,2,3,4))
  }

  // prueba 13
  test("FoldLeft es correcto") {
    assert(foldLeft(ListaEnterosPropia,1)(_/_) == ListaEnterosScala.foldLeft(1)(_/_))
  }

}
