/**
 * Clase abstracta para solucionar el TSP
 */
public interface HeuristicaTSP {

    /**
     * Método de resolución
     * @param problema
     */
    public Ruta resolver(Problema problema);
}
