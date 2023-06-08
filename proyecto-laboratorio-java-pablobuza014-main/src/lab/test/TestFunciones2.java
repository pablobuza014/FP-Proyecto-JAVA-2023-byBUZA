package lab.test;

import lab.funciones.Funciones;

import lab.funciones.Funciones2;
import java.util.List;
import java.util.function.Function;

public class TestFunciones2 {
	
	public static void main(String[] args) {
	    Integer n = 4;
	    Integer k = 2;
	    Integer m = 2;
	    System.out.println("---------------------TestFunciones2---------------------\n");
        System.out.println("################################################");
        System.out.println("Test del metodo 'producto2':");
	    System.out.println("El producto de " + n + " y " + k + " con m = " + m + " es " + Funciones2.producto2(m,n,k) + '\n');
	    System.out.println("Test del metodo 'SNK2':");
	    System.out.println("El numero S(n,k) siendo n = " + n + " y k = " + k + " con m = " + m + " es: " + Funciones2.SMNK(m,n,k) + '\n');
	}
}