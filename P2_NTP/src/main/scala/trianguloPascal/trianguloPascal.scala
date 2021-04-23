package trianguloPascal

object trianguloPascal {

  /**
   * Función factorial tail recursive
   * @param num el número cuyo factorial queremos calcular
   * @param acum tiene el 1 valor por defecto, para que se pueda usar la función
   *             con interfaz natural
   * @return
   */
  @annotation.tailrec
  def factorial(num : BigInt, acum : BigInt = 1) : BigInt = {
      if(num == 0 || num == 1) acum
      else factorial(num-1, acum*num)
  }

  /***
   * Función que calcula el valor del triángulo de pascal
   * en una posición (fila, col) determinada
   * @param fila
   * @param col
   * @return el valor del triángulo de pascal en la posición
   */
  def calcularValorTrianguloPascal(fila: BigInt, col: BigInt): BigInt = {
    val divisor =  factorial(col)*(factorial(fila-col))
    factorial(fila)/divisor
  }

  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={
    println("................... Triangulo de Pascal ...................")

     // Se muestran 10 filas del trinagulo de Pascal
     for (row <- 0 to 10) {
       // Se muestran 10 y 10 filas
       for (col <- 0 to row)
        print(calcularValorTrianguloPascal(row, col) + " ")

       // Salto de linea final para mejorar la presentacion
       println()
     }

    // Se muestra el valor que debe ocupar la fila 10 y columna 5
    print(calcularValorTrianguloPascal(10, 5))

  }
}
