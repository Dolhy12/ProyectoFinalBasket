package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.Jugador;
import logico.ControladoraLiga;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class ListarJugadores extends JDialog {
    
    private ControladoraLiga controladora;
    private JTable table;

    public ListarJugadores(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Listado Completo de Jugadores");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltros.setBackground(Color.WHITE);
        
        JComboBox<String> cmbPosiciones = new JComboBox<>(new String[]{"Todas", "Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"});
        JComboBox<String> cmbNacionalidades = new JComboBox<>();
        JTextField txtNombre = new JTextField(15);
        
        Set<String> nacionalidades = new HashSet<>();
        for(Jugador j : controladora.getMisJugadores()) nacionalidades.add(j.getNacionalidad());
        cmbNacionalidades.addItem("Todas");
        for(String nac : nacionalidades) cmbNacionalidades.addItem(nac);
        
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(255, 147, 30));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> cargarJugadores(
            cmbPosiciones.getSelectedIndex() > 0 ? cmbPosiciones.getSelectedItem().toString() : null,
            cmbNacionalidades.getSelectedIndex() > 0 ? cmbNacionalidades.getSelectedItem().toString() : null,
            txtNombre.getText().trim().isEmpty() ? null : txtNombre.getText().trim().toLowerCase()
        ));
        
        panelFiltros.add(new JLabel("Posición:"));
        panelFiltros.add(cmbPosiciones);
        panelFiltros.add(new JLabel("Nacionalidad:"));
        panelFiltros.add(cmbNacionalidades);
        panelFiltros.add(new JLabel("Nombre:"));
        panelFiltros.add(txtNombre);
        panelFiltros.add(btnFiltrar);
        add(panelFiltros, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Edad", "Posición", "Nacionalidad", "Nacimiento", "Peso", "Altura", "Número"}, 0
        );
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnEstadisticas = new JButton("Estadísticas");
        try {
            ImageIcon icono = new ImageIcon("Imagenes/stats_icon.png");
            btnEstadisticas.setIcon(new ImageIcon(icono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        } catch (Exception e) { System.out.println("Error cargando icono"); }
        btnEstadisticas.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un jugador", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new ListEstadisticasJugador(controladora, (String) table.getValueAt(fila, 0)).setVisible(true);
        });

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un jugador", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Jugador jugador = controladora.buscarJugador((String) table.getValueAt(fila, 0));
            if(jugador != null) new RegJugador(jugador).setVisible(true);
            cargarJugadores(null, null, null);
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if(fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un jugador", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this, "¿Eliminar jugador?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                controladora.eliminarJugador((String) table.getValueAt(fila, 0));
                ((DefaultTableModel) table.getModel()).removeRow(fila);
            }
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        for(JButton btn : new JButton[]{btnEstadisticas, btnModificar, btnEliminar, btnCerrar}) {
            btn.setBackground(new Color(255, 147, 30));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        }
        
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarJugadores(null, null, null);
    }

    private void cargarJugadores(String posicion, String nacionalidad, String nombre) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Jugador j : controladora.getMisJugadores()) {
            if(posicion != null && !j.getPosicion().equalsIgnoreCase(posicion)) continue;
            if(nacionalidad != null && !j.getNacionalidad().equalsIgnoreCase(nacionalidad)) continue;
            if(nombre != null && !j.getNombre().toLowerCase().contains(nombre.toLowerCase())) continue;
            
            model.addRow(new Object[]{
                j.getID(),
                j.getNombre(),
                j.getEdad(),
                j.getPosicion(),
                j.getNacionalidad(),
                sdf.format(j.getFechaDeNacimiento()),
                String.format("%.1f kg", j.getPeso()),
                String.format("%.2f m", j.getAltura()),
                j.getNumero()
            });
        }
    }

    public static void main(String[] args) {
        ListarJugadores dialog = new ListarJugadores(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}