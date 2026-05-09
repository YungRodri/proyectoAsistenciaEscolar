package AsistenciaCurso.modelo;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

/**
 * Representa un curso entero del colegio (por ejemplo, "1 Basico").
 * Su propósito principal es agrupar a los estudiantes y permitirnos ordenarlos
 * o pasar asistencia de manera masiva.
 */
public class Curso {
    private String nombre;
    private TreeMap<String, Estudiante> estudiantes;
    
    /**
     * Al crear un curso solo necesitamos su nombre.
     * La lista de estudiantes parte vacía y se llena después usando el Gestor.
     * @param nombre Nombre del curso (ej. "8 Basico").
     */
    public Curso(String nombre){
        this.nombre = nombre;
        estudiantes = new TreeMap<>();
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    /**
     * OBTENER LOS ESTUDIANTES DEL CURSO.
     * Retornamos un mapa de solo lectura (unmodifiableMap) para evitar
     * que se manipule la colección directamente desde otra clase, respetando el encapsulamiento.
     * @return Mapa de estudiantes donde la clave es el RUT.
     */
    public Map<String, Estudiante> getEstudiantes() {
        return Collections.unmodifiableMap(estudiantes);
    }

    /**
     * SOBRECARGA 1: Muestra todos los estudiantes que pertenecen a este curso.
     * La usamos principalmente para listados generales donde no hay filtros.
     */
    public void mostrarEstudiantes() {
        if(estudiantes.isEmpty()){
            System.out.println("No hay estudiantes registrados en el curso " + nombre);
            return;
        }
        for (Estudiante estudiante : estudiantes.values()) {
            System.out.println(estudiante.getRut() + " - "
                + estudiante.getNombre() + " "
                + estudiante.getApellidoP() + " "
                + estudiante.getApellidoM());
        }
    }

    /**
     * SOBRECARGA 2: Muestra a los estudiantes, pero aplicando un filtro manual extra.
     * La usamos en el menú de consola para reutilizar lógica de impresión, filtrando al vuelo si es necesario.
     * @param filtroCurso Nombre del curso a filtrar.
     */
    public void mostrarEstudiantes(String filtroCurso) {
        System.out.println("Estudiantes en curso: " + filtroCurso);
        boolean hayAlumnos = false;
        for (Estudiante estudiante : estudiantes.values()) {
            if (estudiante.getCurso().equalsIgnoreCase(filtroCurso)) {
                System.out.println(estudiante.getRut() + " - "
                    + estudiante.getNombre() + " "
                    + estudiante.getApellidoP() + " "
                    + estudiante.getApellidoM());
                hayAlumnos = true;
            }
        }
        if (!hayAlumnos) {
            System.out.println("No hay estudiantes en ese curso.");
        }
    }
     
    /**
     * Extrae desde el mapa global del colegio solo los alumnos que pertenecen a este curso
     * y los guarda en el TreeMap interno.
     * @param mapaGlobal El registro completo de todos los alumnos cargados del CSV.
     */
    public void poblarCurso(TreeMap<String, Estudiante> mapaGlobal) {
        for (Estudiante alumno : mapaGlobal.values()) {
            if (alumno.getCurso().equalsIgnoreCase(this.nombre)) {
                estudiantes.put(alumno.getRut(), alumno);
            }
        }
    }

    /**
     * Imprime en consola la clásica lista de curso escolar, ordenada
     * por apellido paterno, materno y finalmente nombre.
     */
    public void generarLista() {
        int i;
        ArrayList<Estudiante> lista = new ArrayList<>(estudiantes.values());

        lista.sort(Comparator.comparing(Estudiante::getApellidoP)
                .thenComparing(Estudiante::getApellidoM)
                .thenComparing(Estudiante::getNombre));

        System.out.println("Lista del curso " + this.nombre + ":");
        for (i = 0; i < lista.size(); i++) {
            Estudiante alumno = lista.get(i);
            System.out.println((i + 1) + ". " + alumno.getNombre() + " " + alumno.getApellidoP() + " " + alumno.getApellidoM());
        }
    }
}
