/**
 * Clase abstracta para solucionar problema del TSP
 */
public abstract  class HeuristicaTSP {
   /**
    * Dato miembro privado para almacenar el problema
    */
   protected Problema problema;

   /**
    * Ruta optima obtenida
    */
   protected Ruta rutaOptima;

   /**
    * Constructor de la clase
    */
   public HeuristicaTSP(){
      // inicializar ruta optima a vacia
      rutaOptima = new Ruta();
   }

   /**
    * Metodo de resolucion
    */
   public abstract void resolver(Problema problema);

   /**
    * Devuelve la ruta optima
    * @return
    */
   public Ruta obtenerOptima(){
      return rutaOptima;
   }
}
