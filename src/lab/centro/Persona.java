package lab.centro;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lab.centro.Persona;

public class Persona {
	
	    private String apellidos;
	    private String nombre;
	    private String dni;
	    private LocalDateTime fechaDeNacimiento;
	    private String telefono;
	    private Direccion direccion;
	    
	    protected Persona(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono,
				Direccion direccion) {
			super();
			this.apellidos = apellidos;
			this.nombre = nombre;
			this.dni = dni;
			this.fechaDeNacimiento = fechaDeNacimiento;
			this.telefono = telefono;
			this.direccion = direccion;
		}
	    
	    
	    /*
	     * PROPIEDADES DERIVADAS
	     */
	    
	    
	    public String getApellidos() {
			return this.apellidos;
		}

		public String getNombre() {
			return this.nombre;
		}

		public String getDni() {
			return dni;
		}

		public LocalDateTime getFechaDeNacimiento() {
			return this.fechaDeNacimiento;
		}

		public String getTelefono() {
			return this.telefono;
		}

		public Direccion getDireccion() {
			return this.direccion;
		}

		public Integer getEdad() {
	        LocalDateTime nw = LocalDateTime.now();
	        return Period.between(this.fechaDeNacimiento.toLocalDate(),nw.toLocalDate()).getYears();
	    }

		public LocalDate siguienteCumple() {
		    LocalDate hoy = LocalDate.now();
		    LocalDate fecha_nacimiento = fechaDeNacimiento.toLocalDate();
		    LocalDate siguienteCumple;
		    // Verificamos si la fecha de nacimiento es posterior a la fecha actual o no. Para ello, comprobamos mes y/o día en el que estamos.
		    if (fecha_nacimiento.getMonthValue() > hoy.getMonthValue() || (fecha_nacimiento.getMonthValue() == hoy.getMonthValue() && fecha_nacimiento.getDayOfMonth() >= hoy.getDayOfMonth())) {
		        siguienteCumple = fecha_nacimiento.withYear(hoy.getYear());
		    } else {
		        siguienteCumple = fecha_nacimiento.withYear(hoy.getYear() + 1);
		    }
		    return siguienteCumple;
		}

		public DayOfWeek diaSemanaSiguienteCumple() {
	        LocalDate proximo_cumple = siguienteCumple();
	        DayOfWeek dia_semana_proximo_cumple = proximo_cumple.getDayOfWeek();
	        return dia_semana_proximo_cumple;
	    }
		
		public DayOfWeek diaSemanaNacimiento() {
	        LocalDate fecha_nacimiento = fechaDeNacimiento.toLocalDate();
	        DayOfWeek dia_semana = fecha_nacimiento.getDayOfWeek();
	        return dia_semana;
	    }
		
		public Integer mesCumple() {
	        LocalDate proximo_cumple = siguienteCumple();
	        Integer mes_cumple = proximo_cumple.getMonthValue();
	        return mes_cumple;
	    }
		
		public String nombreCompleto() {
		    return this.nombre + " " + this.apellidos;
		}

		
		/*
		 * MÉTODOS DE FACTORÍA
		 */
		
		
		public static Persona of(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono, Direccion direccion) {
			return new Persona(apellidos, nombre, dni, fechaDeNacimiento, telefono, direccion);
	    }
		
	    public static Persona parse(String text, DateTimeFormatter ft) {
	    	String[] partes = text.split(",");
	    	Direccion direccion = Direccion.parse(partes[5].trim());
	    	return new Persona(partes[0],partes[1],partes[2],LocalDateTime.parse(partes[3],ft),partes[4],direccion);
	    }
	    
	    public static Persona parse(String text) {
	        DateTimeFormatter formato_fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        return parse(text, formato_fecha);
	    }
	    
	    public static Persona parseList(List<String> partes, DateTimeFormatter ft) {
	    	Direccion direccion = Direccion.parse(partes.get(5).strip());
	    	return new Persona(partes.get(0), partes.get(1), partes.get(2), LocalDateTime.parse(partes.get(3), ft), partes.get(4), direccion);
	    }
	    
