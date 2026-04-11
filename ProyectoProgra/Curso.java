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
    public void mostrarAsistenciaEstudiantes(String rut)
    {
        Estudiante estudiante_cmp = estudiantes.get(rut);
        if(estudiante_cmp != null){
            estudiante_cmp.mostrarHistorial();
        }
    }
    public void listarEstudianteConMasdeInasistencias(int max)
    {
        for(Estudiante estudiante_cmp : estudiantes.values())
        {
            if(estudiante_cmp.contarAsistenciaExtraordinarias() > max)
            {
                System.out.println("Rut -> " + estudiante_cmp.getRut() + " - " + estudiante_cmp.getNombreCompleto());
            }
        }

    }
    public void mostrarAsistenciasDeEstudiante(String Rut)
    {
        Estudiante estudiante_cmp = estudiantes.get(Rut);
        if(estudiante_cmp != null)
        {
            estudiante_cmp.mostrarHistorial();

        }
        else
        {
            System.out.println("Estudiante no encontrado");
        }

    }
    public boolean editarAsistenciaDeEstudiante(String rut , String idAsistenica , String newObserv)
    {
        Estudiante  estudiante_cmp = estudiantes.get(rut );
        if(estudiante_cmp != null)
        {
            return estudiante_cmp.editarAsistencia(idAsistenica, newObserv);
        }
        return false;
    }
    public boolean eliminarAsistenciaDeEstudiante(String rut , String idAsistenica)
    {
        Estudiante  estudiante_cmp = estudiantes.get(rut );
        if(estudiante_cmp != null)
        {
            return estudiante_cmp.eliminarAsistencia(idAsistenica);

        }
        return false;

    }
    public void registrarAsistencia(String rut, Asistencia asistencia)
    {
        Estudiante estudiante_cmp = estudiantes.get(rut);

        if (estudiante_cmp != null)
        {
            estudiante_cmp.agregarAsistencia(asistencia);
        }
        else
        {
            System.out.println("Estudiante no encontrado");
        }
    }


}
