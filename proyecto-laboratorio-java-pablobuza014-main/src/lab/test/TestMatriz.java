package lab.test;

import lab.tipos.Matriz1;
import lab.tipos.Matriz2;

public class TestMatriz {
	
    public static void main(String[] args) {
    	
    	// Para probar Matriz1 o Matriz2, sustituir el número 'X' existente en MatrizX por 1 o 2.
    	
    	Matriz1<String> matriz_caracteres = Matriz1.ofFile("ficheros/matriz_caracteres.txt", " ", x -> x);

        System.out.println("---------------------TestMatriz---------------------\n");
        System.out.println(String.format("La matriz leida desde el fichero matriz_caracteres.txt es:\n----------------\n%s\n----------------\n", matriz_caracteres));
        System.out.println(String.format("La traspuesta de la matriz anterior es:\n----------------\n%s\n----------------\n", matriz_caracteres.traspuesta()));
        System.out.println(String.format("Es una matriz simetrica? --> %b \n", matriz_caracteres.esSimetrica()));
        System.out.println("---------------------Fin del test---------------------\n");
        
        //System.out.println("---------------------Otras pruebas fuera de entrega---------------------\n");
        //System.out.printf("El numero de filas de la matriz es %d y el numero de columnas es %d\n", matriz_caracteres.nf(), matriz_caracteres.nc());
        //System.out.println("El elemento de la matriz que tiene por posicion (0,0) es: -->  " + matriz_caracteres.get(0, 0) + "  <--\n");
        //matriz_caracteres.change(0, 0, "$");
        //System.out.println(String.format("Cambiamos el valor situado en la posición (0,0) de la matriz original por el simbolo $:\n----------------\n%s\n----------------\n", matriz_caracteres));
    
    }
}
