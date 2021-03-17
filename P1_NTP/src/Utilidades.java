/**
 * Clase para utilidades de proposito general
 */
public class Utilidades {
   /**
    * Metodo estatico de calculo de distancia euclidea
    * @param ciudad1
    * @param ciudad2
    * @return
    */
   public static double calcularDistanciaEuclidea(Ciudad ciudad1, Ciudad ciudad2){
      return Math.sqrt(Math.pow(ciudad1.obtenerX() - ciudad2.obtenerX() ,2) +
                       Math.pow(ciudad1.obtenerY() - ciudad2.obtenerY(), 2));
   }
}
