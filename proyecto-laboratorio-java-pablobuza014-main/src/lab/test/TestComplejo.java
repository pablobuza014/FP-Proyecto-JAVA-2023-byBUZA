package lab.test;

import lab.tipos.Complejo;

public class TestComplejo {

    public static void main(String[] args) {
        Complejo c1 = Complejo.of(5.70, 5.00);
        Complejo c2 = Complejo.of(-5.00, -7.00);
        Complejo c2_parse = Complejo.parse("-5.00 + -7.00 i");
        Integer n = 2;
        Complejo complejo_potencia = c1.pow(n);
        Complejo c3 = new Complejo(3, 2);
        Complejo c4 = new Complejo(1, 3);
        Complejo c5 = new Complejo(2, 1);
        Complejo c6 = new Complejo(1, 1);
        Complejo c7 = new Complejo(1, -1);
        Complejo resultado_E = (c3.add(c4)).multiply(((c5.divide(c6))).multiply(c7));

        System.out.println("---------------------TestComplejo---------------------\n");
        System.out.println("################################################");
        System.out.println("TEST DE LA FACTORIA:");
        System.out.printf("El primer numero complejo definido es: c1 = %s\n", c1.toString()); //Con '%s' indicamos impresión de una cadena de caracteres.
        System.out.println("\n################################################");
        System.out.println("TEST DEL PARSEO:");
        System.out.printf("El segundo numero complejo definido es: c2 = %s\n", c2_parse.toString());
        System.out.println("\n################################################");
        System.out.println("TEST DE EQUALS:");
        System.out.printf("Son iguales c1 y c2? --> %s\n", c1.equals(c2));
        System.out.println("\n###############################################");
        System.out.println("TEST DE ALGUNAS PROPIEDADES:");
        System.out.printf("El argumento de c1 es: %.2f\n", c1.arg());
        System.out.printf("El modulo de c2 es: %.2f\n", c2.abs());
        System.out.println("\n################################################");
        System.out.println("TEST DE ALGUNAS FUNCIONES:");
        System.out.printf("La suma entre c1 y c2 es: %s\n", c1.add(c2).toString());
        System.out.printf("El producto entre c1 y c2 es: %s\n", c1.multiply(c2).toString());
        System.out.printf("La division entre c1 y c2 es: %s\n", c1.divide(c2).toString());
        System.out.printf("c1 elevado a %d es: %s\n", n, complejo_potencia.toString()); //Con '%d' indicamos impresión de un entero.
        System.out.printf("\nDEFENSA: El resultado del apartado E de la defensa del proyecto es: %s\n", resultado_E.toString());
        System.out.println("\n---------------------Fin del testeo---------------------");
    }

}
