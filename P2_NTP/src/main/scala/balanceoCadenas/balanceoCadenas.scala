package balanceoCadenas

object balanceoCadenas  {

  /**
   * Función para comprobar el balanceo de paréntesis de una cadena dada
   * @param cadena la cadena cuyo balanceo de paréntesis queremos comprobar, representada como List[Char]
   * @return
   */
  def chequearBalance(cadena: List[Char]) : Boolean = {

    /**
     * Función interna Tail Recursive
     * Analiza la subcadena recibida carácter por carácter, para comprobar si está balanceada o no
     * @param subcadena La subcadena que analiza, en forma de List[Char]
     * @param contador Int para llevar el conteo de paréntesis que abren y que cierran.
     *                 Empieza en 0, por cada '(' se suma 1 y por cada ')' se resta 1.
     *                 Para que la subcadena está balanceada, el contador tiene que valer 0
     * @return
     */
    @annotation.tailrec
    def go (subcadena : List[Char], contador : Int) : Boolean = {

      // si la subcadena está vacía y el contador acaba en 0,
      // es que por cada '(' hay un ')' y está balanceado
      // si el contador no vale 0, la subcadena no está balanceada
      if (subcadena.isEmpty) {
        if (contador == 0) true
        else false
      }

      // si la subcadena empieza por un '(', aumenta en 1 el contador
      // y sigue analizando la cadena (quitándole el '(' del principio)
      else if (subcadena.head == '(')
        go (subcadena.tail, contador+1)

      // si la subcadena empieza por ')'
      else if (subcadena.head == ')') {

        // si el contador es > 0, es que antes ha habido al menos un '(',
        // entonces el orden es correcto, por lo que restamos 1 al contador
        // y seguimos analizando la cadena
        if (contador > 0) go (subcadena.tail, contador-1)

        // si el contador es <= 0, es que no había un '(' antes del ')',
        // es decir, que el orden es incorrecto,
        // por tanto, la subcadena no está balanceada
        else false
      }

      // cualquier otro caso no nos afecta,
      // por lo que eliminamos el carácter "inútil" que hay al principio de la subcadena
      // y seguimos analizando
      else go(subcadena.tail, contador)
    }

    // desencadenar el mecanismo, empezando el contador en 0
    go (cadena,0)
  }

  /***
   * Método main, que no es necesario ya que el desarrollo
   * está guiado por las pruebas
   * @param args
   */
  def main(args: Array[String]) ={

    // probamos que "())(" no está balanceada (resultado es false)
    println("Balanceo de paréntesis en cadenas")
    println("())( está balanceada: " + chequearBalance("())(".toList))
  }

}
