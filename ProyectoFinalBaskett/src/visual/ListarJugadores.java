package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.Jugador;
import logico.ControladoraLiga;

import java.awt.*;
import java.util.List;

public class ListarJugadores extends JDialog {
    
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private ControladoraLiga controladora;
    
    public ListarJugadores(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Listado de Jugadores");
        setBounds(100, 100, 800, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
        String[] columnas = {
            "ID", "Nombre", "Edad", "Posici�n", "Nacionalidad", "Fecha Nacimiento", "Peso", "Altura", "N�mero"
        };
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        table = new JTable(model);
        
        table.setBackground(new Color(240, 240, 240));
        
        table.setSelectionBackground(new Color(255, 165, 0)); 
        table.setSelectionForeground(Color.WHITE);
        
        table.setBackground(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        buttonPane.add(btnCerrar);
        
        cargarJugadores();
    }
    
    private void cargarJugadores() {
        List<Jugador> jugadores = controladora.getMisJugadores();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Jugador jugador : jugadores) {
            Object[] row = {
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
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        try {
            ControladoraLiga controladora = new ControladoraLiga();
            ListarJugadores dialog = new ListarJugadores(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
