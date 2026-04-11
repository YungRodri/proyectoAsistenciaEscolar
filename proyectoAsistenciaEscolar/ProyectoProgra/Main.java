{
    public static void main(String[] args)
    {
        Curso curso = new Curso("3A");

        Estudiante e1 = new Estudiante("19.111.111-1", "Ana", "Rojas", "Perez", 18, "3A");
        curso.inscribirEstudiante(e1);

        curso.registrarAsistencia("19.111.111-1",
                new AsistenciaNormal("A1", "10-04-2026", "Sin observación"));

        curso.registrarAsistencia("19.111.111-1",
                new InasistenciaExtraordinaria("A2", "11-04-2026", "Faltó a clases", "Médico"));

        curso.registrarAsistencia("19.111.111-1",
                new SalidaAnticipada("A3", "12-04-2026", "Retiro anticipado", "11:30"));

        curso.mostrarAsistenciasDeEstudiante("19.111.111-1");

        System.out.println("Inasistencias extraordinarias: " + e1.contarAsistenciaExtraordinarias());
        System.out.println("Salidas anticipadas: " + e1.contarAsistenciaAnticpadas());
    }
}
