package lab.centro;

public record Asignatura(Integer id, String nombre, Integer creditos,Integer numGrupos) {
	
	
	/*
	 * MÉTODO DE FACTORÍA
	 */
	
	
    public static Asignatura parse(String text) {
        String[] partes = text.split(",");
        return new Asignatura(Integer.parseInt(partes[0]), partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
    }
    
    
    /*
     * REPRESENTACIÓN COMO CADENA
     */
    
    
    public String toString() {
    	return String.format("%s, %d, %d",this.nombre(),this.creditos(),this.numGrupos());
    	}
    
    
    /*
     * TEST
     */
    
    
    public static void main(String[] args) {
        Asignatura asignatura1 = Asignatura.parse("13,Sistemas operativos,6,2");
        System.out.println("---------------------Asignatura.java---------------------");
        System.out.println("Impresion con el metodo de factoria 'parse': " + asignatura1);
        System.out.println("ID: " + asignatura1.id());
        System.out.println("NOMBRE: " + asignatura1.nombre());
        System.out.println("CREDITOS: " + asignatura1.creditos());
        System.out.println("NUMERO DE GRUPOS: " + asignatura1.numGrupos());
        System.out.println("---------------------Fin del testeo---------------------");
    }
    
}