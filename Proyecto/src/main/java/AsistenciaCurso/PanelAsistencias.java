package AsistenciaCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

/**
 * Panel GUI para registrar, listar, editar y eliminar asistencias de un
 * estudiante.
 * Cumple SIA-7 (registrar y listar asistencias) y SIA-8 (editar, eliminar,
 * buscar asistencias).
 * Utiliza getResumen() polimórfico de las subclases de Asistencia (SIA-6).
 */
public class PanelAsistencias extends JPanel {

    private final Ventana ventana;
    private JTable tablaAsistencias;
    private DefaultTableModel modeloTabla;
    private JTextField txtRut;
    private Estudiante estudianteActual;

    public PanelAsistencias(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 250, 245));
        initUI();
    }

    private void initUI() {
        // ---- NORTE: título + barra de búsqueda por RUT ----
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 250, 245));

        JLabel titulo = new JLabel("Gestión de Asistencias", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 80, 50));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelRut = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelRut.setBackground(new Color(245, 250, 245));
        panelRut.add(new JLabel("RUT del Estudiante:"));
        txtRut = new JTextField(14);
        JButton btnCargar = new JButton("Cargar Historial");
        btnCargar.addActionListener(e -> cargarHistorial());
        panelRut.add(txtRut);
        panelRut.add(btnCargar);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelRut, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        // ---- CENTRO: tabla de historial (SIA-7: listar asistencias) ----
        String[] cols = { "ID", "Tipo", "Fecha", "Resumen", "Observación" };
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tablaAsistencias = new JTable(modeloTabla);
        tablaAsistencias.setRowHeight(22);
        tablaAsistencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAsistencias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(new JScrollPane(tablaAsistencias), BorderLayout.CENTER);

        // ---- SUR: botones de acción ----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelBotones.setBackground(new Color(245, 250, 245));

        JButton btnRegistrar = new JButton("Registrar Asistencia");
        JButton btnEditar    = new JButton("Editar Observación");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnVolver    = new JButton("Menú Principal");

        btnRegistrar.addActionListener(e -> registrarAsistencia());
        btnEditar.addActionListener(e -> editarAsistencia());
        btnEliminar.addActionListener(e -> eliminarAsistencia());
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * SIA-7: Carga el historial de asistencias del estudiante en la tabla.
     * Utiliza getResumen() polimórfico (SIA-6).
     */
    private void cargarHistorial() {
        String rut = txtRut.getText().trim();
        try {
            estudianteActual = Main.obtenerEstudiantePorRut(rut);
            modeloTabla.setRowCount(0);
            for (Asistencia a : estudianteActual.getListaAsistencia()) {
                // Determinar tipo para mostrar en la tabla
                String tipo;
                if (a instanceof AsistenciaNormal)
                    tipo = "Normal";
                else if (a instanceof InasistenciaExtraordinaria)
                    tipo = "Inasistencia";
                else
                    tipo = "Salida Anticipada";

                // getResumen() es polimórfico (SIA-6)
                modeloTabla.addRow(new Object[] {
                        a.getId(), tipo, a.getFecha(), a.getResumen(), a.getObservacion()
                });
            }
            if (estudianteActual.getListaAsistencia().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "El estudiante " + estudianteActual.getNombreCompleto() + " no tiene registros.",
                        "Sin registros", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (EstudianteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Estudiante no encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * SIA-7: Registra una nueva asistencia para el estudiante cargado.
     */
    private void registrarAsistencia() {
        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero cargue el historial de un estudiante ingresando su RUT.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fecha = JOptionPane.showInputDialog(this, "Fecha (DD/MM/AAAA):");
        if (fecha == null || fecha.isEmpty())
            return;

        String obs = JOptionPane.showInputDialog(this, "Observación (puede estar vacía):");
        if (obs == null)
            obs = "";

        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        String[] tipos = { "Asistencia Normal", "Inasistencia Extraordinaria", "Salida Anticipada" };
        int tipo = JOptionPane.showOptionDialog(this,
                "Seleccione el tipo de registro:", "Tipo de Asistencia",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, tipos, tipos[0]);

        Asistencia asistencia;
        if (tipo == 0) {
            int puntual = JOptionPane.showConfirmDialog(this,
                    "¿El alumno llegó puntual?", "Puntualidad", JOptionPane.YES_NO_OPTION);
            asistencia = new AsistenciaNormal(id, fecha, obs, puntual == JOptionPane.YES_OPTION);
        } else if (tipo == 1) {
            String motivo = JOptionPane.showInputDialog(this, "Motivo de inasistencia:");
            if (motivo == null || motivo.isEmpty())
                return;
            asistencia = new InasistenciaExtraordinaria(id, fecha, obs, motivo);
        } else if (tipo == 2) {
            String hora = JOptionPane.showInputDialog(this, "Hora de salida (HH:MM):");
            if (hora == null || hora.isEmpty())
                return;
            asistencia = new SalidaAnticipada(id, fecha, obs, hora);
        } else {
            return;
        }

        estudianteActual.agregarAsistencia(asistencia);
        cargarHistorial();
        JOptionPane.showMessageDialog(this,
            "Asistencia registrada. ID: " + id, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /** SIA-8: Edita la observación del registro seleccionado en la tabla. */
    private void editarAsistencia() {
        int fila = tablaAsistencias.getSelectedRow();
        if (fila < 0 || estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);
        Asistencia a = estudianteActual.buscarAsistencia(id);
        if (a == null)
            return;

        String nuevaObs = JOptionPane.showInputDialog(this,
                "Nueva observación:", a.getObservacion());
        if (nuevaObs != null) {
            a.setObservacion(nuevaObs);
            cargarHistorial();
            JOptionPane.showMessageDialog(this, "Observación actualizada.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** SIA-8: Elimina el registro de asistencia seleccionado en la tabla. */
    private void eliminarAsistencia() {
        int fila = tablaAsistencias.getSelectedRow();
        if (fila < 0 || estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) modeloTabla.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el registro con ID " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            estudianteActual.eliminarAsistencia(id);
            cargarHistorial();
            JOptionPane.showMessageDialog(this, "Registro eliminado.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
