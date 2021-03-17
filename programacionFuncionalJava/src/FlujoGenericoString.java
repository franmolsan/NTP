import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FlujoGenericoString {

    public static void main (String args []){
        String [] cadenas = {"Rojo", "Naranja", "Amarillo", "Violeta",
                "Verde", "Azul", "Indigo"};

        // creación de flujo y se convierten las cadenas a mayuscula
        List<String> mayuscula = Arrays.stream(cadenas).map(cadena -> cadena.toUpperCase()).
                collect(Collectors.toList());

        // generación de cadena completa con toda la lista, para imprimir
        String contenido = mayuscula.stream().
                            collect(Collectors.joining( " "));
        System.out.println(contenido);

        // filtrar las cadenas con iniciales menores que m, ordenación inversa
        List<String> filtradas = Arrays.stream(cadenas).filter(cadena -> cadena.compareToIgnoreCase("m") > 0).
                sorted(String.CASE_INSENSITIVE_ORDER.reversed()).
                collect(Collectors.toList());

        contenido = filtradas.stream().collect(Collectors.joining(" "));
        System.out.println(contenido);
    }
}
