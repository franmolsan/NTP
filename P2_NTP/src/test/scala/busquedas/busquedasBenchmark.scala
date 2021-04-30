package busquedas

import busquedas.busquedasGenericas.{busquedaBinaria,busquedaASaltos}
import org.scalameter.{Bench, Gen}
import org.scalameter.{Key, Warmer, config}

import scala.util.Random

object busquedasBenchmark extends App {
  // se define la configuracion estandar de las pruebas
  val configuracion = config(
    Key.exec.maxWarmupRuns := 10,
    Key.exec.minWarmupRuns := 5,
    Key.exec.benchRuns := 30,
    Key.verbose := true
  ) withWarmer(new Warmer.Default)
  
  // generar intervalos de forma aleatoria
  val generador = new Random
  val IntArrayYElementoABuscar: List[(Array[Int], Int)] = (1 until 100).map(index => {
    val arrayAleatorio: Array[Int] = List.fill(index)(generador.nextInt(1000)).toArray.sorted
    val aBuscar = generador.nextInt(1000)
    (arrayAleatorio, aBuscar)
  }).toList

  val CharArrayYElementoABuscar: List[(Array[Char], Char)] = (1 until 100).map(index => {
    val arrayAleatorio: Array[Char] = List.fill(index)(generador.nextPrintableChar()).toArray.sorted
    val aBuscar = generador.nextPrintableChar()
    (arrayAleatorio, aBuscar)
  }).toList

  val DoubleArrayYElementoABuscar: List[(Array[Double], Double)] = (1 until 100).map(index => {
    val arrayAleatorio: Array[Double] = List.fill(index)(generador.nextDouble()).toArray.sorted
    val aBuscar = generador.nextDouble()
    (arrayAleatorio, aBuscar)
  }).toList

  val tiemposBusquedaBinariaInt = configuracion measure(
    IntArrayYElementoABuscar.foreach(arrayYElemento => busquedaBinaria(arrayYElemento._1, arrayYElemento._2)(_>_))
  )

  val tiemposBusquedaBinariaChar = configuracion measure(
    CharArrayYElementoABuscar.foreach(arrayYElemento => busquedaBinaria(arrayYElemento._1, arrayYElemento._2)(_>_))
    )

  val tiemposBusquedaBinariaDouble = configuracion measure(
    DoubleArrayYElementoABuscar.foreach(arrayYElemento => busquedaBinaria(arrayYElemento._1, arrayYElemento._2)(_>_))
    )

  val tiemposBusquedaSaltosInt = configuracion measure(
    IntArrayYElementoABuscar.foreach(arrayYElemento => busquedaASaltos(arrayYElemento._1, arrayYElemento._2)(_>_))
    )

  val tiemposBusquedaSaltosChar = configuracion measure(
    CharArrayYElementoABuscar.foreach(arrayYElemento => busquedaASaltos(arrayYElemento._1, arrayYElemento._2)(_>_))
    )

  val tiemposBusquedaSaltosDouble = configuracion measure(
    DoubleArrayYElementoABuscar.foreach(arrayYElemento => busquedaASaltos(arrayYElemento._1, arrayYElemento._2)(_>_))
    )

  println("tiempos búsqueda binaria en colección [Int] " + tiemposBusquedaBinariaInt)
  println("tiempos búsqueda a saltos colección [Int] " + tiemposBusquedaSaltosInt)

  println("tiempos búsqueda binaria en colección [Char] " + tiemposBusquedaBinariaChar)
  println("tiempos búsqueda a saltos colección [Char] " + tiemposBusquedaSaltosChar)

  println("tiempos búsqueda binaria en colección [Double] " + tiemposBusquedaBinariaDouble)
  println("tiempos búsqueda a saltos colección [Double] " + tiemposBusquedaSaltosDouble)
}
