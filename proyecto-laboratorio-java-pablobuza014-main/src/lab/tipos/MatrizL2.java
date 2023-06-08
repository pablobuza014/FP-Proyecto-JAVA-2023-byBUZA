package lab.tipos;

import java.util.ArrayList;

import java.util.List;
import us.lsi.tools.Preconditions;

public class MatrizL2 extends Matriz2<Long> implements MatrizL {
	
	
//  #===========================================================================
//  # CONSTRUCTOR
//  #===========================================================================
	
	private MatrizL2(List<List<Long>> datos) {
        super(datos);
    }
	
//  #===========================================================================
//  # METODOS DE FACTORIA
//  #===========================================================================
    
    public static MatrizL2 as(Matriz2<Long> m) { // Construye un objeto con los datos que contiene la matriz m.
    	return new MatrizL2(m.datos);
	}

	public static MatrizL2 ofI(List<List<Long>> datos) { // Construye un objeto a partir de una lista de elementos de tipo Long.
		return new MatrizL2(datos);
	}

//  #===========================================================================
//  # PROPIEDADES DERIVADAS
//  #===========================================================================
	
	@Override
	public Boolean esAntisimetrica() {
        List<List<Long>> matriz_negada = new ArrayList<>(); // Creamos una lista de listas vacía que albergará la matriz negada.
        boolean resultado = false; // Inicializamos la variable 'resultado' con el valor booleano 'False'.
        if (this.nf() == this.nc()) { // Si la matriz es cuadrada...
        	// Recorremos la matriz original y creamos la matriz 'matriz_negada' con los elementos negados de la matriz original. 
        	// Para cada fila de la matriz original, recorremos cada columna y agregamos el valor negado correspondiente a la lista 'fila_negada'. 
        	// Posteriormente, agregamos esta lista a la matriz 'matriz_negada'.
            for (int f = 0; f<this.nf(); f++) {
                List<Long> fila_negada = new ArrayList<>();
                for (int c = 0; c<this.nc(); c++) {
                    fila_negada.add(-this.get(f, c));
                }
                matriz_negada.add(fila_negada);
            }
        }
        // Verificamos si la traspuesta de la matriz original es igual a la matriz 'matriz_negada'. En ese caso, establecemos el resultado como 'True'.
        if (this.traspuesta().equals(MatrizL2.ofI(matriz_negada))) {
            resultado = true;
        }
        return resultado; // Devolvemos el resultado booleano.
    }
	
	
//  #===========================================================================
//  # OTROS METODOS
//  #===========================================================================
	
