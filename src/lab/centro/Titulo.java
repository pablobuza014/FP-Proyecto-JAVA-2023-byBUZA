package lab.centro;

public enum Titulo {
    Diplomado, Master, Doctor;

	
	/*
	 * TEST
	 */
	
	
    public static void main(String[] args) {
    	System.out.println("---------------------Titulo.java---------------------");
        for (Titulo titulo: Titulo.values()) {
            System.out.println(titulo.name());
        }
        System.out.println("---------------------Fin del testeo---------------------");
    }
}
