package AsistenciaCurso;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Comparator;

public class Curso {
    private String nombre;
    private TreeMap<String, Estudiante> estudiantes;
    
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
    
    // SIA-5: Sobrecarga 1 — muestra todos los estudiantes
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

    // SIA-5: Sobrecarga 2 — filtra estudiantes por nombre de curso
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
     
    public void poblarCurso(TreeMap<String, Estudiante> mapaGlobal) {
        for (Estudiante alumno : mapaGlobal.values()) {
            if (alumno.getCurso().equalsIgnoreCase(this.nombre)) {
                estudiantes.put(alumno.getRut(), alumno);
            }
        }
    }

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
