package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logico.*;

public class ListEstadisticasJugador extends JDialog {
    private Jugador jugador;
    private ControladoraLiga controladora;
    private JLabel lblPuntosTotales;
    private JLabel lblRebotes;
    private JLabel lblAsistencias;
    private JLabel lblDoblesDobles;
    private JLabel lblTriplesDobles;
    private JLabel lblLesionesActivas;
    private JList<String> lstPartidos;
    private JList<String> lstLesiones;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        SwingUtilities.invokeLater(() -> {
            ListEstadisticasJugador dialog = new ListEstadisticasJugador(controladora, "001");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }

    /**
     * Create the dialog.
     */
    public ListEstadisticasJugador(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.jugador = controladora.buscarJugador(idJugador);

        if (jugador == null) {
            JOptionPane.showMessageDialog(null, "Jugador no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Estadísticas de " + jugador.getNombre());
        setSize(800, 600);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 147, 30));
        JLabel lblIcono = new JLabel();
        lblIcono.setIcon(new ImageIcon("Imagenes/jugador_icon.png"));
        topPanel.add(lblIcono);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 147, 30));

        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        statsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        statsPanel.setBackground(new Color(240, 240, 240));
        agregarComponentesEstadisticas(statsPanel);
        tabbedPane.addTab("Estadísticas", statsPanel);

        JPanel lesionesPanel = new JPanel(new BorderLayout());
        lesionesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        lesionesPanel.setBackground(new Color(240, 240, 240));
        agregarComponentesLesiones(lesionesPanel);
        tabbedPane.addTab("Lesiones", lesionesPanel);

        JPanel partidosPanel = new JPanel(new BorderLayout());
        partidosPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        partidosPanel.setBackground(new Color(240, 240, 240));
        agregarComponentesPartidos(partidosPanel);
        tabbedPane.addTab("Partidos", partidosPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBackground(new Color(240, 240, 240));

        JButton btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setIcon(new ImageIcon("Imagenes/refresh_icon.png"));
        btnActualizar.addActionListener(e -> actualizarDatos());
        buttonPane.add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setIcon(new ImageIcon("Imagenes/close_icon.png"));
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);

        add(buttonPane, BorderLayout.SOUTH);
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
        ArrayList<Lesion> lesionesActivas = jugador.getLesionesActivas();
        if (lesionesActivas.isEmpty()) {
            modeloLesiones.addElement("No hay lesiones activas.");
        } else {
            for (Lesion lesion : lesionesActivas) {
                modeloLesiones.addElement(
                    lesion.getTipo() + " - " +
                    lesion.getParteCuerpo() + " - " +
                    lesion.getDuracionEstimada() + " días restantes"
                );
            }
        }

        lstLesiones = new JList<>(modeloLesiones);
        lstLesiones.setBackground(new Color(90, 90, 90));
        lstLesiones.setForeground(Color.WHITE);
        panel.add(new JScrollPane(lstLesiones), BorderLayout.CENTER);
    }

    private void agregarComponentesPartidos(JPanel panel) {
        DefaultListModel<String> modeloPartidos = new DefaultListModel<>();
        ArrayList<Juego> partidos = controladora.getJuegosPorJugador(jugador.getID());
        if (partidos.isEmpty()) {
            modeloPartidos.addElement("No hay partidos registrados.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Juego juego : partidos) {
                String info = juego.getFecha().format(formatter) + " - " +
                              juego.getEquipoLocal().getNombre() + " vs " +
                              juego.getEquipoVisitante().getNombre();
                modeloPartidos.addElement(info);
            }
        }

        lstPartidos = new JList<>(modeloPartidos);
        lstPartidos.setBackground(new Color(90, 90, 90));
        lstPartidos.setForeground(Color.WHITE);
        lstPartidos.addListSelectionListener(e -> mostrarDetallesPartido());
        panel.add(new JScrollPane(lstPartidos), BorderLayout.CENTER);
    }

    private void mostrarDetallesPartido() {
        int index = lstPartidos.getSelectedIndex();
        if (index != -1 && !lstPartidos.getModel().getElementAt(index).equals("No hay partidos registrados.")) {
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

        DefaultListModel<String> modeloLesiones = new DefaultListModel<>();
        ArrayList<Lesion> lesionesActivas = jugador.getLesionesActivas();
        if (lesionesActivas.isEmpty()) {
            modeloLesiones.addElement("No hay lesiones activas.");
        } else {
            for (Lesion lesion : lesionesActivas) {
                modeloLesiones.addElement(
                    lesion.getTipo() + " - " +
                    lesion.getParteCuerpo() + " - " +
                    lesion.getDuracionEstimada() + " días restantes"
                );
            }
        }
        lstLesiones.setModel(modeloLesiones);

        DefaultListModel<String> modeloPartidos = new DefaultListModel<>();
        ArrayList<Juego> partidos = controladora.getJuegosPorJugador(jugador.getID());
        if (partidos.isEmpty()) {
            modeloPartidos.addElement("No hay partidos registrados.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Juego juego : partidos) {
                String info = juego.getFecha().format(formatter) + " - " +
                              juego.getEquipoLocal().getNombre() + " vs " +
                              juego.getEquipoVisitante().getNombre();
                modeloPartidos.addElement(info);
            }
        }
        lstPartidos.setModel(modeloPartidos);
    }

    class DetallePartidoDialog extends JDialog {
        public DetallePartidoDialog(JDialog parent, Juego juego, Jugador jugador) {
            super(parent, "Estadísticas en el partido", true);
            setSize(400, 300);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.setBackground(new Color(240, 240, 240));

            Resultado resultado = juego.getResultado();
            if (resultado != null) {
                int[] stats = obtenerStatsJugadorEnPartido(juego, jugador);
                panel.add(new JLabel("Puntos:"));
                panel.add(new JLabel(String.valueOf(stats[0])));
                panel.add(new JLabel("Rebotes:"));
                panel.add(new JLabel(String.valueOf(stats[1])));
                panel.add(new JLabel("Asistencias:"));
                panel.add(new JLabel(String.valueOf(stats[2])));
            } else {
                panel.add(new JLabel("Sin datos disponibles"));
                panel.add(new JLabel(""));
            }

            add(panel);
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