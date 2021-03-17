/**
 * Clase abstracta para solucionar el TSP
 */
public abstract class HeuristicaTSP {
    protected Problema problema; // protected para que las clases derivadas puedan acceder
    protected Ruta rutaOptima;

    /**
     * Constructor de la clase
     */
    public HeuristicaTSP (){
        rutaOptima = new Ruta();
    }

    /**
     * Método de resolución
     * @param problema
     */
    public abstract void resolver(Problema problema);

    /**
     * Devuelve la ruta optima
     * @return
     */
    public Ruta obtenerOptima (){
        return rutaOptima;
    }
}
