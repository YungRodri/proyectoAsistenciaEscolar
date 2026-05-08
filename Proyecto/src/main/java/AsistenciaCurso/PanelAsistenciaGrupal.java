package AsistenciaCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

    public PanelAsistenciaGrupal(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 248, 255));
        initUI();
    }

    private void initUI() {
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
        panelControles.add(txtFecha);

        panelControles.add(new JLabel("Observación:"));
        txtObservacion = new JTextField(14);
        panelControles.add(txtObservacion);

        JButton btnCargar = new JButton("Cargar Curso");
        btnCargar.addActionListener(e -> cargarCurso());
        panelControles.add(btnCargar);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelControles, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {"Asiste", "RUT", "Nombre Completo"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 0;
            }

            @Override
            public Class<?> getColumnClass(int columna) {
                if (columna == 0) {
                    return Boolean.class;
                }

                return String.class;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setRowHeight(24);
        tablaEstudiantes.getColumnModel().getColumn(0).setMaxWidth(70);
        tablaEstudiantes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelSur.setBackground(new Color(245, 248, 255));

        JButton btnRegistrar = new JButton("Registrar Asistencia");
        JButton btnVolver = new JButton("Menú Principal");

        btnRegistrar.addActionListener(e -> registrarAsistenciaGrupal());
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelSur.add(btnRegistrar);
        panelSur.add(btnVolver);

        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Carga en la tabla los estudiantes del curso seleccionado.
     */
    private void cargarCurso() {
        String cursoSeleccionado = (String) comboCurso.getSelectedItem();

        modeloTabla.setRowCount(0);

        for (Estudiante estudiante : Main.listaGlobal.values()) {
            if (estudiante.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                modeloTabla.addRow(new Object[]{
                        Boolean.TRUE,
                        estudiante.getRut(),
                        estudiante.getNombreCompleto()
                });
            }
        }

        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay estudiantes en el curso: " + cursoSeleccionado,
                    "Sin estudiantes",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Registra asistencia para todos los estudiantes cargados en la tabla.
     */
    private void registrarAsistenciaGrupal() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero cargue un curso.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String fecha = txtFecha.getText().trim();

        if (!validarFecha(fecha)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una fecha válida en formato DD/MM/AAAA.",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE
            );
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

                Asistencia asistencia;

                if (asiste) {
                    asistencia = new AsistenciaNormal(id, fecha, obs, true);
                } else {
                    asistencia = new InasistenciaExtraordinaria(id, fecha, obs, "Inasistencia grupal");
                }

                alumno.agregarAsistencia(asistencia);
                registrados++;

                if (alumno.getPorcentajeAsistencia() < 85.0) {
                    alertas++;
                }

            } catch (EstudianteNoEncontradoException ex) {
                // Si el estudiante fue eliminado mientras el panel estaba cargado,
                // simplemente se ignora esta fila.
            }
        }

        GestorArchivos.guardarEstudiantes(Main.listaGlobal);

        String mensaje = "Asistencia registrada para " + registrados + " estudiante(s).";

        if (alertas > 0) {
            mensaje += "\n" + alertas + " estudiante(s) tienen asistencia por debajo del 85%.";
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Registro completado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Valida formato simple de fecha DD/MM/AAAA.
     */
    private boolean validarFecha(String fecha) {
        if (fecha == null) {
            return false;
        }

        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }

        String[] partes = fecha.split("/");

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            if (anio < 1900 || anio > 2100) {
                return false;
            }

            if (mes < 1 || mes > 12) {
                return false;
            }

            if (dia < 1 || dia > 31) {
                return false;
            }

            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                return false;
            }

            if (mes == 2 && dia > 29) {
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}