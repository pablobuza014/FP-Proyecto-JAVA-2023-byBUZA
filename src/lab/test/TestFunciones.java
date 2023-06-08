package lab.test;

import lab.funciones.Funciones;
import java.util.List;
import java.util.function.Function;

public class TestFunciones {
	
	public static void main(String[] args) {
	    Integer n = 4;
	    Integer k = 2;
	    Double a = 3.0;
	    Double e = 0.001;
	    Function<Double, Double> f = x -> 2*Math.pow(x, 2);
	    Function<Double, Double> f_derivada = x -> 4*x;
	    List<String> frase_palindroma = List.of("hola", "somos" , "o", "no", "somos", "sobornos", "porque", "sevilla", "vienen", "anna", "y", "otto");
	    
	    System.out.println("---------------------TestFunciones---------------------\n");
        System.out.println("################################################");
        System.out.println("Test del metodo 'producto':");
	    System.out.println("El producto de " + n + " y " + k + " es " + Funciones.producto(n,k) + '\n');
        System.out.println("################################################");
        System.out.println("Test del metodo 'combinatorio':");
	    System.out.println("El numero combinatorio de " + n + " y " + k + " es " + Funciones.combinatorio(n,k) + '\n');
        System.out.println("################################################");
        System.out.println("Test del metodo 'SNK':");
	    System.out.println("El numero S(n,k) siendo n = " + n + " y k = " + k + " es: " + Funciones.SNK(n,k) + '\n');
        System.out.println("################################################");
        System.out.println("Test del metodo 'encuentraNewton':");
	    System.out.println("Resultado del metodo encuentraNewton con a = " + a + ", "
	    		+ "e = " + e + ", f(x) = 2*x^2 y f'(x) = 4*x es: " + Funciones.encuentraNewton(f,f_derivada,a,e) +'\n');
        System.out.println("################################################");
        System.out.println("Test del metodo 'palindromos':");
        System.out.println("La frase de entrada es --> hola, somos, o, no, somos, sobornos, porque, sevilla, vienen, anna, y, otto\n");
	    System.out.println("Los primeros 4 palindromos son:\n" + Funciones.palindromos(4, frase_palindroma) + '\n');
        System.out.println("---------------------Fin del testeo---------------------");
	}

}

