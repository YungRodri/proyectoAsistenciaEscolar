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

    
  public void agregarAsistencia(Asistencia asistencia)
    {
        listaAsistencia.add(asistencia);
    }

    public Asistencia buscarAsistencia(String id)
    {
        int largo = listaAsistencia.size();
        Asistencia asistencia_cmp;
        for(int i = 0; i < largo; i++)
        {
            asistencia_cmp = listaAsistencia.get(i);
            if(asistencia_cmp.getId().equals(id))
            {
                return asistencia_cmp;
            }
        }
        return null;
    }
    public boolean eliminarAsistencia(String id)
    {
        for ( int i = 0; i < listaAsistencia.size(); i++)
        {
            if(listaAsistencia.get(i).getId().equals(id))
            {
                listaAsistencia.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean editarAsistencia(String id,  String observacion)
    {
        Asistencia asistencia_cmp = buscarAsistencia(id);
        if(asistencia_cmp != null)
        {
            asistencia_cmp.setObservacion(observacion);
            return true;
        }
        return false;
    }
    public void mostrarHistorial()
    {
        Asistencia asistencia_cmp;
        for(int i = 0; i < listaAsistencia.size(); i++)
        {
            asistencia_cmp = listaAsistencia.get(i);
            System.out.println(asistencia_cmp.getResumen());
        }
    }
    public int contarAsistenciaNormales()
    {
        int cont = 0;
        Asistencia asistencia_cmp;
        for(int i = 0; i < listaAsistencia.size(); i++)
        {
            asistencia_cmp = listaAsistencia.get(i);
            if(asistencia_cmp instanceof AsistenciaNormal)
            {
                cont ++;
            }

        }
        return cont;
    }
    public int contarAsistenciaExtraordinarias()
    {
        int cont = 0;
        Asistencia asistencia_cmp;
        for(int i = 0; i < listaAsistencia.size(); i++)
        {
            asistencia_cmp = listaAsistencia.get(i);
            if(asistencia_cmp instanceof InasistenciaExtraordinaria)
            {
                cont ++;
            }

        }
        return cont;
    }
    public int contarAsistenciaAnticpadas()
    {
        int cont = 0;
        Asistencia asistencia_cmp;
        for(int i = 0; i < listaAsistencia.size(); i++)
        {
            asistencia_cmp = listaAsistencia.get(i);
            if(asistencia_cmp instanceof SalidaAnticipada)
            {
                cont ++;
            }

        }
        return cont;
    }
    public int getCantAsistencia()
    {
        return  listaAsistencia.size();
    }


    
    
}
