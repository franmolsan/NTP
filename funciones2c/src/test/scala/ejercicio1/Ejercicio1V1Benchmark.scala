package ejercicio1

import org.scalameter.{Key, Warmer, config}

import scala.util.Random

object Ejercicio1V1Benchmark extends App{
  // se define la configuracion estandar de las pruebas
  val configuracion = config(
    Key.exec.maxWarmupRuns := 10,
    Key.exec.minWarmupRuns := 5,
    Key.exec.benchRuns := 30,
    Key.verbose := true
  ) withWarmer(new Warmer.Default)

  // generar intervalos de forma aleatoria
  val generador = new Random
  val intervalos = (0 until 1000).map(index => {
    val inicio = generador.nextInt(1000)
    val fin = inicio + generador.nextInt(1000)
    (inicio, fin)
  }).toList

  val tiemposnoTR = configuracion measure(
    intervalos.foreach(intervalo => ejercicio1v1.sumatorio(intervalo._1, intervalo._2))
  )

  val tiemposTR = configuracion measure(
    intervalos.foreach(intervalo => ejercicio1v1.sumatorioTR(intervalo._1, intervalo._2))
    )

  println("tiempo no tr: " + tiemposnoTR)
  println("tiempo tr: " + tiemposTR)

}
