import java.util.ArrayList;

/**
 * Recorrido sobre un conjunto de ciudades
 */
public class Ruta {

    /**
     * Almacena las ciudades recorridas
     */
    private ArrayList<Ciudad> recorridas;

    /**
     * Coste del recorrido
     */
    private double coste;

    /**
     * Constructor por defecto
     */
    public Ruta (){
        recorridas = new ArrayList<>();
        coste = 0;
    }

    /**
     * Agregar una nueva ciudad a las recorridas
     * @param nuevaCiudad
     */
    public void agregarCiudad(Ciudad nuevaCiudad, double distancia){
        recorridas.add(nuevaCiudad);
        coste += distancia;
    }

    public int obtenerLongitud (){
        return recorridas.size();
    }

    public Ciudad obtenerInicio(){
        return recorridas.get(0);
    }

    public Ciudad obtenerFin(){
        return recorridas.get(recorridas.size()-1);
    }

    public void agregarCoste(double distancia){
        coste += distancia;
    }

    public boolean visitada (Ciudad ciudad){
        int indice = recorridas.indexOf(ciudad);
        return indice != -1;
    }

    public double obtenerCoste(){
        return coste;
    }

    public String toString(){
        String salida = "Ruta: ";
        for (int i = 0; i<recorridas.size(); i++){
            salida += recorridas.get(i).getEtiqueta() + " ";
        }
        salida += "Coste: " + coste;
        return salida;
    }
}
