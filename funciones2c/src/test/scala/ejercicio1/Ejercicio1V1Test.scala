package ejercicio1

import org.scalatest.funsuite.AnyFunSuite

class Ejercicio1V1Test extends AnyFunSuite{

  // pruba 1: valor de la suma de 1 a 10 (55)
  test ("sumatorio no tr, inf 1, sup 10"){
    assert(ejercicio1v1.sumatorio(1,10) === 55)
  }

  //prueba 2: valor suma TR
  test ("sumatorio tr, inf 1, sup 10"){
    assert(ejercicio1v1.sumatorioTR(1,10) === 55)
  }
}
