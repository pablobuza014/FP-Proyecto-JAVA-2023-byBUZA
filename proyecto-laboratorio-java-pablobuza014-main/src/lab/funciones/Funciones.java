package lab.funciones;
import java.lang.Math;

import java.util.function.Function;
import us.lsi.tools.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Funciones {
	
	// ##################################
	// EJERCICIO 1
	// Cálculo de producto.
	// ##################################
	
	public static Integer producto(Integer n, Integer k) {
	    Preconditions.checkArgument(n >= k, "n debe ser mayor o igual que k");
	    Integer i = 0;
	    Integer factor = 1; // Inicializamos a 1 pues, en caso contrario, para todo n,k con n>=k daría 0 para un factor inicializado con 0.
	    for (i = 0; i < k; i++) {
	        factor *= (n-i);
	    }
	    return factor;
	}

	// ##################################
	// EJERCICIO 2
	// Cálculo de número combinatorio.
	// ##################################
	
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
	

	// ##################################
	// EJERCICIO 3
	// Cálculo de S.
	// ##################################
	
	public static Double SNK(Integer n, Integer k) {
	    Integer i = 0;
	    Double S = 0.0;
	    Preconditions.checkArgument(n >= k, "n debe ser mayor o igual que k");
	    Preconditions.checkArgument(k >= i, "k debe ser mayor o igual que i");
	    for (i = 0; i < k; i++) {
	        S += (Math.pow(-1, i)) * (combinatorio(k, i)) * (Math.pow((k-i), n));
	    }
	    return ((1.0/factorial(k)) * S);
	}

	
	// ##################################
	// EJERCICIO 4
	// Método de Newton.
	// ##################################
	
	public static Double encuentraNewton(Function<Double, Double> f, Function<Double, Double> f_derivada, Double a, Double e) {
	    Double xo = a;
	    Double error_estimado = Math.abs(f.apply(xo)); // Con '(f.apply(xo))' evaluamos la función 'f' en el punto 'xo' y obtenemos su resultado.
	    while (!(error_estimado <= e)) {
	        Double x_con_iteracion = xo - f.apply(xo)/f_derivada.apply(xo);
	        error_estimado = Math.abs(f.apply(x_con_iteracion));
	        xo=x_con_iteracion;
	    }
	    return xo;
	}

	// ##################################
	// EJERCICIO 5
	// 'N' primeras palabras palíndromas.
	// ##################################
	
			// -------------------------
			// PARTE 1
			// Creación de método auxiliar que nos permita invertir una cadena.
			// -------------------------
	
	public static String invertirCadena(String cadena) {
	    String invertida = "";
	    for (int i = cadena.length() - 1; i >= 0; i--) { // Inicializamos la variable 'i' con el valor de la longitud real de la cadena de caracteres.
	    	// Y recorremos los caracteres de la cadena de texto desde el último al primer carácter (siempre y cuando queden); a medida que se recorren, actualizamos la variable 'i' hasta que llegue a 0. 
	        invertida += cadena.charAt(i);
	    }
	    return invertida;
	}
	
			// -------------------------
			// PARTE 2
			// Creación de método que nos permita encontrar las 'n' primeras palabras palíndromas.
			// -------------------------
	
	public static List<String> palindromos(Integer n, List<String> texto) {
	    String textoConcatenado = String.join(",", texto); // Unimos todas las cadenas en una única cadena (las separamos por comas).
	    String[] palabras = textoConcatenado.split(","); // Separamos la cadena 'textoConcatenado' en varias subcadenas según la coma.
	    List<String> palindromas = new ArrayList<>(); // Creamos una lista vacía llamada "palíndromas".
	    for (String palabra : palabras) { // Bucle en el que inicializamos una variable 'palabra' que recorrerá todos los elementos de la variable 'palabras'.
	        if (palabra.equals(invertirCadena(palabra))) { // Si la palabra es igual que su invertida,
	            palindromas.add(palabra); // la añadimos a la lista de palabras palíndromas
	        }
	        if (palindromas.size() == n) { // En el momento que haya 'n' palabras palíndromas en la lista de palabras palíndromas,
	            break; // salimos del bucle.
	        }
	    }
	    return palindromas;
	}

}