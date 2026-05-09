/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso.modelo;

/**
 * Clase padre (abstracta) para representar cualquier registro del historial de un alumno.
 * La hicimos abstracta porque no tiene sentido registrar una "Asistencia" en el aire;
 * siempre debe ser de un tipo específico (Normal, Extraordinaria o Salida).
 */
public abstract class Asistencia {
    private String id;
    private String fecha;
    private String observacion;

    /**
     * @param id Código único autogenerado.
     * @param fecha Fecha en formato DD/MM/AAAA.
     * @param observacion Una nota opcional sobre la asistencia.
     */
    public Asistencia(String id, String fecha, String observacion) {
        this.id = id;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    /**
     * POLIMORFISMO: Cada subclase implementará su propia versión del resumen.
     * Así en la tabla gráfica o en consola podemos llamar a getResumen() 
     * sin importar qué tipo de asistencia sea.
     */
    public abstract String getResumen();
    /*{
        return "Registro de asistencia del día " + fecha;
    }*/
}

