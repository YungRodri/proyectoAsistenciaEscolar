package AsistenciaCurso.vista;

import AsistenciaCurso.modelo.*;
import AsistenciaCurso.controlador.*;

/**
 * Panel que genera un reporte en tabla y permite exportarlo a Excel (SIA-O2).
 * Sirve para tener una vista general del estado de todo el curso.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Panel que muestra un resumen de asistencia por curso.
 * Permite ver estadísticas de todos los estudiantes de un curso seleccionado.
 */
public class PanelResumenCurso extends JPanel {

    private Ventana ventana;
    private JComboBox<String> comboCurso;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelResumenCurso(Ventana ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(8, 8));
        setBackground(new Color(245, 245, 250));
        initUI();
    }

    private void initUI() {
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(245, 245, 250));

        JLabel titulo = new JLabel("Resumen por Curso", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(50, 50, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSelector.setBackground(new Color(245, 245, 250));

        panelSelector.add(new JLabel("Seleccionar curso:"));

        comboCurso = new JComboBox<>(Main.listaCursos);

        JButton btnCargar = new JButton("Ver Resumen");
        btnCargar.addActionListener(e -> cargarResumen());

        panelSelector.add(comboCurso);
        panelSelector.add(btnCargar);

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelSelector, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {
                "Nombre Completo",
                "RUT",
                "Normales",
                "Inasistencias",
                "Sal. Anticipadas",
                "% Asistencia"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columna) {
                if (columna == 2 || columna == 3 || columna == 4) {
                    return Integer.class;
                }

                if (columna == 5) {
                    return Double.class;
                }

                return String.class;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        TableRowSorter<DefaultTableModel> ordenador = new TableRowSorter<>(modelo);
        tabla.setRowSorter(ordenador);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(new Color(245, 245, 250));

        JButton btnExportar = new JButton("Exportar curso a CSV");
        JButton btnVolver = new JButton("Menú Principal");

        btnExportar.addActionListener(e -> exportarCSV());
        btnVolver.addActionListener(e -> ventana.cambiarVista("menu"));

        panelSur.add(btnExportar);
        panelSur.add(btnVolver);

        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Llena la tabla con los estudiantes del curso seleccionado.
     */
    public void cargarResumen() {
        String cursoSeleccionado = (String) comboCurso.getSelectedItem();
        modelo.setRowCount(0);

        for (Estudiante estudiante : Main.listaGlobal.values()) {
            if (estudiante.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                modelo.addRow(new Object[]{
                        estudiante.getNombreCompleto(),
                        estudiante.getRut(),
                        estudiante.contarAsistenciaNormales(),
                        estudiante.contarAsistenciaExtraordinarias(),
                        estudiante.contarAsistenciasAnticipadas(),
                        Math.round(estudiante.getPorcentajeAsistencia() * 10.0) / 10.0
                });
            }
        }

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay estudiantes registrados en el curso: " + cursoSeleccionado,
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Exporta el resumen actual a un archivo CSV.
     * Excel puede abrir este archivo como planilla.
     */
    private void exportarCSV() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No hay datos para exportar. Cargue primero un curso.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String cursoSeleccionado = (String) comboCurso.getSelectedItem();

        JFileChooser selector = new JFileChooser();
        String nombreArchivo = "resumen_" + cursoSeleccionado.replace(" ", "_") + ".csv";
        selector.setSelectedFile(new File(nombreArchivo));

        if (selector.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        if (!archivo.getName().endsWith(".csv")) {
            archivo = new File(archivo.getAbsolutePath() + ".csv");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                bw.write(modelo.getColumnName(col));

                if (col < modelo.getColumnCount() - 1) {
                    bw.write(";");
                }
            }

            bw.newLine();

            for (int fila = 0; fila < modelo.getRowCount(); fila++) {
                for (int col = 0; col < modelo.getColumnCount(); col++) {
                    Object valor = modelo.getValueAt(fila, col);
                    bw.write(valor == null ? "" : valor.toString());

                    if (col < modelo.getColumnCount() - 1) {
                        bw.write(";");
                    }
                }

                bw.newLine();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Archivo exportado: " + archivo.getName(),
                    "Exportación completada",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al exportar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}