	@Override
	public MatrizL add(MatrizL m) {
		Preconditions.checkArgument(this.nf() == m.nf() && this.nc() == m.nc(), "El número de filas y de columnas de ambas matrices deben ser iguales");
		List<List<Long>> matriz_suma = new ArrayList<>(); // Creamos una lista de listas vacía que albergará o resultará la matriz suma.
		for (int f = 0; f<this.nf(); f++) { // Recorremos las filas de la matriz sumada.
			List<Long> fila = new ArrayList<>(); // Creamos una lista vacía llamada 'fila'.
			for (int c = 0; c<this.nc(); c++) { // Recorremos las columnas de la matriz sumada.
				fila.add(this.get(f, c) + m.get(f,c)); // Sumamos los elementos de ambas matrices y los añadimos a la lista 'fila'.
			}
			matriz_suma.add(fila); // Añadimos cada lista 'fila' a la matriz_suma.
		}
		return MatrizL2.ofI(matriz_suma); // Devolvemos la matriz creada.
	}

	
	@Override
    public MatrizL subtract(MatrizL m) {
    	Preconditions.checkArgument(this.nf() == m.nf() && this.nc() == m.nc(), "El número de filas y de columnas de ambas matrices deben ser iguales");
        List<List<Long>> matriz_diferencia = new ArrayList<>(); // Creamos una lista de listas vacía que albergará o resultará la matriz diferencia.
        for (int f = 0; f<this.nf(); f++) { // Recorremos las filas de la matriz restada.
        	List<Long> fila = new ArrayList<>(); // Creamos una lista vacía llamada 'fila'.
            for (int c = 0; c<this.nc(); c++) { // Recorremos las columnas de la matriz restada.
            	fila.add(this.get(f,c) - m.get(f,c)); // Restamos los elementos de ambas matrices y los añadimos a la lista 'fila'.
            }
            matriz_diferencia.add(fila); // Añadimos cada lista 'fila' a la matriz_diferencia.
        }
        return MatrizL2.ofI(matriz_diferencia); // Devolvemos la matriz creada.
    }
    
    
    @Override
    public MatrizL multiply(MatrizL m) {
        Preconditions.checkArgument(this.nf() == m.nc(), "El número de columnas de la matriz multiplicada debe coincidir con el número de filas de la matriz a multiplicar");
        List<List<Long>> matriz_multiplicacion = new ArrayList<>(); // Creamos una lista de listas vacía que albergará o resultará la matriz multiplicación.
        for (int f = 0; f<this.nf(); f++) { // Recorremos las filas de la matriz multiplicada.
            List<Long> fila_a_sumar = new ArrayList<>(); // Creamos una lista vacía llamada 'fila_a_sumar', que almacenará la fila resultante de la multiplicación.
            for (int c = 0; c<m.nc(); c++) { // Recorremos las columnas de la matriz a multiplicar.
                long suma = 0; // Inicializamos la variable 'suma' con el valor 0, la cual almacenará el resultado de la suma de los productos 
                // de los elementos de la fila de la matriz multiplicada y los elementos de la columna de la matriz a multiplicar.
                for (int k = 0; k<m.nf(); k++) { // Recorremos las filas de la matriz a multiplicar.
                    suma += this.get(f, k) * m.get(k, c); // Realizamos la multiplicación correspondiente de los elementos de la fila de la matriz multiplicada y de
                    // los elementos de la columna de la matriz a multiplicar, y sumamos el resultado a la variable 'suma' creada con anterioridad.
                }
                fila_a_sumar.add(suma); // Añadimos el resultado de la suma a la lista 'fila_a_sumar'.
            }
            matriz_multiplicacion.add(fila_a_sumar); // Añadimos la fila resultante anterior a la matriz multiplicación.
        }
        return MatrizL2.ofI(matriz_multiplicacion); // Devolvemos la matriz creada.
    }
    
    
    @Override
    public MatrizL negate() {
        List<List<Long>> matriz_negada = new ArrayList<>(); // Creamos una lista de listas vacía que albergará la matriz negada.
        for (int f = 0; f<this.nf(); f++) { // Recorremos las filas de la matriz a negar.
            List<Long> fila_negada = new ArrayList<>(); // Creamos una lista vacía llamada "fila_negada".
            for (int c = 0; c<this.nc(); c++) { // Recorremos las columnas de la matriz a negar.
                fila_negada.add(-this.get(f,c)); // Agregamos el elemento posición (fila, columna) negado a la lista fila_negada.
            }
            matriz_negada.add(fila_negada); // Agregamos la fila anterior originada a la matriz negada.
        }
        return MatrizL2.ofI(matriz_negada); // Devolvemos la matriz creada.
    }
    
    
    
    public static MatrizL identity(Integer n) { // Matriz identidad de tamaño n.
        List<List<Long>> matriz_identidad = new ArrayList<>(); // Creamos una lista de listas vacía que albergará la matriz identidad.
        for (int f = 0; f<n; f++) { // Recorremos las filas de la matriz a tratar mientras f sea menor que n.
            List<Long> fila = new ArrayList<>(); // Creamos una lista vacía llamada "fila".
            for (int c = 0; c<n; c++) { // Iteramos sobre las columnas de la matriz a tratar mientras c sea menor que n.
                fila.add((long)(f == c ? 1 : 0)); // Si f es igual a c (si la fila actual es igual a la columna actual), se agrega un 1 a la lista 'fila' 
                // creada con anterioridad. En caso contrario, se agrega un 0 a la lista 'fila'.
            }
            matriz_identidad.add(fila); // Agregamos la fila anterior originada a la lista de listas de la matriz identidad.
        }
        return MatrizL2.ofI(matriz_identidad); // Devolvemos la matriz creada.
    }
    

