import java.security.SecureRandom;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneracionAleatorios {
    public static void main (String args[]){
        SecureRandom random = new SecureRandom();
        long muestras = 100_000;

        // se generan numeros entre 1 y 6
        // Grouping by el propio número
        Map<Integer, Long> collecion = random.ints(muestras, 1, 7).boxed().
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        collecion.entrySet().stream().forEach(entrada -> {
            System.out.printf("%10d - %10d - %f\n", entrada.getKey(), entrada.getValue(),
                    entrada.getValue()/(double)muestras);
        });
    }
}
