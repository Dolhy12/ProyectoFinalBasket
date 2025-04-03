package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.EstadisticasEquipo;

public class ListarEstadisticasEquipo extends JDialog {

    private JTable tablaEstadisticas;
    private DefaultTableModel model;
    
    /**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        try {
            ControladoraLiga controladora = ControladoraLiga.getInstance();
            ListarEstadisticasEquipo dialog = new ListarEstadisticasEquipo(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Create the dialog.
	 */
    public ListarEstadisticasEquipo(ControladoraLiga controladora) {
        setTitle("Estadísticas de Equipos");
        setModal(true);
        setSize(1080, 500);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(240, 240, 240));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        String[] columnNames = {"Equipo", "Partidos", "Victorias", "Derrotas", "% Victorias", "Puntos Por Partido", "Robos", "Bloqueos", "Asistencias"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setBackground(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(212, 122, 25)); 
        table.setSelectionForeground(Color.WHITE);
        
        tablaEstadisticas = new JTable(model);
        tablaEstadisticas.setBackground(new Color(90, 90, 90));
        tablaEstadisticas.setForeground(Color.WHITE);
        tablaEstadisticas.setGridColor(Color.DARK_GRAY);
        
        cargarDatos(controladora.getMisEquipos());
        
        JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);
        scrollPane.getViewport().setBackground(new Color(240, 240, 240));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBackground(new Color(240, 240, 240));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);
        
        contentPane.add(buttonPane, BorderLayout.SOUTH);
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
