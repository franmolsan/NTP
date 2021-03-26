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
      String nombreArchivo = "berlin52.tsp";

      // se crea la heuristica
      HeuristicaVMC heuristicaVMC = new HeuristicaVMC();
      HeuristicaMonteCarlo heuristicaMonteC = new HeuristicaMonteCarlo();
      HeuristicaIntercambio heuristicaIntercambio = new HeuristicaIntercambio();

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

      // cambiar la heuristica del analizador
      analizador.asignarHeuristica(heuristicaIntercambio);

      // se resuelve con la nueva heuristica
      Ruta rutaOptimaIntercambio = analizador.analizar(nombreArchivo);

      // se visualiza la ruta
      Visualizador visualizador = new Visualizador(nombreArchivo, rutaOptimaVMC,
                  rutaOptimaMonteC, rutaOptimaIntercambio);

      System.out.println("Ruta optima según heurística VMC: ");
      System.out.println(rutaOptimaVMC.toString_funcional());

      System.out.println("Ruta optima según heurística MonteCarlo: ");
      System.out.println(rutaOptimaMonteC.toString_funcional());

      System.out.println("Ruta optima según heurística de intercambio: ");
      System.out.println(rutaOptimaIntercambio.toString_funcional());

   }
}
