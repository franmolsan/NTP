package contadorCambiosMoneda

import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object contadorCambiosMonedaTest extends Properties("Prueba del contador de cambios de moneda"){

  val CAMBIO_MINIMO = 10
  val CAMBIO_MAXIMO = CAMBIO_MINIMO * 2
  val MONEDA_MENOR = 1
  val MONEDA_MAYOR = 2

  val datosGenerados: Gen[(Int, List[Int])] = for {
    cantidadADevolver <- Gen.choose(CAMBIO_MINIMO, CAMBIO_MAXIMO)
    posiblesMonedas <- Gen.containerOf[List, Int](Gen.choose(MONEDA_MENOR,MONEDA_MAYOR))
  } yield (cantidadADevolver,posiblesMonedas)

  property ("Todos los cambios suman la cantidad a devolver") = {
    forAll(datosGenerados) { datos =>
      println("cantidad a devolver " + datos._1)
      println("monedas disponibles " + datos._2 )
      val cambios = contadorCambiosMoneda.listarCambiosPosibles(datos._1, datos._2)

      cambios.foreach(_.sum) == datos._1
    }
  }

}
