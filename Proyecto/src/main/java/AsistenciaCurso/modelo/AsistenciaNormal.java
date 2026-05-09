package AsistenciaCurso.modelo;

/**
 * Subclase concreta que hereda de Asistencia.
 * Representa cuando un alumno sí vino a clases. Agregamos el booleano 'puntual'
 * para saber si llegó atrasado o no, lo cual nos sirve para las estadísticas.
 */
public class AsistenciaNormal extends Asistencia
{
    private boolean puntual;


    public AsistenciaNormal(String id, String fecha, String observacion, boolean  puntual)
    {
        super(id, fecha, observacion);
        this.puntual= puntual;
    }
    public boolean isPuntual() {
        return puntual;
    }

    public void setPuntual(boolean puntual) {
        this.puntual = puntual;
    }


    @Override
    public String getResumen() {
        if (puntual) {
            return "Vino a clases el día " + getFecha() + " y llegó temprano.";
        } else {
            return "Vino a clases el día " + getFecha() + " pero no llegó temprano.";
        }
    }

}
