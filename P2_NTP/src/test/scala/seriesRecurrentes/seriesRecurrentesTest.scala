package seriesRecurrentes

import org.scalatest.funsuite.AnyFunSuite
import seriesRecurrentes.{serieFibonacci, seriePell, serieLucas, seriePellLucas, serieJacobsthal}

/**
 * Para cada serie, se realizan 3 pruebas
 * comprobando directamente el valor del primer, quinto y décimo término
 */
class seriesRecurrentesTest extends AnyFunSuite {

  // prueba 1 Fibonacci
  test("Primer término de la serie de Fibonacci == 0") {
    assert(serieFibonacci(1) == 0)
  }

  // prueba 2 Fibonacci
  test("Quinto término de la serie de Fibonacci == 3") {
    assert(serieFibonacci(5) == 3)
  }

  // prueba 3 Fibonacci
  test("Décimo término de la serie de Fibonacci == 34") {
    assert(serieFibonacci(10) == 34)
  }

  // prueba 1 Lucas
  test("Primer término de la serie de Lucas == 2") {
    assert(serieLucas(1) == 2)
  }

  // prueba 2 Lucas
  test("Quinto término de la serie de Lucas == 7") {
    assert(serieLucas(5) == 7)
  }

  // prueba 3 Lucas
  test("Décimo término de la serie de Lucas == 76") {
    assert(serieLucas(10) == 76)
  }

  // prueba 1 Pell
  test("Primer término de la serie de Pell == 2") {
    assert(seriePell(1) == 2)
  }

  // prueba 2 Pell
  test("Quinto término de la serie de Pell == 82") {
    assert(seriePell(5) == 82)
  }

  // prueba 3 Pell
  test("Décimo término de la serie de Pell == 6726") {
    assert(seriePell(10) == 6726)
  }

  // prueba 1 Pell-Lucas
  test("Primer término de la serie de Pell-Lucas == 2") {
    assert(seriePellLucas(1) == 2)
  }

  // prueba 2 Pell-Lucas
  test("Quinto término de la serie de Pell-Lucas == 34") {
    assert(seriePellLucas(5) == 34)
  }

  // prueba 3 Pell-Lucas
  test("Décimo término de la serie de Pell-Lucas == 2786") {
    assert(seriePellLucas(10) == 2786)
  }

  // prueba 1 Jacobsthal
  test("Primer término de la serie de Jacobsthal == 0") {
    assert(serieJacobsthal(1) == 0)
  }

  // prueba 2 Jacobsthal
  test("Quinto término de la serie de Jacobsthal == 5") {
    assert(serieJacobsthal(5) == 5)
  }

  // prueba 3 Jacobsthal
  test("Décimo término de la serie de Jacobsthal == 171") {
    assert(serieJacobsthal(10) == 171)
  }

}
