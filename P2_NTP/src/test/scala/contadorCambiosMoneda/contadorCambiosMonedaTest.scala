package contadorCambiosMoneda

import org.scalatest.funsuite.AnyFunSuite
import contadorCambiosMoneda.listarCambiosPosibles

class contadorCambiosMonedaTest extends AnyFunSuite {

  // prueba 1
  test("comprobar cambio de 4 con monedas de tipo 1 y 2") {
    val cambios: List[List[Int]] = listarCambiosPosibles(4,List(1,2))
    assert(cambios.forall(_.sum == 4))
  }

  // Prueba 2
  test("comprobar cambio de 6 con monedas de tipo 1, 2 y 4") {
    val cambios: List[List[Int]] = listarCambiosPosibles(6,List(1,2,4))
    assert(cambios.forall(_.sum == 6))
  }

  // Prueba 3
  test("comprobar cambio de 7 con monedas de tipo 1, 2, 4 y 6") {
    val cambios: List[List[Int]] = listarCambiosPosibles(7,List(1,2,4,6))
    assert(cambios.forall(_.sum == 7))
  }

  // Prueba 4
  test("comprobar cambio de 8 con monedas de tipo 1 y 4") {
    val cambios: List[List[Int]] = listarCambiosPosibles(8,List(1,4))
    assert(cambios.forall(_.sum == 8))
  }

  // Prueba 5
  test("comprobar cambio de 20 con monedas de tipo 1, 2 y 7") {
    val cambios: List[List[Int]] = listarCambiosPosibles(20,List(1,2,7))
    assert(cambios.forall(_.sum == 20))
  }

  // Prueba 6
  test("comprobar cambio de 30 con monedas de tipo 5 y 6") {
    val cambios: List[List[Int]] = listarCambiosPosibles(30, List(5,6))
    assert(cambios.forall(_.sum == 30))
  }

  // Prueba 7
  test("comprobar cambio de 70 con monedas de tipo 2, 50 y 100") {
    val cambios: List[List[Int]] = listarCambiosPosibles(70, List(2,50,100))
    assert(cambios.forall(_.sum == 70))
  }

  // Prueba 8
  test("comprobar cambio de 10 con monedas de tipo 5 y 6") {
    val cambios: List[List[Int]] = listarCambiosPosibles(10, List(5,6))
    assert(cambios.forall(_.sum == 10))
  }

}
