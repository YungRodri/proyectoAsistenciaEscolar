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
public class Asistencia {
    private String id;
    private String fecha;
    private String observacion;

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
    
    public String getResumen()
    {
        return "Registro de asistencia del día " + fecha;
    }
}

