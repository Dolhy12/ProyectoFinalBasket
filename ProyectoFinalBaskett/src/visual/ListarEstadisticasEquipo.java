package visual;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.EstadisticasEquipo;

public class ListarEstadisticasEquipo extends JDialog {

    private JTable tabla;
    private DefaultTableModel model;
    private ControladoraLiga controladora;
    private JTextField txtFiltro;

    public ListarEstadisticasEquipo(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Estadísticas de Equipos");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.setBorder(new EmptyBorder(10, 10, 0, 10));
        
        JLabel lblFiltro = new JLabel("Filtrar por nombre:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        txtFiltro = new JTextField(20);
        txtFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        });
        
        panelFiltro.add(lblFiltro);
        panelFiltro.add(txtFiltro);
        add(panelFiltro, BorderLayout.NORTH);

        String[] columnas = {"Equipo", "Partidos", "Victorias", "Derrotas", "% Victorias", 
                           "Puntos/Partido", "Robos", "Bloqueos", "Asistencias"};
        model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabla = new JTable(model);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(255, 147, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos(controladora.getMisEquipos());
    }

    private void filtrar() {
        String filtro = txtFiltro.getText().toLowerCase();
        model.setRowCount(0);
        for (Equipo equipo : controladora.getMisEquipos()) {
            if (equipo.getNombre().toLowerCase().contains(filtro)) {
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
    }

    private void cargarDatos(ArrayList<Equipo> equipos) {
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

    public static void main(String[] args) {
        ListarEstadisticasEquipo dialog = new ListarEstadisticasEquipo(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}