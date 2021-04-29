package busquedas

import busquedas.busquedasGenericas.{busquedaASaltos, busquedaBinaria}
import org.scalacheck.Prop.{collect, forAll}
import org.scalacheck.{Gen, Prop, Properties}

import scala.util.Random

object busquedasCheck extends Properties("Pruebas búsquedas"){

  val MINIMO = 1;
  val MAXIMO = 100;

  val intAleatorio = Random.between(MINIMO,MAXIMO)
  val tamAleatorio = Random.between(MINIMO,MAXIMO)

  /*
    val genArrayOrdenado = Gen.sized { tam  =>
    val size = tam+1
    for {
      listaInt <-
      arrayOrdenado <- listaInt.sorted.toArray
    } yield arrayOrdenado
  }
   */

  val genArrayOrdenado = for {
    lista <- Gen.listOfN(tamAleatorio, Gen.posNum[Int])
    arrayOrdenado <- lista.toArray.sorted
  } yield arrayOrdenado


  property ("Las búsqueda de la clase y a saltos coinciden") = {
    forAll(genArrayOrdenado) { coleccion => {
        busquedaASaltos(coleccion,intAleatorio)(_>_) == coleccion.indexOf(intAleatorio)
      }
    }
  }

  property ("Las búsqueda de la clase y binaria coinciden") = {
    forAll(genArrayOrdenado) { coleccion => {
      val resultadoBinaria = busquedaBinaria(coleccion,intAleatorio)(_>_)
      val resultaddoClase = coleccion.indexOf(intAleatorio)
        println(resultadoBinaria + "vs" + resultaddoClase)
      resultadoBinaria == resultaddoClase
      }
    }
  }


}
