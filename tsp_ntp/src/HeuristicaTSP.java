/**
 * Clase abstracta para solucionar el TSP
 */
public abstract class HeuristicaTSP {
    protected Problema problema; // protected para que las clases derivadas puedan acceder
    protected Ruta rutaOptima;

    /**
     * Constructor de la clase
     * @param problema
     */
    public HeuristicaTSP (Problema problema){
        this.problema = problema;
        rutaOptima = new Ruta();
    }

    /**
     * Método de resolución
     */
    public abstract void resolver();

    /**
     * Devuelve la ruta optima
     * @return
     */
    public Ruta obtenerOptima (){
        return rutaOptima;
    }
}
