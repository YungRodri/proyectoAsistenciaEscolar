package AsistenciaCurso.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Clase encargada de la Persistencia de Datos (SIA-P1).
 * Su función es guardar y cargar los datos desde los archivos CSV
 * para que no perdamos la información al cerrar el programa.
 */
public class GestorArchivos {

    // CAMBIO 1:
    // Antes solo existía ARCHIVO_ESTUDIANTES.
    // Ahora agregamos una constante para la carpeta y otra para guardar asistencias.
    private static final String CARPETA = "resources";
    private static final String ARCHIVO_ESTUDIANTES = "resources/estudiantes.csv";
    private static final String ARCHIVO_ASISTENCIAS = "resources/asistencias.csv";

    /**
     * Carga todos los estudiantes desde el CSV al iniciar el programa.
     * Si el archivo no existe, no falla, sino que crea uno con alumnos de prueba.
     * @return TreeMap con los estudiantes cargados (RUT -> Estudiante).
     */
    public static TreeMap<String, Estudiante> cargarEstudiantes() {
        TreeMap<String, Estudiante> estudiantes = new TreeMap<>();
        File archivo = new File(ARCHIVO_ESTUDIANTES);

        // CAMBIO 2:
        // Antes, si el archivo no existía, retornaba un TreeMap vacío.
        // Ahora, si no existe, se cargan datos iniciales y se guardan en CSV.
        // Esto ayuda a cumplir SIA-3 y SIA-11.
        if (!archivo.exists()) {
            estudiantes = cargarDatosIniciales();
            guardarEstudiantes(estudiantes);
            return estudiantes;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                if (datos.length == 6) {
                    try {
                        String rut = datos[0];
                        String nombre = datos[1];
                        String apellidoP = datos[2];
                        String apellidoM = datos[3];
                        int edad = Integer.parseInt(datos[4]);
                        String curso = datos[5];

                        // CAMBIO 3:
                        // Como Estudiante ahora puede lanzar RutInvalidoException,
                        // esta creación queda dentro de try-catch.
                        Estudiante e = new Estudiante(nombre, apellidoP, apellidoM, rut, edad, curso);

                        // clave = RUT, búsqueda O(log N) por TreeMap
                        estudiantes.put(rut, e);

                    } catch (RutInvalidoException e) {
                        System.err.println("Línea ignorada por RUT inválido: " + e.getMessage());

                    } catch (NumberFormatException e) {
                        System.err.println("Línea ignorada por edad inválida: " + linea);
                    } catch (EdadInvalidaException e){
                        System.err.println("Linea ignorada por edad negativa: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar estudiantes desde CSV: " + e.getMessage());
        }

        // CAMBIO 4:
        // Después de cargar estudiantes, cargamos sus asistencias.
        // Esto permite recuperar el historial de asistencia al iniciar el programa.
        cargarAsistencias(estudiantes);

        // CAMBIO 5:
        // Si el archivo existe pero está vacío, se cargan datos iniciales.
        if (estudiantes.isEmpty()) {
            estudiantes = cargarDatosIniciales();
            guardarEstudiantes(estudiantes);
        }

        return estudiantes;
    }

    /**
     * Guarda la memoria actual del programa (el mapa global) en los archivos CSV.
     * Siempre guarda la lista actualizada de alumnos y luego llama a guardarAsistencias.
     * @param mapa El TreeMap global con todos los estudiantes en memoria.
     */
    public static void guardarEstudiantes(TreeMap<String, Estudiante> mapa) {

        // CAMBIO 6:
        // Antes se intentaba guardar directamente.
        // Ahora primero aseguramos que exista la carpeta resources.
        crearCarpetaResources();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARCHIVO_ESTUDIANTES), StandardCharsets.UTF_8))) {

            for (Estudiante e : mapa.values()) {
                // Formato: rut;nombre;apellidoP;apellidoM;edad;curso
                String linea = String.format("%s;%s;%s;%s;%d;%s",
                        e.getRut(),
                        e.getNombre(),
                        e.getApellidoP(),
                        e.getApellidoM(),
                        e.getEdad(),
                        e.getCurso());

                bw.write(linea);
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al guardar estudiantes en CSV: " + e.getMessage());
        }

        // CAMBIO 7:
        // Antes solo se guardaban estudiantes.
        // Ahora también se guardan las asistencias de cada estudiante.
        guardarAsistencias(mapa);
    }

    // CAMBIO 8:
    // Método nuevo.
    // Lee el archivo asistencias.csv y agrega cada asistencia
    // al estudiante correspondiente según su RUT.
    private static void cargarAsistencias(TreeMap<String, Estudiante> estudiantes) {
        File archivo = new File(ARCHIVO_ASISTENCIAS);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                /*
                 * Formato:
                 * rutEstudiante;tipo;id;fecha;observacion;datoExtra
                 *
                 * Ejemplos:
                 * 12345678-5;NORMAL;A001;06/05/2026;Presente;true
                 * 11111111-1;INASISTENCIA;A002;06/05/2026;No asiste;Certificado médico
                 * 22222222-2;SALIDA;A003;06/05/2026;Retiro por apoderado;11:30
                 */
                if (datos.length == 6) {
                    String rutEstudiante = datos[0];
                    String tipo = datos[1];
                    String id = datos[2];
                    String fecha = datos[3];
                    String observacion = datos[4];
                    String datoExtra = datos[5];

                    Estudiante estudiante = estudiantes.get(rutEstudiante);

                    if (estudiante != null) {
                        Asistencia asistencia = null;

                        if (tipo.equalsIgnoreCase("NORMAL")) {
                            boolean puntual = Boolean.parseBoolean(datoExtra);
                            asistencia = new AsistenciaNormal(id, fecha, observacion, puntual);

                        } else if (tipo.equalsIgnoreCase("INASISTENCIA")) {
                            asistencia = new InasistenciaExtraordinaria(id, fecha, observacion, datoExtra);

                        } else if (tipo.equalsIgnoreCase("SALIDA")) {
                            asistencia = new SalidaAnticipada(id, fecha, observacion, datoExtra);
                        }

                        if (asistencia != null) {
                            estudiante.agregarAsistencia(asistencia);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar asistencias desde CSV: " + e.getMessage());
        }
    }

    // CAMBIO 9:
    // Método nuevo.
    // Recorre todos los estudiantes y guarda todas sus asistencias
    // en el archivo asistencias.csv.
    private static void guardarAsistencias(TreeMap<String, Estudiante> mapa) {

        crearCarpetaResources();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARCHIVO_ASISTENCIAS), StandardCharsets.UTF_8))) {

            for (Estudiante estudiante : mapa.values()) {

                for (Asistencia asistencia : estudiante.getListaAsistencia()) {
                    String tipo = "";
                    String datoExtra = "";

                    /*
                     * CAMBIO 10:
                     * Como Asistencia es abstracta, usamos instanceof para saber
                     * qué tipo concreto de asistencia estamos guardando.
                     */
                    if (asistencia instanceof AsistenciaNormal) {
                        tipo = "NORMAL";

                        AsistenciaNormal normal = (AsistenciaNormal) asistencia;
                        datoExtra = String.valueOf(normal.isPuntual());

                    } else if (asistencia instanceof InasistenciaExtraordinaria) {
                        tipo = "INASISTENCIA";

                        InasistenciaExtraordinaria inasistencia = (InasistenciaExtraordinaria) asistencia;
                        datoExtra = inasistencia.getMotivo();

                    } else if (asistencia instanceof SalidaAnticipada) {
                        tipo = "SALIDA";

                        SalidaAnticipada salida = (SalidaAnticipada) asistencia;
                        datoExtra = salida.getHoraSalida();
                    }

                    // Formato: rutEstudiante;tipo;id;fecha;observacion;datoExtra
                    String linea = String.format("%s;%s;%s;%s;%s;%s",
                            estudiante.getRut(),
                            tipo,
                            asistencia.getId(),
                            asistencia.getFecha(),
                            asistencia.getObservacion(),
                            datoExtra);

                    bw.write(linea);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error al guardar asistencias en CSV: " + e.getMessage());
        }
    }

    // CAMBIO 11:
    // Método nuevo.
    // Crea datos iniciales para que el sistema no parta vacío.
    // Esto ayuda a cumplir la observación de que faltaban datos iniciales.
    private static TreeMap<String, Estudiante> cargarDatosIniciales() {
        TreeMap<String, Estudiante> estudiantes = new TreeMap<>();

        try {
            Estudiante e1 = new Estudiante("Ana", "Pérez", "Soto", "12345678-5", 14, "8 Basico");
            Estudiante e2 = new Estudiante("Luis", "González", "Rojas", "11111111-1", 15, "1 Medio");
            Estudiante e3 = new Estudiante("María", "Muñoz", "Castro", "22222222-2", 16, "2 Medio");

            e1.agregarAsistencia(new AsistenciaNormal("A001", "06/05/2026", "Presente en clases", true));
            e1.agregarAsistencia(new SalidaAnticipada("A002", "07/05/2026", "Retiro por apoderado", "11:30"));

            e2.agregarAsistencia(new InasistenciaExtraordinaria("A003", "06/05/2026", "No asiste", "Certificado médico"));

            e3.agregarAsistencia(new AsistenciaNormal("A004", "06/05/2026", "Presente con atraso", false));

            estudiantes.put(e1.getRut(), e1);
            estudiantes.put(e2.getRut(), e2);
            estudiantes.put(e3.getRut(), e3);

        } catch (RutInvalidoException | EdadInvalidaException e) {
            System.err.println("Error en datos iniciales: " + e.getMessage());
        }

        return estudiantes;
    }

    // CAMBIO 12:
    // Método nuevo.
    // Crea la carpeta resources si no existe.
    private static void crearCarpetaResources() {
        File carpeta = new File(CARPETA);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}
