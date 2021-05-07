package objetos


class A(val a: Int, val b:Int){
  def sumar = a+b
}

// los objetos sí pueden heredar de clases
object ObjetoA extends A(8,7)

// class C extends ObjetoA

object EjemploObjeto3 extends App{

  println(ObjetoA.sumar)
}
