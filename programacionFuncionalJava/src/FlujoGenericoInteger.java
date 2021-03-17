import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlujoGenericoInteger {
    public static void main (String args[]){
        Integer [] valores = {2,9,5,0,3,7,1,4,8,6};

        // crear un flujo Stream<Integer> a partir del array
        Stream<Integer> flujo = Arrays.stream(valores);

        // ordenación y recoger resultado sobre lista
        // por defecto se ordena de menor a mayor
        // después de esta operación, el flujo deja de poder usarse.
        List<Integer> lista = flujo.sorted().collect(Collectors.toList());

        // se muestra la lista
        lista.forEach(System.out::println);

        List<Integer> filtrado = Arrays.stream(valores).filter(x -> x >= 4).
                                collect(Collectors.toList());

        filtrado.forEach(valor -> System.out.print(valor + " "));
        System.out.println();

        // utilizamos map para convertir de Integer a Double
        List<Double> dobles = Arrays.stream(valores).
                    map(x -> x * 0.25).collect(Collectors.toList());

        dobles.forEach(System.out::println);

    }
}
