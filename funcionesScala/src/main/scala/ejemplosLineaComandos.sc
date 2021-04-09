def saludo = "Hola"

val saludo2 = "Hola"

saludo2

def multiplicar(x:Int, y:Int) : Int = x*y

multiplicar(3, 4)

def quitarBlancosIniciales(s : String) : String = {
  if(s == null){
    println("parte nula del if... se sale")
    return null
  }

  s.trim
}

def mostrarValor(x:Int) = {
  println(x)
}

mostrarValor(4*3-12/2)

def mostrarError(): Unit ={
  println("Funcion no pura")
}

mostrarError() // No devuelve nada, covenio poner los parentesis