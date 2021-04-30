package declaracion

class Coche (val marca: String, var enUso : Boolean){
  def reservar (flag : Boolean): Unit = {
    enUso = flag
  }

  override def toString() : String = s"Marca : $marca, en uso $enUso"
}

class Renault (val color : String, enUso : Boolean) extends Coche("Renault", enUso){
  override def toString () = super.toString() + "color: " + color
}

object DeclaracionCoche extends App{

}
