package contadorCambiosMoneda

import scala.collection.mutable.ListBuffer

object contadorCambiosMoneda {

  def listarCambiosPosibles(cantidad : Int, monedas : List[Int]) : List[List[Int]] = {

    def go( tope : Int, monedasDisponibles : List[Int], listaCambios : List[List[Int]]): List[List[Int]] ={
      println("empiezo con las monedas : " + monedasDisponibles)
      val nuevaListaCambios = generarCambiosConMonedas(cantidad, monedasDisponibles, listaCambios)
      if ( tope < monedas.length-1 ){
        val nuevoTope = tope + 1
        val nuevasMonedas = monedasDisponibles ::: List(monedas(nuevoTope))
        go (nuevoTope, nuevasMonedas, nuevaListaCambios)
      }
      else{
        nuevaListaCambios
      }
    }

    val listaFinal = go(0, List(monedas.head), List(List()))
    if (listaFinal.nonEmpty) listaFinal.tail
    else listaFinal
  }


  def generarCambiosConMonedas(cantidad : Int, monedas : List[Int], listaCambios :  List[List[Int]]) : List[List[Int]] = {
      val unCambio : List[Int] = generarUnCambio(cantidad, monedas, monedas.head, List())
      if (monedas.size > 1) {
        if (!listaCambios.contains(unCambio) && !unCambio.contains(-1))  generarCambiosConMonedas(cantidad,monedas.tail,listaCambios:::List(unCambio))
        else generarCambiosConMonedas(cantidad,monedas.tail,listaCambios)
      }
      else if (unCambio.contains(-1)) listaCambios
      else listaCambios:::List(unCambio)
  }

  def generarUnCambio(restante: Int, monedasPosibles: List[Int], monedaPreferente: Int, cambioActual: List[Int]): List[Int] = {
    if (restante == 0) {
      println("devuelvo el cambio " + cambioActual)
      cambioActual
    }
    else {
      val tiposUtiles = monedasPosibles.filterNot(_>restante)
      println(" Puedo usar monedas " + tiposUtiles)
      println(" No he acabado el cambio, me quedan " + restante)

      if (tiposUtiles.isEmpty) List(-1)

      else {
        val monedaPref =
          if (monedaPreferente > restante || cambioActual.contains(monedaPreferente)) tiposUtiles.last
          else monedaPreferente
          println("uso moneda " + monedaPref)
          generarUnCambio(restante-monedaPref, tiposUtiles, monedaPreferente ,cambioActual:::List(monedaPref))
      }
    }
  }

  def main(args: Array[String]) ={

    val listaPosiblesCambios = listarCambiosPosibles(4,List(1,2,4,6))

    println(listaPosiblesCambios)
  }

}
