package AsistenciaCurso.controlador;

import AsistenciaCurso.Main;
import AsistenciaCurso.modelo.Estudiante;
import AsistenciaCurso.Main;
import AsistenciaCurso.modelo.EstudianteNoEncontradoException;

import java.util.Collection;

/**
 * Controlador para la gestión de estudiantes.
 * Como nos pidieron aplicar MVC (SIA-O4), creamos esta clase para que
 * la interfaz gráfica (los botones y paneles) no modifique directamente
 * las variables de Estudiante. Todo pasa por aquí primero.
 */
public class EstudianteControlador {

    /**
     * Busca un estudiante en nuestra "base de datos" global usando su RUT.
     * Nos sirve para cargar los datos en pantalla cuando el profe o usuario lo busca.
     *
     * @param rut El RUT del estudiante que queremos buscar.
     * @return El objeto Estudiante si lo pillamos.
     * @throws EstudianteNoEncontradoException Si el RUT no está en la lista global, lanzamos este error.
     */
    public static Estudiante obtener(String rut) throws EstudianteNoEncontradoException {
        return Main.obtenerEstudiantePorRut(rut);
    }

    /**
     * Agrega un estudiante nuevo al mapa global del programa.
     * Así no tocamos el mapa desde la interfaz gráfica.
     *
     * @param estudiante El alumno nuevo que vamos a guardar.
     */
    public static void agregar(Estudiante estudiante) {
        Main.listaGlobal.put(estudiante.getRut(), estudiante);
    }

    /**
     * Borra a un estudiante del registro global usando su RUT.
     * Útil para la pestaña de "Gestionar Estudiantes".
     *
     * @param rut El RUT del estudiante que se va del colegio/sistema.
     */
    public static void eliminar(String rut) {
        Main.listaGlobal.remove(rut);
    }

    /**
     * Nos devuelve todos los estudiantes que hay en el colegio.
     * Lo usamos en casi todos los paneles para recorrerlos y llenar tablas.
     *
     * @return La colección entera de estudiantes del mapa global.
     */
    public static Collection<Estudiante> listar() {
        return Main.listaGlobal.values();
    }
}
