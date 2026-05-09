/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AsistenciaCurso.modelo;

/**
 * Excepción propia (SIA-M2) para manejar edades negativas.
 * Si intentan crear un alumno con -5 años, el sistema lanza esto y 
 * muestra un error amigable en la interfaz en lugar de caerse.
 */
public class EdadInvalidaException extends Exception {
    private final int edadIngresada;

    public EdadInvalidaException(int edad) {
        super("Edad inválida: " + edad + ". Debe ser positiva.");
        this.edadIngresada = edad;
    }

    public int getEdadIngresada() {
        return edadIngresada;
    }
}
