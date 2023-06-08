package lab.centro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lab.centro.profe.Asignatura;
import us.lsi.tools.File2;
import us.lsi.tools.Preconditions;

public class Centro {
	
	
    /*
     * PROPIEDADES
     */
	
	
    public static Centro centro = null;
    private List<Alumno> alumnos;
    private List<Profesor> profesores;
    private List<Asignatura> asignaturas;
    private Set<Matricula> matriculas; 
	private Set<Asignacion> asignacionDeProfesores;
    private Map<String,Alumno> alumnosDni;
    private Map<String,Profesor> profesoresDni;
    private Map<Integer,Asignatura> asignaturasId;
        
    
    /*
     * CONSTRUCTOR
     */
    
    
    private Centro(List<Alumno> alumnos, List<Profesor> profesores, 
    		List<Asignatura> asignaturas,
            Set<Matricula> matriculas, 
			Set<Asignacion> asignacionDeProfesores) {
        this.alumnos = alumnos;
        this.profesores = profesores;
        this.asignaturas = asignaturas;
        this.matriculas = matriculas;
        this.asignacionDeProfesores = asignacionDeProfesores;
        this.alumnosDni = this.alumnos.stream().collect(Collectors.toMap(Alumno::getDni, (alumno) -> alumno));
        this.profesoresDni = this.profesores.stream().collect(Collectors.toMap(Profesor::getDni, (profesor) -> profesor));
        this.asignaturasId = this.asignaturas.stream().collect(Collectors.toMap(Asignatura::id, (asignatura) -> asignatura));
    }     
    
    
    /*
     * PROPIEDADES DERIVADAS
     */
    
    
    public Integer numeroDeProfesores() { 
        return this.profesores.size();
    }
    
    public Integer numeroDeAlumnos() { 
        return this.alumnos.size();
    }
    
    public Integer numeroDeAsignaturas() {
        return this.asignaturas.size();
    }
    
    
    public Integer numeroDeGruposI() {
        int grupos = 0;
        for (Asignatura asignatura: this.asignaturas) {
            grupos += asignatura.numGrupos();
        }
        return grupos;
    }
    
    public Integer numeroDeGruposF() {
    	int grupos = this.asignaturas.stream()
    			.mapToInt(Asignatura::numGrupos)
    			.sum();
    	return grupos;
    }
    
    
    /*
     * MÉTODOS DE FACTORÍA
     */
    
    
    public static Centro of() {
        if (Centro.centro == null)
            Centro.centro = Centro.ofFiles();
        return Centro.centro;
    }
    
    public static Centro ofFiles(String ficheroAlumnos, String ficheroProfesores, String ficheroAsignaturas) {
        List<Alumno> alumnos = File2.lineasDeFichero(ficheroAlumnos)
                .stream().map(linea -> Alumno.parse(linea.trim()))
                .collect(Collectors.toList());
        List<Profesor> profesores = File2.lineasDeFichero(ficheroProfesores)
                .stream().map(linea -> Profesor.parse(linea.trim()))
                .collect(Collectors.toList());
        List<Asignatura> asignaturas = File2.lineasDeFichero(ficheroAsignaturas)
                .stream().map(linea -> Asignatura.parse(linea.trim()))
                .collect(Collectors.toList());
        return new Centro(alumnos, profesores, asignaturas, new HashSet<>(), new HashSet<>());
    }
    
    public static Centro ofFiles() {
        String fichero_alumnos = "ficheros/alumnos.txt";
        String fichero_profesores = "ficheros/profesores.txt";
        String fichero_asignaturas = "ficheros/asignaturas.txt";
        return Centro.ofFiles(fichero_alumnos, fichero_profesores, fichero_asignaturas);
    }

    
    
    /*
     * OTROS MÉTODOS
     */
    
    
    public void addAsignaciones(String fichero) {
        Set<Asignacion> conjunto_asignaciones = new HashSet<>();
        List<String> lineas = File2.lineasDeFichero(fichero);
        for (String linea: lineas) {
            conjunto_asignaciones.add(Asignacion.parse(linea));
        }
        this.asignacionDeProfesores = conjunto_asignaciones;
    }
    
    public void addAsignaciones() {
        addAsignaciones("ficheros/asignaciones.txt");
    }

