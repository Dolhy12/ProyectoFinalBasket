package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ListEstadisticasJugador extends JDialog {

    public ListEstadisticasJugador(ControladoraLiga controladora, String idJugador) {
        Jugador jugador = controladora.buscarJugador(idJugador);
        if (jugador == null) {
            JOptionPane.showMessageDialog(this, "Jugador no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Estadísticas de " + jugador.getNombre());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelEstadisticas = new JPanel(new GridLayout(0, 2, 15, 15));
        panelEstadisticas.setBorder(new EmptyBorder(20, 30, 20, 30));
        panelEstadisticas.setBackground(Color.WHITE);
        
        EstadisticasJugador stats = jugador.getEstadisticas();
        String[][] metricas = {
            {"Puntos totales:", String.valueOf(stats.getPuntosTotales())},
            {"Rebotes:", String.valueOf(stats.getRebotes())},
            {"Asistencias:", String.valueOf(stats.getAsistencias())},
            {"Robos:", String.valueOf(stats.getRobos())},
            {"Bloqueos:", String.valueOf(stats.getBloqueos())},
            {"Dobles-dobles:", String.valueOf(stats.getDoblesDobles())},
            {"Triples-dobles:", String.valueOf(stats.getTriplesDobles())},
            {"Minutos jugados:", String.valueOf(stats.getMinutosJugados())}
        };
        
        for (String[] metrica : metricas) {
            JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            contenedor.setBackground(new Color(240, 240, 240));
            contenedor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
            JLabel lblTitulo = new JLabel(metrica[0]);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            lblTitulo.setForeground(new Color(70, 70, 70));
            
            JLabel lblValor = new JLabel(metrica[1]);
            lblValor.setFont(new Font("Arial", Font.BOLD, 16));
            lblValor.setForeground(new Color(255, 147, 30));
            
            contenedor.add(lblTitulo);
            contenedor.add(lblValor);
            panelEstadisticas.add(contenedor);
        }
        tabbedPane.addTab("Estadísticas", new JScrollPane(panelEstadisticas));

        DefaultTableModel modelLesiones = new DefaultTableModel(
            new String[]{"Tipo", "Parte del cuerpo", "Fecha", "Duración (días)", "Estado"}, 0
        );
        for (Lesion lesion : jugador.getLesiones()) {
            modelLesiones.addRow(new Object[]{
                lesion.getTipo(),
                lesion.getParteCuerpo(),
                lesion.getFechaLesion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                lesion.getDuracionEstimada(),
                lesion.getEstado()
            });
        }
        JTable tablaLesiones = new JTable(modelLesiones);
        tablaLesiones.setRowHeight(30);
        tablaLesiones.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaLesiones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaLesiones.setSelectionBackground(new Color(255, 147, 30));
        tablaLesiones.setSelectionForeground(Color.WHITE);
        tabbedPane.addTab("Lesiones", new JScrollPane(tablaLesiones));

        DefaultTableModel modelPartidos = new DefaultTableModel(
            new String[]{"Fecha", "Equipo Local", "Equipo Visitante", "Puntos", "Rebotes", "Asistencias"}, 0
        );
        for (Juego juego : controladora.getJuegosPorJugador(jugador.getID())) {
            int[] statsJuego = obtenerStatsJugador(juego, jugador.getID());
            if (statsJuego != null) {
                modelPartidos.addRow(new Object[]{
                    juego.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    juego.getEquipoLocal().getNombre(),
                    juego.getEquipoVisitante().getNombre(),
                    statsJuego[0],
                    statsJuego[1],
                    statsJuego[2]
                });
            }
        }
        JTable tablaPartidos = new JTable(modelPartidos);
        tablaPartidos.setRowHeight(30);
        tablaPartidos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPartidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPartidos.setSelectionBackground(new Color(255, 147, 30));
        tablaPartidos.setSelectionForeground(Color.WHITE);
        tabbedPane.addTab("Partidos", new JScrollPane(tablaPartidos));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private int[] obtenerStatsJugador(Juego juego, String idJugador) {
        if (juego.getResultado() == null) return null;
        
        int indexLocal = juego.getResultado().getIdsJugadoresLocales().indexOf(idJugador);
        if (indexLocal != -1) return juego.getResultado().getStatsLocales().get(indexLocal);
        
        int indexVisitante = juego.getResultado().getIdsJugadoresVisitantes().indexOf(idJugador);
        if (indexVisitante != -1) return juego.getResultado().getStatsVisitantes().get(indexVisitante);
        
        return null;
    }
}