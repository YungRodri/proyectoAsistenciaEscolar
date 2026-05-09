/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenciaCurso.modelo;

/**
 * Representa a una persona genérica dentro de nuestro sistema escolar.
 * Esta clase sirve como base para Estudiante y nos permite centralizar
 * la validación del RUT y la edad para no repetir código.
 */
public class Persona {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String rut;
    private int edad;

    /**
     * Constructor principal de Persona.
     * Al crearse, valida inmediatamente el RUT y la edad usando los setters
     * para asegurar que no se instancien objetos con datos corruptos.
     *
     * @param nombre    Primer nombre.
     * @param apellidoP Apellido paterno.
     * @param apellidoM Apellido materno.
     * @param rut       RUT en formato chileno (con o sin guión/puntos).
     * @param edad      Edad de la persona (debe ser mayor a 0).
     * @throws RutInvalidoException  Si el RUT no cumple el algoritmo módulo 11.
     * @throws EdadInvalidaException Si la edad es negativa o cero.
     */
    public Persona(String nombre, String apellidoP, String apellidoM, String rut, int edad)
            throws RutInvalidoException, EdadInvalidaException {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.setRut(rut);
        this.setEdad(edad);
    }

    /**
     * Algoritmo de Módulo 11 para verificar si un RUT chileno es matemáticamente
     * válido.
     * Lo pusimos aquí porque cualquier persona (estudiante o en el futuro un profe)
     * necesita validar su RUT.
     *
     * @param rut RUT ingresado por el usuario.
     * @return true si el dígito verificador coincide, false en caso contrario.
     */
    public boolean validarRut(String rut) {
        // Verificamos el Rut
        if (rut == null) {
            return false;
        }
        // Eliminamos puntos y guiones , asi podemos trabajar el numero del rut
        rut = rut.replace(".", "").replace("-", "");
        // Largo del Rut
        int len = rut.length();
        // Validamos el Largo que debe tener de por si un rut
        if (len < 2 || len > 12) {
            return false;
        }
        // Obtenemos el digito verificador ingresado , en caso de se K lo pasamos a
        // mayuscula
        char dvInput = Character.toUpperCase(rut.charAt(len - 1));
        // variables necesarias para calcular el digito verificador(dv)
        int suma = 0;
        int factor = 2;
        // recorremos el rut de derecha a izquierda
        for (int i = len - 2; i >= 0; i--) {

            char c = rut.charAt(i);
            // Validamos que cada caracter sea un digito
            if (!Character.isDigit(c)) {
                return false;
            }
            // Convertimos el Caracter en un numero , por la tabla ANSCI (creo)
            int dig = c - '0';
            // Acumulamos el resultado del digito x el Factor
            suma += dig * factor;
            // Aumentamos dicho factor
            factor++;
            // Si pasa de 7 , le asignamos 2
            if (factor > 7)
                factor = 2;
        }
        // Calculamos el resta de la suma
        int resto = suma % 11;
        // aplicamos la formula deldigito verificador
        int dvNum = 11 - resto;
        // Variable en dondde guardaremos el dv calculado
        char dvCal;
        // Casos especificos del dv
        if (dvNum == 11) {
            dvCal = '0';
        } else if (dvNum == 10) {
            dvCal = 'K';
        } else {
            dvCal = (char) ('0' + dvNum);
        }
        // Comparamos el Dv ingresadi con el calculado
        // En caso de ser iguales , el rut es valido
        return dvInput == dvCal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoP(String apellido) {
        this.apellidoP = apellido;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    /**
     * Setter del RUT que fuerza la validación. Si es inválido, corta el flujo
     * lanzando una excepción.
     */
    public void setRut(String rut) throws RutInvalidoException {
        if (!validarRut(rut)) {
            throw new RutInvalidoException(rut);
        }

        this.rut = rut;

    }

    /**
     * Setter de la edad que impide ingresar edades negativas o cero.
     */
    public void setEdad(int edad) throws EdadInvalidaException {
        if (edad < 1) {
            throw new EdadInvalidaException(edad);
        }
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public String getRut() {
        return rut;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidoP + " " + apellidoM;
    }

}
