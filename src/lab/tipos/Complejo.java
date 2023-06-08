package lab.tipos;

import java.lang.Math;

import java.text.DecimalFormat;

public record Complejo(double re, double im) {

	// ##################################
	// PROPIEDADES DERIVADAS
	// ##################################

    public double abs() {
        return Math.sqrt(re*re + im*im);
    }

    public double arg() {
        return Math.atan2(re, im);
    }

    public Complejo conjugado() {
        return new Complejo(re, -im);
    }

	// ##################################
	// REPRESENTACIÓN COMO CADENA
	// ##################################
    
    @Override
    public String toString() {
        if (re == 0 && im == 0) { // CASO 1: partes real e imaginaria igual a 0
            return "0.00";
        } else if (re == 0) { // CASO 2: parte real igual a 0
            return String.format("%.2f", im) + "i"; // Con 'String.format("%.2f", x)', limitamos 'x' a dos cifras decimales.
        } else if (im == 0) { // CASO 3: parte imaginaria igual a 0
            return String.format("%.2f", re);
        } else if (im < 0) { // CASO 4: parte imaginaria negativa
            return String.format("%.2f", re) + " - " + String.format("%.2f", -im) + "i";
        } else { // CASO 5: partes real e imaginaria mayor que 0
            return String.format("%.2f", re) + " + " + String.format("%.2f", im) + "i";
        }
    }

    
	// ##################################
	// CRITERIO DE IGUALDAD
	// ##################################

    @Override
    public boolean equals(Object nuevo_objeto) { // 'nuevo_objeto' será cualquier otro número complejo.
        if (nuevo_objeto == this) { // Comprobamos si el objeto que vamos a comparar es el mismo.
            return true; // Dado el caso, devolvemos 'True'.
        }
        if (!(nuevo_objeto instanceof Complejo)) { // Comprobamos si el 'nuevo_objeto' es una instancia de la clase Complejo.
            return false; // En caso contrario, devolvemos 'false'.
        }
        Complejo other = (Complejo) nuevo_objeto; // Convertimos el 'nuevo_objeto' en un Complejo.
        return Double.compare(re, other.re) == 0 && Double.compare(im, other.im) == 0; // Comprobamos las partes real e imaginaria del objeto
        // pasado por parámetro con el objeto actual; si los valores son iguales ("se hacen 0"), entonces se devuelve 'True'.
    }
    
    
	// ##################################
	// MÉTODO DE FACTORÍA
	// ##################################

    public static Complejo of(double re, double im) {
    return new Complejo(re, im);
    }

    public static Complejo of(double re) {
    return new Complejo(re, 0);
    }

    public static Complejo parse(String text) {
        String[] partes = text.split(" ");
        double re = Double.parseDouble(partes[0]); // El signo ocupa la posición 1
        double im = Double.parseDouble(partes[2]);
        if (partes[1].equals("-")) {
            return Complejo.of(re, -im);
        } else {
            return Complejo.of(re, im);
        }
    }

	// ##################################
	// OTROS MÉTODOS
	// ##################################
    
    	// SUMA
    public Complejo add(Complejo other) {
        return new Complejo(re + other.re, im + other.im);
    }

    	// DIFERENCIA
    public Complejo minus(Complejo other) {
        return new Complejo(re - other.re, im - other.im);
    }
    
    	// PRODUCTO
    public Complejo multiply(Complejo other) {
        return new Complejo(re*other.re - im*other.im, re*other.im + im*other.re);
    }

    	// DIVISIÓN
    public Complejo divide(Complejo other) {
        double denominador = other.re*other.re + other.im*other.im;
        double parteReal = (re*other.re + im*other.im) / denominador;
        double parteImaginaria = (im*other.re - re*other.im) / denominador;
        return new Complejo(parteReal, parteImaginaria);
    }

    	// POTENCIA
    public Complejo pow(int n) {
        double r = Math.pow(abs(), n); // 'r' es el módulo del número complejo.
        double angulo = n * arg(); // argumento --> 0.
        double parteReal = r * Math.cos(angulo);
        double parteImaginaria = r * Math.sin(angulo);
        return new Complejo(parteReal, parteImaginaria);
    }
}
