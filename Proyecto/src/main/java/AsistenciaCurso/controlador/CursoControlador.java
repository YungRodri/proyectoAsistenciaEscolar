package AsistenciaCurso.controlador;

import AsistenciaCurso.Main;
import AsistenciaCurso.modelo.Curso;

/**
 * Controlador para las acciones relacionadas a un curso completo.
 * A través de esta clase, la interfaz puede pedir los datos de un curso
 * sin tener que bucear en el mapa global directamente.
 */
public class CursoControlador {

    /**
     * Construye un curso y lo llena con todos los estudiantes correspondientes.
     * Básicamente busca en el colegio (mapaGlobal) quiénes están en este curso
     * y los agrupa en el objeto Curso que devuelve.
     *
     * @param nombreCurso El curso que queremos armar (ej: "1 Medio").
     * @return El objeto Curso con sus estudiantes dentro.
     */
    public static Curso obtenerCurso(String nombreCurso) {
        Curso curso = new Curso(nombreCurso);
        curso.poblarCurso(Main.listaGlobal);
        return curso;
    }
}
