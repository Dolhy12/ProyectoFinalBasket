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
import logico.Equipo;

public class ListarEquiposVisual extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private ControladoraLiga controladora;

    public ListarEquiposVisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladoraLiga controladora = new ControladoraLiga();
                    ListarEquiposVisual frame = new ListarEquiposVisual(controladora);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        setTitle("Lista de Equipos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 400); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] columnas = {"ID", "Nombre", "Ciudad", "Entrenador", "Capitán", "Mascota", "Tiempo Fundado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 760, 340); 
        contentPane.add(scrollPane);

        cargarEquipos();
    }

    private void cargarEquipos() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); 
        ArrayList<Equipo> equipos = controladora.getMisEquipos();
        for (Equipo equipo : equipos) {
            Object[] fila = {
                equipo.getID(),
                equipo.getNombre(),
                equipo.getCiudad(),
                equipo.getEntrenador(),
                equipo.getCapitan(),
                equipo.getNombreDeLaMascota(),
                equipo.getTiempoFundado()
            };
            modelo.addRow(fila);
        }
    }
}