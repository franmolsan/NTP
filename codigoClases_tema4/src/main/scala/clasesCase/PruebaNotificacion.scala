package clasesCase

abstract class Notificacion

// Clases case
// 1.- Los datos miembros son val (aunque no lo ponga)
// 2.- dispone de un companion object con varios métodos disponibles, uno de ellos es apply
//     (por tanto no es necesario poner new)

case class CorreoElectronico(direccion: String, titulo: String, cuerpo: String) extends Notificacion

case class MensajeSMS(numero: String, mensaje: String) extends Notificacion

case class MensajeVoz(contacto: String, enlace: String) extends Notificacion



object PruebaNotificacion extends App{

  val mensajeSMS = MensajeSMS("123456789", "Por favor, llama...")
  val mensajeCorreo = CorreoElectronico("alberto@gmail.com", "Aviso urgente", "Conferencia suspendida")
  val mensajeVoz = MensajeVoz("Tomas", "http://voiceService.org/id/123")

  def mostrarNotificacion(notificacion: Notificacion) : String = {
    notificacion match {
      case CorreoElectronico(direccion,titulo,cuerpo) =>
          "Recibido correo de: " + direccion + " titulo: " + titulo + " mensaje: " + cuerpo

      case MensajeSMS(numero,mensaje) =>
        "Recibido SMS de: " + numero + " mensaje: " + mensaje

      case MensajeVoz(contacto, enlace) =>
        "Recibido mensaje de voz de: " + contacto + " enlace para oir: " + enlace
    }
  }

  val resultado = mostrarNotificacion(mensajeSMS)
  println(resultado)
}
