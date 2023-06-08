package lab.centro.profe;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import us.lsi.tools.File2;

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
        
        this.alumnosDni = this.alumnos.stream()     		
        		.collect(Collectors.toMap(a->a.getDni(),a->a));
        this.profesoresDni = this.profesores.stream()
        		.collect(Collectors.toMap(p->p.getDni(),p->p));
        this.asignaturasId = this.asignaturas.stream()
        		.collect(Collectors.toMap(a->a.id(),a->a));
    }     

    /*
     * MÉTODOS DE FACTORÍA
     */
    public static Centro of() {
        if (Centro.centro == null)
            Centro.centro = Centro.ofFiles();
        return Centro.centro;
    }
    
    public static Centro ofFiles() {
    	return ofFiles("ficheros/alumnos.txt","ficheros/profesores.txt",
            "ficheros/asignaturas.txt");
    	
    }
   
    public static Centro ofFiles(String ficheroAlumnos,String ficheroProfesores,
           String ficheroAsignaturas) {
        List<Alumno> alumnos = File2.streamDeFichero(ficheroAlumnos,"utf-8")
        		.map(ln->Alumno.parse(ln)).collect(Collectors.toList()); 		
        List<Profesor> profesores = File2.streamDeFichero(ficheroProfesores,"utf-8")
        		.map(ln->Profesor.parse(ln)).collect(Collectors.toList());    		
        List<Asignatura> asignaturas = File2.streamDeFichero(ficheroAsignaturas,"utf-8")
        		.map(ln->Asignatura.parse(ln)).collect(Collectors.toList());  		
        Centro.centro = new Centro(alumnos,profesores,asignaturas,new HashSet<>(),new HashSet<>());
        return Centro.centro;
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
    	Integer res = 0;
    	for(Asignatura a: this.asignaturas) {
    		res+=a.numGrupos();
    	}
    	return res;
    }
    
    public Integer numeroDeGruposF() {
    	return this.asignaturas.stream().mapToInt(a -> a.numGrupos()).sum();
    }
    

    
    /*
     * OTROS MÉTODOS
     */
    
    
    public void addAsignaciones(String fichero) {
        File2.streamDeFichero(fichero,"utf-8")
        		.map(ln->Asignacion.parse(ln))
        		.forEach(a->this.asignacionDeProfesores.add(a));
    }    
        
    public void addAsignaciones() {
    	addAsignaciones("ficheros/asignaciones.txt");
    }
    
    public void addAsignacion(Profesor profesor, Asignatura asignatura, Integer grupo) {
        this.asignacionDeProfesores.add(Asignacion.of(profesor.getDni(),asignatura.id(),grupo)); 
    }
    
    public void addMatriculas() {
    	addMatriculas("ficheros/matriculas.txt");
    }
    
    public void addMatriculas(String fichero) {
    	File2.streamDeFichero(fichero,"utf-8")
		.map(ln->Matricula.parse(ln))
		.forEach(m->this.matriculas.add(m));
    }
            
    public void addMatricula(Alumno alumno,Asignatura asignatura,Integer grupo) {
        this.matriculas.add(Matricula.of(alumno.getDni(),asignatura.id(),grupo));
    }
    
    

    
    public Profesor profesor(Integer i) { 
        return this.profesores.get(i);
    }
    
    public Profesor profesorDni(String dni) { 
        return this.profesoresDni.get(dni);
    }
    
    public Alumno alumno(Integer i) {
        return this.alumnos.get(i);
    }
        		
    public Alumno alumnoDni(String dni) {
        return this.alumnosDni.get(dni);
    }
    
    public Asignatura asignatura(Integer i) {
        return this.asignaturas.get(i);
    }
    
    public Asignatura asignaturaId(Integer i) {
    	return this.asignaturasId.get(i);
    }
    
    public Set<Asignatura> asignaturasImpartidasF(Profesor p){
    	return this.asignacionDeProfesores.stream()
    			.filter(a -> a.dni().equals(p.getDni()))
    			.map(a -> this.asignaturaId(a.ida()))
    			.collect(Collectors.toSet());
    }
    
    public Set<Asignatura> asignaturasImpartidasI(Profesor p){
    	Set<Asignatura> res = new HashSet<Asignatura>();
    	for(Asignacion a: this.asignacionDeProfesores) {
    		if(a.dni().equals(p.getDni())) {
    			res.add(this.asignaturaId(a.ida()));
    		}
    	}
    	
    	return res;
    }
    
    public Set<Asignatura> asignaturasCursadas(Alumno a){
    	return this.matriculas.stream()
    			.filter(m -> m.dni().equals(a.getDni()))
    			.map(m -> this.asignaturaId(m.ida()))
    			.collect(Collectors.toSet());
    }
    
    
    
    
    
    
    
    public static void main(String[] args) {
    	Centro c = Centro.of();
        System.out.println(String.format("- Hay %d alumnos en el centro", 
        		c.numeroDeAlumnos()));
        System.out.println(String.format("- Hay %d profesores en el centro", 
        		c.numeroDeProfesores()));
        System.out.println(String.format("- Hay %d asignaturas en el centro", 
        		c.numeroDeAsignaturas()));
        System.out.println(String.format("- Hay %d grupos en el centro", 
        		c.numeroDeGruposF()));
        
        System.out.println("----------------------\n>> Añadiendo asignaciones");
    	c.addAsignaciones();
    	System.out.println(String.format("- El número de asignaciones es:\n %d", 
        		c.asignacionDeProfesores.size()));
        System.out.println("----------------------\n>> Añadiendo matrículas");
    	c.addMatriculas();  
    	System.out.println(String.format("- El número de matrículas es:\n %d", 
        		c.matriculas.size()));
    	
    	String dni = "53045701L";
    	Profesor p = c.profesorDni(dni);
    	System.out.println(String.format("----------------------\n- El profesor con dni %s es %s\n----------------------", 
        		dni, p));
    	
    	Set<Asignatura> ap = c.asignaturasImpartidasF(p);
    	System.out.println(String.format("- Las asignaturas impartidas por %s son:", 
        		p.getNombre()));
    	ap.stream().forEach(x -> System.out.println(x.nombre()));
    	System.out.println("----------------------\n");
    	
    	
    	Integer pos = 200;
    	Alumno a = c.alumno(pos);
    	System.out.println(String.format("- El alumno que se encuentra en la posición %d es %s\n----------------------", 
        		pos, a));
    	
    	Set<Asignatura> ac = c.asignaturasCursadas(a);
    	System.out.println(String.format("- Las asignaturas cursadas por %s son:", 
        		a.getNombre()));
    	ac.stream().forEach(x -> System.out.println(x.nombre()));
    	System.out.println("----------------------\n");

    }
}
