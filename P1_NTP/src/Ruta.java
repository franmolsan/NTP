import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Representa un recorrido sobre un conjunto
 * de ciudades
 */
public class Ruta {
   /**
    * Almacena las ciudades recorridas en la ruta
    */
   private ArrayList<Ciudad> recorridas;

   /**
    * Coste del recorrido
    */
   private double coste;

   SecureRandom random = new SecureRandom();

   /**
    * Constructor por defecto
    */
   public Ruta(){
      recorridas = new ArrayList<>();
      coste = 0;
   }

   /**
    * Constructor de copia
    */
   public Ruta(Ruta aCopiar){
      recorridas = new ArrayList<>(aCopiar.recorridas);
      coste = aCopiar.coste;
   }

   /**
    * Se agrega ciudad a la ruta
    * @param ciudad
    */
   public void agregarCiudad(Ciudad ciudad, double distancia){
      recorridas.add(ciudad);
      coste += distancia;
   }

   /**
    * Devuelve las ciudades recorridas hasta el momento
    * @return
    */
   public int obtenerLongitud(){
      return recorridas.size();
   }

   /**
    * Se devuelve la ciudad inicial
    * @return
    */
   public Ciudad obtenerInicio(){
      return recorridas.get(0);
   }

   /**
    * Devuelve la ciudad final del recorrido
    * @return
    */
   public Ciudad obtenerFin(){
      return recorridas.get(recorridas.size()-1);
   }

   /**
    * Se agrega el coste del recorrido final
    * @param distancia
    */
   public void agregarCoste(double distancia){
      coste += distancia;
   }

   /**
    * Verifica si una ciudad ha sido visitada
    * @param ciudad
    * @return
    */
   public boolean visitada(Ciudad ciudad){
      int indice = recorridas.indexOf(ciudad);
      return indice != -1;
   }

   /**
    * Devuelve el coste de la ruta
    * @return
    */
   public double obtenerCoste(){
      return coste;
   }

   /**
    * Devuelve dos ArrayList con los valores de las coordenadas
    * X e Y
    * @return
    */
   public ArrayList<ArrayList<Double>> obtenerCoordenadas(){
      // se crea el array general
      ArrayList<ArrayList<Double>> resultado = new ArrayList<>();

      // se crean los arrays para X e Y
      resultado.add(new ArrayList<Double>());
      resultado.add(new ArrayList<Double>());

      // se recorren las ciudades
      for(int i=0; i < recorridas.size(); i++){
         Ciudad ciudad = recorridas.get(i);
         resultado.get(0).add(ciudad.obtenerX());
         resultado.get(1).add(ciudad.obtenerY());
      }

      // devolver resultado
      return resultado;
   }

   /**
    * Devuelve dos ArrayList con los valores de las coordenadas
    * X e Y
    * adaptado a programación funcional
    * @return
    */
   public ArrayList<ArrayList<Double>> obtenerCoordenadas_funcional(){
      // se crea el array general
      ArrayList<ArrayList<Double>> resultado = new ArrayList<>();

      // se crean los arrays para X e Y
      resultado.add(new ArrayList<Double>());
      resultado.add(new ArrayList<Double>());

      recorridas.stream().forEach(ciudad -> {
         resultado.get(0).add(ciudad.obtenerX());
         resultado.get(1).add(ciudad.obtenerY());
      });

      // devolver resultado
      return resultado;
   }

   /**
    * Devuelve cadena con contenido de la ruta
    * adaptado a la programación funcional
    * @return
    */
   public String toString(){

      List<String> salida = new ArrayList<>();
      salida.add("Ruta: ");

      recorridas.stream().forEach(i -> {
         salida.add(i.obtenerEtiqueta() + " ");
      });

      salida.add("\nCoste de la ruta: " + coste);

      // devolvemos salida como string
      return salida.stream().collect(Collectors.joining());

   }

   /**
    * Método para calcular el coste de una ruta, por ejemplo, si la hemos cambiado
    * Utiliza programación funcional
    * @param problema en el que se almacena la distancia entre las ciudades
    */
   public void calcularCosteRuta(Problema problema){
      // creamos array de costes
      ArrayList<Double> arrayCostes = new ArrayList<>();

      IntStream.range(1,recorridas.size()).forEach(i -> {
         Ciudad previa = recorridas.get(i-1);
         Ciudad siguiente = recorridas.get(i);
         arrayCostes.add(problema.obtenerDistancia(previa, siguiente));
      });

      // se agrega el coste de cierre
      Ciudad inicio = recorridas.get(0);
      Ciudad fin = recorridas.get(recorridas.size()-1);
      double distanciaCierre = problema.obtenerDistancia(inicio, fin);
      arrayCostes.add(distanciaCierre);

      // calculamos la suma del array de costes
      coste = arrayCostes.stream().mapToDouble(d -> d).sum();
   }

   /**
    * Se intercambian dos ciudades de forma aleatoria
    * @param problema, que contiene las distancias que hay entre cada ciudad
    */
   public void intercambiarDosCiudades(Problema problema){

      // generar dos índices aleatorios (deben ser diferentes)
      int indice1 = random.nextInt(recorridas.size());
      int indice2;

      do {
         indice2 = random.nextInt(recorridas.size());
      } while (indice1 == indice2);

      // obtenemos las ciudades
      Ciudad c1 = recorridas.get(indice1);
      Ciudad c2 = recorridas.get(indice2);

      // las intercambiamos
      recorridas.set(indice1,c2);
      recorridas.set(indice2,c1);

      // recalculamos el coste de la ruta porque la hemos cambiado
      calcularCosteRuta(problema);
   }

}
