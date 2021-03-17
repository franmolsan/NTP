import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
         String linea = lectorb.readLine();

         // dividir el contenido en las palabras contenidas, mediante
         // split
         String[] datos = linea.split("\\s+");

         // se comprueba que hay dos palabras en datos
         if(datos.length != 2){
            System.out.println("Formato incorrecto en archivo: " + nombreFichero);
            System.exit(-1);
         }

         // guardo la dimension
         dimension = Integer.parseInt(datos[1]);
         System.out.println("Dimension del problema: " + dimension);

         // bucle de lectura del resto de lineas
         for(int i=0; i < dimension; i++){
            // lectura de nueva linea
            linea = lectorb.readLine();

            // se hace split
            datos = linea.split("\\s+");

            // comprobacion: tiene que haber 3 palabras
            if(datos.length != 3){
               System.out.println("Formato incorrecto en archivo: " + nombreFichero);
               System.out.println("Linea: " + linea);
               System.exit(-1);
            }

            // creacion de ciudad con los datos leidos
            Ciudad ciudad = new Ciudad(datos[0], Double.parseDouble(datos[1]),
                    Double.parseDouble(datos[2]));
            // se agrega al vector de ciudades
            ciudades.add(ciudad);
         }

         // se calculan las distancias
         calcularDistancias();

      }catch(IOException e){
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

}
