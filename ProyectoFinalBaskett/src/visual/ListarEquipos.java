package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import logico.ControladoraLiga;
import logico.Equipo;
import java.awt.*;

public class ListarEquipos extends JDialog {
    
    private JTable table;
    private ControladoraLiga controladora;
    private DefaultTableModel model;
    private JTextField txtFiltro;

    public ListarEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Listado de Equipos");
        setSize(1100, 600);
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

        String[] columnas = {"ID", "Nombre", "Ciudad", "Entrenador", "Mascota", "Fundación"};
        model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnVerDetalles = crearBotonNaranja("Ver Detalles");
        JButton btnModificar = crearBotonNaranja("Modificar");
        JButton btnEliminar = crearBotonRojo("Eliminar");
        JButton btnCerrar = crearBotonNaranja("Cerrar");

        btnVerDetalles.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila == -1) mostrarAdvertencia();
            else abrirDetalles(fila);
        });

        btnModificar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila == -1) mostrarAdvertencia();
            else modificarEquipo(fila);
        });

        btnEliminar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila == -1) mostrarAdvertencia();
            else eliminarEquipo(fila);
        });

        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnVerDetalles);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
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

    private JButton crearBotonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(178, 34, 34));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return btn;
    }

    private void cargarDatos() {
        model.setRowCount(0);
        String filtro = txtFiltro.getText().toLowerCase();
        for (Equipo equipo : controladora.getMisEquipos()) {
            if (equipo.getNombre().toLowerCase().contains(filtro) || 
                equipo.getID().toLowerCase().contains(filtro)) {
                model.addRow(new Object[]{
                    equipo.getID(),
                    equipo.getNombre(),
                    equipo.getCiudad(),
                    equipo.getEntrenador(),
                    equipo.getNombreDeLaMascota(),
                    equipo.getTiempoFundado()
                });
            }
        }
    }

    private void abrirDetalles(int fila) {
        String id = (String) table.getValueAt(fila, 0);
        Equipo equipo = controladora.buscarEquipo(id);
        if (equipo != null) {
            DetalleEquipo dialog = new DetalleEquipo(controladora, equipo);
            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }

    private void modificarEquipo(int fila) {
        String id = (String) table.getValueAt(fila, 0);
        Equipo equipo = controladora.buscarEquipo(id);
        if (equipo != null) {
            RegEquipo dialog = new RegEquipo(equipo);
            dialog.setModal(true);
            dialog.setVisible(true);
            cargarDatos();
        }
    }

    private void eliminarEquipo(int fila) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este equipo?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String id = (String) table.getValueAt(fila, 0);
            controladora.eliminarEquipo(id);
            model.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Equipo eliminado exitosamente");
        }
    }

    private void mostrarAdvertencia() {
        JOptionPane.showMessageDialog(this,
            "Seleccione un equipo de la tabla", "Sin selección", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        ListarEquipos dialog = new ListarEquipos(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}