    public void addAsignacion(Profesor profesor, Asignatura asignatura, Integer grupo) {
        this.asignacionDeProfesores.add(Asignacion.of(profesor.getDni(),asignatura.id(),grupo)); 
    }
    
    
    
    public void addMatriculas(String fichero) {
        Set<Matricula> conjunto_matriculas = new HashSet<>();
        List<String> lineas = File2.lineasDeFichero(fichero);
        for (String linea: lineas) {
            conjunto_matriculas.add(Matricula.parse(linea));
        }
        this.matriculas = conjunto_matriculas;
    }

    public void addMatriculas() {
        addMatriculas("ficheros/matriculas.txt");
    }
            
    public void addMatricula(Alumno alumno,Asignatura asignatura,Integer grupo) {
        this.matriculas.add(Matricula.of(alumno.getDni(),asignatura.id(),grupo));
    }
    
    
    
    public Profesor profesor(Integer i) { 
        return this.profesores.get(i);
    }
    
    public Profesor profesorDni(String dni) {
        return profesoresDni.get(dni);
    }
    
    public Alumno alumno(Integer i) {
        return this.alumnos.get(i);
    }
    
    public Alumno alumnoDni(String dni) {
        return alumnosDni.get(dni);
    }
    
    public Asignatura asignatura(Integer i) {
        return this.asignaturas.get(i);
    }
    
    public Asignatura asignaturaId(Integer i) {
    	return this.asignaturasId.get(i);
    }
    
    
    
    public Set<Asignatura> asignaturasImpartidasI(Profesor p) {
        Set<Asignatura> asignaturas_impartidas = new HashSet<>();
        for (Asignacion asignacion: asignacionDeProfesores) {
            if (asignacion.dni().equals(p.getDni())) {
                Asignatura asignatura = asignaturasId.get(asignacion.ida());
                asignaturas_impartidas.add(asignatura);
            }
        }
        return asignaturas_impartidas;
    }
    
    public Set<Asignatura> asignaturasImpartidasF(Profesor p) {
        return asignacionDeProfesores.stream()
            .filter(asignacion -> asignacion.dni().equals(p.getDni()))
            .map(asignacion -> asignaturasId.get(asignacion.ida()))
            .collect(Collectors.toSet());
    }

    public Set<Asignatura> asignaturasCursadas(Alumno a) {
        return matriculas.stream()
            .filter(matricula -> matricula.dni().equals(a.getDni()))
            .map(matricula -> asignaturasId.get(matricula.ida()))
            .collect(Collectors.toSet());
    }
    
    
    // DEFENSA
    // Nota: Respecto a los enunciados originales presentes en la defensa, se han aplicado algunos cambios.
    
    // EJERCICIO 1
    public List<Alumno> alumnosConLetraNumeroDNI(Character letra, Integer digito) {
    	Preconditions.checkArgument(0 <= digito && digito <= 9, "El parametro digito solo puede valer entre 0 y 9, ambos incluidos.");
        return alumnos.stream()
                .filter(alumno -> alumno.getDni().charAt(0) == Character.forDigit(digito, 10)) // En Java, el método forDigit() de la clase Character se 
                // utiliza para convertir un valor entero en su representación de carácter correspondiente en una determinada base numérica (ej.: decimal, 10).
                .filter(alumno -> alumno.getDni().charAt(alumno.getDni().length() - 1) == letra)
                .collect(Collectors.toList());
    }
    
    // EJERCICIO 2
    public Set<Asignatura> asignaturasNoImpartidasProfesor(String dni) {
        Profesor profesor = profesorDni(dni);
        
        if (profesor == null) {
            System.out.println("El DNI no se encuentra");
            return Collections.emptySet();
        }
        
        Set<Asignatura> asignaturas_impartidas = asignaturasImpartidasF(profesor);
       
        return asignaturas.stream()
                .filter(asignatura -> !asignaturas_impartidas.contains(asignatura))
                .collect(Collectors.toSet());
    }