    @Override
    public MatrizL pow(Integer n) {
    	Preconditions.checkArgument(n >= 0, "El exponente debe ser mayor o igual que 0");
    	MatrizL matriz_potencia = MatrizL2.identity(this.nf()); // Creamos una nueva matriz como la matriz identidad.
    	MatrizL potencia = this; // Creamos una nueva matriz 'potencia' como la matriz actual.
    	while (n > 0) { // Mientras el exponente sea mayor que 0...
    		if (n % 2 == 1) { // Si el exponente es impar...
    			matriz_potencia = matriz_potencia.multiply(potencia); // ...multiplicamos la matriz potencia por la matriz actual.
    		}
    		potencia = potencia.multiply(potencia); // Multiplicamos la matriz actual consigo misma tantas veces como indique el exponente.
    		n /= 2; // Dividimos el exponente por 2. (La división de 'n' entre 2 es más eficiente que restar a 'n' una unidad).
    	}
    	return matriz_potencia; // Devolvemos la matriz potencia.
    }
    
    
    // DEFENSA
    
    // EJERCICIO 1: Haz la matriz TRAZA.
    public int traza() {
        int traza = 0;
        for (int i=0; i<this.nf(); i++) {
            traza += datos.get(i).get(i);
        }
        return traza;
    }
    
    // EJERCICIO 2: Haz la matriz ZERO.
    public static MatrizL2 zero(int nf) {
        Preconditions.checkArgument(nf>1 && nf<10, "El número de filas (nf) debe ser mayor a 1 y menor de 10");
        List<List<Long>> matriz_cero = new ArrayList<>();
        for (int i=0; i<nf; i++) {
            List<Long> fila = new ArrayList<>();
            for (int j=0; j<nf; j++) {
                fila.add(0L);
            }
            matriz_cero.add(fila);
        }
        return MatrizL2.ofI(matriz_cero);
    }
    
    
    // EJERCICIO 3: Haz la matriz DIAGONAL.
    public static MatrizL2 diagonal(int nf, int nd) {
        Preconditions.checkArgument(nf>1 && nf<10, "El número de filas (nf) debe ser mayor a 1 y menor de 10");
        Preconditions.checkArgument(nd>0 && nd<10, "El número de la diagonal (nd) debe ser mayor a 0 y menor de 10");
        List<List<Long>> matriz_diagonal = new ArrayList<>();
        for (int i=0; i<nf; i++) {
            List<Long> fila = new ArrayList<>();
            for (int j=0; j<nf; j++) {
                fila.add(i==j ? (long) nd:0L); // Agregamos un valor a la fila de la matriz, en función de si el índice de la fila y de la columna coinciden o no. 
                // Si se da el primer caso, agregamos el valor especificado por 'nd'; si se da el segundo, ponemos un 0.
            }
            matriz_diagonal.add(fila);
        }
        return MatrizL2.ofI(matriz_diagonal);
    }

    

    // EJERCICIO 4: Haz la matriz POTENCIA (sin pow())
    public MatrizL2 potencia(int po) {
        Preconditions.checkArgument(po<10 && po>4, "'po' debe ser menor a 10 y mayor a 4");
        MatrizL2 matriz_potencia = MatrizL2.as(this);
        for (int i=1; i<po; i++) {
        	matriz_potencia = (MatrizL2) matriz_potencia.multiply(this);
        }
        return matriz_potencia;
    }
    
}