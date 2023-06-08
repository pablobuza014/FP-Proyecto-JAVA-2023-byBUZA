package lab.tipos;

import java.time.LocalDate;

public record Movie(String titulo, String director, TipoGenero genero, LocalDate fechaEstreno, int duracion) {

	
	// ##################################
	// PROPIEDADES
	// ##################################

	
    public enum TipoGenero {
        Action, Animation, Biopic, Cartoon, Comedy, Documentary, Drama, Educational
    }

    public enum TipoMetraje {
        SHORT, MID_LENGTH, FEATURE
    }

    
    public TipoMetraje tipoMetraje() {
        if (duracion < 30) {
            return TipoMetraje.SHORT;
        } else if (duracion >= 30 && duracion <= 75) {
            return TipoMetraje.MID_LENGTH;
        } else {
            return TipoMetraje.FEATURE;
        }
    }
    
    
	// ##################################
	// MÉTODOS DE FACTORÍA
	// ##################################
    
    public static Movie of(String titulo, String director, TipoGenero genero, LocalDate fechaEstreno, int duracion) {
        if (titulo == null || director == null) {
            throw new IllegalArgumentException("El título y el director deben estar informados, esto es, no pueden ser nulos");
        }
        return new Movie(titulo, director, genero, fechaEstreno, duracion);
    }
    

    public static Movie parse(String text) {
        String[] partes = text.split(";");
        if (partes.length < 2) {
            throw new IllegalArgumentException("La cadena debe tener al menos dos partes separadas por ';', que son el 'tituloMovie' y el 'directorMovie'");
        }
        String titulo = partes[0].trim();
        String director = partes[1].trim();
        TipoGenero genero = TipoGenero.Comedy; // Voy a establecer como valor valor por defecto 'Comedy'.
        LocalDate fechaEstreno = LocalDate.now(); // Voy a establecer como valor por defecto la hora actual a la que se genere.
        int duracion = 90; // Voy a establecer como valor por defecto 90 minutos, es decir, 1 hora y media.
        
        return of(titulo, director, genero, fechaEstreno, duracion);
    }
    
    
	// ##################################
	// OTROS MÉTODOS
	// ##################################
    
    public boolean equals(Object nuevo_objeto) { // 'nuevo_objeto' será cualquier otro número complejo.
        if (nuevo_objeto == this) { // Comprobamos si el objeto que vamos a comparar es el mismo.
        	return true; // Dado el caso, devolvemos 'True'.
        }
        if (!(nuevo_objeto instanceof Movie)) { // Comprobamos si el 'nuevo_objeto' es una instancia de 'Movie'.
        	return false;
        }
        Movie other = (Movie) nuevo_objeto; // Convertimos el 'nuevo_objeto' en un Movie.
        return titulo == other.titulo && director == other.director && genero == other.genero;
    }

    
    public String toStringShort() {
        return "Formato Corto: " + titulo + ", de " + director + " (" + fechaEstreno.getYear() + ")";
    }
    
    public String toString() {
    	return "Movie [Título=" + titulo + ", Director=" + director + ", Tipo metraje = " + tipoMetraje() + ", Año estreno=" + fechaEstreno.getYear() + "]";
    }
    
}