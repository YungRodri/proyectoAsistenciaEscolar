import java.util.TreeMap;

public class Curso
{
    private String nombre;
    private TreeMap<String, Estudiante> estudiantes;

    public Curso(String nombre)
    {
        this.nombre = nombre;
        this.estudiantes = new TreeMap<>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public TreeMap<String, Estudiante> getEstudiantes()
    {
        return estudiantes;
    }

    public boolean inscribirEstudiante(Estudiante estudiante)
    {
        if (estudiante != null && !estudiantes.containsKey(estudiante.getRut()))
        {
            estudiantes.put(estudiante.getRut(), estudiante);
            return true;
        }
        return false;
    }

    public Estudiante buscarEstudiante(String rut)
    {
        return estudiantes.get(rut);
    }

    public boolean retirarEstudiante(String rut)
    {
        if (estudiantes.containsKey(rut))
        {
            estudiantes.remove(rut);
            return true;
        }
        return false;
    }

    public boolean existeEstudiante(String rut)
    {
        return estudiantes.containsKey(rut);
    }

    public int getCantidadAlumnos()
    {
        return estudiantes.size();
    }

    public void mostrarEstudiantes()
    {
        for (Estudiante estudiante : estudiantes.values())
        {
            System.out.println(estudiante.getRut() + " - " + estudiante.getNombreCompleto());
        }
    }
}