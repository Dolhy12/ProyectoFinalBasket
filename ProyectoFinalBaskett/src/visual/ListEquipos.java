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
import logico.Equipo;

public class ListEquipos extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private ControladoraLiga controladora;

    public ListEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControladoraLiga controladora = new ControladoraLiga();
                ListEquipos frame = new ListEquipos(controladora);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        setTitle("Lista de Equipos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 450);
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

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(10, 360, 100, 30);
        contentPane.add(btnModificar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(120, 360, 100, 30);
        contentPane.add(btnEliminar);

        cargarEquipos();

        btnModificar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                String id = (String) table.getValueAt(fila, 0);
    
                RegEquipo ventana = new RegEquipo(controladora, id);
                ventana.setVisible(true);
                ventana.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        cargarEquipos();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                String id = (String) table.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar equipo " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controladora.eliminarEquipo(id);
                    cargarEquipos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un equipo.");
            }
        });
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