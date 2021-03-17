import java.util.ArrayList;

/**
 * Heuristica del vecino mas cercano
 */
public class HeuristicaVMC extends HeuristicaTSP{

   /**
    * Resuelve el TSP mediante vecino mas cercano
    */
   @Override
   public void resolver(Problema problema) {
      this.problema = problema;

      // creacion de coleccion de rutas (una por ciudad)
      ArrayList<Ruta> rutas = new ArrayList<>();

      // se crean rutas para cada una de las ciudades
      for(int i=0; i < problema.obtenerDimension(); i++){
         Ruta rutaNueva = new Ruta();
         rutaNueva.agregarCiudad(problema.obtenerCiudad(i), 0);

         // se completa la ruta con el resto de ciudades,
         // eligiendo siempre la mas cercana
         completarRuta(rutaNueva);

         // agrega la nueva ruta a la coleccion de rutas
         rutas.add(rutaNueva);
      }

      // tengo coleccion de rutas, 1 por ciudad, y seleccionar
      // la de menor coste
      seleccionarRuta(rutas);
   }

   /**
    * Completa la ruta agregando siempre la ciudad mas cercana
    * @param ruta
    */
   private void completarRuta(Ruta ruta){
      // determinar si ya esta completa
      if(ruta.obtenerLongitud() == problema.obtenerDimension()){
         // ruta completa: se agrega la distancia entre ciudad
         // inicial y final
         Ciudad inicio = ruta.obtenerInicio();
         Ciudad fin = ruta.obtenerFin();

         // tenemos que calcular la distancia entre inicio y fin
         double distancia = problema.obtenerDistancia(inicio, fin);

         // agregar distancia de fin de recorrido
         ruta.agregarCoste(distancia);
      }
      else{
         // determinar la ciudad mas cercana a la ultima ciudad
         // de la ruta
         Ciudad masCercana = problema.obtenerMasCercana(ruta);

         // se determina el coste de ir desde fin hasta masCercana
         double distancia = problema.obtenerDistancia(ruta.obtenerFin(),
                 masCercana);

         // agregar la nueva ciudad a la ruta, con sus distancia
         ruta.agregarCiudad(masCercana, distancia);

         // llamada recursiva a completarRuta
         completarRuta(ruta);
      }
   }

   /**
    * Selecciona de la coleccion la ruta de menor coste
    * @param rutas
    */
   private void seleccionarRuta(ArrayList<Ruta> rutas){
      rutaOptima = rutas.get(0);
      for(int i=1; i < rutas.size(); i++){
         if(rutas.get(i).obtenerCoste() < rutaOptima.obtenerCoste()){
            rutaOptima = rutas.get(i);
         }
      }
   }
}
