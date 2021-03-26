import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Clase para heuristica MonteCarlo
 */
public class HeuristicaIntercambio extends HeuristicaTSP{
    /**
     * Dato miembro para guardar el numero de muestras
     */
    private int muestras;

    /**
     * Dato miembro auxiliar para generar soluciones aleatorias
     */
    private ArrayList<Integer> indices;

    /**
     * Dato miembro auxiliar para guardar las rutas aleatorias que
     * se van generando
     */
    private ArrayList<Ruta> rutas;

    /**
     * Dato miembro auxiliar para guardar las rutas aleatorias que
     * se van generando
     */
    private double costeMedioRuta = Double.MAX_VALUE;

    /**
     * Constante que limita el número de veces que se va intentar mejorar una ruta
     * Asegura que intentamos mejorar una ruta al menos LIMITE_MEJORA veces
     */
    private final int LIMITE_MEJORA = 10;

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
        muestras = problema.obtenerDimension() * 1000;

        // se genera el array de indices
        indices = new ArrayList<>();
        IntStream.range(0, problema.obtenerDimension()).
                forEach(i -> indices.add(i));

        // generamos colección de rutas aleatorias
        rutas = new ArrayList<>();
        IntStream.range(0,muestras).forEach(i-> rutas.add(generarAleatoria_funcional()));

        // calculamos el coste medio de las rutas
        // que nos servirá de referencia para saber si una ruta es buena o mala
        calcularCosteMedioRutas();

        // vamos realizando intercambios en cada ruta, hasta que no mejore
        rutas.forEach(ruta -> {
            mejoraRutaIntercambiando(ruta,0);
        });

        // ordenamos las rutas por el coste, de menor a mayor
        // y nos quedamos con la primera (la del coste menor)
        Stream<Ruta> rutasOrdenadas = rutas.stream().sorted(Comparator.comparing(Ruta::obtenerCoste));
        rutaOptima = rutasOrdenadas.limit(1).collect(Collectors.toList()).get(0);

        System.out.println("Coste medio de la ruta: " + costeMedioRuta);
        System.out.println("Coste de la ruta optima: " + rutaOptima.obtenerCoste());

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


    /**
     * Método privado para calcular el coste medio de las rutas que tenemos
     */
    private void calcularCosteMedioRutas (){

        // obtenemos un array de costes a partir de las rutas
        double[] costes = rutas.stream().mapToDouble(Ruta::obtenerCoste).toArray();

        // calculamos el valor medio del array de costes
        costeMedioRuta = Arrays.stream(costes).average().getAsDouble();
    }

    /**
     * Método privado para mejorar una ruta intercambiando dos ciudades aleatoriamente
     * Si la ruta mejora, se guarda; si no, se descarta.
     * Se llama recursivamente hasta que no se mejore
     */
    private void mejoraRutaIntercambiando (Ruta rutaAMejorar, int numMejora){

        /*
        // creamos una copia de la ruta que queremos mejorar
        Ruta nuevaRuta = new Ruta (rutaAMejorar);

        // realizar intercambio en la ruta
        nuevaRuta.intercambiarDosCiudades(problema);

        // si la nueva ruta es mejor, cambiamos el array de rutas,
        // recalculamos el coste medio
        // y repetimos el proceso, de forma recursiva
        if (nuevaRuta.obtenerCoste() < rutaAMejorar.obtenerCoste()){
            rutas.set(rutas.indexOf(rutaAMejorar), nuevaRuta);
            calcularCosteMedioRutas();
            mejoraRutaIntercambiando(nuevaRuta, numMejora+1);
        }
        // si la nueva ruta no es mejor y no hemos alcanzado el límite de mejoras,
        // intentamos otro intercambio
        else if (numMejora < LIMITE_MEJORA) {
            mejoraRutaIntercambiando(rutaAMejorar, numMejora+1);
        }

         */

        // si la ruta es buena
        if (rutaAMejorar.obtenerCoste() < costeMedioRuta){
            // creamos una copia de la ruta que queremos mejorar
            Ruta nuevaRuta = new Ruta (rutaAMejorar);

            // realizar intercambio en la ruta
            nuevaRuta.intercambiarDosCiudades(problema);

            // si la nueva ruta es mejor, cambiamos el array de rutas,
            // recalculamos el coste medio
            // y repetimos el proceso, de forma recursiva
            if (nuevaRuta.obtenerCoste() < rutaAMejorar.obtenerCoste()){
                rutas.set(rutas.indexOf(rutaAMejorar), nuevaRuta);
                calcularCosteMedioRutas();
                mejoraRutaIntercambiando(nuevaRuta, numMejora+1);
            }
            // si la nueva ruta no es mejor y no hemos alcanzado el límite de mejoras,
            // intentamos otro intercambio
            else if (numMejora < LIMITE_MEJORA) {
                mejoraRutaIntercambiando(rutaAMejorar, numMejora+1);
            }
        }


    }
}