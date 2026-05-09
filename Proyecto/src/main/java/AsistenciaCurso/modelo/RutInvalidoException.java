package AsistenciaCurso.modelo;

/**
 * Excepción personalizada para atrapar RUTs inválidos.
 * Nos sirve para que el programa no colapse si se intenta ingresar
 * un estudiante con un RUT que no cumple la fórmula del dígito verificador.
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
