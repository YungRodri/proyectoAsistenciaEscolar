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
    
    public Estudiante(String nombre, String apellidoP, String apellidoM, String rut, int edad, String curso)  throws RutInvalidoException {
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

    public ArrayList<Asistencia> getListaAsistencia()
    {
        // mala practica retornar colecciones
        // return listaAsistencia;
        return new ArrayList<>(listaAsistencia);
    }

    public void setListaAsistencia(ArrayList<Asistencia> listaAsistencia)
    {
        // mas de lo mismo , hago el cambio porq podrian modificar la lista desde afuera
        // Por lo que vimos en clase en encapsulamiento , la clase debe poder modificarse solo en si misma

        //this.listaAsistencia = listaAsistencia;
        if(listaAsistencia != null)
        {
            this.listaAsistencia = new  ArrayList<>(listaAsistencia);
        }
    }

    
    // SIA-5: Sobrecarga 1 — agrega objeto Asistencia ya construido
    public void agregarAsistencia(Asistencia asistencia)
    {
        if(listaAsistencia != null)
        {
            listaAsistencia.add(asistencia);

        }
    }

    // SIA-5: Sobrecarga 2 — crea una AsistenciaNormal y la agrega directamente
    public void agregarAsistencia(String fecha, String observacion, boolean puntual)
    {
        /*
        String id = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        listaAsistencia.add(new AsistenciaNormal(id, fecha, observacion, true));
        */
        String id = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        listaAsistencia.add(new AsistenciaNormal(id, fecha, observacion, puntual));
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
    public int contarAsistenciasAnticipadas()
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
    /*
     * Retorna el historial de asistencias como texto.
     * Este método permite reutilizar la información tanto en consola como en ventana.
     */
    public String obtenerHistorialComoTexto() {
        String texto = "";
        Asistencia asistencia_cmp;

        if (listaAsistencia.isEmpty()) {
            return "El estudiante no tiene asistencias registradas.";
        }

        for (int i = 0; i < listaAsistencia.size(); i++) {
            asistencia_cmp = listaAsistencia.get(i);
            texto = texto + asistencia_cmp.getResumen() + "\n";
        }

        return texto;
    }

    public double getPorcentajeAsistencia()
    {
        int total = getCantAsistencia();

        if (total == 0) {
            return 0.0;
        }

        int normales = contarAsistenciaNormales();

        return (normales * 100.0) / total;
    }
    
}
