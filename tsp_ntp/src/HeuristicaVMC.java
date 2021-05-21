import java.util.ArrayList;

/**
 * Heurística del vecino más cercano
 */
public class HeuristicaVMC extends HeuristicaTSP{


    public HeuristicaVMC (Problema problema){
        super(problema);
    }

    /**
     * Resuelve TSP mediante Vecino más cercano
     */
    @Override
    public void resolver() {

        // Crear una colección de rutas (una por ciudad);
        ArrayList<Ruta> rutas = new ArrayList<>();

        for (int i=0 ; i < problema.obtenerDimension(); i++){
            Ruta rutaNueva = new Ruta();
            rutaNueva.agregarCiudad(problema.obtenerCiudad(i),0);

            // completar la ruta eligiendo siempre la más cercana, y añadirla a la colección
            completarRuta(rutaNueva);
            rutas.add(rutaNueva);

            // elegir la ruta menos costosa
            seleccionarRuta(rutas);
        }
    }

    private void completarRuta(Ruta ruta){
        if (ruta.obtenerLongitud() == problema.obtenerDimension()){
            // ruta completa: se agrega la distancia entre la inicial y la final (porque volvermos a la inicial)
            Ciudad inicio = ruta.obtenerInicio();
            Ciudad fin = ruta.obtenerFin();

            // calcular la distancia entre ambas ciudades
            double distancia = problema.obtenerDistancia(inicio, fin);

            ruta.agregarCoste(distancia);
        }
        else {
            // determinar la ciudad mas cercana
            Ciudad masCercana = problema.obtenerMasCercana(ruta);

            double distancia = problema.obtenerDistancia(ruta.obtenerFin(), masCercana);

            ruta.agregarCiudad(masCercana, distancia);

            // llamada recursiva a completarRuta
            completarRuta(ruta);
        }
    }

    private void seleccionarRuta(ArrayList<Ruta> rutas){
        rutaOptima = rutas.get(0);
        for (int i=0; i<rutas.size(); i++){
            if (rutas.get(i).obtenerCoste() < rutaOptima.obtenerCoste()){
                rutaOptima = rutas.get(i);
            }
        }
    }
}
