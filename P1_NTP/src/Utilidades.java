import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

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

   /**
    * Metodo de generacion de rutas aleatorias
    * mediante programación funcional
    * @return
    */
   public static Ruta generarAleatoria_funcional(ArrayList<Integer> indices, Problema problema){
      Ruta resultado = new Ruta();

      // se desordena el array de indices
      Collections.shuffle(indices);

      // se van agregando las ciudades en el orden en que
      // aparecen en indices
      resultado.agregarCiudad(problema.obtenerCiudad(indices.get(0)), 0);

      IntStream.range(1,indices.size()).forEach(i -> {
         Ciudad previa = problema.obtenerCiudad(indices.get(i-1));
         Ciudad siguiente = problema.obtenerCiudad(indices.get(i));
         double distancia = problema.obtenerDistancia(previa, siguiente);
         resultado.agregarCiudad(siguiente, distancia);
      });

      // se agrega el coste de cierre
      Ciudad inicio = problema.obtenerCiudad(indices.get(0));
      Ciudad fin = problema.obtenerCiudad(indices.get(indices.size()-1));
      double distanciaCierre = problema.obtenerDistancia(inicio, fin);
      resultado.agregarCoste(distanciaCierre);

      // se devuelve el resultado
      return resultado;
   }
}
