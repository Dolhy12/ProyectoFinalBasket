package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladoraLiga controladora = new ControladoraLiga();
                    ListarJugadoresVisual frame = new ListarJugadoresVisual(controladora);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        setTitle("Lista de Jugadores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 400); 
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

        cargarJugadores();
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