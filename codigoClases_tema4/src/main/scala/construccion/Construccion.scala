package construccion




//objetos compañeros: companion object
// idea del patrón factoria
object Multiplicador {
  def apply(factor: Int) = new Multiplicador(factor)

  // "métodos estáticos" de java
  def componer(obj1: Multiplicador, obj2: Multiplicador) =
    new Multiplicador(obj1.factor + obj2.factor)
}

class Multiplicador (val factor : Int) {
  def apply (valor : Int) = valor * factor

}

object Construccion extends App{
  val multiplicadorPor3 = new Multiplicador(3)

  // se usa apply de forma directa
  println("multiplicador por 8: " + multiplicadorPor3.apply(8))

  // por el hecho de llamarse apply se usar de forma más directa
  // no conviene abusar, solo debemos usarlo cuando sea natural
  val resultado = multiplicadorPor3(8)

  val lista = List(1,2,3,4,5)
  val tercer = List(3)
  val tercer2 = lista.apply(3)

  // creación de objetos haciendo uso de apply
  val multiplicadorPor10 = Multiplicador(10)

  // funciona como un método estático de java
  val multiplicadorCompuesto = Multiplicador.componer(multiplicadorPor3,multiplicadorPor10)
}
