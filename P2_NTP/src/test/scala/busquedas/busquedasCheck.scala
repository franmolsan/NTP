package busquedas

import busquedas.busquedasGenericas.{busquedaASaltos, busquedaBinaria}
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object busquedasCheck extends Properties("Prueba de las búsquedas genéricas"){

  val MINIMO = 1;
  val MAXIMO = 30;

  // val arrayOrdenadoInt : Gen[Array[Int]] = Gen.containerOf[Set, Int](Gen.posNum[Int]).map(_.toArray).map(_.sorted)
  // val arrayOrdenadoInt : Gen[Array[Int]] = Gen.sized(Gen.choose(MINIMO,MAXIMO))(Gen.containerOf[Array, Int](Gen.posNum[Int]).map(_.toArray).map(_.sorted))

  val genArrayOrdenado = Gen.sized { tam  =>
    val size = tam
    for {
      listaInt <- Gen.listOfN(size, Gen.posNum[Int])
      arrayOrdenado <- listaInt.sorted.toArray
    } yield arrayOrdenado
  }

  property ("Las búsquedas binaria, a saltos y propia de la clase coinciden") = {
    forAll(genArrayOrdenado) { coleccion =>
      coleccion.forall({
        println("colección " + coleccion.toString)
        val aBuscar = coleccion(_)
        busquedaASaltos(coleccion,aBuscar)(_>_) == busquedaBinaria(coleccion,aBuscar)(_>_) == coleccion.indexOf(aBuscar)
      })

    }
  }

}
