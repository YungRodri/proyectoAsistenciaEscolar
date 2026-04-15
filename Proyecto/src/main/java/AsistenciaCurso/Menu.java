package AsistenciaCurso;

import java.awt.*;
import javax.swing.*;

/**
 * Panel de menú principal de la interfaz gráfica.
 * Ofrece acceso a todas las funcionalidades del sistema (SIA-7, SIA-8, SIA-10).
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
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 12));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 80, 40, 80));
        panelBotones.setBackground(new Color(55, 65, 90));

        JButton btnIngresarEstudiante = crearBoton("Ingresar Estudiante");
        JButton btnGestionarEstudiantes = crearBoton("Gestionar Estudiantes");
        JButton btnAsistencias = crearBoton("Gestionar Asistencias");

        btnIngresarEstudiante.addActionListener(e -> ventana.cambiarVista("estudiantes"));
        btnGestionarEstudiantes.addActionListener(e -> {
            // Refrescar tabla al entrar al panel
            ventana.refrescarPanelEstudiantes();
            ventana.cambiarVista("gestionEstudiantes");
        });
        btnAsistencias.addActionListener(e -> ventana.cambiarVista("asistencias"));

        panelBotones.add(btnIngresarEstudiante);
        panelBotones.add(btnGestionarEstudiantes);
        panelBotones.add(btnAsistencias);

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
