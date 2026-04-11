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
public class Curso {
    private String nivel;
    private int cantidad;
    private ArrayList<Estudiante> listaCurso = new ArrayList<Estudiante>() ;
    
    public Curso(String nivel, int cantidad){
        this.nivel = nivel;
        this.cantidad = 45;
}

    public String getNivel() {
        return nivel;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}


