public class Main
{
    public static void main(String[] args)
    {

        Curso curso = new Curso("3A");

        Estudiante e1 = new Estudiante("19.111-1", "Ana", "Rojas", "Perez", 18, "3A");
        Estudiante e2 = new Estudiante("20.222-2", "Bruno", "Soto", "Diaz", 17, "3A");

        curso.inscribirEstudiante(e1);
        curso.inscribirEstudiante(e2);

        Asistencia a1 = new AsistenciaNormal("A1", "10-04-2026", "Sin observación");
        Asistencia a2 = new InasistenciaExtraordinaria("A2", "11-04-2026", "Resfriado", "Médico");

        e1.agregarAsistencia(a1);
        e1.agregarAsistencia(a2);

        System.out.println("Historial de " + e1.getNombreCompleto());

        for (Asistencia a : e1.getListaAsistencia())
        {
            System.out.println(a.getResumen());
        }
        System.out.println("\nEstudiantes en el curso:");
        curso.mostrarEstudiantes();
    }
}
