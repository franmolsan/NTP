package abstractas

import java.time.LocalDate

// es abstracta pero realmente no tiene nada abstracto
abstract class Coche(val fechaCompra : LocalDate, var automatico : Boolean){
  // metodo toString
  override def toString : String = "Fecha " + fechaCompra + ", automatico: " + automatico

  def cambiarAutomatico : Unit
}

class CocheMarca(val marca : String, fecha : LocalDate, automatico : Boolean,
                 val color : String) extends Coche(fecha, automatico){
  override def toString: String = super.toString + ", color: " + color + ", marca: " + marca

  override def cambiarAutomatico: Unit = {
    automatico = false
  }
}


object ClasesAbstractas extends App {
  // utilizamos los parámetros con nombre, por lo que podemos alterar el orden en el que los pasamos
  val obj1 = new CocheMarca(fecha = java.time.LocalDate.now, marca = "Seat", automatico = false, color = "rojo")

  println(obj1)
}
