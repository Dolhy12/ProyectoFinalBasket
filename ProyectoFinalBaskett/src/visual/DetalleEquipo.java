package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import logico.EstadisticasEquipo;
import java.awt.*;

public class DetalleEquipo extends JDialog {

    private ControladoraLiga controladora;
    private Equipo equipo;
    private JTable tablaJugadores;

    public DetalleEquipo(ControladoraLiga controladora, Equipo equipo) {
        this.controladora = controladora;
        this.equipo = equipo;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Detalles de " + equipo.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblInfo = new JLabel("Equipo: " + equipo.getNombre() + " | Ciudad: " + equipo.getCiudad() +
                                    " | Entrenador: " + equipo.getEntrenador());
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblInfo);
        add(panelSuperior, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Jugadores", crearPanelJugadores());
        tabbedPane.addTab("Estadísticas", crearPanelEstadisticas());
        add(tabbedPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        JButton btnCerrar = crearBoton("Cerrar", new Color(255, 147, 30));
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columnas = {"ID", "Nombre", "Edad", "Posición", "Número"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaJugadores = new JTable(model);
        personalizarTabla(tablaJugadores);
        cargarJugadores(model);

        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        EstadisticasEquipo stats = equipo.getEstadisticas();
        panel.add(new JLabel("Partidos Jugados:"));
        panel.add(new JLabel(String.valueOf(stats.getPartidosJugados())));
        panel.add(new JLabel("Victorias:"));
        panel.add(new JLabel(String.valueOf(stats.getVictorias())));
        panel.add(new JLabel("Derrotas:"));
        panel.add(new JLabel(String.valueOf(stats.getDerrotas())));
        panel.add(new JLabel("Porcentaje de Victorias:"));
        panel.add(new JLabel(String.format("%.1f%%", stats.getPorcentajeVictorias())));
        panel.add(new JLabel("Puntos Promedio por Partido:"));
        panel.add(new JLabel(String.format("%.1f", stats.getPromedioPuntosPorPartido())));
        panel.add(new JLabel("Puntos Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getTotalPuntos())));
        panel.add(new JLabel("Robos Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getRobosTotales())));
        panel.add(new JLabel("Bloqueos Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getBloqueosTotales())));
        panel.add(new JLabel("Asistencias Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getAsistenciasTotales())));

        return panel;
    }

    private void personalizarTabla(JTable tabla) {
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setBackground(Color.WHITE);
    }

    private void cargarJugadores(DefaultTableModel model) {
        model.setRowCount(0);
        for (Jugador jugador : equipo.getJugadores()) {
            model.addRow(new Object[]{
                jugador.getID(),
                jugador.getNombre(),
                jugador.getEdad(),
                jugador.getPosicion(),
                jugador.getNumero()
            });
        }
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setFocusPainted(false);
        return boton;
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        Equipo equipo = controladora.getMisEquipos().isEmpty() ? null : controladora.getMisEquipos().get(0);
        if (equipo != null) {
            DetalleEquipo dialog = new DetalleEquipo(controladora, equipo);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
    }
}