package lab.centro;

public record Grupo(Integer ida, Integer idg) {
	
	    
		/*
		 * MÉTODOS DE FACTORÍA
		 */
	
	
	    public static Grupo of(Integer ida,Integer idg) {
	    	return new Grupo(ida,idg);
	    }

	    
	    /*
	     * REPRESENTACIÓN COMO CADENA
	     */
	    
	    public String toString() {
	        return String.format("Asignatura = %s, Grupo = %d", Centro.of().asignaturaId(ida).nombre(), idg);
	    }
	    
	    
	    /*
	     * TEST
	     */
	    
	    
	    public static void main(String[] args) {
	        Grupo grupo1 = Grupo.of(4, 2);
	        System.out.println("---------------------Grupo.java---------------------");
	        System.out.println("Impresion usando el metodo de factoria 'of': " + grupo1);
	        System.out.println("---------------------Fin del testeo---------------------");
	    }

}