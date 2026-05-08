package AsistenciaCurso;

import java.awt.*;
import javax.swing.*;

/**
 * Panel de menú principal de la interfaz gráfica.
 * Ofrece acceso a las funcionalidades principales del sistema.
 */
public class Menu extends JPanel {

    Ventana ventana;

    public Menu(Ventana ventana) {
        this.ventana = ventana;
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(55, 65, 90));
        setLayout(new BorderLayout(10, 10));

        // ---- Título ----
        JLabel titulo = new JLabel("Sistema de Asistencia Escolar", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // ---- Botones ----
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 0, 12));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 80, 40, 80));
        panelBotones.setBackground(new Color(55, 65, 90));

        JButton btnIngresarEstudiante = crearBoton("Ingresar Estudiante");
        JButton btnGestionarEstudiantes = crearBoton("Gestionar Estudiantes");
        JButton btnAsistencias = crearBoton("Gestionar Asistencias");
        JButton btnEstadisticas = crearBoton("Estadísticas Estudiante");
        JButton btnResumenCurso = crearBoton("Resumen por Curso");
        JButton btnGraficoEstudiante = crearBoton("Gráfico Estudiante");
        JButton btnGraficoCurso = crearBoton("Gráfico Curso");
        JButton btnAsistenciaGrupal = crearBoton("Asistencia Grupal");
        btnIngresarEstudiante.addActionListener(e -> ventana.cambiarVista("estudiantes"));

        btnGestionarEstudiantes.addActionListener(e -> {
            ventana.refrescarPanelEstudiantes();
            ventana.cambiarVista("gestionEstudiantes");
        });

        btnAsistencias.addActionListener(e -> ventana.cambiarVista("asistencias"));

        btnEstadisticas.addActionListener(e -> ventana.cambiarVista("estadisticas"));
        btnResumenCurso.addActionListener(e -> ventana.cambiarVista("resumenCurso"));
        btnGraficoEstudiante.addActionListener(e -> ventana.cambiarVista("graficoEstudiante"));
        btnGraficoCurso.addActionListener(e -> ventana.cambiarVista("graficoCurso"));
        btnAsistenciaGrupal.addActionListener(e -> ventana.cambiarVista("asistenciaGrupal"));
        panelBotones.add(btnIngresarEstudiante);
        panelBotones.add(btnGestionarEstudiantes);
        panelBotones.add(btnAsistencias);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnResumenCurso);
        panelBotones.add(btnGraficoEstudiante);
        panelBotones.add(btnGraficoCurso);
        panelBotones.add(btnAsistenciaGrupal);
        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(90, 130, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}
