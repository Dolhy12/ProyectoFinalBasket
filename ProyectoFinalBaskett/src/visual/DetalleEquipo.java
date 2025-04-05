package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import logico.EstadisticasEquipo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetalleEquipo extends JDialog {

    private ControladoraLiga controladora;
    private Equipo equipo;

    public DetalleEquipo(ControladoraLiga controladora, Equipo equipo) {
        this.controladora = controladora;
        this.equipo = equipo;
        
        setTitle("Detalles de " + equipo.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(Color.WHITE);
        JLabel lblInfo = new JLabel("Equipo: " + equipo.getNombre() + " | Ciudad: " + equipo.getCiudad() + " | Entrenador: " + equipo.getEntrenador());
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblInfo);
        add(panelSuperior, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panelJugadores = new JPanel(new BorderLayout());
        panelJugadores.setBackground(Color.WHITE);
        String[] columnas = {"ID", "Nombre", "Edad", "Posición", "Número"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaJugadores = new JTable(model);
        tablaJugadores.setRowHeight(30);
        tablaJugadores.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaJugadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaJugadores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaJugadores.setFillsViewportHeight(true);
        tablaJugadores.setSelectionBackground(new Color(255, 147, 30));
        tablaJugadores.setSelectionForeground(Color.WHITE);
        tablaJugadores.setGridColor(new Color(220, 220, 220));
        tablaJugadores.setBackground(Color.WHITE);
        
        for (Jugador jugador : equipo.getJugadores()) {
            model.addRow(new Object[]{
                jugador.getID(),
                jugador.getNombre(),
                jugador.getEdad(),
                jugador.getPosicion(),
                jugador.getNumero()
            });
        }
        
        JScrollPane scrollJugadores = new JScrollPane(tablaJugadores);
        scrollJugadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelJugadores.add(scrollJugadores, BorderLayout.CENTER);
        tabbedPane.addTab("Jugadores", panelJugadores);

        JPanel panelStats = new JPanel(new GridLayout(0, 2, 10, 10));
        panelStats.setBackground(Color.WHITE);
        panelStats.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        EstadisticasEquipo stats = equipo.getEstadisticas();
        panelStats.add(new JLabel("Partidos Jugados:"));
        panelStats.add(new JLabel(String.valueOf(stats.getPartidosJugados())));
        panelStats.add(new JLabel("Victorias:"));
        panelStats.add(new JLabel(String.valueOf(stats.getVictorias())));
        panelStats.add(new JLabel("Derrotas:"));
        panelStats.add(new JLabel(String.valueOf(stats.getDerrotas())));
        panelStats.add(new JLabel("Porcentaje de Victorias:"));
        panelStats.add(new JLabel(String.format("%.1f%%", stats.getPorcentajeVictorias())));
        panelStats.add(new JLabel("Puntos Promedio por Partido:"));
        panelStats.add(new JLabel(String.format("%.1f", stats.getPromedioPuntosPorPartido())));
        panelStats.add(new JLabel("Puntos Totales:"));
        panelStats.add(new JLabel(String.valueOf(stats.getTotalPuntos())));
        panelStats.add(new JLabel("Robos Totales:"));
        panelStats.add(new JLabel(String.valueOf(stats.getRobosTotales())));
        panelStats.add(new JLabel("Bloqueos Totales:"));
        panelStats.add(new JLabel(String.valueOf(stats.getBloqueosTotales())));
        panelStats.add(new JLabel("Asistencias Totales:"));
        panelStats.add(new JLabel(String.valueOf(stats.getAsistenciasTotales())));
        tabbedPane.addTab("Estadísticas", panelStats);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(255, 147, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        if (!controladora.getMisEquipos().isEmpty()) {
            DetalleEquipo dialog = new DetalleEquipo(controladora, controladora.getMisEquipos().get(0));
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
    }
}