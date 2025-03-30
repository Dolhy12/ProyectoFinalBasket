package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.EstadisticasEquipo;

public class EstadisticasEquipoVisual extends JFrame {

    private JTable tablaEstadisticas;
    private DefaultTableModel model;

    public EstadisticasEquipoVisual(ControladoraLiga controladora) {
        setTitle("Estadísticas de Equipos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(70, 70, 70));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        String[] columnNames = {"Equipo", "Partidos", "Victorias", "Derrotas", "% Victorias", "Puntos/Partido", "Robos", "Bloqueos", "Asistencias"};
        model = new DefaultTableModel(columnNames, 0);
        
        tablaEstadisticas = new JTable(model);
        tablaEstadisticas.setBackground(new Color(90, 90, 90));
        tablaEstadisticas.setForeground(Color.WHITE);
        tablaEstadisticas.setGridColor(Color.DARK_GRAY);
        
        cargarDatos(controladora.getMisEquipos());
        
        JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);
        scrollPane.getViewport().setBackground(new Color(70, 70, 70));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private void cargarDatos(ArrayList<Equipo> equipos) {
        model.setRowCount(0);
        for (Equipo equipo : equipos) {
            EstadisticasEquipo stats = equipo.getEstadisticas();
            Object[] row = {
                equipo.getNombre(),
                stats.getPartidosJugados(),
                stats.getVictorias(),
                stats.getDerrotas(),
                String.format("%.1f%%", stats.getPorcentajeVictorias()),
                String.format("%.1f", stats.getPromedioPuntosPorPartido()),
                stats.getRobosTotales(),
                stats.getBloqueosTotales(),
                stats.getAsistenciasTotales()
            };
            model.addRow(row);
        }
    }
}