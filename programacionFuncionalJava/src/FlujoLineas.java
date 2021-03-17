import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlujoLineas {

    public static void main(String args []) throws IOException {

        // creacion de patron
        Pattern patron = Pattern.compile("\\s+");

        // obtención las líneas del archivo
        List<String> lineas = Files.lines(Paths.get("./data/alicia.txt"), StandardCharsets.ISO_8859_1).
                map(linea -> linea.replaceAll("(?!')\\p{Punct}", "")).
                collect(Collectors.toList());

        // mapa de tipo <palabra - contador>
        // el flatMap permite tener solo una colección
        // si usaramos map, tendríamos una coleeción de colecciones
        TreeMap<String, Long> grupos = lineas.stream().flatMap(linea -> patron.splitAsStream(linea)).
                collect(Collectors.groupingBy(String::toLowerCase, TreeMap::new, Collectors.counting()));
        
        grupos.entrySet().stream().forEach(entrada -> {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        });

        //  mapa de tipo <caracter (inicial) - lista de palabras>
        lineas.stream().flatMap(linea -> patron.splitAsStream(linea)).
                collect(Collectors.groupingBy(cadena -> cadena.charAt(0),
                        TreeMap::new, Collectors.toList()));
    }
}
