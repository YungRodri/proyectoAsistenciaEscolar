package AsistenciaCurso.vista;

import AsistenciaCurso.modelo.*;
import AsistenciaCurso.Main;
import AsistenciaCurso.controlador.*;

/**
 * Panel que usa JFreeChart (SIA-O1) para mostrar un gráfico de torta 
 * con el porcentaje de asistencia de un solo estudiante.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Panel que muestra un gráfico de torta con la distribución de asistencias
 * de un estudiante: asistencias normales, inasistencias y salidas anticipadas.
 */
public class PanelGraficoEstudiante extends JPanel {

    private Ventana ventana;
    private JTextField txtRut;
    private JPanel panelGrafico;

    public PanelGraficoEstudiante(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(250, 248, 245));
        initUI();
    }

    private void initUI() {
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(250, 248, 245));

        JLabel titulo = new JLabel("Gráfico de Asistencia por Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 40, 10));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBuscar.setBackground(new Color(250, 248, 245));

        panelBuscar.add(new JLabel("RUT del Estudiante:"));

        txtRut = new JTextField(14);

        JButton btnVer = new JButton("Ver Gráfico");
        btnVer.addActionListener(e -> mostrarGrafico());

        panelBuscar.add(txtRut);
        panelBuscar.add(btnVer);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelBuscar, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        panelGrafico = new JPanel(new BorderLayout());
        panelGrafico.setBackground(new Color(250, 248, 245));

        JLabel etiquetaEspera = new JLabel("Ingrese un RUT y presione Ver Gráfico", SwingConstants.CENTER);
        etiquetaEspera.setForeground(Color.GRAY);

        panelGrafico.add(etiquetaEspera, BorderLayout.CENTER);

        add(panelGrafico, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(new Color(250, 248, 245));

        JButton btnVolver = new JButton("Menú Principal");
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelSur.add(btnVolver);

        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Busca el estudiante por RUT y muestra su gráfico de asistencia.
     */
    private void mostrarGrafico() {
        String rut = txtRut.getText().trim();

        try {
            Estudiante estudiante = Main.obtenerEstudiantePorRut(rut);

            int normales = estudiante.contarAsistenciaNormales();
            int inasistencias = estudiante.contarAsistenciaExtraordinarias();
            int salidas = estudiante.contarAsistenciasAnticipadas();
            int total = estudiante.getCantAsistencia();

            if (total == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El estudiante no tiene registros de asistencia.",
                        "Sin datos",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            panelGrafico.removeAll();

            GraficoTortaPanel grafico = new GraficoTortaPanel(
                    estudiante.getNombreCompleto(),
                    normales,
                    inasistencias,
                    salidas
            );

            panelGrafico.add(grafico, BorderLayout.CENTER);
            panelGrafico.revalidate();
            panelGrafico.repaint();

        } catch (EstudianteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Estudiante no encontrado",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Panel interno encargado de dibujar el gráfico de torta.
     */
    private static class GraficoTortaPanel extends JPanel {

        private String nombreEstudiante;
        private int normales;
        private int inasistencias;
        private int salidas;

        public GraficoTortaPanel(String nombreEstudiante, int normales, int inasistencias, int salidas) {
            this.nombreEstudiante = nombreEstudiante;
            this.normales = normales;
            this.inasistencias = inasistencias;
            this.salidas = salidas;
            setBackground(new Color(250, 248, 245));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int total = normales + inasistencias + salidas;

            if (total == 0) {
                g2.drawString("Sin datos para mostrar.", 30, 30);
                return;
            }

            int ancho = getWidth();
            int alto = getHeight();

            int diametro = Math.min(ancho, alto) - 160;

            if (diametro < 120) {
                diametro = 120;
            }

            int x = 60;
            int y = 60;

            int anguloInicio = 0;

            int anguloNormales = (int) Math.round((normales * 360.0) / total);
            int anguloInasistencias = (int) Math.round((inasistencias * 360.0) / total);
            int anguloSalidas = 360 - anguloNormales - anguloInasistencias;

            // Asistencias normales
            g2.setColor(new Color(60, 160, 80));
            g2.fillArc(x, y, diametro, diametro, anguloInicio, anguloNormales);
            anguloInicio += anguloNormales;

            // Inasistencias
            g2.setColor(new Color(200, 50, 50));
            g2.fillArc(x, y, diametro, diametro, anguloInicio, anguloInasistencias);
            anguloInicio += anguloInasistencias;

            // Salidas anticipadas
            g2.setColor(new Color(220, 160, 40));
            g2.fillArc(x, y, diametro, diametro, anguloInicio, anguloSalidas);

            // Borde
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(x, y, diametro, diametro);

            // Título
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.setColor(Color.BLACK);
            g2.drawString("Asistencia de " + nombreEstudiante, 60, 30);

            // Leyenda
            int leyendaX = x + diametro + 40;
            int leyendaY = y + 40;

            dibujarLeyenda(g2, leyendaX, leyendaY, new Color(60, 160, 80),
                    "Normales: " + normales + " (" + porcentaje(normales, total) + "%)");

            dibujarLeyenda(g2, leyendaX, leyendaY + 35, new Color(200, 50, 50),
                    "Inasistencias: " + inasistencias + " (" + porcentaje(inasistencias, total) + "%)");

            dibujarLeyenda(g2, leyendaX, leyendaY + 70, new Color(220, 160, 40),
                    "Salidas: " + salidas + " (" + porcentaje(salidas, total) + "%)");

            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            g2.setColor(Color.BLACK);
            g2.drawString("Total registros: " + total, leyendaX, leyendaY + 115);
        }

        private void dibujarLeyenda(Graphics2D g2, int x, int y, Color color, String texto) {
            g2.setColor(color);
            g2.fillRect(x, y - 12, 18, 18);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            g2.drawString(texto, x + 28, y + 2);
        }

        private double porcentaje(int cantidad, int total) {
            if (total == 0) {
                return 0.0;
            }

            return Math.round((cantidad * 1000.0) / total) / 10.0;
        }
    }
}