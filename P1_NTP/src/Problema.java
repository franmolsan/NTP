import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Almacenar conjuntos de localidades a recorrer
 */
public class Problema {
   /**
    * Coleccion de ciudades del problema
    */
   private ArrayList<Ciudad> ciudades;

   /**
    * Matriz de distancias
    */
   private double[][] distancias;

   /**
    * Constructor para leer datos de archivo
    * adaptado a programación funcional (bucles internos)
    * @param nombreFichero
    */
   public Problema(String nombreFichero){
      int dimension;

      // creo objeto para ciudades
      ciudades = new ArrayList<>();

      // procesamiento del archivo
      try{
         // creacion de flujo para lectura del archivo
         FileReader lector = new FileReader(nombreFichero);
         BufferedReader lectorb = new BufferedReader(lector);

         // se lee la primera linea
         String primeraLinea = lectorb.readLine();

         // dividir el contenido en las palabras contenidas, mediante
         // split
         String[] datos = primeraLinea.split("\\s+");

         // se comprueba que hay dos palabras en datos
         if(datos.length != 2){
            System.out.println("Formato incorrecto en archivo: " + nombreFichero);
            System.exit(-1);
         }

         // guardo la dimension
         dimension = Integer.parseInt(datos[1]);
         System.out.println("Dimension del problema: " + dimension);

         // leer cada linea y crear ciudad
         IntConsumer leerCiudad = indice -> {
            // lectura de nueva linea
            String linea = new String();
            try {
               linea = lectorb.readLine();
            } catch (IOException e) {
               e.printStackTrace();
            }

            // se hace split
            String[] datosCiudad = linea.split("\\s+");

            // comprobacion: tiene que haber 3 palabras
            if(datosCiudad.length != 3){
               System.out.println("Formato incorrecto en archivo: " + nombreFichero);
               System.out.println("Linea: " + linea);
               System.exit(-1);
            }

            // creacion de ciudad con los datos leidos
            Ciudad ciudad = new Ciudad(datosCiudad[0], Double.parseDouble(datosCiudad[1]),
                    Double.parseDouble(datosCiudad[2]));
            // se agrega al vector de ciudades
            ciudades.add(ciudad);
         };

         IntStream.range(0,dimension).forEach(leerCiudad);

         // se calculan las distancias
         calcularDistancias_funcional();

      } catch(IOException e){
         System.out.println("Excepcion: " + e);
         System.out.println("Error en apertura del archivo");
         System.exit(-1);
      }
   }

   /**
    * Ofrece una cadena con la informacion del objeto
    * @return
    */
   public String toString(){
      String salida = "Dimension: " + ciudades.size() + "\n";
      salida += "Matriz distancias: \n";
      for(int i=0; i < ciudades.size(); i++){
         for(int j=0; j < ciudades.size(); j++){
            salida += String.format("%7.2f", distancias[i][j]) + " ";
         }
         salida += "\n";
      }

      // return salida
      return salida;
   }

   /**
    * Ofrece una cadena con la informacion del objeto
    * adaptado a programación funcional
    * @return
    */
   public String toString_funcional(){

      List<String> salida = new ArrayList<>();
      salida.add("Dimension: " + ciudades.size() + "\n");
      salida.add("Matriz distancias: \n");

      // para cada fila de la matriz, añadir cada elemento al string de salida
      IntStream.range(0,ciudades.size()).forEach(i ->{
         Arrays.stream(distancias[i]).forEach(j -> salida.add(String.format("%7.2f", j) + " "));
         salida.add("\n");
      });

      // pasar de lista de strings a un único string
      String stringSalida = salida.stream().collect(Collectors.joining());

      // return salida
      return stringSalida;
   }

   /**
    * Devuelve el numero de ciudades
    * @return
    */
   public int obtenerDimension(){
      return ciudades.size();
   }

   /**
    * Devuelve la ciudad almacenada en una posicion
    * @param indice
    * @return
    */
   public Ciudad obtenerCiudad(int indice){
      return ciudades.get(indice);
   }

   /**
    * Devuelve todas las ciudades del problema
    * @return
    */
   public List<Ciudad> obtenerCiudades(){
      return ciudades;
   }

