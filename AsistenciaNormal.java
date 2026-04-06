public class AsistenciaNormal extends Asistencia
{


    public AsistenciaNormal(String id, String fecha, String observacion)
    {
        super(id, fecha, observacion);
    }

    @Override
    public String getResumen()
    {
        return "Vino a clases el día " + getFecha();
    }
}