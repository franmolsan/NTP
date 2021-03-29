import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Heuristica del vecino mas cercano
 */
public class HeuristicaVMCFuncional extends HeuristicaVMC{

   /**
    * Resuelve el TSP mediante vecino mas cercano
    */
   @Override
   public void resolver(Problema problema) {
      this.problema = problema;

      // creacion de coleccion de rutas (una por ciudad)
      ArrayList<Ruta> rutas = new ArrayList<>();

      problema.obtenerCiudades().stream().forEach(ciudad -> {
         Ruta rutaNueva = new Ruta();
         rutaNueva.agregarCiudad(ciudad,0);
         completarRuta(rutaNueva);
         rutas.add(rutaNueva);
      });


      // tengo coleccion de rutas, 1 por ciudad, y seleccionar
      // la de menor coste
      rutaOptima = rutas.stream().
              sorted(Comparator.comparing(Ruta::obtenerCoste)).limit(1). // ordena de menor a mayor
              collect(Collectors.toList()).get(0);
   }

}
