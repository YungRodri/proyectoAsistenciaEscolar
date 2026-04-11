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
public class Profesor extends Persona{
    private String asignatura;
    private String profJefe;

    public Profesor(String nombre, String apellidoP, String apellidoM, String rut, int edad, String asignatura, String profJefe) {
        super(nombre, apellidoP, apellidoM, rut, edad);
        this.asignatura = asignatura;
        this.profJefe = profJefe;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getProfJefe() {
        return profJefe;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setProfJefe(String profJefe) {
        this.profJefe = profJefe;
    }
    
    
    
}
