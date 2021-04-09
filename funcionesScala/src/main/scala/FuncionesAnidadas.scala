object FuncionesAnidadas extends App{

  // definir la funcion para calcular el maximo de 3 valores
  // hace uso de una funcion auxiliar interna (anidada)
  // que permite organizar mejor el código
  def max (x: Int, y: Int, z: Int) : Int = {

    // funcion auxiliar para calcular el maximo de 2 valores
    def max (x: Int, y : Int) = if (x > y) x else y

    max (x, max(y,z))
  }

  // funcion factorial
  def factorial (x: Int) : Int = {
    if (x == 0) 1
    else x*factorial(x-1)
  }

  // funcion factorial tail - recursive
  // es muy eficiente porque no necesita la pila
  // por dentro se parece a una iteración
  @annotation.tailrec
  def factorialTR1(x: Int, acum: Int): Int = {
    if(x == 0 || x == 1) acum
    else factorialTR1(x-1, acum*x)
  }

  // funcion factorial tail recursive con una interfaz natural, solo una argumento
  def factorialTR2(x : BigInt) : BigInt = {

    // funcion auxiliar que permite recurisidad tail recursive
    def go(x: BigInt, acum: BigInt) : BigInt = {
      if(x == 0 || x == 1) acum
      else go(x-1, acum*x)
    }

    // desencadena la ejecución de la función auxiliar
    go(x, 1)
  }

  // funcion con valores con defecto
  @annotation.tailrec
  def factorialTR3(x : BigInt, acum : BigInt = 1) : BigInt = {
    if(x == 0 || x == 1) acum
    else factorialTR3(x-1, acum*x)
  }

  println("factorial de 30 = " + factorialTR2(30))
  println("factorial de 30 = " + factorialTR3(30))


  // funciones con numero variable de args
  def sumar(numeros: Int *) : Int = {
    var total = 0
    for (i <- numeros) total += i
    total
  }

  println("suma de 1 valor " + sumar(-1))
  println("suma de 2 valores: " + sumar(-1,8))
  println("suma de 3 valores: " + sumar(-1,8,10))

  // utilizar genericos en las funciones
  def eliminarPrimero [A] (lista: List[A] ) : List[A] = lista.tail

  eliminarPrimero(List(1,2,3,4))
  eliminarPrimero(List('a','b','c','d'))

}
