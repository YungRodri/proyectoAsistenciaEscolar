public class Asistencia
{
    private String id;
    private String fecha;
    private String observacion;

    public Asistencia(String id, String fecha, String observacion)
    {
        this.id = id;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFecha()
    {
        return fecha;
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public String getObservacion()
    {
        return observacion;
    }

    public void setObservacion(String observacion)
    {
        this.observacion = observacion;
    }

    public String getResumen()
    {
        return "Registro de asistencia del día " + fecha;
    }
}
