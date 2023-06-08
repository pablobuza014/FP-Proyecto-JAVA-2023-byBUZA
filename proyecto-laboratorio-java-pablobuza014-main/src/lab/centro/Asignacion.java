package lab.centro;

public record Asignacion(String dni, Integer ida, Integer idg) {
	
    
    /*
     * MÉTODOS DE FACTORÍA
     */
    
    
    public static Asignacion of(String dni,Integer ida,Integer idg) {
        return new Asignacion(dni,ida,idg);
    }

    public static Asignacion parse(String text) {
        String[] partes = text.split(",");
        return new Asignacion(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }
    
    
    /*
     * TEST
     */
    
    
    public static void main(String[] args) {
        Asignacion asignacion1 = Asignacion.of("63588347V", 17, 0);
        Asignacion asignacion2 = Asignacion.parse("63588347V,17,0");
        System.out.println("---------------------Asignacion.java---------------------");
        System.out.println("Impresion usando el metodo de factoria 'of': " + asignacion1);
        System.out.println("Impresion usando el metodo de factoria 'parse': " + asignacion2);
        System.out.println("DNI: " + asignacion1.dni());
        System.out.println("ID DE LA ASIGNATURA: " + asignacion1.ida());
        System.out.println("ID DEL GRUPO: " + asignacion1.idg());
        System.out.println("---------------------Fin del testeo---------------------");
    }

}