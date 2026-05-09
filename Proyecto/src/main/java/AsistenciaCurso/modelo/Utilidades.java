package AsistenciaCurso.modelo;

/**
 * Clase utilitaria con métodos estáticos de validación y ayuda general.
 */
public class Utilidades {

    /**
     * Valida que una cadena tenga el formato de fecha DD/MM/AAAA y que los
     * valores de día, mes y año sean coherentes.
     *
     * @param fecha Cadena a validar.
     * @return {@code true} si el formato y los valores son válidos.
     */
    public static boolean validarFecha(String fecha) {
        if (fecha == null || !fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        String[] partes = fecha.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);

        if (mes < 1 || mes > 12) return false;
        if (dia < 1 || dia > 31) return false;
        if (anio < 1900 || anio > 2100) return false;

        // Meses con solo 30 días
        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) return false;

        // Febrero
        if (mes == 2) {
            boolean bisiesto = (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
            if (dia > (bisiesto ? 29 : 28)) return false;
        }

        return true;
    }

    /**
     * Compara dos fechas con formato DD/MM/AAAA.
     *
     * @param f1 Primera fecha.
     * @param f2 Segunda fecha.
     * @return Negativo si f1 es anterior a f2, cero si son iguales,
     *         positivo si f1 es posterior a f2.
     */
    public static int compararFechas(String f1, String f2) {
        String[] p1 = f1.split("/");
        String[] p2 = f2.split("/");
        int n1 = Integer.parseInt(p1[2]) * 10000
                + Integer.parseInt(p1[1]) * 100
                + Integer.parseInt(p1[0]);
        int n2 = Integer.parseInt(p2[2]) * 10000
                + Integer.parseInt(p2[1]) * 100
                + Integer.parseInt(p2[0]);
        return Integer.compare(n1, n2);
    }
}
