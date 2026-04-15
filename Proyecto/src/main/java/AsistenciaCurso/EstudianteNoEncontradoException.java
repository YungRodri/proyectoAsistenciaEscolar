package AsistenciaCurso;

/**
 * Excepción personalizada lanzada cuando no existe ningún estudiante
 * registrado con el RUT proporcionado.
 * Cumple requisito SIA-12.
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
