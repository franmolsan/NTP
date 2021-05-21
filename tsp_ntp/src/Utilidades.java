/**
 * Clase para utilidades de propósito general
 */
public class Utilidades {

    // static para no tener que crear un objeto
    public static double calcularDistanciaEuclidea(Ciudad ciudad1, Ciudad ciudad2){
        return Math.sqrt(Math.pow(ciudad1.getX() - ciudad2.getX(), 2) +
                Math.pow(ciudad1.getY() - ciudad2.getY(), 2));
    }


}
