package AsistenciaCurso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.TreeMap;

public class GestorArchivos {
    private static final String ARCHIVO_ESTUDIANTES = "estudiantes.csv";

    public static TreeMap<String, Estudiante> cargarEstudiantes() {
        TreeMap<String, Estudiante> estudiantes = new TreeMap<>();
        File archivo = new File(ARCHIVO_ESTUDIANTES);

        if (!archivo.exists()) {
            return estudiantes; // Retorna vacio si no existe
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";", -1);
                if (datos.length == 6) {
                    String rut      = datos[0];
                    String nombre   = datos[1];
                    String apellidoP = datos[2];
                    String apellidoM = datos[3];
                    int edad        = Integer.parseInt(datos[4]);
                    String curso    = datos[5];

                    Estudiante e = new Estudiante(nombre, apellidoP, apellidoM, rut, edad, curso);
                    estudiantes.put(rut, e); // clave = RUT, busqueda O(log N) ordenada
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar estudiantes desde CSV: " + e.getMessage());
        }

        return estudiantes;
    }

    public static void guardarEstudiantes(TreeMap<String, Estudiante> mapa) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ARCHIVO_ESTUDIANTES), StandardCharsets.UTF_8))) {
            for (Estudiante e : mapa.values()) {
                // Formato: rut;nombre;apellidoP;apellidoM;edad;curso
                String linea = String.format("%s;%s;%s;%s;%d;%s",
                        e.getRut(), e.getNombre(), e.getApellidoP(), e.getApellidoM(), e.getEdad(), e.getCurso());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar estudiantes en CSV: " + e.getMessage());
        }
    }
}
