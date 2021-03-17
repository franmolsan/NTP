/**
 * Clase
 */
public class Ciudad {
   /**
    * Almacena nombre de la ciudad
    */
   private String etiqueta;

   /**
    * Coordenadas de la ciudad
    */
   private double x;
   private double y;

   /**
    * Constructor de la clase
    * @param etiqueta
    * @param coordx
    * @param coordy
    */
   public Ciudad(String etiqueta, double coordx, double coordy){
      this.etiqueta = etiqueta;
      x = coordx;
      y = coordy;
   }

   /**
    * Devuelve el valor del dato mimebro x
    * @return
    */
   public double obtenerX(){
      return x;
   }

   /**
    * Devuelve el valor del dato miembro y
    * @return
    */
   public double obtenerY(){
      return y;
   }

   /**
    * Devuelve la etiqueta
    * @return
    */
   public String obtenerEtiqueta(){
      return etiqueta;
   }
}