   /**
    * Determina la distancia entre dos ciudades
    * @param ciudad1
    * @param ciudad2
    * @return
    */
   public double obtenerDistancia(Ciudad ciudad1, Ciudad ciudad2){
      // hay que pasar de ciudad1 a la posicion de ciudad1 en ciudades
      int indice1 = ciudades.indexOf(ciudad1);
      int indice2 = ciudades.indexOf(ciudad2);

      // se devuelve la distancia
      return distancias[indice1][indice2];
   }

   /**
    * Devuelve la ciudad mas cercana
    * @param ruta
    * @return
    */
   public Ciudad obtenerMasCercana(Ruta ruta){
      // se obtiene la ultima ciudad
      Ciudad fin = ruta.obtenerFin();
      int indiceFin = ciudades.indexOf(fin);

      // buscar el indice con menor distancia de indiceFin
      double distanciaMinima = Double.MAX_VALUE;
      int indice = 0;
      for(int i=0; i < ciudades.size(); i++){
         // solo se consideran las no visitadas
         boolean visitada = ruta.visitada(ciudades.get(i));
         if (!visitada){
            if(distancias[indiceFin][i] < distanciaMinima){
               distanciaMinima = distancias[indiceFin][i];
               indice = i;
            }
         }
      }

      // devuelve la ciudad mas cercana
      return ciudades.get(indice);
   }

   /**
    * Devuelve la ciudad mas cercana
    * adaptado a programación funcional
    * @param ruta
    * @return
    */
   public Ciudad obtenerMasCercana_funcional(Ruta ruta){
      // se obtiene la ultima ciudad
      Ciudad fin = ruta.obtenerFin();
      int indiceFin = ciudades.indexOf(fin);

      /* buscar el indice con menor distancia de indiceFin */

      // primero nos quedamos con las ciudades no visitadas
      Predicate<Ciudad> condicionNoVisitada = ciudad -> !ruta.visitada(ciudad);
      Stream<Ciudad> noVisitadas = ciudades.stream().filter(condicionNoVisitada);

      // ahora ordenamos las ciudades posibles según la distancia, y nos quedamos con la primera (la más cercana)
      Stream<Ciudad> ciudadesOrdenadasPorDistancia = noVisitadas.
              sorted(Comparator.comparing(ciudad -> obtenerDistancia(fin, ciudad)));

      Ciudad ciudadMasCercana  = ciudadesOrdenadasPorDistancia.limit(1).collect(Collectors.toList()).get(0);

      // devuelve la ciudad mas cercana
      return ciudadMasCercana;
   }

   /**
    * Metodo de calculo de ciudades
    */
   private void calcularDistancias(){
      // se reserva espacio para el array de distancias
      distancias = new double[ciudades.size()][ciudades.size()];

      // basta con calcular las distancias de las posiciones sobre
      // la diagonal principal
      for(int i=0; i < ciudades.size(); i++){
         for(int j = i+1; j < ciudades.size(); j++){
            distancias[i][j] = Utilidades.calcularDistanciaEuclidea(ciudades.get(i), ciudades.get(j));
            distancias[j][i] = distancias[i][j];
         }
      }


   }

   /**
    * Metodo de calculo de ciudades
    * adaptado a programación funcional
    */
   private void calcularDistancias_funcional(){
      // se reserva espacio para el array de distancias
      distancias = new double[ciudades.size()][ciudades.size()];

      // basta con calcular las distancias de las posiciones sobre
      // la diagonal principal

      IntStream.range(0,ciudades.size()).forEach( i -> {
         IntStream.range(i+1, ciudades.size()).forEach(j -> {
            distancias[i][j] = Utilidades.calcularDistanciaEuclidea(ciudades.get(i), ciudades.get(j));
            distancias[j][i] = distancias[i][j];
         });
      });

      // otra posibilidad, recorriendo las ciudades en lugar de un rango de números
      /*
      ArrayList<Ciudad> calculadas = new ArrayList<>();
      ciudades.stream().forEach(ciudad1 -> {
         calculadas.add(ciudad1);
         ciudades.stream().filter(ciudad2 -> !calculadas.contains(ciudad2)).forEach(ciudad2 ->{
            distancias[ciudades.indexOf(ciudad1)][ciudades.indexOf(ciudad2)] =
                  Utilidades.calcularDistanciaEuclidea(ciudad1, ciudad2);
            distancias[ciudades.indexOf(ciudad2)][ciudades.indexOf(ciudad1)] =
                    distancias[ciudades.indexOf(ciudad1)][ciudades.indexOf(ciudad2)];
         });
      });
       */
   }


}
