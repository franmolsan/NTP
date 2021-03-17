/**
 * Analizador de problemas via TSP
 */
public class Analizador {
    private HeuristicaTSP heuristicaTSP;

    /**
     * Metodo para asignar la heuristica a usar
     * @param heuristica
     */
    public void asignarHeuristica (HeuristicaTSP heuristica){
        heuristicaTSP = heuristica;
    }


    /**
     * metodo par aproducir el analisis de un problema
     * @param nombreFichero
     * @return
     */
    public Ruta analizar(String nombreFichero){

        // se crea el problema
        Problema problema = new Problema("./data/" + nombreFichero);

        // se analiza el problema: se dele ga la funcionalidad en el dato miembro
        // heuristica TSP
        return heuristicaTSP.resolver(problema);
    }

    /**
     * metodo main para pruebas
     * @param args
     */
    public static void main (String args []){
        String nombreArchivo = "small10.tsp";
        // se crea la heuristica
        HeuristicaVMC heuristicaVMC = new HeuristicaVMC();
        HeuristicaMonteCarlo heuristicaMonteC = new HeuristicaMonteCarlo();

        // se crea el analizador
        Analizador analizador = new Analizador();

        // se asigna la heuristica
        analizador.asignarHeuristica(heuristicaVMC);

        // se produce el analisis del problema
        Ruta rutaOptima = analizador.analizar(nombreArchivo);


        // se asigna la heuristica
        analizador.asignarHeuristica(heuristicaMonteC);

        // se produce el analisis del problema
        Ruta rutaOptimaMonteC = analizador.analizar(nombreArchivo);

        // se visualiza la ruta
        Visualizador visualizador = new Visualizador(nombreArchivo, rutaOptima);
    }
}
