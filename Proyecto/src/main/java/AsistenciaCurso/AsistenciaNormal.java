package AsistenciaCurso;

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
