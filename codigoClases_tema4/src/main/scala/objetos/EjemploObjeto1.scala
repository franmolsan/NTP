package objetos

object Saludo {
  println("En objeto saludo......") // solo se va a mostrar una vez, porque es un objeto!
  def saludar = "Hola desde objeto saludo"
}

object EjemploObjeto1 extends App{
// se hace uso del objeto
  println(Saludo.saludar)
  println(Saludo.saludar)
}
