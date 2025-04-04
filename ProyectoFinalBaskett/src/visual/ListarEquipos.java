package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import java.awt.*;
import java.util.ArrayList;

public class ListarEquipos extends JDialog {

    private JTable table;
    private ControladoraLiga controladora;
    
    public ListarEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        configurarVentana();
        inicializarComponentes();
        cargarEquipos();
    }

    private void configurarVentana() {
        setTitle("Listado de Equipos");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {
        configurarTabla();
        configurarBotones();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Ciudad", "Entrenador", "Capitán", "Mascota", "Fundación"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        personalizarTabla();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void personalizarTabla() {
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
    }

    private void configurarBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnModificar = crearBoton("Modificar", new Color(255, 147, 30));
        JButton btnEliminar = crearBoton("Eliminar", new Color(178, 34, 34));
        JButton btnCerrar = crearBoton("Cerrar", new Color(255, 147, 30));
        
        btnModificar.addActionListener(e -> modificarEquipo());
        btnEliminar.addActionListener(e -> eliminarEquipo());
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        boton.setFocusPainted(false);
        return boton;
    }

    private void cargarEquipos() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (Equipo equipo : controladora.getMisEquipos()) {
            model.addRow(new Object[]{
                equipo.getID(),
                equipo.getNombre(),
                equipo.getCiudad(),
                equipo.getEntrenador(),
                (equipo.getCapitan() != null) ? equipo.getCapitan().getNombre() : "Sin capitán",
                equipo.getNombreDeLaMascota(),
                equipo.getTiempoFundado()
            });
        }
    }

    private void modificarEquipo() {
        int fila = table.getSelectedRow();
        if(fila != -1) {
            JOptionPane.showMessageDialog(this, "Función en desarrollo", "Próximamente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un equipo de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarEquipo() {
        int fila = table.getSelectedRow();
        if(fila != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar este equipo?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if(confirmacion == JOptionPane.YES_OPTION) {
                String id = (String) table.getValueAt(fila, 0);
                controladora.eliminarEquipo(id);
                ((DefaultTableModel) table.getModel()).removeRow(fila);
                JOptionPane.showMessageDialog(this, "Equipo eliminado exitosamente");
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un equipo de la tabla", 
                "Sin selección", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        ListarEquipos dialog = new ListarEquipos(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}