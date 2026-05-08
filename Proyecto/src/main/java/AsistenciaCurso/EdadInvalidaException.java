/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AsistenciaCurso;

/**
 *
 * @author HP
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