	    public static Persona parseList(List<String> partes) {
	    	DateTimeFormatter formato_fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        return parseList(partes, formato_fecha);
	    }
	    
	    
	    /*
	     * COMPROBACIÓN DNI
	     */
	    
	    
	    private static Boolean checkDni(String text) {
	        List<Character> ls = Arrays.asList('T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');
	        String pn = text.substring(0,text.length()-1);
	        Character lt = text.charAt(text.length()-1);
	        Integer n = Integer.parseInt(pn) % 23;
	        return ls.get(n) == lt;
	    }
	    
	    
	    /*
	     * REPRESENTACIÓN COMO CADENA
	     */


		public String toString() {
			String fs = this.getFechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String hs = this.getFechaDeNacimiento().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
			return String.format("%s, de %d, nacido el %s a las %s", this.nombreCompleto(), 
					this.getEdad(), fs, hs);
		}
		
		
		
		/*
		 * TEST
		 */
		
		
		public static void main(String[] args) {
			Persona p1 = Persona.parse("Casares Amador,Ramiro,00895902Y,2003-06-14 10:02,+34721510926,Ronda de Samanta Cobos 392;Málaga;29316");	
	        System.out.println("---------------------Persona.java---------------------");
	        System.out.println(p1);
	        System.out.printf("La fecha de nacimiento de %s es %s\n", p1.getNombre(), p1.getFechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        System.out.printf("La edad de %s es %d\n", p1.getNombre(), p1.getEdad());
	        System.out.printf("Su proximo cumpleanos sera un %s\n", p1.diaSemanaSiguienteCumple());
	        System.out.println("---------------------Fin del testeo---------------------");

	        /*
	        System.out.println("\n \n \n");
	        Persona p2 = Persona.parseList(Arrays.asList("Casares Amador", "Ramiro", "00895902Y", "2003-06-14 10:02", "+34721510926", "Ronda de Samanta Cobos 392;Málaga;29316"));
	        LocalDateTime datetime_of = LocalDateTime.parse("2003-06-14 10:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        Direccion direccion_of = Direccion.parse("Ronda de Samanta Cobos 392;Málaga;29316");
	        Persona p3 = Persona.of("Casares Amador", "Ramiro", "00895902Y", datetime_of, "+34721510926", direccion_of);
	        System.out.println("---------------------TESTEO GLOBAL---------------------");
	        System.out.println(String.format("Impresion usando 'parse': %s", p1));
			System.out.println(String.format("Impresion usando 'parse_list': %s", p2));
			System.out.println(String.format("Impresion usando 'of': %s", p3));
			System.out.println(String.format("APELLIDOS: %s", p1.getApellidos()));
			System.out.println(String.format("NOMBRE: %s", p1.getNombre()));
			System.out.println(String.format("DNI: %s", p1.getDni()));
			System.out.println(String.format("FECHA Y HORA DE NACIMIENTO: %s", p1.getFechaDeNacimiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
			System.out.println(String.format("TELEFONO: %s", p1.getTelefono()));
			System.out.println(String.format("DIRECCION: %s", p1.getDireccion()));
			System.out.println(String.format("EDAD: %d", p1.getEdad()));
			System.out.println(String.format("PROXIMO CUMPLEANOS: %s", p1.siguienteCumple()));
			System.out.println(String.format("DIA DE LA SEMANA DEL PROXIMO CUMPLEANOS: %s", p1.diaSemanaSiguienteCumple()));
			System.out.println(String.format("DIA DE LA SEMANA DE NACIMIENTO: %s", p1.diaSemanaNacimiento()));
			System.out.println(String.format("MES DE NACIMIENTO: %s", p1.mesCumple()));
			System.out.println(String.format("NOMBRE COMPLETO: %s", p1.nombreCompleto()));
			System.out.println("---------------------Fin del testeo global---------------------");
	        */
	        
	}

}