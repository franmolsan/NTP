import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Practicar con flujos para valores enteros
 */
public class FlujoEnteros {

    int [] valores;

    public FlujoEnteros(int numeroValores){
        valores = new int[numeroValores];
        Random generador = new Random();
        for (int i =0 ; i < numeroValores; i++){
            valores[i] = generador.nextInt();
        }
    }

    /**
     * Iteración externa
     */
    public void listadoImperativo(){
        System.out.println("Listado imperativo: ");
        for (int i=0; i<valores.length; i++){
            System.out.println(valores[i] + " ");
        }
        System.out.println();
    }

    // iteración interna
    public void listadoFuncional(){
        System.out.println("Listado funcional: ");
        // crear un flijo para tratar con valores
        IntStream flujo = IntStream.of(valores);

        // funcion a apliocar a cada miembro del flujo
        IntConsumer operacion = valor -> System.out.println(valor + " ");

        // recorrido sobre el flujo
        flujo.forEach(operacion);
    }

    // iteración interna corta: como en realidad vamos a trabajar
    // NOTA: Se pueden enccadenar operaciones
    public void listadoFuncionalCorto(){
        IntStream.of(valores).forEach(valor -> System.out.println(valor + " "));

        // versión incluso más corta:
        IntStream.of(valores).forEach(System.out::println);
    }

    public int sumar (){
        int suma = 0;
        for (int i=0; i<valores.length; i++){
            suma = suma + valores[i];

        }
        return suma;
    }

    /**
     * Suma mediante programación funcional
     * @return
     */
    public int sumaFuncional (){
        return IntStream.of(valores).sum();
    }

    /**
     * Suma con aproximación reduce
     * @return
     */
    public int sumaReduce(){
        return IntStream.of(valores).reduce(0,(x,y) -> x+y);
    }

    public int sumaCuadrados(){
        return IntStream.of(valores).reduce(0, (x,y) -> x+y*y);
    }

    public int sumarCuadradosMap(){
        return  IntStream.of(valores).map(x -> x * x).sum();
    }

    public int obtenerMinimo(){
        int min = valores[0];
        for (int i=0; i<valores.length; i++){
            if (valores[i] < min){
                min = valores[i];
            }
        }
        return min;
    }


    public int obtenerMinimoFuncional(){
        OptionalInt min = IntStream.of(valores).min();
        return min.orElse(-1); // hacemos esto porque el min es opcional. Si la colección está vacia no hay int, puede dar fallo.
        // return min.getAsInt(); // si estamos seguros de que hay minimo.
    }

    public int obtenerMinimoReduce(){
        return IntStream.of(valores).reduce(Integer.MAX_VALUE, (x,y) -> {
            if (x<y) return x;
            else return y;
        });
    }

    public void mostrarPares(){
        IntStream.of(valores).filter(x -> x%2 == 0).forEach(System.out::println);
    }

    public int [] obtenerPares(){
        return IntStream.of(valores).filter(x -> x % 2 == 0).
                sorted().
                distinct().
                toArray();
    }

    public int [] generar (int numeroValores){
        int resultado [] = new int [numeroValores];
        Random generador = new Random();

        IntStream.range(0, numeroValores).forEach(indice ->{
            resultado[indice] = generador.nextInt();
        });

        return resultado;
    }

    public List<Double> generarDobles(int numeroValores){
        double resultado [] = new double[numeroValores];
        Random generador = new Random();

        // hacemos el boxed y esto asi para esquivar restricciones
        // mediante boxed paso de IntStream a Stream<Integer>, que permite transformar los tipos de datos.
        Stream<Integer> boxed = IntStream.range(0,numeroValores).boxed();

        return IntStream.range(0,numeroValores).boxed().map(indice -> generador.nextInt()*1.0).collect(Collectors.toList());

    }

    public static void main (String args []){
        FlujoEnteros objeto = new FlujoEnteros(10);
        objeto.listadoImperativo();
        objeto.listadoFuncional();
    }
}

