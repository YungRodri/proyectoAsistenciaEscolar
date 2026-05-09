package AsistenciaCurso.modelo;

/**
 * Excepción propia (SIA-M2) para cuando buscamos un alumno por RUT
 * y no existe en los registros. Así el programa lanza esto y mostramos
 * un popup, evitando un temido NullPointerException.
 */
public class EstudianteNoEncontradoException extends Exception {

    private final String rutBuscado;

    /**
     * @param rut El RUT que no fue encontrado en el sistema.
     */
    public EstudianteNoEncontradoException(String rut) {
        super("No se encontró ningún estudiante con el RUT: " + rut);
        this.rutBuscado = rut;
    }

    /** Retorna el RUT que no fue encontrado. */
    public String getRutBuscado() {
        return rutBuscado;
    }
}
