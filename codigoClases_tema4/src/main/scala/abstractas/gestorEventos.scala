package abstractas

abstract class GestorEventos{
  def ejecutar
}

class Aplicacion{
  var gestor : GestorEventos = null

  def registrarGestor(gestor: GestorEventos) = {
    this.gestor = gestor
  }

  def notificar = gestor.ejecutar
}

class GestorEventosConcrreto extends GestorEventos{
  override def ejecutar: Unit = println("Ejecucion desde el gestor de eventos concreto")
}

object GestorEventos extends App{
  val obj1 = new Aplicacion

  obj1.registrarGestor(new GestorEventos {
    def ejecutar: Unit = println("Ejecucion del gestor de eventos")
  })

  obj1.notificar

  val obj2 = new GestorEventosConcrreto
  obj2.ejecutar
}
