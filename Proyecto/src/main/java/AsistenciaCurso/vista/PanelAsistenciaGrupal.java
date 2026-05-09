package AsistenciaCurso.vista;

import AsistenciaCurso.modelo.*;
import AsistenciaCurso.Main;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Panel para registrar la asistencia de todos los estudiantes de un curso
 * en una sola operación.
 *
 * Los estudiantes marcados como "Asiste" reciben una AsistenciaNormal.
 * Los estudiantes no marcados reciben una InasistenciaExtraordinaria.
 */
public class PanelAsistenciaGrupal extends JPanel {

    private Ventana ventana;
    private JComboBox<String> comboCurso;
    private JTextField txtFecha;
    private JTextField txtObservacion;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JLabel etiquetaConteo;

    public PanelAsistenciaGrupal(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 248, 255));
        initUI();
    }

    private void initUI() {
        // ── NORTE: título + controles
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 248, 255));

        JLabel titulo = new JLabel("Asistencia Grupal por Curso", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelControles.setBackground(new Color(245, 248, 255));

        panelControles.add(new JLabel("Curso:"));
        comboCurso = new JComboBox<>(Main.listaCursos);
        panelControles.add(comboCurso);

        panelControles.add(new JLabel("Fecha (DD/MM/AAAA):"));
        txtFecha = new JTextField(10);
        // Auto-completar con la fecha de hoy
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panelControles.add(txtFecha);

        panelControles.add(new JLabel("Observación:"));
        txtObservacion = new JTextField(14);
        panelControles.add(txtObservacion);

        // Etiqueta de conteo de alumnos cargados
        etiquetaConteo = new JLabel("  0 alumnos cargados");
        etiquetaConteo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        etiquetaConteo.setForeground(new Color(80, 80, 80));
        panelControles.add(etiquetaConteo);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelControles, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        // ── CENTRO: tabla
        String[] columnas = { "Asiste", "RUT", "Nombre Completo" };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 0;
            }

            @Override
            public Class<?> getColumnClass(int columna) {
                return columna == 0 ? Boolean.class : String.class;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setRowHeight(26);
        tablaEstudiantes.getColumnModel().getColumn(0).setMaxWidth(70);
        tablaEstudiantes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaEstudiantes.setSelectionBackground(new Color(210, 225, 255));

        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        // ── SUR: botones ──────────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelSur.setBackground(new Color(245, 248, 255));

        // Botón marcar todos / desmarcar todos
        JButton btnTodos = new JButton("✔ Marcar todos");
        JButton btnNinguno = new JButton("✘ Desmarcar todos");

        // Botón principal de registro (verde y destacado)
        JButton btnRegistrar = new JButton("  Registrar Asistencia  ");
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setOpaque(true);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton btnVolver = new JButton("Menú Principal");

        btnTodos.addActionListener(e -> marcarTodos(true));
        btnNinguno.addActionListener(e -> marcarTodos(false));
        btnRegistrar.addActionListener(e -> registrarAsistenciaGrupal());
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelSur.add(btnTodos);
        panelSur.add(btnNinguno);
        panelSur.add(btnRegistrar);
        panelSur.add(btnVolver);
        add(panelSur, BorderLayout.SOUTH);

        // Cargar el primer curso automáticamente al abrir el panel
        cargarCurso();

        // Recargar automáticamente al cambiar el combo
        comboCurso.addActionListener(e -> cargarCurso());
    }

    /** Carga en la tabla los estudiantes del curso seleccionado. */
    private void cargarCurso() {
        String cursoSeleccionado = (String) comboCurso.getSelectedItem();
        modeloTabla.setRowCount(0);

        for (Estudiante estudiante : Main.listaGlobal.values()) {
            if (estudiante.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                modeloTabla.addRow(new Object[] {
                        Boolean.TRUE,
                        estudiante.getRut(),
                        estudiante.getNombreCompleto()
                });
            }
        }

        int n = modeloTabla.getRowCount();
        etiquetaConteo.setText("  " + n + (n == 1 ? " alumno cargado" : " alumnos cargados"));
    }

    /** Marca o desmarca todos los checkboxes de la tabla. */
    private void marcarTodos(boolean valor) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            modeloTabla.setValueAt(valor, i, 0);
        }
    }

    /** Registra asistencia para todos los estudiantes cargados en la tabla. */
    private void registrarAsistenciaGrupal() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay estudiantes cargados. Seleccione un curso.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fecha = txtFecha.getText().trim();
        if (!validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese una fecha válida en formato DD/MM/AAAA.",
                    "Fecha inválida", JOptionPane.WARNING_MESSAGE);
            txtFecha.requestFocus();
            txtFecha.selectAll();
            return;
        }

        String obs = txtObservacion.getText().trim();

        int registrados = 0;
        int alertas = 0;

        for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
            boolean asiste = (Boolean) modeloTabla.getValueAt(fila, 0);
            String rut = (String) modeloTabla.getValueAt(fila, 1);

            try {
                Estudiante alumno = Main.obtenerEstudiantePorRut(rut);
                String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                Asistencia asistencia = asiste
                        ? new AsistenciaNormal(id, fecha, obs.isEmpty() ? "Pase grupal" : obs, true)
                        : new InasistenciaExtraordinaria(id, fecha, obs.isEmpty() ? "Inasistencia grupal" : obs,
                                "Inasistencia grupal");

                alumno.agregarAsistencia(asistencia);
                registrados++;

                if (alumno.getPorcentajeAsistencia() < 85.0) {
                    alertas++;
                }

            } catch (EstudianteNoEncontradoException ex) {
                // Estudiante eliminado mientras el panel estaba cargado; se ignora.
            }
        }

        GestorArchivos.guardarEstudiantes(Main.listaGlobal);

        String mensaje = "✔ Asistencia registrada para " + registrados + " estudiante(s).";
        if (alertas > 0) {
            mensaje += "\n⚠ " + alertas + " estudiante(s) tienen asistencia bajo el 85%.";
        }

        JOptionPane.showMessageDialog(this, mensaje,
                "Registro completado", JOptionPane.INFORMATION_MESSAGE);

        // Refrescar la tabla para el siguiente pase (mantener el curso)
        cargarCurso();
    }

    /** Valida formato simple de fecha DD/MM/AAAA. */
    private boolean validarFecha(String fecha) {
        if (fecha == null || !fecha.matches("\\d{2}/\\d{2}/\\d{4}"))
            return false;
        String[] p = fecha.split("/");
        try {
            int dia = Integer.parseInt(p[0]);
            int mes = Integer.parseInt(p[1]);
            int anio = Integer.parseInt(p[2]);
            if (anio < 1900 || anio > 2100)
                return false;
            if (mes < 1 || mes > 12)
                return false;
            if (dia < 1 || dia > 31)
                return false;
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30)
                return false;
            if (mes == 2 && dia > 29)
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}