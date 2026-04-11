/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso;

import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public class Estudiante extends Persona{
    private String curso;
    private ArrayList<Asistencia> listaAsistencia;
    
    public Estudiante(String nombre, String apellidoP, String apellidoM, String rut, int edad, String curso) {
        super(nombre, apellidoP, apellidoM, rut, edad);      
        this.curso = curso;
        this.listaAsistencia = new ArrayList<>();
    }


    public String getCurso(){
        return curso;
    }

    
    public void setCurso(String newCurso){
        this.curso = newCurso;
    }

    public ArrayList<Asistencia> getListaAsistencia() {
        return listaAsistencia;
    }

    public void setListaAsistencia(ArrayList<Asistencia> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    
  
    
    
}
