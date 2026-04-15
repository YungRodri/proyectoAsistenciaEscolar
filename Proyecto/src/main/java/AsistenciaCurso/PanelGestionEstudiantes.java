package AsistenciaCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel GUI para el listado, búsqueda, edición y eliminación de estudiantes.
 * Cumple SIA-7 (listar) y SIA-8 (buscar, editar, eliminar) para la colección de
 * Estudiantes.
 */
public class PanelGestionEstudiantes extends JPanel {

    private final Ventana ventana;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscarRut;

    public PanelGestionEstudiantes(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 245, 250));
        initUI();
    }

    private void initUI() {
        // ---- NORTE: título ----
        JLabel titulo = new JLabel("Gestión de Estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(50, 50, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));
        add(titulo, BorderLayout.NORTH);

        // ---- CENTRO: tabla ----
        String[] columnas = { "RUT", "Nombre", "Apellido Paterno", "Apellido Materno", "Edad", "Curso" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setRowHeight(22);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEstudiantes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        // ---- SUR: búsqueda + botones ----
        JPanel panelSur = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelSur.setBackground(new Color(245, 245, 250));

        // Barra de búsqueda por RUT (SIA-8)
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscar.setBackground(new Color(245, 245, 250));
        panelBuscar.add(new JLabel("Buscar por RUT:"));
        txtBuscarRut = new JTextField(14);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarEstudiante());
        panelBuscar.add(txtBuscarRut);
        panelBuscar.add(btnBuscar);

        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 2));
        panelBotones.setBackground(new Color(245, 245, 250));

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Menú Principal");

        btnActualizar.addActionListener(e -> cargarTabla());
        btnEditar.addActionListener(e -> editarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        panelSur.add(panelBuscar);
        panelSur.add(panelBotones);
        add(panelSur, BorderLayout.SOUTH);

        cargarTabla();
    }

    /** Recarga la tabla con todos los estudiantes actuales (SIA-7). */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Estudiante e : Main.listaGlobal.values()) {
            modeloTabla.addRow(new Object[] {
                    e.getRut(), e.getNombre(), e.getApellidoP(),
                    e.getApellidoM(), e.getEdad(), e.getCurso()
            });
        }
    }

    /** SIA-8: Busca un estudiante por RUT y muestra sus datos. */
    private void buscarEstudiante() {
        String rut = txtBuscarRut.getText().trim();
        try {
            Estudiante e = Main.obtenerEstudiantePorRut(rut);
            JOptionPane.showMessageDialog(this,
                    "Nombre  : " + e.getNombreCompleto() + "\n"
                            + "Curso   : " + e.getCurso() + "\n"
                            + "Edad    : " + e.getEdad() + "\n"
                            + "Asistencias registradas: " + e.getCantAsistencia(),
                    "Estudiante encontrado", JOptionPane.INFORMATION_MESSAGE);
        } catch (EstudianteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** SIA-8: Edita nombre y apellidos del estudiante seleccionado en la tabla. */
    private void editarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String rut = (String) modeloTabla.getValueAt(fila, 0);
        try {
            Estudiante alumno = Main.obtenerEstudiantePorRut(rut);

            String nuevoNombre = JOptionPane.showInputDialog(this, "Nombre:", alumno.getNombre());
            if (nuevoNombre != null && !nuevoNombre.isEmpty())
                alumno.setNombre(nuevoNombre);

            String nuevoAp = JOptionPane.showInputDialog(this, "Apellido Paterno:", alumno.getApellidoP());
            if (nuevoAp != null && !nuevoAp.isEmpty())
                alumno.setApellidoP(nuevoAp);

            String nuevoAm = JOptionPane.showInputDialog(this, "Apellido Materno:", alumno.getApellidoM());
            if (nuevoAm != null && !nuevoAm.isEmpty())
                alumno.setApellidoM(nuevoAm);

            cargarTabla();
            JOptionPane.showMessageDialog(this, "Estudiante actualizado.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (EstudianteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** SIA-8: Elimina el estudiante seleccionado en la tabla. */
    private void eliminarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String rut = (String) modeloTabla.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al estudiante con RUT " + rut + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            Main.listaGlobal.remove(rut);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Estudiante eliminado.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
