package usoTrait

class A{
  def mostrar = getClass.getName
}

trait B{self:A =>
  override def toString : String = "B " + mostrar
}

// se intenta crear clase C que use el trait, sin que C derive de A
// no se puede así
// class C extends B
// así si:
class C extends A with B

object EjemploTrait2 extends App{
  val objeto = new C
  println(objeto)
}
