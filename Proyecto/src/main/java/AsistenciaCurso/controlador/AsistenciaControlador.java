package AsistenciaCurso.controlador;

import AsistenciaCurso.modelo.Asistencia;
import AsistenciaCurso.modelo.Estudiante;

/**
 * Controlador específico para manejar todo lo que tenga que ver con la asistencia.
 * Separamos esta lógica del modelo para cumplir con el MVC (SIA-O4) y mantener 
 * la interfaz gráfica lo más limpia posible.
 */
public class AsistenciaControlador {

    /**
     * Le guarda una asistencia al estudiante que le pasemos.
     * Esto es llamado directamente por los botones de la interfaz gráfica.
     *
     * @param alumno El estudiante al que le vamos a anotar la asistencia.
     * @param asistencia El registro de asistencia ya creado.
     */
    public static void registrar(Estudiante alumno, Asistencia asistencia) {
        alumno.agregarAsistencia(asistencia);
    }

    /**
     * Borra un registro del historial de un alumno usando la ID única.
     *
     * @param alumno El estudiante afectado.
     * @param id El código de la asistencia que nos equivocamos y queremos borrar.
     * @return true si logró borrarla, false si no pilló la ID.
     */
    public static boolean eliminar(Estudiante alumno, String id) {
        return alumno.eliminarAsistencia(id);
    }

    /**
     * Revisa el historial del estudiante para encontrar una asistencia específica.
     *
     * @param alumno El estudiante dueño del historial.
     * @param id El código de la asistencia.
     * @return La asistencia buscada, o null si le pasamos un ID fantasma.
     */
    public static Asistencia buscar(Estudiante alumno, String id) {
        return alumno.buscarAsistencia(id);
    }

    /**
     * Nos calcula el porcentaje de asistencias 'Normales' frente al total.
     * Súper útil para el gráfico de torta de las estadísticas.
     *
     * @param alumno El estudiante a calcular.
     * @return Un número double (ej. 85.5).
     */
    public static double calcularPorcentaje(Estudiante alumno) {
        return alumno.getPorcentajeAsistencia();
    }
}
