package lab.centro;

public record Matricula(String dni, Integer ida, Integer idg) {

	    
		/*
		 * MÉTODOS DE FACTORÍA
		 */
	
	
	    public static Matricula of(String dni,Integer ida,Integer idg) {
	        return new Matricula(dni,ida,idg);
		}
	    
	    public static Matricula parse(String text) {
	        String[] partes = text.split(",");
	        return Matricula.of(partes[0],Integer.parseInt(partes[1]),Integer.parseInt(partes[2]));
	    }
	    
	    
	    /*
	     * REPRESENTACIÓN COMO CADENA
	     */


	    public String toString() {
	        return String.format("%s,%s,%s",this.dni(),this.ida(),this.idg());
	    }
	    
	    
	    /*
	     * TEST
	     */
	    
	    
	    public static void main(String[] args) {
	        Matricula matricula1 = Matricula.of("72842943B", 0, 0);
	        Matricula matricula2 = Matricula.parse("72842943B,0,0");
	        System.out.println("---------------------Matricula.java---------------------");
	        System.out.println("Impresion usando el metodo de factoria 'of': " + matricula1);
	        System.out.println("Impresion usando el metodo de factoria 'parse': " + matricula2);
	        System.out.println("DNI: " + matricula2.dni());
	        System.out.println("ID DE LA ASIGNATURA: " + matricula2.ida());
	        System.out.println("ID DEL GRUPO: " + matricula2.idg());
	        System.out.println("---------------------Fin del testeo---------------------");
	    }

}