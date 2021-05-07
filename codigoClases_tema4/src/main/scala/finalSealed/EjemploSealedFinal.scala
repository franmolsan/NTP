package finalSealed


class A(final val datoFinal: Int , val datoNormal : Int){

  final override def toString : String = {
    s"dato final: $datoFinal - dato normal: $datoNormal"
  }
}

// no se puede heredar de A modificando la definición del dato
// miembro datoFinal
// class B(val datoFinal: Int, dato1 : Int) extends A(datoFinal, dato1)

class C(dato1 :Int, dato2: Int) extends A(dato1, dato2){
  // no se puede hacer override porque en A está como final
  // override def toString : String = "metodo propio C"
}

// una clase completa puede definirse como final
final class D

// no se puede derivar de D porque eso implicaría la posibilidad
// de redefinir su comportamiento
// class F extends D

object EjemploSealedFinal extends App{

}
