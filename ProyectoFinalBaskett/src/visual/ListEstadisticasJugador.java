package visual;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import logico.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListEstadisticasJugador extends JFrame {
    private Jugador jugador;
    private ControladoraLiga controladora;
    private JLabel lblPuntosTotales;
    private JLabel lblRebotes;
    private JLabel lblAsistencias;
    private JLabel lblDoblesDobles;
    private JLabel lblTriplesDobles;
    private JLabel lblLesionesActivas;
    private JList<String> lstPartidos;
    
	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        SwingUtilities.invokeLater(() -> {
            ListEstadisticasJugador frame = new ListEstadisticasJugador(controladora, "001");
            frame.setVisible(true);
        });
    }
    
	/**
	 * Create the dialog.
	 */
    public ListEstadisticasJugador(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.jugador = controladora.buscarJugador(idJugador);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Estadísticas de " + jugador.getNombre());
        setSize(800, 600);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        
        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        agregarComponentesEstadisticas(statsPanel);
        tabbedPane.addTab("Estadísticas", statsPanel);

        
        JPanel lesionesPanel = new JPanel(new BorderLayout());
        agregarComponentesLesiones(lesionesPanel);
        tabbedPane.addTab("Lesiones", lesionesPanel);

        
        JPanel partidosPanel = new JPanel(new BorderLayout());
        agregarComponentesPartidos(partidosPanel);
        tabbedPane.addTab("Partidos", partidosPanel);

        add(tabbedPane, BorderLayout.CENTER);
        
        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.addActionListener(e -> actualizarDatos());
        add(btnActualizar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void agregarComponentesEstadisticas(JPanel panel) {
        EstadisticasJugador stats = jugador.getEstadisticas();
        
        panel.add(new JLabel("Puntos totales:"));
        lblPuntosTotales = new JLabel(String.valueOf(stats.getPuntosTotales()));
        panel.add(lblPuntosTotales);
        
        panel.add(new JLabel("Rebotes:"));
        lblRebotes = new JLabel(String.valueOf(stats.getRebotes()));
        panel.add(lblRebotes);
        
        panel.add(new JLabel("Asistencias:"));
        lblAsistencias = new JLabel(String.valueOf(stats.getAsistencias()));
        panel.add(lblAsistencias);
        
        panel.add(new JLabel("Dobles-dobles:"));
        lblDoblesDobles = new JLabel(String.valueOf(stats.getDoblesDobles()));
        panel.add(lblDoblesDobles);
        
        panel.add(new JLabel("Triples-dobles:"));
        lblTriplesDobles = new JLabel(String.valueOf(stats.getTriplesDobles()));
        panel.add(lblTriplesDobles);
        
        panel.add(new JLabel("Lesiones activas:"));
        lblLesionesActivas = new JLabel(String.valueOf(jugador.getLesionesActivas().size()));
        panel.add(lblLesionesActivas);
    }

    private void agregarComponentesLesiones(JPanel panel) {
        DefaultListModel<String> modeloLesiones = new DefaultListModel<>();
        for (Lesion lesion : jugador.getLesionesActivas()) {
            modeloLesiones.addElement(
                lesion.getTipo() + " - " + 
                lesion.getParteCuerpo() + " - " +
                lesion.getDuracionEstimada() + " días restantes"
            );
        }
        
        JList<String> listaLesiones = new JList<>(modeloLesiones);
        panel.add(new JScrollPane(listaLesiones), BorderLayout.CENTER);
    }

    private void agregarComponentesPartidos(JPanel panel) {
        DefaultListModel<String> modeloPartidos = new DefaultListModel<>();
        ArrayList<Juego> partidos = controladora.getJuegosPorJugador(jugador.getID());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Juego juego : partidos) {
            String info = juego.getFecha().format(formatter) + " - " +
                          juego.getEquipoLocal().getNombre() + " vs " +
                          juego.getEquipoVisitante().getNombre();
            modeloPartidos.addElement(info);
        }
        
        lstPartidos = new JList<>(modeloPartidos);
        lstPartidos.addListSelectionListener(e -> mostrarDetallesPartido());
        panel.add(new JScrollPane(lstPartidos), BorderLayout.CENTER);
    }

    private void mostrarDetallesPartido() {
        int index = lstPartidos.getSelectedIndex();
        if (index != -1) {
            Juego juego = controladora.getJuegosPorJugador(jugador.getID()).get(index);
            new DetallePartidoDialog(this, juego, jugador).setVisible(true);
        }
    }

    private void actualizarDatos() {
        EstadisticasJugador stats = jugador.getEstadisticas();
        lblPuntosTotales.setText(String.valueOf(stats.getPuntosTotales()));
        lblRebotes.setText(String.valueOf(stats.getRebotes()));
        lblAsistencias.setText(String.valueOf(stats.getAsistencias()));
        lblDoblesDobles.setText(String.valueOf(stats.getDoblesDobles()));
        lblTriplesDobles.setText(String.valueOf(stats.getTriplesDobles()));
        lblLesionesActivas.setText(String.valueOf(jugador.getLesionesActivas().size()));
    }

   
    class DetallePartidoDialog extends JDialog {
        public DetallePartidoDialog(JFrame parent, Juego juego, Jugador jugador) {
            super(parent, "Estadísticas en el partido", true);
            setSize(400, 300);
            
            JPanel panel = new JPanel(new GridLayout(0, 2));
            Resultado resultado = juego.getResultado();
            
            if (resultado != null) {
                int[] stats = obtenerStatsJugadorEnPartido(juego, jugador);
                panel.add(new JLabel("Puntos:"));
                panel.add(new JLabel(String.valueOf(stats[0])));
                panel.add(new JLabel("Rebotes:"));
                panel.add(new JLabel(String.valueOf(stats[1])));
                panel.add(new JLabel("Asistencias:"));
                panel.add(new JLabel(String.valueOf(stats[2])));
            }
            
            add(panel);
            setLocationRelativeTo(parent);
        }

        private int[] obtenerStatsJugadorEnPartido(Juego juego, Jugador jugador) {
            Resultado resultado = juego.getResultado();
            int index = resultado.getJugadoresLocales().indexOf(jugador);
            if (index != -1) return resultado.getStatsLocales().get(index);
            
            index = resultado.getJugadoresVisitantes().indexOf(jugador);
            if (index != -1) return resultado.getStatsVisitantes().get(index);
            
            return new int[]{0, 0, 0};
        }
    }
}