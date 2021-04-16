package ejercicio1

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object EjercicioV1Check extends Properties("Pruba de ejercicio1 version 1"){

  val INF : Int = 100
  val SUP : Int = 1000

  // generador de intervalos
  val intervalos: Gen[(Int, Int)] = for {
    inferior <- Gen.choose(INF, INF+50)
    superior <- Gen.choose(SUP, SUP+50)
  }yield (inferior,superior)

  property("Igualdad de metodos no TR y TR") = {
    forAll(intervalos) { intervalo => {
      val resultadoNOTR = ejercicio1v1.sumatorio(intervalo._1, intervalo._2)
      val resultadoTR = ejercicio1v1.sumatorioTR(intervalo._1, intervalo._2)

      //comprobamos
      resultadoTR == resultadoNOTR
    }}
  }
}
