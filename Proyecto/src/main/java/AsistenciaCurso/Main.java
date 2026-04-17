package AsistenciaCurso;

import java.util.TreeMap;
import java.util.Scanner;
import java.util.UUID;

/**
 * Clase principal del Sistema de Asistencia Escolar.
 * Contiene la lista global de estudiantes, el menú de consola
 * y el punto de entrada de la aplicación (main).
 */
public class Main {

    // Lista global de estudiantes, la clave es el RUT
    public static TreeMap<String, Estudiante> listaGlobal = new TreeMap<>();

    static Scanner entrada = new Scanner(System.in);

    // Listado de cursos disponibles en el sistema
    public static String[] listaCursos = {
            "1 Basico", "2 Basico", "3 Basico", "4 Basico", "5 Basico",
            "6 Basico", "7 Basico", "8 Basico", "1 Medio", "2 Medio",
            "3 Medio", "4 Medio"
    };

    // Utilidades

    /**
     * Lee un entero desde consola con manejo de excepción interno.
     * 
     * @return El entero leído, o -1 si la entrada no es numérica.
     */
    private static int leerEntero() {
        try {
            return Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Obtiene un estudiante de la lista global por su RUT.
     * 
     * @param rut El RUT a buscar.
     * @return El estudiante encontrado.
     * @throws EstudianteNoEncontradoException si no existe ningún estudiante con
     *                                         ese RUT.
     */
    public static Estudiante obtenerEstudiantePorRut(String rut) throws EstudianteNoEncontradoException {
        Estudiante e = listaGlobal.get(rut);
        if (e == null) {
            throw new EstudianteNoEncontradoException(rut);
        }
        return e;
    }

    // Gestión de Estudiantes

    /**
     * Agrega uno o más estudiantes a la lista global.
     * Valida el RUT y la edad por consola.
     */
    public static void agregarEstudiante() {
        String opcion;
        do {
            System.out.println("\nAgregar Estudiante");
            System.out.print("Nombre: ");
            String nombre = entrada.nextLine();

            System.out.print("Apellido Paterno: ");
            String apellidoP = entrada.nextLine();

            System.out.print("Apellido Materno: ");
            String apellidoM = entrada.nextLine();

            System.out.print("RUT (ej: 12345678-9): ");
            String rut = entrada.nextLine().trim();

            // Validación de RUT
            try {
                Estudiante temp = new Estudiante("x", "x", "x", rut, 1, "x");
                if (!temp.validarRut(rut)) {
                    throw new RutInvalidoException(rut);
                }
            } catch (RutInvalidoException e) {
                System.out.println("" + e.getMessage());
                System.out.print("¿Continuar? (S/N): ");
                opcion = entrada.nextLine();
                continue;
            }

            // Validación de edad
            int edad = -1;
            while (edad < 0) {
                System.out.print("Edad: ");
                try {
                    edad = Integer.parseInt(entrada.nextLine().trim());
                    if (edad < 0)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para la edad.");
                    edad = -1;
                }
            }

            int numCurso;
            do {
                System.out.println("Seleccione un Curso (1-12):");
                for (int i = 0; i < listaCursos.length; i++) {
                    System.out.println((i + 1) + ". " + listaCursos[i]);
                }
                numCurso = leerEntero();
                if (numCurso < 1 || numCurso > listaCursos.length) {
                    System.out.println("Ingrese un número válido (1-12).");
                }
            } while (numCurso < 1 || numCurso > listaCursos.length);

            String curso = listaCursos[numCurso - 1];
            Estudiante alumno = new Estudiante(nombre, apellidoP, apellidoM, rut, edad, curso);
            listaGlobal.put(alumno.getRut(), alumno);
            System.out.println("Estudiante registrado correctamente.");

            System.out.print("¿Agregar otro? (S/N): ");
            opcion = entrada.nextLine();
        } while (opcion.equalsIgnoreCase("S"));
    }

    /**
     * Muestra el listado de todos los estudiantes registrados.
     */
    public static void mostrarEstudiantes() {
        System.out.println("\nLista de Estudiantes (" + listaGlobal.size() + " registrados)");
        if (listaGlobal.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        int i = 1;
        for (Estudiante alumno : listaGlobal.values()) {
            System.out.println(i + ". RUT: " + alumno.getRut() + " | " + alumno.getNombreCompleto() + " | Curso: "
                    + alumno.getCurso() + " | Edad: " + alumno.getEdad());
            i++;
        }
    }

    /**
     * Busca un estudiante por RUT y muestra su información.
     */
    public static void buscarEstudiante() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante e = obtenerEstudiantePorRut(rut);
            System.out.println("Encontrado: " + e.getNombreCompleto()
                    + " | Curso: " + e.getCurso()
                    + " | Edad: " + e.getEdad()
                    + " | Asistencias: " + e.getCantAsistencia());
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Edita el nombre y apellidos de un estudiante existente.
     */
    public static void editarEstudiante() {
        System.out.print("\nIngrese el RUT del estudiante a editar: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.println("Editando: " + alumno.getNombreCompleto());

            System.out.print("Nuevo nombre (Enter para mantener [" + alumno.getNombre() + "]): ");
            String nombre = entrada.nextLine();
            if (!nombre.isEmpty())
                alumno.setNombre(nombre);

            System.out.print("Nuevo apellido paterno (Enter para mantener [" + alumno.getApellidoP() + "]): ");
            String ap = entrada.nextLine();
            if (!ap.isEmpty())
                alumno.setApellidoP(ap);

            System.out.print("Nuevo apellido materno (Enter para mantener [" + alumno.getApellidoM() + "]): ");
            String am = entrada.nextLine();
            if (!am.isEmpty())
                alumno.setApellidoM(am);

            System.out.println("Estudiante actualizado correctamente.");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Elimina un estudiante de la lista global por RUT.
     */
    public static void eliminarEstudiante() {
        System.out.print("\nIngrese el RUT del estudiante a eliminar: ");
        String rut = entrada.nextLine().trim();
        try {
            obtenerEstudiantePorRut(rut); // Lanza excepción si no existe
            listaGlobal.remove(rut);
            System.out.println("Estudiante eliminado correctamente.");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    // Gestión de Asistencias

    /**
     * Registra una asistencia (Normal, Inasistencia o Salida Anticipada)
     * para un estudiante.
     */
    public static void registrarAsistencia() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.println("Registrando asistencia para: " + alumno.getNombreCompleto());

            System.out.print("Fecha (DD/MM/AAAA): ");
            String fecha = entrada.nextLine();
            System.out.print("Observación (puede estar vacía): ");
            String obs = entrada.nextLine();

            // Generar ID único de 8 caracteres
            String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            System.out.println("Tipo de registro:");
            System.out.println("1. Asistencia Normal");
            System.out.println("2. Inasistencia Extraordinaria");
            System.out.println("3. Salida Anticipada");
            int tipo = leerEntero();

            Asistencia asistencia;
            switch (tipo) {
                case 1:
                    System.out.print("¿Llegó puntual? (S/N): ");
                    boolean puntual = entrada.nextLine().trim().equalsIgnoreCase("S");
                    asistencia = new AsistenciaNormal(id, fecha, obs, puntual);
                    break;
                case 2:
                    System.out.print("Motivo de inasistencia: ");
                    String motivo = entrada.nextLine();
                    asistencia = new InasistenciaExtraordinaria(id, fecha, obs, motivo);
                    break;
                case 3:
                    System.out.print("Hora de salida (HH:MM): ");
                    String hora = entrada.nextLine();
                    asistencia = new SalidaAnticipada(id, fecha, obs, hora);
                    break;
                default:
                    System.out.println("Tipo inválido. Operación cancelada.");
                    return;
            }
            alumno.agregarAsistencia(asistencia);
            System.out.println("Asistencia registrada. ID asignado: " + id);

        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Muestra el historial completo de asistencias de un estudiante.
     */
    public static void verHistorial() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.println("\nHistorial de " + alumno.getNombreCompleto());
            System.out.println("Normales: " + alumno.contarAsistenciaNormales() + " | Inasistencias: "
                    + alumno.contarAsistenciaExtraordinarias() + " | Salidas anticipadas: "
                    + alumno.contarAsistenciasAnticipadas());
            System.out.println();
            if (alumno.getListaAsistencia().isEmpty()) {
                System.out.println("Sin registros de asistencia.");
            } else {
                // Llama al método polimórfico getResumen()
                for (Asistencia a : alumno.getListaAsistencia()) {
                    System.out.println("[ID: " + a.getId() + "] " + a.getResumen());
                }
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Edita la observación de una asistencia específica por su ID.
     */
    public static void editarAsistenciaMenu() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.print("ID de la asistencia a editar: ");
            String idAsistencia = entrada.nextLine().trim().toUpperCase();

            Asistencia a = alumno.buscarAsistencia(idAsistencia);
            if (a == null) {
                System.out.println("No se encontró una asistencia con el ID: " + idAsistencia);
                return;
            }
            System.out.println("Registro actual: " + a.getResumen());
            System.out.print("Nueva observación: ");
            String nuevaObs = entrada.nextLine();
            a.setObservacion(nuevaObs);
            System.out.println("Observación actualizada correctamente.");

        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Elimina una asistencia del historial de un estudiante por su ID.
     */
    public static void eliminarAsistenciaMenu() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.print("ID de la asistencia a eliminar: ");
            String idAsistencia = entrada.nextLine().trim().toUpperCase();

            if (alumno.eliminarAsistencia(idAsistencia)) {
                System.out.println("Asistencia eliminada correctamente.");
            } else {
                System.out.println("No se encontró una asistencia con el ID: " + idAsistencia);
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Busca una asistencia por ID en el historial de un estudiante.
     */
    public static void buscarAsistenciaMenu() {
        System.out.print("\nIngrese el RUT del estudiante: ");
        String rut = entrada.nextLine().trim();
        try {
            Estudiante alumno = obtenerEstudiantePorRut(rut);
            System.out.print("ID de la asistencia: ");
            String idAsistencia = entrada.nextLine().trim().toUpperCase();

            Asistencia a = alumno.buscarAsistencia(idAsistencia);
            if (a == null) {
                System.out.println("No se encontró una asistencia con el ID: " + idAsistencia);
            } else {
                System.out.println("Encontrado:");
                System.out.println("  Resumen   : " + a.getResumen());
                System.out.println("  Observación: " + a.getObservacion());
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("" + e.getMessage());
        }
    }

    // Funcionalidades Adicionales

    /**
     * Genera la lista ordenada alfabéticamente de un curso seleccionado.
     */
    public static Curso generarCurso() {
        int numCurso;
        System.out.println("\nSeleccione un curso:");
        for (int i = 0; i < listaCursos.length; i++) {
            System.out.println((i + 1) + ". " + listaCursos[i]);
        }
        numCurso = leerEntero();
        if (numCurso >= 1 && numCurso <= listaCursos.length) {
            return new Curso(listaCursos[numCurso - 1]);
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }

    // Main

    public static void main(String[] args) {
        // SIA-11: Cargar datos al iniciar desde CSV (persistencia batch)
        listaGlobal = GestorArchivos.cargarEstudiantes();

        System.out.println("Sistema de Asistencia Escolar");
        System.out.println("Seleccione el modo de ejecucion:");
        System.out.println("1. Modo Interfaz Grafica (Ventana)");
        System.out.println("2. Modo Consola");
        System.out.print("Opcion: ");

        // Selector de modo al iniciar (SIA-10)
        int modo = 2;
        try {
            modo = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Iniciando en modo consola por defecto...");
        }

        if (modo == 1) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Ventana().setVisible(true);
                }
            });
            return;
        }

        // Menu por consola
        int opcion;
        do {
            System.out.println("\nMenu Principal");
            System.out.println("Estudiantes:");
            System.out.println(" 1. Agregar Estudiante");
            System.out.println(" 2. Listar Estudiantes");
            System.out.println(" 3. Buscar Estudiante por RUT");
            System.out.println(" 4. Editar Estudiante");
            System.out.println(" 5. Eliminar Estudiante");
            System.out.println("Asistencias:");
            System.out.println(" 6. Registrar Asistencia");
            System.out.println(" 7. Ver Historial de Asistencias");
            System.out.println(" 8. Editar Observacion de Asistencia");
            System.out.println(" 9. Eliminar Asistencia");
            System.out.println("10. Buscar Asistencia por ID");
            System.out.println("Reportes:");
            System.out.println("11. Generar Lista Ordenada de Curso");
            System.out.println("12. Alumnos con exceso de inasistencias");
            System.out.println("Sistema:");
            System.out.println(" 0. Guardar y Salir");
            System.out.print("Opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    agregarEstudiante();
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    buscarEstudiante();
                    break;
                case 4:
                    editarEstudiante();
                    break;
                case 5:
                    eliminarEstudiante();
                    break;
                case 6:
                    registrarAsistencia();
                    break;
                case 7:
                    verHistorial();
                    break;
                case 8:
                    editarAsistenciaMenu();
                    break;
                case 9:
                    eliminarAsistenciaMenu();
                    break;
                case 10:
                    buscarAsistenciaMenu();
                    break;
                case 11:
                    Curso curso = generarCurso();
                    if (curso != null) {
                        curso.poblarCurso(listaGlobal);
                        // SIA-5: usa sobrecarga mostrarEstudiantes(String filtroCurso)
                        curso.mostrarEstudiantes(curso.getNombre());
                        System.out.println();
                        curso.generarLista(); // lista ordenada (SIA-9)
                    }
                    break;
                case 12:
                    System.out.print("Numero maximo de inasistencias permitidas: ");
                    int max = leerEntero();
                    boolean hayAlumnos = false;
                    for (Estudiante e : listaGlobal.values()) {
                        if (e.contarAsistenciaExtraordinarias() > max) {
                            System.out.println("  " + e.getNombreCompleto()
                                    + " - " + e.contarAsistenciaExtraordinarias() + " inasistencias");
                            hayAlumnos = true;
                        }
                    }
                    if (!hayAlumnos)
                        System.out.println("Ningun alumno supera el limite ingresado.");
                    break;
                case 0:
                    // SIA-11: Grabar datos al salir
                    System.out.println("Guardando datos...");
                    GestorArchivos.guardarEstudiantes(listaGlobal);
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida. Ingrese un numero entre 0 y 12.");
            }
        } while (opcion != 0);
    }
}
