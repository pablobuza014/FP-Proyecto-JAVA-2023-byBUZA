package lab.centro;

public record Direccion(String calle, String ciudad, Integer zip_code) {
	
	
	/*
	 * MÉTODO DE FACTORÍA
	 */
	
	
	public static Direccion parse(String text) {
        String[] partes = text.split(";");
        return new Direccion(partes[0],partes[1],Integer.parseInt(partes[2]));
	}
	
	
	/*
	 * TEST
	 */
	
	
	public static void main(String[] args) {
		Direccion direccion1 = Direccion.parse("Avenida de la Reina Mercedes;Sevilla;41012");
		System.out.println("---------------------Direccion.java---------------------");
		System.out.println("Impresion con el metodo de factoria 'parse': " + direccion1);
		System.out.println("CALLE: " + direccion1.calle());
		System.out.println("CIUDAD: " + direccion1.ciudad());
		System.out.println("CODIGO POSTAL: " + direccion1.zip_code());
		System.out.println("---------------------Fin del testeo---------------------");
		}

}
