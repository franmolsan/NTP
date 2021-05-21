package singleton;

public class Tarea implements Runnable{

    @Override
    public void run(){

        // se obtiene acceso al singleton
        GestorConexiones gestor = GestorConexiones.obtenerInstancia();

        // se simula la realización de alguna tarea
        for (int i=0; i<100; i++){
            gestor.obtenerContadorInstancias();
        }

    }

}
