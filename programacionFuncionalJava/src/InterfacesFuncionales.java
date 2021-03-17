import java.util.function.*;

public class InterfacesFuncionales {
    public static void main (String args []){
        // Interfaz funcional operador binario - con
        // expresion lambda asociada

        BinaryOperator<Integer> f1 = (Integer x, Integer y) -> {return x*y;};
        int res1 = f1.apply(3,4);
        System.out.println("res1 " + res1);

        // f1 es una referencia y puedo apuntarla a otra funcion
        f1 = (Integer x, Integer y) -> {return x+y;};
        int res2 = f1.apply(3,4);
        System.out.println("res2 " + res2);

        // Existen versiones especificas de las interfaces para los
        // tipos concretos
        IntBinaryOperator f2 = (int x, int y) -> {
            return x-y;
        };

        // si no hay ambiguedad, simplificamos la expresion
        // "azucar sintactico"
        IntBinaryOperator f3 =  (x,y) -> (x+y);

        // en realidad, la expresion lambda por debajo, es
        IntBinaryOperator f3Completa = new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return 0;
            }
        };

        // Tambien hay otros tipos
        DoubleBinaryOperator f4 = (x,y) -> (x*y);

        // Expresion de tipo consumidor (no producen datos)
        Consumer<Integer> f5 = valor -> System.out.println("Valor: " + valor);
        f5.accept(16);

        // Expresion lambda sin argumentos
        Runnable exp6 = () -> System.out.println("Hola mundo");
        exp6.run();

        IntPredicate f7 = x -> x >0;
        boolean res3 = f7.test(17);
        System.out.println("res3:" + res3);
    }


}
