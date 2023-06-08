package lab.tipos;

import java.util.List;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.tools.IntPair;
import us.lsi.tools.Preconditions;

public class MatrizL1 extends Matriz1<Long> implements MatrizL {
	
	
//  #===========================================================================
//  # CONSTRUCTOR
//  #===========================================================================

	private MatrizL1(List<List<Long>> datos) {
        super(datos);
    }
	
//  #===========================================================================
//  # METODOS DE FACTORIA
//  #===========================================================================
	
	public static MatrizL1 as(MatrizA<Long> m) {
        return new MatrizL1(m.datos);
	}

    public static MatrizL1 ofI(List<List<Long>> datos) {
        return new MatrizL1(datos);
    }
	
//  #===========================================================================
//  # PROPIEDADES DERIVADAS
//  #===========================================================================

    @Override
    public Boolean esAntisimetrica() {
        return (this.nf() == this.nc()) && IntStream.range(0, this.nf())
        	// El método 'allMatch' obtiene 'True' si todos los elementos del stream cumplen el predicado.
            .allMatch(f -> IntStream.range(0, f)
                .allMatch(c -> (get(f, c) == -get(c, f)) && (f != c))); 
        		// ACLARACIÓN: no es necesario comparar los elementos de la diagonal principal, ya que éstos siempre son iguales a sus opuestos.
    }


//  #===========================================================================
//  # OTROS METODOS
//  #===========================================================================
    
	
	// SUMA
    @Override
    public MatrizL add(MatrizL m) {
        Preconditions.checkArgument(nf() == m.nf() && nc() == m.nc(), "El número de filas y de columnas de ambas matrices deben ser iguales");
        List<List<Long>> matriz_suma = IntStream.range(0, nf())
                .mapToObj(f -> IntStream.range(0, nc())
                        .mapToLong(c -> get(f, c) + m.get(f, c))
                        .boxed() // este método convierte un stream de tipos primitivos en un stream de objetos. 
                        // Es decir, convierte el LongStream resultante de la suma de matrices en un Stream<Long> que se usará para crear la matriz final.
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return MatrizL1.ofI(matriz_suma);
    }
    

    // RESTA
    @Override
    public MatrizL subtract(MatrizL m) {
        Preconditions.checkArgument(nf() == m.nf() && nc() == m.nc(), "El número de filas y de columnas de ambas matrices deben ser iguales");
        List<List<Long>> matriz_diferencia = IntStream.range(0, nf())
                .mapToObj(f -> IntStream.range(0, nc())
                		.mapToLong(c -> get(f, c) - m.get(f, c))
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return MatrizL1.ofI(matriz_diferencia);
    }


    // MULTIPLICACIÓN
    @Override
    public MatrizL multiply(MatrizL m) {
        Preconditions.checkArgument(nc() == m.nf(), "El número de columnas de la matriz multiplicada debe coincidir con el número de filas de la matriz a multiplicar");
        List<List<Long>> matriz_multiplicacion = IntStream.range(0, nf())
                .mapToObj(f -> IntStream.range(0, m.nc())
                		// Esta expresión lambda, calcula el valor de cada elemento de la matriz resultante de la multiplicación de las dos matrices, 
                		// utilizando la fórmula de multiplicación de matrices, y utilizando la función sum() para obtener la suma de los productos 
                		// de cada fila y columna correspondiente.
                        .mapToLong(c -> IntStream.range(0, nc()).mapToLong(i -> get(f, i) * m.get(i, c)).sum())
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return MatrizL1.ofI(matriz_multiplicacion);
    }

    
    // NEGACIÓN
	@Override
	public MatrizL negate() {
	    List<List<Long>> matriz_negada = IntStream.range(0, this.nf())
	            .mapToObj(f -> IntStream.range(0, this.nc())
	                    .mapToLong(c -> -this.get(f, c))
	                    .boxed()
	                    .collect(Collectors.toList()))
	            .collect(Collectors.toList());
	    return MatrizL1.ofI(matriz_negada);
	}
	
		
	// POTENCIA (con método estático auxiliar)
	public static MatrizL identity(Integer n) {
	    List<List<Long>> matriz_identidad = IntStream.range(0, n)
	            .mapToObj(f -> IntStream.range(0, n)
	                    .mapToLong(c -> (f == c ? 1 : 0)) // Si f y c son iguales (si la fila actual es igual a la columna actual), 
	                    // la expresión (f == c ? 1 : 0) devuelve 1. En caso contrario, devuelve 0.
	                    .boxed()
	                    .collect(Collectors.toList()))
	            .collect(Collectors.toList());
	    return MatrizL1.ofI(matriz_identidad);
	}

	
    @Override
    public MatrizL pow(Integer n) {
        Preconditions.checkArgument(n>=0, "El exponente debe ser mayor o igual a 0");
        if (n == 0) { // Si el exponente es igual a 0...
            return MatrizL1.identity(nf()); // ..., la matriz potencia es equivalente a la identidad de orden número de filas (o columnas).
        } else if (n == 1) { // Si el exponente es igual a 1...
            return this; // ..., la matriz potencia es equivalente a la matriz actual / original.
        } else { // Si el exponente es mayor o igual que 2...
        	// "BiFunction" toma dos objetos de tipo MatrizL y devuelve otro objeto de tipo MatrizL.
        	// Le pasamos como argumento el método "multiply" de la interfaz.
            BiFunction<MatrizL, MatrizL, MatrizL> multiplicar = MatrizL::multiply;
            MatrizL entre_dos = this.pow(n / 2); // ... potenciamos dividiendo el exponente por 2. (La división de 'n' entre 2 es más eficiente que restar a 'n' una unidad).
            entre_dos = multiplicar.apply(entre_dos, entre_dos); // Multiplicamos la matriz anterior consigo misma.
            if (n % 2 == 1) { // Si el exponente es impar..
                entre_dos = multiplicar.apply(entre_dos, this); // Multiplicamos la matriz anterior por la matriz original 
                // Hacemos esto pues, si el exponente es impar, el resto de la división por 2 es igual a 1; luego, será necesario multiplicar 
                // una vez más por la matriz original para obtener la solución correcta.
            }
            return entre_dos;
        }
    }
    
}