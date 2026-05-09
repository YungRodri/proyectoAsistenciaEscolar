package AsistenciaCurso.vista;

import AsistenciaCurso.modelo.*;
import AsistenciaCurso.Main;
import AsistenciaCurso.controlador.*;

/**
 * Panel que usa JFreeChart (SIA-O1) para mostrar gráficos a nivel de curso.
 * Así podemos ver visualmente cómo va el rendimiento de asistencia de todo un grado.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelGraficoCurso extends JPanel {

    private Ventana ventana;
    private JComboBox<String> comboCurso;
    private JPanel panelGrafico;

    public PanelGraficoCurso(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 248, 255));
        initUI();
    }

    private void initUI() {
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 248, 255));

        JLabel titulo = new JLabel("Gráfico de Asistencia por Curso", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSelector.setBackground(new Color(245, 248, 255));

        panelSelector.add(new JLabel("Seleccionar curso:"));

        comboCurso = new JComboBox<>(Main.listaCursos);

        JButton btnVer = new JButton("Ver Gráfico");
        btnVer.addActionListener(e -> mostrarGrafico());

        panelSelector.add(comboCurso);
        panelSelector.add(btnVer);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelSelector, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        panelGrafico = new JPanel(new BorderLayout());
        panelGrafico.setBackground(new Color(245, 248, 255));

        JLabel etiquetaEspera = new JLabel("Seleccione un curso y presione Ver Gráfico", SwingConstants.CENTER);
        etiquetaEspera.setForeground(Color.GRAY);

        panelGrafico.add(etiquetaEspera, BorderLayout.CENTER);

        add(panelGrafico, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(new Color(245, 248, 255));

        JButton btnVolver = new JButton("Menú Principal");
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelSur.add(btnVolver);

        add(panelSur, BorderLayout.SOUTH);
    }

    private void mostrarGrafico() {
        String cursoSeleccionado = (String) comboCurso.getSelectedItem();

        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Double> porcentajes = new ArrayList<>();

        for (Estudiante estudiante : Main.listaGlobal.values()) {
            if (estudiante.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                nombres.add(estudiante.getNombreCompleto());
                porcentajes.add(estudiante.getPorcentajeAsistencia());
            }
        }

        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay estudiantes en el curso: " + cursoSeleccionado,
                    "Sin datos",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        panelGrafico.removeAll();

        GraficoBarrasPanel grafico = new GraficoBarrasPanel(cursoSeleccionado, nombres, porcentajes);

        panelGrafico.add(grafico, BorderLayout.CENTER);
        panelGrafico.revalidate();
        panelGrafico.repaint();
    }

    private static class GraficoBarrasPanel extends JPanel {

        private String curso;
        private ArrayList<String> nombres;
        private ArrayList<Double> porcentajes;

        public GraficoBarrasPanel(String curso, ArrayList<String> nombres, ArrayList<Double> porcentajes) {
            this.curso = curso;
            this.nombres = nombres;
            this.porcentajes = porcentajes;
            setBackground(new Color(245, 248, 255));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();

            int margenIzquierdo = 70;
            int margenDerecho = 40;
            int margenSuperior = 55;
            int margenInferior = 90;

            int anchoGrafico = ancho - margenIzquierdo - margenDerecho;
            int altoGrafico = alto - margenSuperior - margenInferior;

            if (anchoGrafico <= 0 || altoGrafico <= 0) {
                return;
            }

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString("Asistencia del curso " + curso, margenIzquierdo, 30);

            // Ejes
            int xBase = margenIzquierdo;
            int yBase = margenSuperior + altoGrafico;

            g2.setColor(Color.DARK_GRAY);
            g2.drawLine(xBase, margenSuperior, xBase, yBase);
            g2.drawLine(xBase, yBase, xBase + anchoGrafico, yBase);

            // Líneas y etiquetas del eje Y
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            for (int i = 0; i <= 10; i++) {
                int valor = i * 10;
                int y = yBase - (int) ((valor / 100.0) * altoGrafico);

                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(xBase, y, xBase + anchoGrafico, y);

                g2.setColor(Color.BLACK);
                g2.drawString(valor + "%", 25, y + 4);
            }

            int cantidad = nombres.size();
            int espacioPorBarra = anchoGrafico / cantidad;
            int anchoBarra = Math.max(20, espacioPorBarra / 2);

            for (int i = 0; i < cantidad; i++) {
                double porcentaje = porcentajes.get(i);

                int altoBarra = (int) ((porcentaje / 100.0) * altoGrafico);
                int x = xBase + i * espacioPorBarra + (espacioPorBarra - anchoBarra) / 2;
                int y = yBase - altoBarra;

                if (porcentaje < 85.0) {
                    g2.setColor(new Color(200, 60, 60));
                } else {
                    g2.setColor(new Color(70, 130, 200));
                }

                g2.fillRect(x, y, anchoBarra, altoBarra);

                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x, y, anchoBarra, altoBarra);

                // Porcentaje sobre la barra
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                String textoPorcentaje = String.format("%.1f%%", porcentaje);
                g2.drawString(textoPorcentaje, x - 3, y - 5);

                // Nombre abajo
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                String nombre = nombres.get(i);

                if (nombre.length() > 12) {
                    nombre = nombre.substring(0, 12) + ".";
                }

                g2.drawString(nombre, x - 8, yBase + 20);
            }

            // Leyenda
            int leyendaX = margenIzquierdo;
            int leyendaY = alto - 35;

            g2.setColor(new Color(70, 130, 200));
            g2.fillRect(leyendaX, leyendaY, 18, 18);
            g2.setColor(Color.BLACK);
            g2.drawString("Asistencia igual o superior a 85%", leyendaX + 25, leyendaY + 14);

            g2.setColor(new Color(200, 60, 60));
            g2.fillRect(leyendaX + 240, leyendaY, 18, 18);
            g2.setColor(Color.BLACK);
            g2.drawString("Asistencia bajo 85%", leyendaX + 265, leyendaY + 14);
        }
    }
}