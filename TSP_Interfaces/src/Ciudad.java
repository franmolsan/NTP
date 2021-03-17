/**
 * Clase
 */
public class Ciudad {
    /**
     * Guarda nombre de la ciudad
     */
    private String etiqueta;

    /**
     * Coordenadas de la ciudad
     */
    private double x, y;

    /**
     * Constructor de la clase
     * @param etiqueta
     * @param coordx
     * @param coordy
     */
    public Ciudad(String etiqueta, double coordx, double coordy){
        this.etiqueta = etiqueta;
        x = coordx;
        y = coordy;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
