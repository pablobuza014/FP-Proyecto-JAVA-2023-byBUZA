package lab.centro;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import us.lsi.tools.Preconditions;

public class Alumno extends Persona {
    
	private Double nota;

    private Alumno(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono,
			Direccion direccion, Double nota) {
		super(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
		this.nota = nota;
	}
	
    public Double getNota() {
		return this.nota;
	}
    
    
    /*
     * MÉTODOS DE FACTORÍA
     */

	public static Alumno of(Persona p, Double nota) {
		Preconditions.checkArgument(0 <= nota && nota <= 14,
				String.format("La nota debe estar comprendida entre 0 y 14 y es %.2f", nota));
		return new Alumno(p.getApellidos(), p.getNombre(), p.getDni(), p.getFechaDeNacimiento(), p.getTelefono(),
				p.getDireccion(), nota);
	}
	
	public static Alumno parse(String text) {
		List<String> partes = Arrays.asList(text.split(","));
		Persona persona = Persona.parseList(partes.subList(0,6));
	    Double nota = Double.parseDouble(partes.get(6));
	    return Alumno.of(persona, nota);
	}
	
	
	/*
	 * REPRESENTACIÓN COMO CADENA
	 */
	    
	
    public String toString() {
        return super.toString() + String.format(" con nota de entrada %.2f",this.getNota());
    }   		
    
    
    /*
     * TEST
     */
    
    
    public static void main(String[] args) {
        Persona p_alumno = Persona.parse("Rincon Crespo,Jose Francisco,60675744L,2003-04-17 04:34,+34992384073,C. Anselma Pons 96 Puerta 7;Barcelona;06633");
        Alumno alumno1 = Alumno.of(p_alumno, 9.0);
        Alumno alumno2 = Alumno.parse("Rincon Crespo,Jose Francisco,60675744L,2003-04-17 04:34,+34992384073,C. Anselma Pons 96 Puerta 7;Barcelona;06633,9.0");
        System.out.println("---------------------Alumno.java---------------------");
        System.out.println("Impresion usando el metodo de factoria 'of': " + alumno1);
        System.out.println("Impresion usando el metodo de factoria 'parse': " + alumno2);
        System.out.println("NOTA: " + alumno2.getNota());
        System.out.println("---------------------Fin del testeo---------------------");
    }

}
