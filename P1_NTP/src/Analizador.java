/**
 * Analizador de problemas via TSP
 */
public class Analizador {
   /**
    * Dato miembro con la heuristica a usar
    */
   private HeuristicaTSP heuristicaTSP;

   /**
    * Metodo para asignar la heuristica a usar
    * @param heuristica
    */
   public void asignarHeuristica(HeuristicaTSP heuristica){
      heuristicaTSP = heuristica;
   }

   /**
    * metodo para producir el analisis de un problema
    * @param nombreFichero
    * @return
    */
   public Ruta analizar(String nombreFichero){
      // se crea el problema
      Problema problema = new Problema("./data/" + nombreFichero);

      // se analiza el problema: se delega la funcionalidad en el
      // dato miembro heuristicaTSP
      heuristicaTSP.resolver_funcional(problema);

      // se devuelve la ruta optima
      return heuristicaTSP.obtenerOptima();
   }

   /**
    * metodo main para pruebas
    * @param args
    */
   public static void main(String args[]){
      String nombreArchivo = "small10.tsp";
      // se crea la heuristica
      HeuristicaVMC heuristicaVMC = new HeuristicaVMC();
      HeuristicaMonteCarlo heuristicaMonteC = new HeuristicaMonteCarlo();

      // se crea el analizador
      Analizador analizador = new Analizador();

      // se asigna la heuristica
      analizador.asignarHeuristica(heuristicaVMC);

      // se produce el analisis del problema
      Ruta rutaOptimaVMC = analizador.analizar(nombreArchivo);

      // cambiar la heuristica del analizador
      analizador.asignarHeuristica(heuristicaMonteC);

      // se resuelve con la nueva heuristica
      Ruta rutaOptimaMonteC = analizador.analizar(nombreArchivo);

      // se visualiza la ruta
      Visualizador visualizador = new Visualizador(nombreArchivo, rutaOptimaVMC,
              rutaOptimaMonteC);

      System.out.println("Coste de ruta optima según heurística VMC: " + rutaOptimaVMC.obtenerCoste());
      System.out.println("Coste de ruta optima según heurística MonteCarlo: " + rutaOptimaMonteC.obtenerCoste());
   }
}
