import java.util.ArrayList;
import java.util.Collections;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Clase para heuristica MonteCarlo
 */
public class HeuristicaMonteCarloFuncional extends HeuristicaMonteCarlo{

   /**
    * Metodo de resolucion a partir del problema
    * @param problema
    */
   @Override
   public void resolver(Problema problema) {
      // se asigna el problema
      this.problema = problema;

      // asignar el numero de muestras a generar
      muestras = problema.obtenerDimension() * 10000;

      // se genera el array de indices
      indices = new ArrayList<>();
      IntStream.range(0, problema.obtenerDimension()).
              forEach(i -> indices.add(i));

      // generamos solución aleatoria inicial
      rutaOptima = generarAleatoria();


      IntConsumer generarRutaAleatoriaYComparar = i -> { Ruta aleatoria = generarAleatoria();
         if (rutaOptima.obtenerCoste() > aleatoria.obtenerCoste()){
            rutaOptima = aleatoria;
         }
      };
      // generamos muestras y comparamos, quedándonos con la mejor
      IntStream.range(0,muestras).forEach(generarRutaAleatoriaYComparar);
   }


   /**
    * Metodo de generacion de rutas aleatorias
    * mediante programación funcional
    * @return
    */
   @Override
   protected Ruta generarAleatoria(){
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
