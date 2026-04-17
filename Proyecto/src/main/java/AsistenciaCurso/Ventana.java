package AsistenciaCurso;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

/**
 * Panel de menú principal de la interfaz gráfica.
 * Ofrece acceso a todas las funcionalidades del sistema (SIA-7, SIA-8, SIA-10).
 */
public class Ventana extends javax.swing.JFrame {

    private CardLayout layout;
    private PanelGestionEstudiantes panelGestionEstudiantes;

    private Stack<String> historial;
    private String vistaActual;

    public Ventana() {
        initComponents();

        Main.listaGlobal = GestorArchivos.cargarEstudiantes();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GestorArchivos.guardarEstudiantes(Main.listaGlobal);
            }
        });

        layout = new CardLayout();
        panelContenedor.setLayout(layout);

        historial = new Stack<>();
        vistaActual = "menu";

        panelGestionEstudiantes = new PanelGestionEstudiantes(this);

        panelContenedor.add(new Menu(this), "menu");
        panelContenedor.add(new IngresoEstudiante(this), "estudiantes");
        panelContenedor.add(panelGestionEstudiantes, "gestionEstudiantes");
        panelContenedor.add(new PanelAsistencias(this), "asistencias");

        layout.show(panelContenedor, "menu");
    }

    public void cambiarVista(String nombre) {
        if (!vistaActual.equals(nombre)) {
            historial.push(vistaActual);
            vistaActual = nombre;
        }

        if (nombre.equals("gestionEstudiantes")) {
            refrescarPanelEstudiantes();
        }

        layout.show(panelContenedor, nombre);
    }

    public void volver() {
        if (!historial.isEmpty()) {
            vistaActual = historial.pop();

            if (vistaActual.equals("gestionEstudiantes")) {
                refrescarPanelEstudiantes();
            }

            layout.show(panelContenedor, vistaActual);
        } else {
            vistaActual = "menu";
            layout.show(panelContenedor, "menu");
        }
    }

    public void irMenu() {
        historial.clear();
        vistaActual = "menu";
        layout.show(panelContenedor, "menu");
    }

    public void refrescarPanelEstudiantes() {
        panelGestionEstudiantes.cargarTabla();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        panelContenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
                panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 511, Short.MAX_VALUE));
        panelContenedorLayout.setVerticalGroup(
                panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 338, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

    private javax.swing.JPanel panelContenedor;
}