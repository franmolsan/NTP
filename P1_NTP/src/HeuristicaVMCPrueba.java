import java.util.SplittableRandom;

/**
 * Prueba de la heuristica del vecino mas cercano
 */
public class HeuristicaVMCPrueba {
   public static void main(String args[]){
      String nombreArchivo = "./data/small10.tsp";
      Problema problema = new Problema(nombreArchivo);

      // se crea la heuristica
      HeuristicaVMC heuristica = new HeuristicaVMC();

      // se resuelve
      heuristica.resolver(problema);

      // se muestra la ruta optima
      System.out.println(heuristica.obtenerOptima());
   }
}
