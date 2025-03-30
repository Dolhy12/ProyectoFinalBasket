package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;

import java.awt.*;
import java.util.ArrayList;

public class ListarEquipos extends JDialog {
    
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private ControladoraLiga controladora;
    
    public ListarEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Listado de Equipos");
        setBounds(100, 100, 800, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        String[] columnas = {"ID", "Nombre", "Ciudad", "Entrenador", "Capitán", "Mascota", "Año de Fundación"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 147, 30));
        
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        JButton btnModificar = new JButton("Modificar");
        buttonPane.add(btnModificar);
        
        JButton btnEliminar = new JButton("Eliminar");
        buttonPane.add(btnEliminar);
        buttonPane.add(btnCerrar);
        
        cargarEquipos();
    }
    
    private void cargarEquipos() {
        ArrayList<Equipo> equipos = controladora.getMisEquipos();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Equipo equipo : equipos) {
            Object[] row = {
                equipo.getID(),
                equipo.getNombre(),
                equipo.getCiudad(),
                equipo.getEntrenador(),
                equipo.getCapitan(),
                equipo.getNombreDeLaMascota(),
                equipo.getTiempoFundado()
            };
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        try {
            ControladoraLiga controladora = new ControladoraLiga();
            ListarEquipos dialog = new ListarEquipos(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
