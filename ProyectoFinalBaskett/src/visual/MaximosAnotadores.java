package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import java.awt.*;
import java.util.Comparator;

public class MaximosAnotadores extends JDialog {

    private JTable table;
    private ControladoraLiga controladora;
    private DefaultTableModel model;
    private JTextField txtFiltro;

    public MaximosAnotadores(ControladoraLiga controladora) {
        this.controladora = controladora;

        setTitle("Máximos Anotadores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JLabel lblFiltro = new JLabel("Filtrar:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        txtFiltro = new JTextField(20);
        txtFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { cargarDatos(); }
            public void removeUpdate(DocumentEvent e) { cargarDatos(); }
            public void changedUpdate(DocumentEvent e) { cargarDatos(); }
        });
        panelFiltro.add(lblFiltro);
        panelFiltro.add(txtFiltro);
        add(panelFiltro, BorderLayout.NORTH);

        String[] columnas = {"Jugador", "Equipo", "Puntos Totales"};
        model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = crearBotonNaranja("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarDatos();
    }

    private JButton crearBotonNaranja(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(255, 147, 30));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return btn;
    }

    private void cargarDatos() {
        model.setRowCount(0);
        String filtro = txtFiltro.getText().toLowerCase();

        controladora.getMisJugadores().stream()
            .filter(j -> j.getNombre().toLowerCase().contains(filtro))
            .sorted(Comparator.comparingInt(j -> -j.getEstadisticas().getPuntosTotales()))
            .limit(10)
            .forEach(j -> {
                Equipo equipo = controladora.buscarEquipoPorJugador(j.getID());
                model.addRow(new Object[]{
                    j.getNombre(),
                    equipo != null ? equipo.getNombre() : "Sin equipo",
                    j.getEstadisticas().getPuntosTotales()
                });
            });
    } 

    public static void main(String[] args) {
        MaximosAnotadores dialog = new MaximosAnotadores(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}