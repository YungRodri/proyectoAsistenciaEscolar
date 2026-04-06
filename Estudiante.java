import java.util.ArrayList;

public class Estudiante extends Persona
{
    private String curso;
    private ArrayList<Asistencia> listaAsistencia;

    public Estudiante(String rut, String nombre, String apellidoP, String apellidoM, int edad, String curso)
    {
        super(rut, nombre, apellidoP, apellidoM, edad);
        this.curso = curso;
        this.listaAsistencia = new ArrayList<>();
    }

    public String getCurso()
    {
        return curso;
    }

    public void setCurso(String curso)
    {
        this.curso = curso;
    }

    public ArrayList<Asistencia> getListaAsistencia()
    {
        return listaAsistencia;
    }

    public void agregarAsistencia(Asistencia asistencia)
    {
        listaAsistencia.add(asistencia);
    }
}