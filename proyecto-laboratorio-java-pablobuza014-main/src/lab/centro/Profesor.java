package lab.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lab.centro.Titulo;

public class Profesor extends Persona {
	
	private Titulo titulo;

    private Profesor(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono, Direccion direccion, Titulo titulo) {
		super(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
		this.titulo = titulo;
	}
	
    public Titulo getTitulo() {
		return this.titulo;
	}
    
    
    /*
     * MÉTODOS DE FACTORÍA
     */

    
	public static Profesor of(Persona p, Titulo titulo) {
		return new Profesor(p.getApellidos(), p.getNombre(), p.getDni(), p.getFechaDeNacimiento(), p.getTelefono(), p.getDireccion(), titulo);
	}
	
	public static Profesor parse(String text) {
		List<String> partes = Arrays.asList(text.split(","));
		Persona persona = Persona.parseList(partes.subList(0,6));
		Titulo titulo = Titulo.valueOf(partes.get(6));
		return Profesor.of(persona, titulo);
	}
	
	
	/*
	 * REPRESENTACIÓN COMO CADENA
	 */
    
	
    public String toString() {
        return super.toString() + String.format(" con titulo %s", this.getTitulo().toString());
    } 
    
    
    /*
     * TEST
     */
    
    
    public static void main(String[] args) {
        Persona p_profesor = Persona.parse("Rivero Figuerola,Camilo,63435022X,2000-01-25 06:55,+34732834867,Calle Noa Monreal 850;Salamanca;19602");
        Profesor profesor1 = Profesor.of(p_profesor, Titulo.Doctor);
        Profesor profesor2 = Profesor.parse("Rivero Figuerola,Camilo,63435022X,2000-01-25 06:55,+34732834867,Calle Noa Monreal 850;Salamanca;19602,Doctor");
        System.out.println("---------------------Profesor.java---------------------");
        System.out.println("Impresion usando el metodo de factoria 'of': " + profesor1);
        System.out.println("Impresion usando el metodo de factoria 'parse': " + profesor2);
        System.out.println("TITULO: " + profesor2.getTitulo());
        System.out.println("---------------------Fin del testeo---------------------");
    }

    
}