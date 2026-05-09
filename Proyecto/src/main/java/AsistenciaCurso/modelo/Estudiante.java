/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import AsistenciaCurso.EdadInvalidaException;
import AsistenciaCurso.RutInvalidoException;

/**
 * Representa a un estudiante de nuestro colegio. 
 * Además de sus datos personales (heredados de Persona), maneja a qué curso pertenece
 * y guarda su lista de asistencias a lo largo del año.
 */
public class Estudiante extends Persona{
    private String curso;
    private ArrayList<Asistencia> listaAsistencia;
    
    /**
     * Constructor del estudiante. 
     * Como hereda de Persona, si le pasamos un RUT malo o edad negativa, 
     * saltarán las excepciones automáticamente.
     */
    public Estudiante(String nombre, String apellidoP, String apellidoM, String rut, int edad, String curso)  throws RutInvalidoException, EdadInvalidaException {
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

    /**
     * OBTENER EL HISTORIAL.
     * Retornamos una lista "unmodifiable" (de solo lectura). 
     * Hicimos esto como corrección porque retornar la lista directamente es una mala práctica
     * que rompe el encapsulamiento.
     *
     * @return Una vista de solo lectura del historial de asistencias.
     */
    public List<Asistencia> getListaAsistencia()
    {
        return Collections.unmodifiableList(listaAsistencia);
    }

    public void setListaAsistencia(ArrayList<Asistencia> listaAsistencia)
    {
        // mas de lo mismo , hago el cambio porq podrian modificar la lista desde afuera
        // Por lo que vimos en clase en encapsulamiento , la clase debe poder modificarse solo en si misma
        if(listaAsistencia != null)
        {
            this.listaAsistencia = new ArrayList<>(listaAsistencia);
        }
    }

    /**
     * SOBRECARGA 1: Agregar asistencia recibiendo el objeto ya instanciado.
     * La usamos cuando creamos el objeto Asistencia en los controladores
     * (por ejemplo, desde la interfaz gráfica ya armamos el objeto completo).
     *
     * @param asistencia El objeto de asistencia a guardar en el historial.
     */
    public void agregarAsistencia(Asistencia asistencia)
    {
        if(listaAsistencia != null)
        {
            listaAsistencia.add(asistencia);

        }
    }

    /**
     * SOBRECARGA 2: Agregar asistencia pasando solo los datos básicos.
     * Los ayudantes nos preguntaron por qué hicimos esta sobrecarga:
     * La justificamos porque al probar el programa en modo consola, era muy tedioso
     * tener que instanciar objetos completos a mano en el Main. Con este método,
     * simplemente le pasamos la fecha y la observación como strings, y la clase Estudiante
     * se encarga de crear el objeto AsistenciaNormal por nosotros. Nos ahorra mucho código repetido.
     *
     * @param fecha La fecha en formato DD/MM/AAAA.
     * @param observacion Una nota opcional sobre la asistencia.
     * @param puntual true si llegó a la hora, false si llegó tarde.
     */
    public void agregarAsistencia(String fecha, String observacion, boolean puntual)
    {
        /*
        String id = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        listaAsistencia.add(new AsistenciaNormal(id, fecha, observacion, true));
        */
        String id = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        listaAsistencia.add(new AsistenciaNormal(id, fecha, observacion, puntual));
    }

    /**
     * Busca un registro de asistencia en el historial usando su ID único.
     * @param id El código autogenerado de la asistencia.
     * @return La asistencia encontrada, o null si no existe.
     */
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
