package visual;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import logico.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TopEquipos extends JDialog {

    private ControladoraLiga controladora;
    private JTable tabla;
    private DefaultTableModel model;
    private JTextField txtFiltro;

    public TopEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;

        setTitle("Top 5 Equipos con Más Victorias");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblFiltro = new JLabel("Filtrar por nombre o ciudad:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        txtFiltro = new JTextField(25);
        txtFiltro.setFont(new Font("Arial", Font.PLAIN, 14));

        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { cargarDatos(); }
            public void removeUpdate(DocumentEvent e) { cargarDatos(); }
            public void changedUpdate(DocumentEvent e) { cargarDatos(); }
        });

        panelFiltro.add(lblFiltro);
        panelFiltro.add(txtFiltro);
        add(panelFiltro, BorderLayout.NORTH);

        String[] columnas = {"Equipo", "Victorias", "Derrotas", "% Victorias", "Puntos/Partido"};
        model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(model);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setFillsViewportHeight(true);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabla.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(255, 147, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        model.setRowCount(0);
        String filtro = txtFiltro.getText().toLowerCase();

        List<Equipo> filtrados = controladora.getMisEquipos().stream()
            .filter(e -> e.getNombre().toLowerCase().contains(filtro) || e.getCiudad().toLowerCase().contains(filtro))
            .sorted(Comparator.comparingInt((Equipo e) -> e.getEstadisticas().getVictorias()).reversed())
            .limit(5)
            .collect(Collectors.toList());

        for (Equipo e : filtrados) {
            EstadisticasEquipo stats = e.getEstadisticas();
            model.addRow(new Object[]{
                e.getNombre(),
                stats.getVictorias(),
                stats.getDerrotas(),
                String.format("%.1f%%", stats.getPorcentajeVictorias()),
                String.format("%.1f", stats.getPromedioPuntosPorPartido())
            });
        }
    }

    public static void main(String[] args) {
        TopEquipos dialog = new TopEquipos(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}