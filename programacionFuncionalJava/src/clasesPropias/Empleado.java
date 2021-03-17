package clasesPropias;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Clase Empleado
 */
public class Empleado {

   // Dato miembro para almacenar el nombre
   private String nombre;

   // Dato miembro para almacenar primer apellido
   private String apellidos;

   // Dato miembro para almacenar el sueldo
   private double sueldo;

   // Dato miembro para almacenar el departamento
   private String departamento;

   // Constructor
   public Empleado(String nombre, String apellidos,
           double sueldo, String dapartamento) {
      this.nombre = nombre;
      this.apellidos = apellidos;
      this.sueldo = sueldo;
      this.departamento = dapartamento;
   }

   /**
    * Constructor a partir de linea de datos
    * @param linea
    * @return
    */
   public Empleado(String linea) {
      // Se define el patron para las comas que hacen de separadores
      Pattern pattern = Pattern.compile(",");

      // Se procesa la linea
      List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

      // Se asignan valores de datos miembro
      nombre=infos.get(0);
      apellidos=infos.get(1);
      sueldo=new Double(infos.get(2));
      departamento=infos.get(3);
   }

   // Metodo para asignar el nombre
   public void asignarNombre(String nombre) {
      this.nombre = nombre;
   }

   // Metodo para acceder al nombre
   public String obtenerNombre() {
      return nombre;
   }

   // Metodo para asignar el apellido
   public void asignarApellidos(String primerApellido) {
      this.apellidos = primerApellido;
   }

   // Metodo para obtener el primer apellido
   public String obtenerApellidos() {
      return apellidos;
   }

   // Metodo para asignar el valor de sueldo
   public void asignarSueldo(double sueldo) {
      this.sueldo = sueldo;
   }

   // Metodo para obtener el sueldo
   public double obtenerSueldo() {
      return sueldo;
   }

   // Metodo par asignar el departamento
   public void asignarDepartamento(String departamento) {
      this.departamento = departamento;
   }

   // Metodo para obteber el valor del dato miembro departamento
   public String obtenerDepartamento() {
      return departamento;
   }

   // Recupera nombre y primer apellido
   public String obtenerNombreApellido() {
      return String.format("%s %s", obtenerNombre(), obtenerApellidos());
   }

   // Metodo toString
   @Override
   public String toString() {
      // con - despues del % se indica que se hace alineacion
      // a la izquierda
      return String.format("%20s %25s %8.2f %15s",
              obtenerNombre(), obtenerApellidos(), obtenerSueldo(),
              obtenerDepartamento());
   }

   /**
    * Metodo generarLineaSimple genera una linea con la informacion de los empleados,
    * separada por comas
    *
    * @return
    */
   public String generarLineaSimple() {
      String info = nombre + ",  " + apellidos + ",  " + sueldo+" , "+ departamento;
      return info;
   }
}
