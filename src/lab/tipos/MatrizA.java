package lab.tipos;

import java.util.ArrayList;
import java.util.List;
import us.lsi.tools.Preconditions;
import java.util.Objects;

public abstract class MatrizA<E> implements Matriz<E> {

	protected List<List<E>> datos;

	
//  #===========================================================================
//  # CONSTRUCTOR
//  #===========================================================================

	
	protected MatrizA(List<List<E>> datos) {
		this.datos = datos; // datos o elementos de la matriz.
        boolean filas_mismo_tamaño = true; // declaramos, en un primer momento, que las filas sí tienen el mismo tamaño.
        int elementos_columna = datos.get(0).size(); // obtenemos el número de columnas.
        for (List<E> f : datos) {
            if (f.size() != elementos_columna) { // si el número de columnas es distinto al número de elementos que hay en cada fila, devolvemos 'False'.
            	filas_mismo_tamaño = false;
            }
        }
        Preconditions.checkArgument(filas_mismo_tamaño, "Todas las filas tienen que tener el mismo tamaño");
        Preconditions.checkArgument(nf() > 0 && nc() > 0, "El número de filas y el número de columnas debe ser mayor que 0");
    }
	
	

//  #===========================================================================
//  # PROPIEDADES
//  #===========================================================================
	
	@Override
    public Integer nf() { // indica el número de filas
        return datos.size();
    }

	
    @Override
    public Integer nc() { // indica el número de columnas
        return datos.get(0).size();
    }
    

    @Override
    public E get(Integer f, Integer c) {
        Preconditions.checkArgument(f>=0 && f<nf() && c>=0 && c<nc(), "Para obtener el elemento que se encuentre en una fila y columna determinadas, "
                + "el número de fila y columna a localizar deberá ser mayor o igual que 0 y menor que el número total de filas y columnas existentes");
        return datos.get(f).get(c);
    }
    

    @Override
    public void change(Integer f, Integer c, E n) {
        Preconditions.checkArgument(f>=0 && f<nf() && c>=0 && c<nc(), "Para cambiar el elemento que se encuentre en una fila y columna determinadas, "
                + "el número de fila y columna a localizar deberá ser mayor o igual que 0 y menor que el número total de filas y columnas existentes");
        datos.get(f).set(c, n);
    }
    
    
    @Override
    public Boolean esSimetrica() {
        boolean resultado = false; // devolvemos, en un primer momento, que la matriz NO es simétrica.
        if (this.nf() == this.nc()) { // si la matriz es cuadrada...
            if (this.equals(this.traspuesta())) { // ...y coincide con su traspuesta...
                resultado = true; // ...devolvemos que la matriz sí es simétrica.
            }
        }
        return resultado;
    }
    

    @Override
    public abstract Matriz<E> traspuesta(); // Definimos un método abstracto llamado "traspuesta" que será implementado por las clases hijas o subclases.


    
//  #===========================================================================
//  # CRITERIO DE IGUALDAD
//  #===========================================================================
	
    
	@Override
	public int hashCode() {
		return Objects.hash(datos);
	}

	
	@Override
	public boolean equals(Object nuevo_objeto) {
		
		if (nuevo_objeto == this) { // Comprobamos si el objeto a comparar es igual al objeto actual, esto es, es el mismo.
			return true;
		}
		if (!(nuevo_objeto instanceof MatrizA)) { // Comprobamos si el nuevo objeto es una instancia de la clase MatrizA. Si no lo es, devolvemos 'False'.
			return false;
        }
		
        Matriz<?> other = (Matriz<?>) nuevo_objeto; // Convertimos el nuevo objeto que sea en un tipo de dato de la interfaz Matriz para poder compararlo con la matriz actual.
        // En definitiva, se está llevando a cabo un casting a la interfaz Matriz y almacenando el nuevo_objeto en la variable "other", para poder compararlo así con la matriz actual que implementa la interfaz Matriz.
        if (this.nf() != other.nf() || this.nc() != other.nc()) { // Comprobamos si el número de filas y de columnas de ambas matrices son iguales.
        	return false; // Si no son iguales, devolvemos 'False'.
        }
        for (int f=0; f<this.nf(); f++) { // Recorremos las filas de la matriz actual.
        	for (int c=0; c<this.nc(); c++) { // Recorremos las columnas de la matriz actual.
        		if (!Objects.equals(this.get(f,c), other.get(f,c))) { // Si los elementos situados en cada posición (fila,columna) de ambas matrices no coinciden...
        			return false; // ...devolvemos 'False'.
        		}
        	}
        }
        return true; // En caso contrario, devolvemos 'True' (si todos los elementos (fila, columna) coinciden).
	}

}