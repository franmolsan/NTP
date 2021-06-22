package arbolPropio

import arbolPropio.ArbolBinario.{aplicarFuncionHojas, numNodosHoja, numNodosInternos, numTotalNodos, profundidad, recorridoAnchura, recorridoInOrden, recorridoPosOrden, recorridoPreOrden, sumarValoresHojas, unirArboles}
import org.scalatest.funsuite.AnyFunSuite

class ArbolBinarioTest extends AnyFunSuite{

  // árboles a utilizar durante las pruebas
  val arbol1 = ArbolBinario(1,2,3,4,5,6,7,8)
  val arbol2 = ArbolBinario(1,2)
  val arbolVacio = ArbolBinario();

  /**** Pruebas de los recorridos en profundidad ****/

  // prueba 1
  test("Recorrido InOrden correcto arbol 1") {
    assert(recorridoInOrden(arbol1) ==  "1 raiz-izq-izq 2 raiz-izq 3 raiz-izq-dcha 4 raiz 5 raiz-dcha-izq 6 raiz-dcha 7 raiz-dcha-dcha 8 ")
  }

  // prueba 2
  test("Recorrido InOrden correcto arbol 2") {
    assert(recorridoInOrden(arbol2) ==  "1 raiz 2 ")
  }

  // prueba 3
  test("Recorrido InOrden correcto arbol vacío") {
    assert(recorridoInOrden(arbolVacio) ==  "")
  }

  // prueba 4
  test("Recorrido PreOrden correcto arbol 1") {
    assert(recorridoPreOrden(arbol1) ==  "raiz raiz-izq raiz-izq-izq 1 2 raiz-izq-dcha 3 4 raiz-dcha raiz-dcha-izq 5 6 raiz-dcha-dcha 7 8 ")
  }

  // prueba 5
  test("Recorrido PreOrden correcto arbol 2") {
    assert(recorridoPreOrden(arbol2) ==  "raiz 1 2 ")
  }

  // prueba 6
  test("Recorrido PreOrden correcto arbol vacío") {
    assert(recorridoPreOrden(arbolVacio) ==  "")
  }

  // prueba 7
  test("Recorrido PosOrden correcto arbol 1") {
    assert(recorridoPosOrden(arbol1) ==  "1 2 raiz-izq-izq 3 4 raiz-izq-dcha raiz-izq 5 6 raiz-dcha-izq 7 8 raiz-dcha-dcha raiz-dcha raiz ")
  }

  // prueba 8
  test("Recorrido PosOrden correcto arbol 2") {
    assert(recorridoPosOrden(arbol2) ==  "1 2 raiz ")
  }

  // prueba 9
  test("Recorrido PosOrden correcto arbol vacío") {
    assert(recorridoPosOrden(arbolVacio) ==  "")
  }

  /**** Pruebas de los recorridos en anchura ****/

  // prueba 10
  test("Recorrido anchura correcto arbol 1") {
    assert(recorridoAnchura(arbol1) ==  "raiz raiz-izq raiz-dcha raiz-izq-izq raiz-izq-dcha raiz-dcha-izq raiz-dcha-dcha 1 2 3 4 5 6 7 8 ")
  }

  // prueba 11
  test("Recorrido anchura correcto arbol 2") {
    assert(recorridoAnchura(arbol2) ==  "raiz 1 2 ")
  }

  // prueba 12
  test("Recorrido anchura correcto arbol vacío") {
    assert(recorridoAnchura(arbolVacio) ==  "")
  }


  /**** Pruebas del tamaño  ****/

  // prueba 13
  test("Numero correcto de nodos hoja arbol 1") {
    assert(numNodosHoja(arbol1) == 8)
  }

  // prueba 14
  test("Numero correcto de nodos hoja arbol 2") {
    assert(numNodosHoja(arbol2) == 2)
  }

  // prueba 15
  test("Numero correcto de nodos hoja arbol vacío") {
    assert(numNodosHoja(arbolVacio) == 0)
  }

  // prueba 16
  test("Calculo correcto del numero de nodos arbol 1") {
    assert(numTotalNodos(arbol1) == numNodosInternos(arbol1) + numNodosHoja(arbol1))
  }

  // prueba 17
  test("Calculo correcto del numero de nodos arbol 2") {
    assert(numTotalNodos(arbol2) == numNodosInternos(arbol2) + numNodosHoja(arbol2))
  }

  // prueba 18
  test("Calculo correcto del numero de nodos arbol vacío") {
    assert(numTotalNodos(arbolVacio) == numNodosInternos(arbolVacio) + numNodosHoja(arbolVacio))
  }

  /**** Pruebas de las operaciones con las hojas  ****/

  // prueba 19
  test("Suma de los valores de los nodos hoja arbol 1") {
    assert(sumarValoresHojas(arbol1) == 36)
  }

  // prueba 20
  test("Suma de los valores de los nodos hoja arbol 2") {
    assert(sumarValoresHojas(arbol2) == 3)
  }

  // prueba 21
  test("Suma de los valores de los nodos hoja arbol vacío") {
    assert(sumarValoresHojas(arbolVacio) == 0)
  }

  // prueba 22
  test("Multiplicacion de los valores de los nodos hoja arbol 1") {
    assert(aplicarFuncionHojas(arbol1,1)(_*_) == 40320)
  }

  // prueba 23
  test("Multiplicacion de los valores de los nodos hoja arbol 2") {
    assert(aplicarFuncionHojas(arbol2,1)(_*_) == 2)
  }

  // prueba 24
  test("Multiplicacion de los valores de los nodos hoja arbol vacio") {
    assert(aplicarFuncionHojas(arbolVacio,1)(_*_) == 1) // devuelve 1 porque es el elemento neutro
  }

  /**** Pruebas de la unión de árboles  ****/

  // prueba 25
  test("Tam correcto de la unión arbol 1 y arbol 2") {
    val arbolUnion = unirArboles(arbol1,arbol2)
    assert(numTotalNodos(arbolUnion) == numTotalNodos(arbol1) + numTotalNodos(arbol2) + 1)
  }

  // prueba 26
  test("Tam correcto de la unión arbol 1 y arbol vacio") {
    val arbolUnion = unirArboles(arbol1,arbolVacio)
    assert(numTotalNodos(arbolUnion) == numTotalNodos(arbol1))
  }

  // prueba 27
  test("Tam correcto de la unión arbol 2 y arbol vacio") {
    val arbolUnion = unirArboles(arbol2,arbolVacio)
    assert(numTotalNodos(arbolUnion) == numTotalNodos(arbol2))
  }

  /**** Pruebas de la profundidad  ****/

  // prueba 28
  test("Profundidad arbol 1") {
    assert(profundidad(arbol1) == 3)
  }

  // prueba 29
  test("Profundidad arbol 2") {
    assert(profundidad(arbol2) == 1)
  }

  // prueba 30
  test("Profundidad arbol vacio") {
    assert(profundidad(arbolVacio) == 0)
  }

}
