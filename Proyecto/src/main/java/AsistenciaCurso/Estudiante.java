/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso;

/**
 *
 * @author aleja
 */
public class Estudiante extends Persona{
    private String curso;
    int nroAsistencias;
    
    public Estudiante(String nombre, String apellidoP, String apellidoM, String rut, int edad, String curso) {
        super(nombre, apellidoP, apellidoM, rut, edad);      
        this.curso = "N/A";
    }


    public String getCurso(){
        return curso;
    }

    
    public void setCurso(String newCurso){
        this.curso = newCurso;
    }

    
  
    
    
}
