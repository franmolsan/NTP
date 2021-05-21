package singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase a la que se quiere dotar la capacidad
 * de crear una única instancia
 */
public class GestorConexiones {

    private static int contadorInstancias = 0;
    /**
     * Referencia a objeto de la clase GestorConexiones
     * (esta referencia apuntará al unico ojbeto que debe crearse)
     */
    private static GestorConexiones gestor = null;

    /**
     * Constructor de la clase
     * Nota: Se hace privado para evitar que puedan generarse
     * instancias desde fuera de la clase
     */
    private GestorConexiones(){
        contadorInstancias++;
    }

    public int obtenerContadorInstancias(){
        return contadorInstancias;
    }

    public static synchronized GestorConexiones obtenerInstancia(){
        if(gestor == null){
            gestor = new GestorConexiones();
        }
        // se devuelve la referencia a la instancia unica
        return gestor;
    }

    public static void main(String args[]){

        GestorConexiones referenciaSingleton = null;

        // creamos pool de 1000 hebras controlado por un ejecutor
        ExecutorService ex = Executors.newFixedThreadPool(1000);


        for(int i=0; i<1000; i++){
            ex.execute(new Tarea());
        }

        ex.shutdown();


        GestorConexiones gestor = GestorConexiones.obtenerInstancia();
        System.out.println("contador de instancias: " + gestor.obtenerContadorInstancias());
    }


}
