package AsistenciaCurso.vista;

import AsistenciaCurso.modelo.*;
import AsistenciaCurso.controlador.*;
import AsistenciaCurso.Main;

/**
 * Panel para ver de forma rápida los números de un alumno (cuántas veces faltó, etc).
 * Se integra con el MVC delegando las búsquedas al Controlador.
 */
import javax.swing.*;
import java.awt.*;

/**
 * Panel GUI que muestra las estadísticas de asistencia de un estudiante:
 * totales por tipo, porcentaje global e indicador visual de alerta (F-2).
 */
public class PanelEstadisticasEstudiante extends JPanel {

    private static final double UMBRAL = 85.0;

    private final Ventana ventana;
    private JTextField txtRut;
    private JLabel etiquetaNombre;
    private JLabel etiquetaNormales;
    private JLabel etiquetaInasistencias;
    private JLabel etiquetaSalidas;
    private JLabel etiquetaTotal;
    private JLabel etiquetaPorcentaje;
    private JLabel etiquetaIndicador;

    public PanelEstadisticasEstudiante(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 250, 245));
        initUI();
    }

    private void initUI() {
        // ── NORTE: título + buscador ──────────────────────────────────────────
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 250, 245));

        JLabel titulo = new JLabel("Estadísticas por Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 80, 50));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBuscar.setBackground(new Color(245, 250, 245));
        panelBuscar.add(new JLabel("RUT del Estudiante:"));
        txtRut = new JTextField(14);
        JButton btnCargar = new JButton("Ver Estadísticas");
        btnCargar.addActionListener(e -> cargarEstadisticas());
        panelBuscar.add(txtRut);
        panelBuscar.add(btnCargar);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelBuscar, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        // ---- CENTRO: cuadro de estadisticas ----
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(new Color(245, 250, 245));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(8, 10, 8, 10);
        restricciones.anchor = GridBagConstraints.WEST;

        // Nombre del estudiante
        restricciones.gridx = 0; restricciones.gridy = 0;
        panelDatos.add(crearEtiquetaTitulo("Estudiante:"), restricciones);
        restricciones.gridx = 1;
        etiquetaNombre = crearEtiquetaValor("---");
        panelDatos.add(etiquetaNombre, restricciones);

        // Separador
        restricciones.gridx = 0; restricciones.gridy = 1; restricciones.gridwidth = 2;
        panelDatos.add(new JSeparator(), restricciones);
        restricciones.gridwidth = 1;

        // Asistencias normales
        restricciones.gridx = 0; restricciones.gridy = 2;
        panelDatos.add(crearEtiquetaTitulo("Asistencias normales:"), restricciones);
        restricciones.gridx = 1;
        etiquetaNormales = crearEtiquetaValor("---");
        panelDatos.add(etiquetaNormales, restricciones);

        // Inasistencias
        restricciones.gridx = 0; restricciones.gridy = 3;
        panelDatos.add(crearEtiquetaTitulo("Inasistencias:"), restricciones);
        restricciones.gridx = 1;
        etiquetaInasistencias = crearEtiquetaValor("---");
        panelDatos.add(etiquetaInasistencias, restricciones);

        // Salidas anticipadas
        restricciones.gridx = 0; restricciones.gridy = 4;
        panelDatos.add(crearEtiquetaTitulo("Salidas anticipadas:"), restricciones);
        restricciones.gridx = 1;
        etiquetaSalidas = crearEtiquetaValor("---");
        panelDatos.add(etiquetaSalidas, restricciones);

        // Total
        restricciones.gridx = 0; restricciones.gridy = 5;
        panelDatos.add(crearEtiquetaTitulo("Total de registros:"), restricciones);
        restricciones.gridx = 1;
        etiquetaTotal = crearEtiquetaValor("---");
        panelDatos.add(etiquetaTotal, restricciones);

        // Separador
        restricciones.gridx = 0; restricciones.gridy = 6; restricciones.gridwidth = 2;
        panelDatos.add(new JSeparator(), restricciones);
        restricciones.gridwidth = 1;

        // Porcentaje
        restricciones.gridx = 0; restricciones.gridy = 7;
        JLabel tituloPorcentaje = crearEtiquetaTitulo("% de asistencia:");
        tituloPorcentaje.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelDatos.add(tituloPorcentaje, restricciones);
        restricciones.gridx = 1;
        etiquetaPorcentaje = new JLabel("---");
        etiquetaPorcentaje.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelDatos.add(etiquetaPorcentaje, restricciones);

        // Indicador de estado
        restricciones.gridx = 0; restricciones.gridy = 8; restricciones.gridwidth = 2;
        etiquetaIndicador = new JLabel(" ");
        etiquetaIndicador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelDatos.add(etiquetaIndicador, restricciones);

        add(panelDatos, BorderLayout.CENTER);

        // ── SUR: botón volver ─────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(new Color(245, 250, 245));
        JButton btnVolver = new JButton("Menú Principal");
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));
        panelSur.add(btnVolver);
        add(panelSur, BorderLayout.SOUTH);
    }

    /** Carga y muestra las estadísticas del estudiante con el RUT ingresado. */
    public void cargarEstadisticas() {
        String rut = txtRut.getText().trim();
        try {
            Estudiante estudiante = Main.obtenerEstudiantePorRut(rut);

            int normales       = estudiante.contarAsistenciaNormales();
            int inasistencias  = estudiante.contarAsistenciaExtraordinarias();
            int salidas        = estudiante.contarAsistenciasAnticipadas();
            int total          = estudiante.getCantAsistencia();
            double porcentaje  = estudiante.getPorcentajeAsistencia();

            etiquetaNombre.setText(estudiante.getNombreCompleto() + "  |  Curso: " + estudiante.getCurso());
            etiquetaNormales.setText(String.valueOf(normales));
            etiquetaInasistencias.setText(String.valueOf(inasistencias));
            etiquetaSalidas.setText(String.valueOf(salidas));
            etiquetaTotal.setText(String.valueOf(total));
            etiquetaPorcentaje.setText(String.format("%.1f%%", porcentaje));

            if (total == 0) {
                etiquetaIndicador.setText("Sin registros de asistencia.");
                etiquetaIndicador.setForeground(Color.GRAY);
                etiquetaPorcentaje.setForeground(Color.GRAY);
            } else if (porcentaje < UMBRAL) {
                etiquetaIndicador.setText("Asistencia insuficiente (umbral: " + (int) UMBRAL + "%)");
                etiquetaIndicador.setForeground(new Color(180, 30, 30));
                etiquetaPorcentaje.setForeground(new Color(180, 30, 30));
            } else {
                etiquetaIndicador.setText("Asistencia dentro del rango");
                etiquetaIndicador.setForeground(new Color(0, 130, 60));
                etiquetaPorcentaje.setForeground(new Color(0, 130, 60));
            }

        } catch (EstudianteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Estudiante no encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JLabel crearEtiquetaTitulo(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return etiqueta;
    }

    private JLabel crearEtiquetaValor(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return etiqueta;
    }
}