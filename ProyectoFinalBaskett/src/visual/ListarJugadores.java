package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.Jugador;
import logico.ControladoraLiga;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListarJugadores extends JDialog {
    
    private JTable table;
    private ControladoraLiga controladora;
    
    public ListarJugadores(ControladoraLiga controladora) {
        this.controladora = controladora;
        configurarVentana();
        iniciarComponentes();
        cargarJugadores();
    }

    private void configurarVentana() {
        setTitle("Listado Completo de Jugadores");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
    }

    private void iniciarComponentes() {
        String[] columnas = {"ID", "Nombre", "Edad", "Posición", "Nacionalidad", "Nacimiento", "Peso", "Altura", "Número"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        
        table = new JTable(model);
        personalizarTabla();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        configurarBotones();
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
    }

    private void configurarBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnEstadisticas = crearBotonConIcono("Estadísticas", "Imagenes/stats_icon.png");
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(this::accionModificar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this::accionEliminar);
        
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
    }

    private JButton crearBotonConIcono(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        try {
            ImageIcon icono = new ImageIcon(rutaIcono);
            Image imagen = icono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagen));
        } catch (Exception e) {
            System.out.println("Error al cargar el icono: " + e.getMessage());
        }
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        return boton;
    }

    private void cargarJugadores() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Jugador jugador : controladora.getMisJugadores()) {
            model.addRow(new Object[]{
                jugador.getID(),
                jugador.getNombre(),
                jugador.getEdad(),
                jugador.getPosicion(),
                jugador.getNacionalidad(),
                sdf.format(jugador.getFechaDeNacimiento()),
                String.format("%.1f kg", jugador.getPeso()),
                String.format("%.2f m", jugador.getAltura()),
                jugador.getNumero()
            });
        }
    }

    private void mostrarEstadisticas() {
        int fila = table.getSelectedRow();
        if(fila != -1) {
            String id = (String) table.getValueAt(fila, 0);
            new ListEstadisticasJugador(controladora, id).setVisible(true);
        } else {
            mostrarMensaje("Selecciona un jugador de la tabla", "Sin selección");
        }
    }

    private void accionModificar(ActionEvent e) {
        int fila = table.getSelectedRow();
        if(fila != -1) {
            String id = (String) table.getValueAt(fila, 0);
            mostrarMensaje("Función en desarrollo", "Próximamente");
        } else {
            mostrarMensaje("Selecciona un jugador para modificar", "Sin selección");
        }
    }

    private void accionEliminar(ActionEvent e) {
        int fila = table.getSelectedRow();
        if(fila != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de eliminar este jugador?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if(confirmacion == JOptionPane.YES_OPTION) {
                String id = (String) table.getValueAt(fila, 0);
                controladora.eliminarJugador(id);
                ((DefaultTableModel) table.getModel()).removeRow(fila);
                mostrarMensaje("Jugador eliminado exitosamente", "Éxito");
            }
        } else {
            mostrarMensaje("Selecciona un jugador para eliminar", "Sin selección");
        }
    }

    private void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        ListarJugadores dialog = new ListarJugadores(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}