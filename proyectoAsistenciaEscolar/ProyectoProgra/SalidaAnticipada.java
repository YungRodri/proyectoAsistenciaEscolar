public class SalidaAnticipada extends Asistencia
{
    private String horaSalida;

    public SalidaAnticipada(String id, String fecha, String observacion, String horaSalida)
    {
        super(id, fecha, observacion);
        this.horaSalida = horaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    @Override
    public String getResumen()
    {
        return "Salida anticipada el día " + getFecha() +
            " a las " + horaSalida +
            ". Observación: " + getObservacion();

    }
}
