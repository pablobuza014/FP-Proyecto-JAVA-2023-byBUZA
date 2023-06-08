package lab.tipos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class Matriz1<E> extends MatrizA<E> {
	
//	    #===========================================================================
//	    # CONSTRUCTOR
//	    #===========================================================================
	
	    protected Matriz1(List<List<E>> datos) {
	        super(datos);
	    }
	    
//	    #===========================================================================
//	    # METODOS DE FACTORIA
//	    #===========================================================================
	   
	    public static <E> Matriz1<E> of(List<List<E>> datos) {
	        return new Matriz1<>(datos);
	    }
	    
	    
	    public static <E> Matriz1<E> ofFile(String fichero, String sep, Function<String,E> t) {
	        List<List<E>> datos = File2.lineasDeFichero(fichero).stream() // Leemos las líneas del fichero especificado por parámetro y las convertimos en una lista de listas de elementos del tipo E.
	        		.map(linea -> Arrays.stream(linea.split(sep)).map(t).collect(Collectors.toList())) // Dividimos o separamos cada línea por el separador pasado por parámetro y aplicamos la función 't' a cada elemento resultante.
	        		.collect(Collectors.toList());
	        return new Matriz1<>(datos);
	    }

	                                     
//	    #===========================================================================
//	    # PROPIEDADES DERIVADAS
//	    #===========================================================================

	    public Matriz1<E> traspuesta() {
	    	// Obtenemos las columnas de la matriz original y las convertimos en las filas de la matriz traspuesta. 
	    	// Cada fila de elementos la convertimos en una lista, y las listas obtenidas en una lista de listas, que resultará la matriz traspuesta.
	        List<List<E>> matriz_traspuesta = IntStream.range(0, nc())
	        		.mapToObj(c -> IntStream.range(0, nf())
	        				.mapToObj(f -> get(f, c))
	        				.collect(Collectors.toList()))
	        		.collect(Collectors.toList());
	        return new Matriz1<>(matriz_traspuesta);
	    }

	            
//	    #===========================================================================
//	    # REPRESENTACION COMO CADENA
//	    #===========================================================================
	    
	    public String toString() {
	    	// Convertimos cada fila de la matriz en una cadena de caracteres.
	    	// Luego, unimos los elementos de cada fila separados por un espacio en blanco, dando así una lista de cadenas que representen cada una de las filas de la matriz.
	    	// Para concluir, unimos todas las filas en una única cadena de caracteres, separadas por saltos de línea, originándose así la matriz por pantalla.
	        return datos.stream()
	        		.map(fila -> fila.stream().map(Object::toString).collect(Collectors.joining("  ")))
	        		.collect(Collectors.joining("\n"));
	    }
	    
	    
//	    #===========================================================================
//	    # CRITERIO DE IGUALDAD
//	    #===========================================================================
	    
		@Override
	    public int hashCode() {
	        return super.hashCode(); // Llamamos al método hashCode de la superclase MatrizA.
	    }
	    
	    @Override
		public boolean equals(Object nuevo_objeto) {
	    	return super.equals(nuevo_objeto); // Llamamos al método equals de la superclase MatrizA, pasando como argumento el nuevo objeto a comparar.
	    }

}