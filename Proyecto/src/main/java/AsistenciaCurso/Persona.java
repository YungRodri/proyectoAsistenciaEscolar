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
public class Persona {
    protected String nombre;
    protected String apellidoP;
    protected String apellidoM;
    protected String rut;
    protected int edad;

    public Persona(String nombre, String apellidoP, String apellidoM, String rut, int edad) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.rut = rut;
        this.edad = edad;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoP(String apellido) {
        this.apellidoP = apellidoP;
    }
    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }
    

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public String getRut() {
        return rut;
    }

    public int getEdad() {
        return edad;
    }

    
}
