package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListEstadisticasJugador extends JDialog {
    private Jugador jugador;
    private ControladoraLiga controladora;
    private JTabbedPane tabbedPane;
    private JTable tablaEstadisticas;
    private JTable tablaLesiones;
    private JTable tablaPartidos;

    public ListEstadisticasJugador(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.jugador = controladora.buscarJugador(idJugador);
        if (jugador == null) {
            JOptionPane.showMessageDialog(this, "Jugador no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        configurarVentana();
        initComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Estadísticas de " + jugador.getNombre());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setModal(true);
    }

    private void initComponentes() {
        tabbedPane = new JTabbedPane();
        
        JPanel pnlEstadisticas = new JPanel(new BorderLayout());
        pnlEstadisticas.add(crearPanelEstadisticas(), BorderLayout.NORTH);
        pnlEstadisticas.add(crearTablaEstadisticas(), BorderLayout.CENTER);
        tabbedPane.addTab("Estadísticas", pnlEstadisticas);

        tablaLesiones = new JTable(new DefaultTableModel(
            new String[]{"Tipo", "Parte del cuerpo", "Fecha", "Duración (días)", "Estado"}, 0
        ));
        tabbedPane.addTab("Lesiones", new JScrollPane(tablaLesiones));

        tablaPartidos = new JTable(new DefaultTableModel(
            new String[]{"Fecha", "Equipo Local", "Equipo Visitante", "Puntos", "Rebotes", "Asistencias"}, 0
        ));
        tabbedPane.addTab("Partidos", new JScrollPane(tablaPartidos));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        EstadisticasJugador stats = jugador.getEstadisticas();
        
        agregarMetrica(panel, "Puntos totales:", String.valueOf(stats.getPuntosTotales()));
        agregarMetrica(panel, "Rebotes:", String.valueOf(stats.getRebotes()));
        agregarMetrica(panel, "Asistencias:", String.valueOf(stats.getAsistencias()));
        agregarMetrica(panel, "Robos:", String.valueOf(stats.getRobos()));
        agregarMetrica(panel, "Bloqueos:", String.valueOf(stats.getBloqueos()));
        agregarMetrica(panel, "Dobles-dobles:", String.valueOf(stats.getDoblesDobles()));
        agregarMetrica(panel, "Triples-dobles:", String.valueOf(stats.getTriplesDobles()));
        agregarMetrica(panel, "Minutos jugados:", String.valueOf(stats.getMinutosJugados()));
        
        return panel;
    }

    private void agregarMetrica(JPanel panel, String titulo, String valor) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblValor = new JLabel(valor);
        
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedor.add(lblTitulo);
        contenedor.add(lblValor);
        panel.add(contenedor);
    }

    private JScrollPane crearTablaEstadisticas() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Métrica", "Valor"}, 0);
        tablaEstadisticas = new JTable(model);
        return new JScrollPane(tablaEstadisticas);
    }

    private void cargarDatos() {
        cargarLesiones();
        cargarPartidos();
    }

    private void cargarLesiones() {
        DefaultTableModel model = (DefaultTableModel) tablaLesiones.getModel();
        model.setRowCount(0);
        
        for (Lesion lesion : jugador.getLesiones()) {
            model.addRow(new Object[]{
                lesion.getTipo(),
                lesion.getParteCuerpo(),
                lesion.getFechaLesion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                lesion.getDuracionEstimada(),
                lesion.getEstado()
            });
        }
    }

    private void cargarPartidos() {
        DefaultTableModel model = (DefaultTableModel) tablaPartidos.getModel();
        model.setRowCount(0);
        
        for (Juego juego : controladora.getJuegosPorJugador(jugador.getID())) {
            int[] stats = obtenerStatsJugador(juego);
            if (stats != null) {
                model.addRow(new Object[]{
                    juego.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    juego.getEquipoLocal().getNombre(),
                    juego.getEquipoVisitante().getNombre(),
                    stats[0],
                    stats[1],
                    stats[2]
                });
            }
        }
    }

    private int[] obtenerStatsJugador(Juego juego) {
        if (juego.getResultado() == null) return null;
        
        int index = juego.getResultado().getIdsJugadoresLocales().indexOf(jugador.getID());
        if (index != -1) {
            return juego.getResultado().getStatsLocales().get(index);
        }
        
        index = juego.getResultado().getIdsJugadoresVisitantes().indexOf(jugador.getID());
        if (index != -1) {
            return juego.getResultado().getStatsVisitantes().get(index);
        }
        
        return null;
    }
}