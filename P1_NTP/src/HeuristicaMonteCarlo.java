import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Clase para heuristica MonteCarlo
 */
public class HeuristicaMonteCarlo extends HeuristicaTSP{
   /**
    * Dato miembro para guardar el numero de muestras
    */
   private int muestras;

   /**
    * Dato miembro auxiliar para generar soluciones aleatorias
    */
   private ArrayList<Integer> indices;

   /**
    * Metodo de resolucion a partir del problema
    * @param problema
    */
   @Override
   public void resolver(Problema problema) {
      // se asigna el problema
      this.problema = problema;

      // asignar el numero de muestras a generar
      muestras = problema.obtenerDimension() * 100;

      // se genera el array de indices
      indices = new ArrayList<>();
      for(int i=0; i < problema.obtenerDimension(); i++){
         indices.add(i);
      }

      // se genera solucion aleatoria
      rutaOptima = generarAleatoria();

      // considerancion de las muestras indicadas
      for(int i=0; i < muestras; i++){
         Ruta aleatoria = generarAleatoria();

         // comprobar si hay que actualizar la optima
         if(rutaOptima.obtenerCoste() > aleatoria.obtenerCoste()){
            rutaOptima = aleatoria;
         }
      }
   }


   /**
    * Metodo de resolucion a partir del problema
    * @param problema
    */
   @Override
   public void resolver_funcional(Problema problema) {
      // se asigna el problema
      this.problema = problema;

      // asignar el numero de muestras a generar
      muestras = problema.obtenerDimension() * 10000;

      // se genera el array de indices
      indices = new ArrayList<>();
      IntStream.range(0, problema.obtenerDimension()).
              forEach(i -> indices.add(i));

      // se genera solucion aleatoria
      rutaOptima = generarAleatoria_funcional();

      // creamos array final, necesario para modificar la variable dentro de
      // la expresión lambda
      IntConsumer generarRutaAleatoriaYComparar = i -> { Ruta aleatoria = generarAleatoria_funcional();
         if (rutaOptima.obtenerCoste() > aleatoria.obtenerCoste()){
            rutaOptima = aleatoria;
         }
      };
      IntStream.range(0,muestras).forEach(generarRutaAleatoriaYComparar);
   }

   /**
    * Metodo de generacion de rutas aleatorias
    * @return
    */
   private Ruta generarAleatoria(){
      Ruta resultado = new Ruta();

      // se desordena el array de indices
      Collections.shuffle(indices);

      // se van agregando las ciudades en el orden en que
      // aparecen en indices
      resultado.agregarCiudad(problema.obtenerCiudad(indices.get(0)), 0);

      // agregamos el resto de ciudades
      for(int i=1; i < indices.size(); i++){
         Ciudad previa = problema.obtenerCiudad(indices.get(i-1));
         Ciudad siguiente = problema.obtenerCiudad(indices.get(i));
         double distancia = problema.obtenerDistancia(previa, siguiente);
         resultado.agregarCiudad(siguiente, distancia);
      }

      // se agrega el coste de cierre
      Ciudad inicio = problema.obtenerCiudad(indices.get(0));
      Ciudad fin = problema.obtenerCiudad(indices.get(indices.size()-1));
      double distanciaCierre = problema.obtenerDistancia(inicio, fin);
      resultado.agregarCoste(distanciaCierre);

      // se devuelve el resultado
      return resultado;
   }


   /**
    * Metodo de generacion de rutas aleatorias
    * mediante programación funcional
    * @return
    */
   private Ruta generarAleatoria_funcional(){
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
