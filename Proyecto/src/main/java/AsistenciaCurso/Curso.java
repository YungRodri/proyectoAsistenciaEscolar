/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;


/**
 *
 * @author aleja
 */
public class Curso {
    private String nombre;
    private TreeMap<String, Estudiante> estudiantes;

    public Curso(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new TreeMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public TreeMap<String, Estudiante> getListaCurso() {
        return estudiantes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListaCurso(TreeMap<String, Estudiante> listaCurso) {
        this.estudiantes = listaCurso;
    }
    
    public void poblarCurso(ArrayList<Estudiante> listaGlobal){
        for(Estudiante alumno: listaGlobal){
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
            String nombres= alumno.getNombre();
            String apellidoP = alumno.getApellidoP();
            String apellidoM = alumno.getApellidoM();
            
            System.out.printf("%-10d||%-14s||%-14s %-14s||\n",i+1,nombres,apellidoP,apellidoM);
        }
    }
    
   public boolean retirarEstudiante(String rut)
    {
        if (estudiantes.containsKey(rut))
        {
            estudiantes.remove(rut);
            return true;
        }
        return false;
    }

    public boolean existeEstudiante(String rut)
    {
        return estudiantes.containsKey(rut);
    }

    public int getCantidadAlumnos()
    {
        return estudiantes.size();
    }

    public void mostrarEstudiantes()
    {
        for (Estudiante estudiante : estudiantes.values())
        {
            System.out.println(estudiante.getRut() + " - " + estudiante.getNombre() + estudiante.getApellidoP() + estudiante.getApellidoP());
        }
    }
}


