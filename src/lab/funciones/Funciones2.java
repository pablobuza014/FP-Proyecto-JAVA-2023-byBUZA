package lab.funciones;

import us.lsi.tools.Preconditions;

public class Funciones2 {
	
	// ##################################
	// EJERCICIO A
	// Cálculo de producto.
	// ##################################
	
	public static Integer producto2(Integer m, Integer n, Integer k) {
	    Preconditions.checkArgument(n >= k, "n debe ser mayor o igual que k");
	    Preconditions.checkArgument(m>0, "m debe ser mayor que 0");
	    Integer i = 0;
	    Integer factor = 1;
	    for (i = 0; i < k; i++) {
	        factor *= ((n-i)/m);
	    }
	    return factor;
	}
	
	
	// ##################################
	// EJERCICIO B
	// Cálculo de S.
	// ##################################
	
	// AUXILIAR (de FUNCIONES1)
	public static Double factorial(Integer n) {
	    if (n < 0) {
	        System.out.println("El factorial de un número negativo no existe, luego el factorial se realiza sobre números naturales");
	        return null;
	    } else if (n == 1 || n == 0) {
	        return 1.00; // NOTA: el factorial de 0 y 1 es 1; 0!=1!=1
	    } else {
	        return n * factorial(n - 1);
	    }
	}
	
	public static Double combinatorio(Integer n, Integer k) {
	    Preconditions.checkArgument(n >= k, "n debe ser mayor o igual que k");
	    return factorial(n) / (factorial(k) * factorial(n-k));
	}
	
	// IMPLEMENTACIÓN DE SMNK()
	public static Double SMNK(Integer m, Integer n, Integer k) {
	    Integer i = 0;
	    Double S = 0.0;
	    Preconditions.checkArgument(n >= k, "n debe ser mayor o igual que k");
	    Preconditions.checkArgument(k >= i, "k debe ser mayor o igual que i");
	    Preconditions.checkArgument(m>0, "m debe ser mayor que 0");
	    for (i = 0; i < k; i++) {
	        S += (Math.pow(-1/m, i)) * (combinatorio(k, i)) * (Math.pow((k-i), n));
	    }
	    return ((1.0/factorial(k+1)) * S);
	}
}
