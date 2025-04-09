package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;

public class ListarLesiones extends JDialog {

    public ListarLesiones(ControladoraLiga controladora) {
        setTitle("Listado de Lesiones");
        setSize(900, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltros.setBackground(Color.WHITE);
        
        JComboBox<String> cmbJugadores = new JComboBox<>();
        cmbJugadores.addItem("Todos");
        for (Jugador j : controladora.getMisJugadores()) {
            cmbJugadores.addItem(j.getNombre() + " (" + j.getID() + ")");
        }
        
        JComboBox<String> cmbEquipos = new JComboBox<>();
        cmbEquipos.addItem("Todos");
        for (Equipo e : controladora.getMisEquipos()) {
            cmbEquipos.addItem(e.getNombre() + " (" + e.getID() + ")");
        }

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(255, 147, 30));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnFiltrar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        panelFiltros.add(new JLabel("Jugador:"));
        panelFiltros.add(cmbJugadores);
        panelFiltros.add(new JLabel("Equipo:"));
        panelFiltros.add(cmbEquipos);
        panelFiltros.add(btnFiltrar);
        add(panelFiltros, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Jugador", "Equipo", "Tipo", "Tratamiento", "Fecha", "Duración", "Estado"}, 0
        );
        JTable tabla = new JTable(model);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar");
        
        for (JButton btn : new JButton[]{btnModificar, btnEliminar, btnCerrar}) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            btn.setForeground(Color.WHITE);
        }
        btnModificar.setBackground(new Color(255, 147, 30));
        btnEliminar.setBackground(new Color(178, 34, 34));
        btnCerrar.setBackground(new Color(100, 100, 100));
        
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        btnFiltrar.addActionListener(e -> {
            String idJugador = cmbJugadores.getSelectedIndex() > 0 ? 
                cmbJugadores.getSelectedItem().toString().split("\\(")[1].replace(")", "") : null;
            
            String idEquipo = cmbEquipos.getSelectedIndex() > 0 ? 
                cmbEquipos.getSelectedItem().toString().split("\\(")[1].replace(")", "") : null;

            model.setRowCount(0);
            for (Jugador jugador : controladora.getMisJugadores()) {
                if (idJugador != null && !jugador.getID().equals(idJugador)) continue;
                
                Equipo equipo = controladora.buscarEquipoPorJugador(jugador.getID());
                if (idEquipo != null && (equipo == null || !equipo.getID().equals(idEquipo))) continue;
                
                String nombreEquipo = equipo != null ? equipo.getNombre() : "Sin equipo";
                
                for (Lesion lesion : jugador.getLesiones()) {
                    model.addRow(new Object[]{
                        jugador.getNombre(),
                        nombreEquipo,
                        lesion.getTipo(),
                        lesion.getTratamiento(),
                        lesion.getFechaLesion(),
                        lesion.getDuracionEstimada() + " días",
                        lesion.getEstado()
                    });
                }
            }
        });

        btnFiltrar.doClick();
    }

    public static void main(String[] args) {
        ListarLesiones dialog = new ListarLesiones(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}