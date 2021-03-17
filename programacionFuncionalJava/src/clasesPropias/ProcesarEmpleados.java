package clasesPropias;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcesarEmpleados {
    /**
     * Contador de empleados
     */
    private int numeroEmpleados;

    /**
     * Lista de empleados generados a partir del archivo
     */
    private List<Empleado> empleados;

    /**
     * Agrupamiento por departamentos
     */
    private Map<String, List <Empleado>> porDepartamento;

    public ProcesarEmpleados(String nombreArchivo){
        try {
            // leer líneas del archivo
            Stream<String> lineas = Files.lines(Paths.get(nombreArchivo));

            // para cada linea, genero el objeto de la clase Empleado
            empleados = lineas.map(linea -> new Empleado(linea)).collect(Collectors.toList());

            // obtener el número de empleados
            numeroEmpleados = empleados.size();
            System.out.println("Número de empleados: " + numeroEmpleados);

        } catch (IOException e){
            System.out.println(e);
            System.out.println("Problema tratamiento archivo " + nombreArchivo);
        }

        // creacion del mapa
        porDepartamento = new HashMap<>();
    }

    /**
     * Devuleve una codena con los datos de todsos los trabajadores
     * @return
     */
    public String toString(){
        return empleados.stream().map(Empleado::toString).
                collect(Collectors.joining("\n"));
    }

    /**
     * Genera lista quedandose con los empleados con sueldos
     * comprendidos entre minimo y maximo.
     * @param minimo
     * @param maximo
     * @return
     */
    public List<Empleado> filtradoSueldos(double minimo, double maximo){
        Predicate<Empleado> condicion = empleado -> empleado.obtenerSueldo() >= minimo &&
                                                    empleado.obtenerSueldo() <= maximo;
        return empleados.stream().filter(condicion).
                sorted(Comparator.comparing(Empleado::obtenerSueldo).reversed()).
                collect(Collectors.toList());
    }

    /**
     * Obtiene el empleado con mayor sueldo
     * @return
     */
    public Empleado obtenerMejorPagado(){
        Stream<Empleado> ordenados = empleados.stream().
                sorted(Comparator.comparing(Empleado::obtenerSueldo).reversed());
        return ordenados.limit(1).collect(Collectors.toList()).get(0); // nos quedamos con el primer elemento
    }

    /**
     * ordenacion de empleados por apeillido y nombre
     */
    public List<Empleado> obtenerApellidosNombre() {
        Comparator<Empleado> comparador = Comparator.comparing(Empleado::obtenerApellidos).
                thenComparing(Empleado::obtenerNombre);
        return empleados.stream().sorted(comparador).collect(Collectors.toList());
    }

    /**
     * Se obtiene la lista de apellidos, sin repeticiones
     * @return
     */
    public List<String> apellidosSinRepeticiones(){
        // paralelizamos con parallel, solo la operacion de obtener apellidos (sobre ese flujo)
        // el resto no está paralelizado
        return empleados.stream().parallel().map(Empleado::obtenerApellidos).
                flatMap(apellido -> Arrays.stream(apellido.split(""))).
                distinct().sorted().collect(Collectors.toList());
    }

    /**
     * Agrupar los empleados por departamento
     * Se hace de forma imperativa
     */
    public void agruparDepartamentosImperativa(){
        String departamento;
        List<Empleado> listaEmpleados;

        // tratamiento de empleados uno a uno
        for (int i=0; i<empleados.size(); i++){
            departamento = empleados.get(i).obtenerDepartamento();

            // se comprueba si en el diccionario ya hay entrada para ese departamento
            listaEmpleados = porDepartamento.get(departamento);

            // si no hay entrada para el departamento
            if (listaEmpleados == null){
                listaEmpleados = new ArrayList<Empleado>();
                listaEmpleados.add(empleados.get(i));

                // agregar al diccionario una entrada con el departamento y la lista
                porDepartamento.put(departamento,listaEmpleados);
            }

            // si existe la entrada
            else {
                // se agrega el empleado a la lista
                listaEmpleados.add(empleados.get(i));
            }
        }
    }

    /**
     * agrupamiento por departamento de forma funcional
     */
    public void agruparDepartamentoFuncional(){
        porDepartamento = empleados.stream().collect(Collectors.groupingBy(Empleado::obtenerDepartamento));
    }

    /**
     * Cuenta los trabajadores de cada departamento
     * @return
     */
    //public TreeMap<String, Long> contarPorDepartamento (){
        //empleados.stream().collect(Collectors.groupingBy(Empleado::obtenerDepartamento,
          //      TreeMap::new))
    //}

    /**
     * main de pruebas
     */
    public static void main (String args []){
        System.out.println("argumentos " + args[0]);
        ProcesarEmpleados procesador = new ProcesarEmpleados(args[0]);
        System.out.println(procesador);

        // filtrar por sueldo
        System.out.println("------------- Sueldos entre 5000 y 6000 ------------- ");
        List<Empleado> seleccionados = procesador.filtradoSueldos(5000,6000);
        seleccionados.forEach(System.out::println);

        System.out.println("Mejor pagado: ");
        Empleado mejor = procesador.obtenerMejorPagado();
        System.out.println(mejor);
    }
}
