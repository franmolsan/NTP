import org.scalameter.{config, Key, Warmer}

object TiemposEjecucion extends App{
  val configuracion = config {
    Key.exec.maxWarmupRuns := 10;
    Key.exec.minWarmupRuns := 5;
    Key.exec.benchRuns := 10;
    Key.verbose := true;
  } withWarmer(new Warmer.Default)


  // se determina el intervalo a usar en todas
  // las purebas
  val inf = 1000
  val sup = 1000000
  val n = 5

  // se prueba el método usando secuencias, filtrando
  // a partor de un rango de valores
  val tiempoMetodo1 = configuracion measure{
    NumerosPrimos.enesimoPrimoEnRango(inf,sup,n)
  }

  println("Tiempo de ejecucion con el metodo1 " + tiempoMetodo1)

  val tiempoMetodo2 = configuracion measure{
    NumerosPrimos.enesimoPrimoEnRangoRecursivo(inf,sup,n)
  }

  println("Tiempo de ejecucion con el metodo2 " + tiempoMetodo2)

  val tiempoMetodo3 = configuracion measure{
    NumerosPrimos.enesimoPrimoEnRangoPerezoso(inf,sup,n)
  }

  println("Tiempo de ejecucion con el metodo3 " + tiempoMetodo3)

  val tiempoMetodo4 = configuracion measure{
    NumerosPrimos.enesimoPrimoEnRangoView(inf,sup,n)
  }

  println("Tiempo de ejecucion con el metodo4 " + tiempoMetodo4)

}
