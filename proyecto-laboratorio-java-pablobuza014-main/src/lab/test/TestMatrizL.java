package lab.test;

import java.util.List;
import lab.tipos.MatrizL1;
import lab.tipos.MatrizL2;
import us.lsi.tools.Preconditions;
import lab.tipos.Matriz1;
import lab.tipos.Matriz2;


public class TestMatrizL {

	public static void main(String[] args) {
		
		// Para probar MatrizL1 o MatrizL2, sustituir el número 'X' existente en MatrizLX y/o MatrizX1 por 1 o 2.

		MatrizL2 matriz_long = MatrizL2.as(Matriz2.ofFile("ficheros/matriz_long.txt", " ", x -> Long.parseLong(x.toString())));
		MatrizL2 matriz_orden_2 = MatrizL2.as(Matriz2.ofFile("ficheros/matriz_orden_2.txt", " ", x -> Long.parseLong(x.toString())));

        System.out.println("---------------------TestMatrizL---------------------\n");
        System.out.printf(String.format("La matriz leida desde el fichero matriz_long.txt es:\n----------------\n%s\n----------------\n", matriz_long.toString()));
        System.out.printf("La suma entre m y ella misma es:\n----------------\n%s\n----------------\n", matriz_long.add(matriz_long).toString());
        System.out.printf("La diferencia entre m y ella misma es:\n----------------\n%s\n----------------\n", matriz_long.subtract(matriz_long).toString());
        System.out.printf("La multiplicacion entre m y ella misma es:\n----------------\n%s\n----------------\n", matriz_long.multiply(matriz_long).toString());
        System.out.printf("La negada de m es:\n----------------\n%s\n----------------\n", matriz_long.negate().toString());
        System.out.printf(String.format("\nEs antisimetrica? --> %b \n", matriz_long.esAntisimetrica()));
        System.out.printf("\nm elevado al cubo es:\n----------------\n%s\n----------------\n", matriz_long.pow(3).toString());
        System.out.println("---------------------Fin del test---------------------\n");

        
        
        //#===========================================================================
        //# DEFENSA
        //#===========================================================================
        
        
        // PRUEBAS CORRECTAS
        
        System.out.println("---------------------TestMatrizL DEFENSA---------------------\n");
        System.out.printf("Defensa --> TRAZA: %d\n", matriz_long.traza());
        System.out.printf("Defensa --> MATRIZ ZERO (prueba correcta, nf = 3):\n----------------\n%s\n----------------\n", MatrizL2.zero(3).toString());
        System.out.printf("Defensa --> MATRIZ DIAGONAL (prueba correcta, nf = 3 y nd = 3):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(3,3).toString());
        System.out.printf("Defensa --> MATRIZ DIAGONAL (prueba correcta, nf = 4 y nd = 5):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(4,5).toString());
        System.out.printf("Defensa --> MATRIZ POTENCIA (prueba correcta, po = 7; IMPAR):\n----------------\n%s\n----------------\n", matriz_orden_2.potencia(7).toString());
        System.out.printf("Defensa --> MATRIZ POTENCIA (prueba correcta, po = 8; PAR):\n----------------\n%s\n----------------\n", matriz_orden_2.potencia(8).toString());
        
        // PRUEBAS DE FALLOS
        System.out.println("\n---------------------PRUEBA DE FALLOS---------------------\n");
        
        // MATRIZ ZERO
        try {
        	System.out.printf("Defensa --> MATRIZ ZERO (nf = 1):\n----------------\n%s\n----------------\n", MatrizL2.zero(1).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas debe ser mayor a 1 y menor de 10, y usted ha puesto que nf=1 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ ZERO (nf = 10):\n----------------\n%s\n----------------\n", MatrizL2.zero(10).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas debe ser mayor a 1 y menor de 10, y usted ha puesto que nf=10 \n" + e);
        }
        
        // MATRIZ DIAGONAL        
        try {
        	System.out.printf("Defensa --> MATRIZ DIAGONAL (nf = 1):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(1,3).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas (nf) debe ser mayor a 1 y menor de 10, mientras que el numero de la diagonal (nd) debe ser mayor a 0 y menor de 10; usted ha puesto que nf = 1 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ DIAGONAL (nf = 10):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(10,3).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas (nf) debe ser mayor a 1 y menor de 10, mientras que el numero de la diagonal (nd) debe ser mayor a 0 y menor de 10; usted ha puesto que nf = 10 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ DIAGONAL (nd = 0):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(3,0).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas (nf) debe ser mayor a 1 y menor de 10, mientras que el numero de la diagonal (nd) debe ser mayor a 0 y menor de 10; usted ha puesto que nd = 0 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ DIAGONAL (nd = 10):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(3,10).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas (nf) debe ser mayor a 1 y menor de 10, mientras que el numero de la diagonal (nd) debe ser mayor a 0 y menor de 10; usted ha puesto que nd = 10 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ DIAGONAL (nf = 1, nd = 0):\n----------------\n%s\n----------------\n", MatrizL2.diagonal(1,0).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: el numero de filas (nf) debe ser mayor a 1 y menor de 10, mientras que el numero de la diagonal (nd) debe ser mayor a 0 y menor de 10; usted ha puesto que nf = 1 y que nd = 0 \n" + e);
        }
        
        // MATRIZ POTENCIA
        try {
        	System.out.printf("Defensa --> MATRIZ POTENCIA (po = 10):\n----------------\n%s\n----------------\n", matriz_orden_2.potencia(10).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: 'po' debe ser menor a 10 y mayor a 4, y usted ha puesto que po=10 \n" + e);
        }
        
        try {
        	System.out.printf("Defensa --> MATRIZ POTENCIA (po = 4):\n----------------\n%s\n----------------\n", matriz_orden_2.potencia(4).toString());
        } catch(Exception e) {
        	System.out.println("Excepcion capturada: 'po' debe ser menor a 10 y mayor a 4, y usted ha puesto que po=4 \n" + e);
        }
        
        System.out.println("\n---------------------Fin del test de la DEFENSA---------------------\n");
    }
}