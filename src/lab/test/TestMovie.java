package lab.test;

import lab.tipos.Movie;


public class TestMovie {

	public static void main(String[] args) {
		Movie m1_parse = Movie.parse("TRAKWORTY;Pablo Buza");
        Movie m2_parse = Movie.parse("ATRACO;James Cameron");
        System.out.println("---------------------TestMovie---------------------\n");
        System.out.println("\n################################################");
        System.out.println("TEST DE LA FACTORIA usando PARSE y toStringShort:");
        System.out.printf("La primera movie definida es: m1 = %s\n", m1_parse.toStringShort());
        System.out.printf("La segunda movie definida es: m2 = %s\n", m2_parse.toStringShort());
        System.out.println("\n################################################");
        System.out.println("TEST DE LA FACTORIA usando PARSE y toString:");
        System.out.printf("La primera movie definida es: m1 = %s\n", m1_parse.toString());
        System.out.printf("La segunda movie definida es: m2 = %s\n", m2_parse.toString());
        System.out.println("\n################################################");
        System.out.println("TEST DE EQUALS:");
        System.out.printf("Son iguales m1 y m2? --> %s\n", m1_parse.equals(m2_parse));
        System.out.printf("Son iguales m1 y m1? --> %s\n", m1_parse.equals(m1_parse));
        System.out.println("\n###############################################");
        System.out.println("\n---------------------Fin del testeo---------------------");
	}

}
