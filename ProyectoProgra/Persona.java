public class Persona
{
    private String rut;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private int edad;

    public Persona(String rut, String nombre, String apellidoP, String apellidoM, int edad)
    {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.edad = edad;
    }
    public boolean validarRut(String rut)
    {
        // Verificamos el Rut
        if (rut == null)
        {
            return false;
        }
        //  Eliminamos puntos y guiones , asi podemos trabajar el numero del rut
        rut = rut.replace(".", "").replace("-", "");
        // Largo del Rut
        int len = rut.length();
        // Validamos el Largo que debe tener de por si un rut
        if (len < 2 || len > 12)
        {
            return false;
        }
        // Obtenemos el digito verificador ingresado , en caso de se K lo pasamos a mayuscula
        char dvInput = Character.toUpperCase(rut.charAt(len - 1));
        // variables necesarias para calcular el digito verificador(dv)
        int suma = 0;
        int factor = 2;
        // recorremos el rut de derecha a izquierda
        for (int i = len - 2; i >= 0; i--)
        {

            char c = rut.charAt(i);
            // Validamos que cada caracter sea un digito
            if (!Character.isDigit(c))
            {
                return false;
            }
            // Convertimos el Caracter en un numero , por la tabla ANSCI (creo)
            int dig = c - '0';
            // Acumulamos el resultado del digito x el Factor
            suma += dig * factor;
            // Aumentamos dicho factor
            factor++;
            // Si pasa de 7 , le asignamos 2
            if (factor > 7) factor = 2;
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
        }
        else if (dvNum == 10) {
            dvCal = 'K';
        }
        else {
            dvCal = (char) ('0' + dvNum);
        }
        // Comparamos el Dv ingresadi con el calculado
        // En caso de ser iguales , el rut es valido
        return dvInput == dvCal;
    }

    public String getRut()
    {
        return rut;
    }

    public void setRut(String rut)
    {
        this.rut = rut;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidoP()
    {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP)
    {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM()
    {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM)
    {
        this.apellidoM = apellidoM;
    }

    public int getEdad()
    {
        return edad;
    }

    public void setEdad(int edad)
    {
        this.edad = edad;
    }

    public String getNombreCompleto()
    {
        return nombre + " " + apellidoP + " " + apellidoM;
    }
}
