/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso.modelo;

/**
 * Subclase de Asistencia.
 * La usamos cuando el alumno falta a clases justificadamente o por una razón mayor.
 * Nos permite guardar el 'motivo' exacto (ej. certificado médico).
 */
public class InasistenciaExtraordinaria extends Asistencia{
    private String motivo;

    public InasistenciaExtraordinaria(String id, String fecha, String observacion, String motivo)
    {
        super(id, fecha, observacion);
        this.motivo = motivo;
    }

    public String getMotivo()
    {
        return motivo;
    }

    public void setMotivo(String motivo)
    {
        this.motivo = motivo;
    }

    @Override
    public String getResumen()
    {
        return "Faltó el día " + getFecha() + ". Motivo: " + motivo;
    }
}

