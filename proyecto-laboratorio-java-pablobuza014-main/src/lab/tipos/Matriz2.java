package lab.tipos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class Matriz2<E> extends MatrizA<E>{
	 

    //#===========================================================================
    //# CONSTRUCTOR
    //#===========================================================================

    protected Matriz2(List<List<E>> datos) { 
        super(datos);
    }
    
    //#===========================================================================
    //# METODOS DE FACTORIA
    //#===========================================================================
	
	public static <E> Matriz2<E> of(List<List<E>> datos) {
        return new Matriz2<>(datos);
    }

    public static <E> Matriz2<E> ofFile(String fichero, String sep, Function<String,E> t) {
        List<List<E>> datos = new ArrayList<>(); // Creamos una lista de listas vacía para guardar los datos del fichero pasado por parámetro.
        for (String linea: File2.lineasDeFichero(fichero)) { // Iteramos sobre las líneas del fichero.
            List<E> fila = new ArrayList<>(); // Creamos una nueva lista vacía para almacenar los elementos de cada fila.
            for (String elemento: linea.split(sep)) { // Separamos cada línea por el separador especificado por parámetro. 
                fila.add(t.apply(elemento)); // Aplicamos la función 't' a cada elemento y agregamos cada elemento transformado a la fila. 
            }
            datos.add(fila); // Añadimos cada fila a la lista de datos.
        }
        return new Matriz2<>(datos); // Devolvemos la matriz creada en base al fichero dado.
    }
    
    
    //#===========================================================================
    //# PROPIEDADES DERIVADAS
    //#===========================================================================

	public Matriz2<E> traspuesta() {
        List<List<E>> matriz_traspuesta = new ArrayList<>(); // Creamos una lista de listas vacía para guardar los datos de la matriz traspuesta.
        for (int f = 0; f<nf(); f++) { // Iteramos sobre las filas de la matriz original.
            List<E> nuevaFila = new ArrayList<>(); // Creamos una nueva lista vacía para almacenar los elementos de la fila traspuesta.
            for (int c = 0; c<nc(); c++) { // Iteramos sobre las columnas de la matriz original.
                nuevaFila.add(get(c, f)); // Añadimos como nueva fila la anterior columna.
            }
            matriz_traspuesta.add(nuevaFila); // Agregamos la nueva fila a la lista de datos de la matriz traspuesta.
        }
        return new Matriz2<>(matriz_traspuesta); // Devolvemos la matriz traspuesta obtenida, en base a la clase Matriz2.
    }
	
        
    //#===========================================================================
    //# REPRESENTACION COMO CADENA
    //#===========================================================================
	
	@Override
    public String toString() {
        String representacion_matriz = "\n"; // Provocamos en la matriz resultante un salto de línea.
        for (List<E> f: datos) { // Para cada fila en la matriz...
            List<String> ls = new ArrayList<>(); // ...creamos una nueva lista para guardar o almacenar los elementos de la fila.
            for (E elemento: f) { // Para cada elemento en la fila...
                ls.add(elemento.toString()); // ...llamamos al método toString del elemento y lo agregamos a la lista.
            }
            representacion_matriz += String.join("  ", ls) + "\n"; // unimos la lista de cadenas con un espacio y, posteriormente, provocamos un salto de línea.
        }
        return representacion_matriz;
    }
        		

    //#===========================================================================
    //# CRITERIO DE IGUALDAD
    //#===========================================================================
	
	@Override
    public int hashCode() {
        return super.hashCode(); // Llamamos al método hashCode de la superclase MatrizA.
    }
    
    @Override
	public boolean equals(Object nuevo_objeto) {
    	return super.equals(nuevo_objeto); // Llamamos al método equals de la superclase MatrizA, pasando como argumento el nuevo objeto a comparar.
    }

}