    // EJERCICIO 3    
    public Set<Asignatura> asignaturasAlumno(String dni, String texto) {
        Alumno alumno = alumnoDni(dni);
        if (alumno == null) {
            System.out.println("El DNI no se encuentra");
            return Collections.emptySet();
        }
        Set<Asignatura> asignaturas_cursadas = asignaturasCursadas(alumno);
        if (asignaturas_cursadas.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Asignatura> asignaturas_filtradas = asignaturas_cursadas.stream()
                .filter(asignatura -> texto == null || asignatura.nombre().contains(texto))
                .collect(Collectors.toSet());
        
        if (asignaturas_filtradas.isEmpty()) {
            System.out.println("No se encontraron asignaturas que coincidan con el criterio de busqueda.");
        }
        return asignaturas_filtradas;
    }

    // EJERCICIO 4
    public Integer edadMediaAlumnosDelCentro(Integer edadDesde, Integer edadHasta) {
    	Preconditions.checkArgument(edadDesde<=edadHasta, "La edadDesde debe ser menor o igual que edadHasta");
        List<Integer> edades = alumnos.stream()
        		.filter(alumno -> alumno.getEdad() != null && (edadDesde == null || alumno.getEdad() >= edadDesde) && (edadHasta == null || alumno.getEdad() <= edadHasta))
                .map(Alumno::getEdad)
                .collect(Collectors.toList());
        double edadMedia = edades.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        return (int) edadMedia;
    }
    
    
    // EXAMEN DE PYTHON (enero de 2023) a Java.
    // EJERCICIO 1: Integer [numeroCreditosProfesor]
    public Integer numeroCreditosProfesor(Profesor profesor) {
        return asignacionDeProfesores.stream()
                .filter(asignacion -> asignacion.dni().equals(profesor.getDni()))
                .map(asignacion -> asignaturasId.get(asignacion.ida()))
                .mapToInt(Asignatura::creditos)
                .sum();
    }
    
    // String p_dni = "53045701L";
    // TEST: System.out.println(centro1.numeroCreditosProfesor(centro1.profesorDni(p_dni)));
    // RESULTADO: 18
    
    
    // EJERCICIO 2: Map [edadProfesor -> totalGruposProfesorDichaEdad]
    public Map<Integer, Integer> totalGruposProfesor() {
        return profesores.stream()
                .collect(Collectors.groupingBy(Profesor::getEdad,
                        Collectors.summingInt(p -> numeroDeGruposF())));
    }
   
    // TEST: System.out.println(centro1.totalGruposProfesor());
    // RESULTADO: {18=684, 19=684, 20=798, 21=1026, 22=228, 23=1026, 24=798, 25=570, 26=456}
    
    
    // EJERCICIO 3: eliminarProfesor
    public void eliminarProfesor(Profesor p) {
        Set<Asignatura> asignaturasImpartidas = asignaturasImpartidasF(p);
        if (!asignaturasImpartidas.isEmpty()) {
            throw new IllegalStateException("El profesor imparte asignaturas y no puede ser eliminado");
        } else { // Suponemos que debemos eliminar el profesor p de la lista de profesores y del diccionario que relaciona el dni con él.
            profesores.remove(p); // Eliminamos el profesor p de la lista de profesores.
            profesoresDni.remove(p.getDni()); // Eliminamos el profesor p del diccionario que relaciona cada dni con el profesor correspondiente.
        }
    }
    
    // TEST Y RESULTADOS:
    /*
     * String p2_dni = "77943623G";
     * centro1.eliminarProfesor(centro1.profesorDni(p2_dni));
     * System.out.println(centro1.profesorDni(p2_dni));
     * 
     * Añadimos en profesores.txt este Profesor (por ejemplo, en la línea 56):
     * 		NuevoProfesor NuevoProfesor,NuevoProfesor,77943623G,2000-11-11 11:11,+34612814061,Reino del NuevoProfesor 1;Huesca;24093,Doctor
     * 
     * Si ejecutamos el código de TEST, veremos que al intentar imprimir "System.out.println(centro1.profesorDni(p2_dni));", aparece "null",
     * ya que el profesor se ha eliminado correctamente.
     * 
     * Ahora, si añadimos en asignaciones.txt esta Asignacion (por ejemplo, en la línea 115):
     * 		77943623G,51,0
     * 
     * Si ejecutamos nuevamente el código de TEST, veremos que el profesor no puede eliminarse (ya que imparte asignatura) y, en consecuencia, la 
     * siguiente línea de código de test nos imprime el profesor con el dni dado.
     */


    
    // EJERCICIO 4: Implementar el método alumnosPorGrupo. Devuelve un diccionario con los alumnos que cursan la asignatura cuyo código es ida. 
    // En dicho diccionario, las claves serán los identificadores de los grupos y los valores son las listas de alumnos que cursan la asignatura en ese grupo.
    
    public Map<Integer, List<Alumno>> alumnosPorGrupo(int ida) {
        return matriculas.stream()
        		.filter(matricula -> matricula.ida() == ida)
        		.collect(Collectors.groupingBy(Matricula::idg, Collectors.mapping(matricula -> alumnoDni(matricula.dni()), Collectors.toList())));
        }
    
    // TEST: 
    /* Añadimos otra linea de test para hacer un size() de los alumnosPorGrupo con ida = 0, para facilitar la corrección.
     * System.out.println(centro1.alumnosPorGrupo(0));
     * System.out.println(centro1.alumnosPorGrupo(0).size());
     */
    // RESULTADO: 3
    
    
    
    // EJERCICIO 5: devuelve los alumnos cuyo código postal es igual al código postal del profesor p.
    public Set<Alumno> alumnosMismoDistritoPostal(Profesor p) {
        return alumnos.stream()
                .filter(alumno -> alumno.getDireccion().zip_code().equals(p.getDireccion().zip_code()))
                .collect(Collectors.toSet());
    }
    
    // TEST Y RESULTADOS: 
    /*
     * Como encontrar alumnos y profesores que compartan código postal es difícil en unos ficheros relativamente pequeños,
     * veo favorable forzar la comprobación y test de este método. Por ello, si cambiamos el código postal de los alumnos de las líneas 599 y 600
     * en "alumnos.txt" y el del profesor de la línea 55 en "profesores.txt" por "33333", veremos que, tras ejecutar:
     * 
     * String p3_dni = "87761882T";
     * System.out.println(centro1.alumnosMismoDistritoPostal(centro1.profesorDni(p3_dni)));
     * 
     * El resultado es:
     * 
     * [Omar Meléndez Reig, de 25, nacido el 24-08-1997 a las 00:00 con nota de entrada 9,30, Miguela Castrillo Valle, de 24, nacido el 19-08-1998 a las 02:39 con nota de entrada 5,80]
     * 
     * Si probamos el método sin forzarlo, lo más normal es que se nos devuelva un Set vacío: []
     */

    
    
    
    // EXAMEN DE JAVA (mayo de 2023)
    
    // EJERCICIO 1
    /*
     * Diseñar el método profesoresDeGrupo(Integer ida, Integer idg) que devuelva para 
     * el grupo idg de la asignatura de identificador ida el conjunto de profesores de ese grupo.
     */
    
    public Set<Profesor> profesoresDeGrupo(Integer ida, Integer idg) {
        Set<Profesor> profesores_grupo = new HashSet<>();
        Asignatura asignatura_ida = asignaturaId(ida);
        if (asignatura_ida != null) {
            Set<Asignacion> asignaciones_grupo = asignacionDeProfesores.stream()
                    .filter(asignacion -> asignacion.ida().equals(ida) && asignacion.idg().equals(idg))
                    .collect(Collectors.toSet());
            for (Asignacion asignacion: asignaciones_grupo) {
                Profesor p = profesorDni(asignacion.dni());
                if (p != null) {
                    profesores_grupo.add(p);
                }
            }
        }

        return profesores_grupo;
    }

    /* Si probamos con el siguiente TEST: 
     *		System.out.println(centro1.profesoresDeGrupo(1,0));
     * se nos devuelve:
     * 		[Primitiva Andres Esparza, de 26, nacido el 21-02-1997 a las 18:15 con titulo Diplomado]
     * mientras que si probamos con:
     * 		System.out.println(centro1.profesoresDeGrupo(5,5));
     * se nos devuelve un Set Vacío: []
     */
    
    
    // EJERCICIO 2
    /*
     * Diseñar el tipo Grupo que tiene como propiedades: ida, el identificador de la asignatura, e idg, 
     * el identificador del grupo. Incluir un método de factoría of y un método toString que represente 
     * el grupo incluyendo el nombre de la asignatura y el número del grupo, como en el ejemplo siguiente: 
     * (Asignatura = Lógica y estructuras discretas, Grupo = 2)
     */
    
    // SOLUCIÓN: Mirar el tipo "Grupo".
    
   
    // EJERCICIO 3
    /*
     * Diseñar un método que calcule la media de las edades de los profesores del centro. 
     * Tenga en cuenta que los profesores se repiten en las asignaciones.
     */
    
    public double mediaEdadesProfesoresCentro() {
        double media_edades = this.profesores.stream()
                .mapToInt(profesor -> profesor.getEdad())
                .average()
                .orElse(0.0);
        return media_edades;
    }
    
    /*
     * Si probamos el método:
     * 		System.out.println(centro1.mediaEdadesProfesoresCentro());
     * el resultado que nos da es:
     * 		21.836363636363636
     * Esto es, si redondeamos a la décima: 21.8
     */
    
    
    // EJERCICIO 4
    /*
     * Realice el método asignaturaMasMatriculas() que devuelva el nombre de la asignatura con más matrículas.
     */
    public String asignaturaMasMatriculas() {
        Map<Asignatura,Long> conteoAsignaturas = matriculas.stream().map(Matricula::ida)
        		.map(this.asignaturasId::get)
        		.collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        return conteoAsignaturas.entrySet().stream()
        		.max(Map.Entry.comparingByValue())
        		.map(Map.Entry::getKey)
                .map(Asignatura::nombre)
                .orElse(null);
    }
    
   /*
    * Si probamos el método:
    * 		System.out.println(centro1.asignaturaMasMatriculas());	
    * el resultado que nos da es:
    * 		Usabilidad y accesibilidad
    */
    
    
    // EJERCICIO 5
    /*
     * Diseñar el método numProfesoresPorEdad() que devuelva el número de profesores para cada edad
     */
    
    public Map<Integer, Long> numProfesoresPorEdad() {
        return this.profesores.stream()
                .collect(Collectors.groupingBy(Profesor::getEdad, Collectors.counting()));
    }
    
    /*
     * Si probamos el método:
     * 		System.out.println(centro1.numProfesoresPorEdad());	
     * el resultado que nos da es:
     * 		{18=6, 19=6, 20=7, 21=8, 22=3, 23=9, 24=7, 25=4, 26=5}
     * Podemos comprobar que es correcto porque, si sumamos los profesores por edad, nos da 55, y esa es la cifra de profesores del centro.
     */
    
    
    
    
    
    /*
     * TEST
     */
    
    
    public static void main(String[] args) {
    	Centro centro1 = Centro.of();
        String p_dni = "53045701L";
        String a_dni = "27440582H";
        String p_nombre = "Felipa";
        String a_nombre = "Jessica";
        int i = 200;
        //int i2 = 22;
        //int i3 = 11;

        System.out.println("---------------------Centro.java---------------------");
        System.out.printf(" - Hay %d alumnos en el centro\n", centro1.numeroDeAlumnos());
        System.out.printf(" - Hay %d profesores en el centro\n", centro1.numeroDeProfesores());
        System.out.printf(" - Hay %d asignaturas en el centro\n", centro1.numeroDeAsignaturas());
        System.out.printf(" - [FUNCIONAL] Hay %d grupos en el centro\n", centro1.numeroDeGruposF());
        System.out.printf(" - [IMPERATIVO] Hay %d grupos en el centro\n", centro1.numeroDeGruposI());
        System.out.println("___________");
        System.out.println(">> Anadiendo asignaciones");
        centro1.addAsignaciones();
        System.out.printf(" - El numero de asignaciones es: \n%d\n", centro1.asignacionDeProfesores.size());
        System.out.println("___________");
        System.out.println(">> Anadiendo matriculas");
        centro1.addMatriculas();
        System.out.printf(" - El numero de matriculas es: \n%d\n", centro1.matriculas.size());
        System.out.println("___________");
        System.out.printf(" - El profesor con dni %s es %s\n", p_dni, centro1.profesorDni(p_dni));
        System.out.println("___________");
        System.out.printf(" - [FUNCIONAL] Las asignaturas impartidas por %s son: %s\n", p_nombre, centro1.asignaturasImpartidasF(centro1.profesorDni(p_dni)));
        System.out.printf(" - [IMPERATIVO] Las asignaturas impartidas por %s son: %s\n", p_nombre, centro1.asignaturasImpartidasI(centro1.profesorDni(p_dni)));
        System.out.println("___________");
        System.out.printf(" - El alumno que se encuentra en la posicion %d es %s\n", i, centro1.alumno(i));
        System.out.println("___________");
        System.out.printf(" - Las asignaturas cursadas por %s son: %s\n", a_nombre, centro1.asignaturasCursadas(centro1.alumnoDni(a_dni)));
        System.out.println("___________");
        System.out.println("---------------------Fin del testeo---------------------\n");
        
		/*
        System.out.println("---------------------OTRAS PRUEBAS---------------------");
        System.out.println("- Lista con todos los alumnos que hay en el centro: " + centro1.alumnos);
        System.out.println("- Lista con todos los profesores del centro: " + centro1.profesores);
        System.out.println("- Lista con todas las asignaturas que se imparten en el centro: " + centro1.asignaturas);
        System.out.println("- Diccionario que relaciona cada dni con el alumno correspondiente: " + centro1.alumnosDni);
        System.out.println("- Diccionario que relaciona cada dni con el profesor correspondiente: " + centro1.profesoresDni);
        System.out.println("- Diccionario que relaciona cada identificador de asignatura con la asignatura correspondiente: " + centro1.asignaturasId);
        
        centro1.addAsignacion(Profesor.parse("Sol de la Cruz,Vanesa,70946161Q,1992-02-22 18:31,+34693352648,Avenida del Amor 11;Cádiz;11003,Doctor"), Asignatura.parse("52,Ingeniería informática sobre el mundo espacial i,6,4"),1);
        centro1.addMatricula(Alumno.parse("Romero Bécquer,Hugo,77943623G,2003-04-11 16:57,+34682513497,Calle de la tupla 2;Jaén;24701,9.9"), Asignatura.parse("52,Ingeniería informática sobre el mundo espacial i,6,4"),1);
	
        System.out.println("- El numero total de asignaciones tras haber anadido una nueva usando 'add_asignacion' es: " + centro1.asignacionDeProfesores.size());
        System.out.println("- El numero total de matriculas tras haber anadido una nueva usando 'add_matricula' es: " + centro1.matriculas.size());
        
        System.out.println("- Conjunto con todas las asignaciones llevadas a cabo en el centro entre profesores y alumnos: " + centro1.asignacionDeProfesores);
        System.out.println("- Conjunto con todas las matrículas hechas en el centro: " + centro1.matriculas);
        System.out.println("- El profesor que se encuentra en la posición " + i2 + " es " + centro1.profesor(i2));
        System.out.println("- El alumno con dni " + a_dni + " es " + centro1.alumnoDni(a_dni));
        System.out.println("- La asignatura con ID = " + i3 + " es " + centro1.asignaturaId(i3));
        System.out.println("- La asignatura que se encuentra en la posición " + i3 + " es " + centro1.asignatura(i3));
        */
        
        
        
        
        //#===========================================================================
        //# EXAMEN DE PYTHON (enero de 2023) a Java.
        //#===========================================================================
        System.out.println("\n---------------------ExamenPython.java---------------------");
        // EJERCICIO 1
        System.out.println(centro1.numeroCreditosProfesor(centro1.profesorDni(p_dni)));
        
        // EJERCICIO 2
        System.out.println(centro1.totalGruposProfesor());
        
        // EJERCICIO 3
        //String p2_dni = "77943623G";
        try {
            centro1.eliminarProfesor(centro1.profesorDni(p_dni));
            System.out.println(centro1.profesorDni(p_dni));
        } catch (Exception e) {
            System.out.println(e);
        }
        /* 
         * Añadimos en profesores.txt este Profesor (por ejemplo, en la línea 56):
         * 		NuevoProfesor NuevoProfesor,NuevoProfesor,77943623G,2000-11-11 11:11,+34612814061,Reino del NuevoProfesor 1;Huesca;24093,Doctor
         * 
         * Si ejecutamos el código de TEST, veremos que al intentar imprimir "System.out.println(centro1.profesorDni(p2_dni));", aparece "null",
         * ya que el profesor se ha eliminado correctamente.
         * 
         * Ahora, si añadimos en asignaciones.txt esta Asignacion (por ejemplo, en la línea 115):
         * 		77943623G,51,0
         * 
         * Si ejecutamos nuevamente el código de TEST, veremos que el profesor no puede eliminarse (ya que imparte asignatura) y, en consecuencia, la 
         * siguiente línea de código de test nos imprime el profesor con el dni dado
         */
        
        // EJERCICIO 4
        System.out.println(centro1.alumnosPorGrupo(0));
        System.out.println(centro1.alumnosPorGrupo(0).size());
        
        // EJERCICIO 5
        String p3_dni = "87761882T";
        System.out.println(centro1.alumnosMismoDistritoPostal(centro1.profesorDni(p3_dni)));
        
        System.out.println("---------------------Fin del testeo---------------------\n");
        
        
        
        
        //#===========================================================================
        //# EXAMEN DE JAVA (mayo de 2023)
        //#===========================================================================
        System.out.println("\n---------------------ExamenJava.java---------------------");
        System.out.println(centro1.profesoresDeGrupo(1,0));
        System.out.println(centro1.profesoresDeGrupo(5,5));
        System.out.println(centro1.mediaEdadesProfesoresCentro());
        System.out.println(centro1.asignaturaMasMatriculas());
        System.out.println(centro1.numProfesoresPorEdad());
        System.out.println("---------------------Fin del testeo---------------------\n");
        
        
        
        
        //#===========================================================================
        //# DEFENSA (test vía control de excepciones)
        //#===========================================================================
        System.out.println("\n---------------------Defensa.java---------------------");
        
        // alumnosConLetraNumeroDNI(
        // BIEN
        System.out.println("\n********************************************************************************");
        System.out.println("alumnosConLetraNumeroDNI() - BIEN");
        try {
            List<Alumno> alumnos = centro.alumnosConLetraNumeroDNI('A', 5);
            alumnos.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // MAL
        System.out.println("\n********************************************************************************");
        System.out.println("alumnosConLetraNumeroDNI() - MAL");
        try {
            List<Alumno> alumnos = centro.alumnosConLetraNumeroDNI('A', 10);
            alumnos.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
      
        
        // asignaturasNoImpartidasProfesor()
        // BIEN
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasNoImpartidasProfesor() - BIEN \n");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasNoImpartidasProfesor("53045701L");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // MAL
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasNoImpartidasProfesor() - MAL");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasNoImpartidasProfesor("77943623G");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }

        // asignaturasAlumno()
        // BIEN(DNI, ASIGNATURA)
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasAlumno() - BIEN (DNI, ASIGNATURA)");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasAlumno("27440582H", "Seguridad");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // MAL(DNI)
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasAlumno() - MAL(DNI)");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasAlumno("77943623G", "Seguridad");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // MAL(ASIGNATURA)
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasAlumno() - MAL(ASIGNATURA)");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasAlumno("27440582H", "HolaMundo");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // BIEN(VACIO)
        System.out.println("\n********************************************************************************");
        System.out.println("asignaturasAlumno() - BIEN (VACIO)");
        try {
            Set<Asignatura> asignaturas = centro.asignaturasAlumno("27440582H", "");
            asignaturas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }

        // edadMediaAlumnosDelCentro()
        // BIEN
        System.out.println("\n********************************************************************************");
        System.out.println("edadMediaAlumnosDelCentro() - BIEN");
        try {
            Integer edadMedia = centro.edadMediaAlumnosDelCentro(18, 25);
            System.out.println("Edad media de los alumnos del centro entre 18 y 25 anyos: " + edadMedia);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        // MAL(INVERSIÓN)
        System.out.println("\n********************************************************************************");
        System.out.println("edadMediaAlumnosDelCentro() - MAL");
        try {
            Integer edadMedia = centro.edadMediaAlumnosDelCentro(25, 18);
            System.out.println("Edad media de los alumnos del centro entre 25 y 18 anyos: " + edadMedia);
        } catch (Exception e) {
            System.out.println("Excepcion capturada \n" + e);
        }
        
        System.out.println("---------------------Fin del testeo---------------------");

    }

}