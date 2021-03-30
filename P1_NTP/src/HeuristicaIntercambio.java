import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Clase para heuristica de intercambio
 * Usa programación funcional
 */
public class HeuristicaIntercambio extends HeuristicaMonteCarloFuncional{
    /**
     * Dato miembro auxiliar para almacenar las rutas aleatorias que
     * se van generando
     */
    private ArrayList<Ruta> rutas;

    /**
     * Dato miembro para almacenar el coste medio de la ruta
     */
    private double costeMedioRuta = Double.MAX_VALUE;

    /**
     * Constante que indica el número de veces que se va intentar mejorar una ruta
     * Asegura que intentamos mejorar una ruta al menos NUMERO_MIN_INTERCAMBIOS veces
     */
    private int NUMERO_MIN_INTERCAMBIOS;

    /**
     * Constante que limita el número de veces que se va intentar mejorar una ruta
     * Para evitar que la ejecución sea demasiado larga en problemas de gran dimensión
     */
    private int LIMITE_MAXIMO_MEJORA = 500;


    /**
     *
     */
    TreeMap<String, Long> agrupacionCiudadesSegunInicio;

    /**
     *
     */
    long valorMasPopular;

    /**
     *
     */
    String inicioMasPopular;

    /**
     * Metodo de resolucion a partir del problema
     * @param problema
     */
    @Override
    public void resolver(Problema problema) {
        // se asigna el problema
        this.problema = problema;

        // el número mínimo de intercambios toma el valor de la dimensión del problmea
        // si el problema es muy grande, limitamos su valor al de la constante LIMITE_MAXIMO_MEJORA
        // para no realizar demasiados intercambios por ruta
        NUMERO_MIN_INTERCAMBIOS = Integer.min(problema.obtenerDimension(),LIMITE_MAXIMO_MEJORA);

        // asignar el numero de muestras a generar
        muestras = problema.obtenerDimension() * 1000;

        // se genera el array de indices
        indices = new ArrayList<>();
        IntStream.range(0, problema.obtenerDimension()).
                forEach(i -> indices.add(i));

        // generamos colección de rutas aleatorias
        rutas = new ArrayList<>();
        IntStream.range(0,muestras).forEach(i-> rutas.add(generarAleatoria()));

        // calculamos el coste medio de las rutas
        // que nos servirá para filtrar las rutas y quedarnos con las mejores
        calcularCosteMedioRutas();

        // filtramos las rutas, y nos quedamos con las mejores
        // realizaremos más o menos filtrados en función del tamaño del problema
        int vecesAFiltrar = problema.obtenerDimension()/50 + 1;
        IntStream.range(0,vecesAFiltrar).forEach(i ->{
            rutas = filtradoRutasSegunCoste(0,costeMedioRuta);

            // recalculamos el coste medio
            calcularCosteMedioRutas();
        });

        calcularDatosCiudadesInicio();

        // vamos realizando intercambios en cada ruta, hasta que no mejore
        rutas.forEach(ruta -> {
            mejoraRutaIntercambiando(ruta,0);
        });

        // ordenamos las rutas por el coste, de menor a mayor
        // y nos quedamos con la primera (la del coste menor)
        Stream<Ruta> rutasOrdenadas = rutas.stream().sorted(Comparator.comparing(Ruta::obtenerCoste));
        rutaOptima = rutasOrdenadas.limit(1).collect(Collectors.toList()).get(0);
    }


    /**
     * Método para imprimir la información sobre el procesamiento que ha realizado la heurística
     */
    public void imprimirInformacion(){
        System.out.println("Información sobre el procesamiento mediante la heurística de intercambio: ");
        System.out.println("*   Coste medio de la ruta: " + costeMedioRuta);
        System.out.println("*   Coste de la ruta optima: " + rutaOptima.obtenerCoste());
        System.out.println("*   Inicialmente se han generado " + muestras + " rutas");
        System.out.println("*   Se han filtrado las muestras, pasando a considerar las " + rutas.size() + " mejores muestras");
        System.out.println("*   Para cada una de estas muestras, se han realizado al menos " + NUMERO_MIN_INTERCAMBIOS + " intercambios");
        System.out.println("*   Agrupación de ciudades " + agrupacionCiudadesSegunInicio );
        System.out.println("*   La ciudad de inicio que más se repite es " + inicioMasPopular + ", ya que hay " + valorMasPopular +
                " rutas que empiezan por ella.");
    }

    private void calcularDatosCiudadesInicio(){
        agrupacionCiudadesSegunInicio = obtenerAgrupacionSegunInicio();
        inicioMasPopular = Collections.max(agrupacionCiudadesSegunInicio.entrySet(), Comparator.comparing(Map.Entry::getValue)).getKey();
        valorMasPopular = agrupacionCiudadesSegunInicio.get(inicioMasPopular).longValue();
    }


    /**
     * Método privado para filtrar las rutas, obteniendo aquellas cuyo coste está comprendido entre dos valores
     * @param costeMin
     * @param costeMax
     * @return ArrayList de rutas cuyo coste está entre costeMin y costeMax
     */
    private ArrayList<Ruta> filtradoRutasSegunCoste (double costeMin, double costeMax){
        Predicate<Ruta> condicionCoste = ruta -> (ruta.obtenerCoste() >= costeMin &&
                                                      ruta.obtenerCoste() <= costeMax);
        return (ArrayList<Ruta>) rutas.stream().filter(condicionCoste).collect(Collectors.toList());
    }

    /**
     * Método privado para obtener un diccionario que guarde el número de rutas que empiezan por cada ciudad
     * @return
     */
    private TreeMap<String, Long> obtenerAgrupacionSegunInicio(){
        TreeMap<String, Long> agrupacion = rutas.stream().map(ruta -> {
            return ruta.obtenerInicio().obtenerEtiqueta();
        }).collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
        return agrupacion;
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

        // creamos una copia de la ruta que queremos mejorar
        Ruta nuevaRuta = new Ruta (rutaAMejorar);

        // realizar intercambio en la ruta
        nuevaRuta.intercambiarDosCiudades(problema);

        // si la nueva ruta es mejor, cambiamos el array de rutas,
        // y repetimos el proceso, de forma recursiva
        if (nuevaRuta.obtenerCoste() < rutaAMejorar.obtenerCoste()){
            rutas.set(rutas.indexOf(rutaAMejorar), nuevaRuta);
            mejoraRutaIntercambiando(nuevaRuta, numMejora+1);
        }
        // si la nueva ruta no es mejor y no hemos alcanzado el límite de mejoras,
        // intentamos otro intercambio
        else if (numMejora < NUMERO_MIN_INTERCAMBIOS) {
            mejoraRutaIntercambiando(rutaAMejorar, numMejora+1);
        }
    }

}