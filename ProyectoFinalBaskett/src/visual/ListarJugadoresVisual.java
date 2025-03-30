package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.ControladoraLiga;
import logico.Jugador;

public class ListarJugadoresVisual extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private ControladoraLiga controladora;

    public ListarJugadoresVisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControladoraLiga controladora = new ControladoraLiga();
                ListarJugadoresVisual frame = new ListarJugadoresVisual(controladora);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        setTitle("Lista de Jugadores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] columnas = {"ID", "Nombre", "Edad", "Posición", "Nacionalidad", "Nacimiento", "Peso", "Altura", "Número"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 860, 340);
        contentPane.add(scrollPane);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(10, 360, 100, 30);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(120, 360, 100, 30);
        contentPane.add(btnEliminar);

        cargarJugadores();

        btnModificar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                String id = (String) table.getValueAt(fila, 0);
                jugadorvisual ventana = new jugadorvisual(controladora, id);
                ventana.setVisible(true);
                ventana.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        cargarJugadores();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un jugador.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                String id = (String) table.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar jugador " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controladora.eliminarJugador(id);
                    cargarJugadores();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un jugador.");
            }
        });
    }

    private void cargarJugadores() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0);
        ArrayList<Jugador> jugadores = controladora.getMisJugadores();
        for (Jugador jugador : jugadores) {
            Object[] fila = {
                jugador.getID(),
                jugador.getNombre(),
                jugador.getEdad(),
                jugador.getPosicion(),
                jugador.getNacionalidad(),
                jugador.getFechaDeNacimiento(),
                jugador.getPeso(),
                jugador.getAltura(),
                jugador.getNumero()
            };
            modelo.addRow(fila);
        }
    }
}