import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Clase para heuristica MonteCarlo
 */
public class HeuristicaMonteCarlo extends HeuristicaTSP{
    /**
     * Dato miembro para guardar el numero de muestras
     */
    private  int muestras;

    /**
     * Dato miembro auxiliar para generar soluciones aleatorias
     */
    private ArrayList<Integer> indices;


    /**
     * Metodo de resolucion a partir del problema
     * @param problema
     */
    @Override
    public void resolver (Problema problema){

        this.problema = problema;

        // asignar el numero de muestras a generar
        muestras = problema.obtenerDimension() * 100;

        // se genera el arrray de indices
        indices = new ArrayList<>();
        for (int i=0; i < problema.obtenerDimension(); i++){
            indices.add(i);
        }

        rutaOptima = generarAleatoria();

        for (int i=0; i < muestras; i++){
            Ruta aleatoria = generarAleatoria();

            if (rutaOptima.obtenerCoste() > aleatoria.obtenerCoste()){
                rutaOptima = aleatoria;
            }
        }
    }

    /**
     * Metodo de generación de rutas aleatorias
     * @return
     */
    private Ruta generarAleatoria () {
        Ruta resultado = new Ruta();

        // se desordena el array de indices
        Collections.shuffle(indices);

        // se van agregando las ciudades en el orden el que aparecen en indices
        resultado.agregarCiudad(problema.obtenerCiudad(indices.get(0)),0);

        for (int i = 0; i < indices.size(); i++){
            Ciudad previa = problema.obtenerCiudad(indices.get(i-1));
            Ciudad siguiente = problema.obtenerCiudad(indices.get(i));
            double distancia = problema.obtenerDistancia(previa, siguiente);
            resultado.agregarCiudad(siguiente, distancia);
        }

        // distancia de cierre (volver al inicio desde el final)
        Ciudad inicio = problema.obtenerCiudad(indices.get(0));
        Ciudad fin = problema.obtenerCiudad(indices.get(indices.size()-1));
        double distanciaCierre = problema.obtenerDistancia(inicio, fin);
        resultado.agregarCoste(distanciaCierre);

        // se devuelve el resultado
        return resultado;
    }
}
