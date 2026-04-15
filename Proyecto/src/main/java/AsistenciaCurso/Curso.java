/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
   
    public boolean inscribirEstudiante(Estudiante estudiante){
        if(estudiante != null && !estudiantes.containsKey(estudiante.getRut())){
            estudiantes.put(estudiante.getRut(), estudiante);
            return true;
        }
        return false;
    }
    
    public void mostrarEstudiantes(){
        if(estudiantes.isEmpty()){
            System.out.println("No hay estudiantes registrados en el curso " + nombre);
            return;
        }
        for(Estudiante e : estudiantes.values()){
            System.out.println(e.getRut() + " - " + e.getNombre() + e.getApellidoP() + e.getApellidoM());
        }
    }
    
    public Estudiante buscarEstudiante(String rut){
        return estudiantes.get(rut);
    }
    
    public boolean editarEstudiante(String rut, String nombre, String ApellidoP, String ApellidoM, int edad){
        Estudiante e = buscarEstudiante(rut);
        if(e != null){
            e.setNombre(nombre);
            e.setApellidoP(ApellidoP);
            e.setApellidoM(ApellidoM);
            e.setEdad(edad);
            return true;
        }
        return false;
    }
    
    public void retirarEstudiante(String rut){
         if(estudiantes.containsKey(rut)){
             estudiantes.remove(rut);
         }
    }
    
    public void registrarAsistencia(String rut, Asistencia asistencias){
        Estudiante e = buscarEstudiante(rut);
        if(e != null){
            e.agregarAsistencia(asistencias);
        }
    }
    
    public void mostrarAsistenciasDeEstudiante(String rut){
        Estudiante e = buscarEstudiante(rut);
        if(e != null){
            e.mostrarHistorial();
        } else{
            System.out.println("Estudiante no encontrado.");
        }
    }
    
    public Asistencia buscarAsistenciaDeEstudiante(String rut, String id){
        Estudiante e = buscarEstudiante(rut);
        if(e!= null){
            return e.buscarAsistencia(id);
        } else{
            return null;
        }
    }
    
    public boolean editarAsistenciaDeEstudiante(String rut, String id, String observacion){
        Estudiante e = buscarEstudiante(rut);
        if(e!= null){
            return e.editarAsistencia(id, observacion);
        }
        return false;
    }
    
    public boolean eliminarAsistenciaDeEstudiante(String rut, String id) {
        Estudiante e = buscarEstudiante(rut);
        if (e != null) {
            return e.eliminarAsistencia(id);
        }
        return false;
    }
    
    public void listarEstudiantesConMasdeInasistencias(int limite) {
        for (Estudiante e : estudiantes.values()) {
            int totalFaltas = e.contarAsistenciaExtraordinarias() + e.contarAsistenciaAnticpadas();
            if (totalFaltas > limite) {
                  System.out.println("Rut -> " + e.getRut() + " - " + e.getNombre() + e.getApellidoP() + e.getApellidoM());
            }
        }
    }
    
    public void poblarCurso(TreeMap<String, Estudiante> mapaGlobal){
        for(Estudiante alumno: mapaGlobal.values()){
            if(alumno.getCurso().equalsIgnoreCase(this.nombre)){
                estudiantes.put(alumno.getRut(), alumno);
            }
        }
    }
    
    public void generarLista(){
        int i;
        ArrayList<Estudiante> lista = new ArrayList<>(estudiantes.values());
        
        lista.sort(Comparator.comparing(Estudiante::getApellidoP)
        .thenComparing(Estudiante::getApellidoM)
        .thenComparing(Estudiante::getNombre));
        
        System.out.println("Lista curso "+this.nombre+": ");
        System.out.println("Nro. Lista||    Nombre    ||          Apellidos          ||");
        for(i=0; i<lista.size();i++){
            Estudiante alumno = lista.get(i);          
            System.out.printf("%-10d||%-14s||%-14s %-14s||\n",i+1,alumno.getNombre(),alumno.getApellidoP(),alumno.getApellidoM());
        }
    }
}


