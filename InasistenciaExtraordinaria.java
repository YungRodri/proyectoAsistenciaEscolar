public class InasistenciaExtraordinaria extends Asistencia
{
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