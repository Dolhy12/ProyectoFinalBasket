package visual;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.EstadisticasEquipo;

public class ListarEstadisticasEquipo extends JDialog {

    private JTable tabla;
    private static DefaultTableModel model;
    private static ControladoraLiga controladora;
    
    public ListarEstadisticasEquipo(ControladoraLiga controladora) {
        configurarVentana();
        initComponents(controladora);
        cargarDatos(controladora.getMisEquipos());
    }

    private void configurarVentana() {
        setTitle("Estadísticas de Equipos");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
    }

    private void initComponents(ControladoraLiga controladora) {
        String[] columnas = {
            "Equipo", "Partidos", "Victorias", "Derrotas", 
            "% Victorias", "Puntos/Partido", "Robos", 
            "Bloqueos", "Asistencias"
        };
        
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(model);
        personalizarTabla();
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        configurarBotones();
    }

    private void personalizarTabla() {
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(new Color(60, 60, 60));
    }

    private void configurarBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        btnCerrar.setBackground(new Color(255, 147, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private static void cargarDatos(ArrayList<Equipo> equipos) {
        model.setRowCount(0);
        for (Equipo equipo : equipos) {
            EstadisticasEquipo stats = equipo.getEstadisticas();
            model.addRow(new Object[]{
                equipo.getNombre(),
                stats.getPartidosJugados(),
                stats.getVictorias(),
                stats.getDerrotas(),
                String.format("%.1f%%", stats.getPorcentajeVictorias()),
                String.format("%.1f", stats.getPromedioPuntosPorPartido()),
                stats.getRobosTotales(),
                stats.getBloqueosTotales(),
                stats.getAsistenciasTotales()
            });
        }
    }
    
    public static void refrescarDatos() {
        cargarDatos(controladora.getMisEquipos());
    }
    
    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        ListarEstadisticasEquipo dialog = new ListarEstadisticasEquipo(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}