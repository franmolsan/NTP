import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Almacena conjuntos de localidades a recorrer
 */
public class Problema {
    /**
     * Colección de ciudades del problema
     */
    private ArrayList<Ciudad> ciudades;

    /**
     * Matriz de distancias
     */
    private double[][] distancias;

    /**
     * Constructor para leer de datos de archivo
     * @param nombreFichero
     */
    public Problema (String nombreFichero){
        int dimension;

        // creo objeto para ciudades;
        ciudades = new ArrayList<>();

        // procesamiento del archivo
        // try catch por si no existe el archivo, o hay problemas de otro tipo
        try {
            // creación de flujo para la lectura del programa
            FileReader lector = new FileReader(nombreFichero);
            BufferedReader lectorb = new BufferedReader(lector); // el buffered reader simplifica la lectura

            // se lee la priemra línea
            String linea = lectorb.readLine();

            // dividir el ocntenido de la linea, mediante split
            String[] datos = linea.split("\\s+");

            if (datos.length != 2){ // formato incorrecto
                System.out.println("Formato de fichero incorrecto " + nombreFichero);
                System.exit(-1);
            }

            // guardo dimension del problema
            dimension = Integer.parseInt(datos[1]);
            System.out.println("Dimension del problema: "  + dimension);

            // bucle de lectura del resto de lineas
            for (int i =0; i < dimension; i++){
                linea = lectorb.readLine();

                datos = linea.split("\\s+");

                // comprobacion: tiene que haber tres palabras (etiqueta, x, y)
                if (datos.length != 3){
                    System.out.println("Formato de fichero incorrecto " + nombreFichero);
                    System.out.println("Linea " + linea);
                    System.exit(-1);
                }

                // crear ciudad con los datos leidos de fichero y añadirla al vector de ciudades
                Ciudad ciudad = new Ciudad(datos[0], Double.parseDouble(datos[1]),  Double.parseDouble(datos[2]));
                ciudades.add(ciudad);
            }

            // calcular distancias entre ciudades
            calcularDistancias();

        } catch (IOException e){
            System.out.println("Hay errores en el fichero");
            System.exit(-1);
        }
    }


    /**
     * Obtener dimension del problema
     * @return
     */
    public int obtenerDimension(){
        return ciudades.size();
    }

    /**
     * Devuelve la ciudad almacenada en un índice
     * @param indice
     * @return
     */
    public Ciudad obtenerCiudad(int indice){
        return ciudades.get(indice);
    }

    public double obtenerDistancia (Ciudad ciudad1, Ciudad ciudad2){
        int indice1 = ciudades.indexOf(ciudad1);
        int indice2 = ciudades.indexOf(ciudad2);
        return distancias[indice1][indice2];
    }

    public String toString(){
        String salida = "Dimension " + ciudades.size() + "\n";
        salida += "Matriz de distancias: \n";
        for (int i = 0; i < ciudades.size(); i++){
            for (int j = 0; j < ciudades.size(); j++){
                salida += String.format("%7.2f", distancias[i][j]) + " ";
            }
            salida += "\n";
        }
        return (salida);
    }

    public Ciudad obtenerMasCercana(Ruta ruta){
        Ciudad fin = ruta.obtenerFin();
        int indiceFin = ciudades.indexOf(fin);

        // buscar el indice con menor distancia de indiceFin
        double distanciaMinima = Double.MAX_VALUE;
        int indice = 0;
        for (int i = 0; i < ciudades.size(); i++){
            // solo se consideran las no visitadas
            boolean visiatada = ruta.visitada(ciudades.get(i));
            if (!visiatada){
                if(distancias[indiceFin][i] < distanciaMinima){
                    distanciaMinima = distancias[indiceFin][i];
                    indice = i;
                }
            }
        }
        return ciudades.get(indice);
    }



    /**
     * Método de cálculo de distancias entre ciudades
     */
    private void calcularDistancias(){

        // reserva de espacio
        distancias = new double[ciudades.size()][ciudades.size()];

        // basta con calcular las distancias de las posiciones
        // sobre la diagonal principal
        for (int i=0; i< ciudades.size(); i++){
            for (int j=i+1; j < ciudades.size(); j++){
                distancias[i][j] = Utilidades.calcularDistanciaEuclidea(ciudades.get(i), ciudades.get(j));
                distancias[j][i] = distancias[i][j];
            }
        }
    }


}
