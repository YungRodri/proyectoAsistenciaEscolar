package AsistenciaCurso;

/**
 * Excepción personalizada lanzada cuando el RUT ingresado no cumple
 * con el formato y dígito verificador del RUT chileno.
 * Cumple requisito SIA-12.
 */
public class RutInvalidoException extends Exception {

    private final String rutIngresado;

    /**
     * @param rut El RUT que causó el error de validación.
     */
    public RutInvalidoException(String rut) {
        super("RUT inválido: \"" + rut + "\". Verifique el número y el dígito verificador.");
        this.rutIngresado = rut;
    }

    /** Retorna el RUT que causó la excepción. */
    public String getRutIngresado() {
        return rutIngresado;
    }
}
