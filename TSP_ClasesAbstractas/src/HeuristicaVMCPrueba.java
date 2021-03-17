public class HeuristicaVMCPrueba {

    public static void main(String args[]){
        String nombreArchivo = "./data/small10.tsp";
        Problema problema = new Problema(nombreArchivo);

        HeuristicaVMC heuristica = new HeuristicaVMC();
        heuristica.resolver(problema);
        System.out.println(heuristica.obtenerOptima());
    }
}
