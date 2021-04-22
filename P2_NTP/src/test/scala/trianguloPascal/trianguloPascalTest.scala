package trianguloPascal


import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object trianguloPascalTest extends Properties("Prueba del triangulo de pascal"){

  val MAXIMO = 10;
  // generamos el valor de fila y de la columna para obtener los bordes.
  val coordenadasExtremos = for {
    // generar número de fila, entre el 0 y máximo
    // excluyendo el maximo
    fila <- Gen.choose(0, MAXIMO)

    // elegimos el número de la columna:
    // puede ser el 0, o el valor de la fila
    // de esta forma, obtendremos un valor de los extremos
    columna <- Gen.oneOf(0,fila)
  } yield (fila, columna)

  // generamos el valor de fila y de la columna
  // para obtener los valores internos
  val coordenadasInternas = for {
    // generar número de fila, entre el 2 y máximo
    fila <- Gen.choose(2, MAXIMO)

    // elegimos el número de la columna:
    // un número entre el 1 y el valor de la fila - 1
    // de esta forma, obtendremos un valor de los extremos
    columna <- Gen.choose(1,fila-1)
  } yield (fila, columna)

  property (" Elementos en los lados del triangulo valen 1") = {
    forAll(coordenadasExtremos) { (i) => {
      val resultado = trianguloPascal.calcularValorTrianguloPascal(i._1, i._2)
      println("Fila = " + i._1 + " Columna = " + i._2 +
      " Resultado = " + resultado);
      resultado == 1
    }}
  }



  property (" Valores internos son iguales a la suma de los dos elementos superiores") = {
    forAll(coordenadasInternas) { (i) => {
      val resultado = trianguloPascal.calcularValorTrianguloPascal(i._1, i._2)
      println("Fila = " + i._1 + " Columna = " + i._2 +
        " Resultado = " + resultado);
      val sumaSuperiores = trianguloPascal.calcularValorTrianguloPascal(i._1-1, i._2-1) +
        trianguloPascal.calcularValorTrianguloPascal(i._1-1, i._2)
      resultado == sumaSuperiores
    }}
  }
}
