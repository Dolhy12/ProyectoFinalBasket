package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarEquipos extends JDialog {
    
    private JTable table;
    private ControladoraLiga controladora;

    public ListarEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Listado de Equipos");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        String[] columnas = {"ID", "Nombre", "Ciudad", "Entrenador", "Mascota", "Fundación"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        for (Equipo equipo : controladora.getMisEquipos()) {
            model.addRow(new Object[]{
                equipo.getID(),
                equipo.getNombre(),
                equipo.getCiudad(),
                equipo.getEntrenador(),
                equipo.getNombreDeLaMascota(),
                equipo.getTiempoFundado()
            });
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setBackground(Color.WHITE);

        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.setBackground(new Color(255, 147, 30));
        btnVerDetalles.setForeground(Color.WHITE);
        btnVerDetalles.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerDetalles.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(255, 147, 30));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(178, 34, 34));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(255, 147, 30));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btnVerDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila = table.getSelectedRow();
                if (fila != -1) {
                    String id = (String) table.getValueAt(fila, 0);
                    Equipo equipo = controladora.buscarEquipo(id);
                    if (equipo != null) {
                        DetalleEquipo dialog = new DetalleEquipo(controladora, equipo);
                        dialog.setModal(true);
                        dialog.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(ListarEquipos.this,
                        "Seleccione un equipo de la tabla",
                        "Sin selección",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila = table.getSelectedRow();
                if (fila != -1) {
                    String id = (String) table.getValueAt(fila, 0);
                    Equipo equipo = controladora.buscarEquipo(id);
                    if (equipo != null) {
                        RegEquipo dialog = new RegEquipo(equipo);
                        dialog.setModal(true);
                        dialog.setVisible(true);
                        model.setRowCount(0);
                        for (Equipo eq : controladora.getMisEquipos()) {
                            model.addRow(new Object[]{
                                eq.getID(),
                                eq.getNombre(),
                                eq.getCiudad(),
                                eq.getEntrenador(),
                                eq.getNombreDeLaMascota(),
                                eq.getTiempoFundado()
                            });
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ListarEquipos.this,
                        "Seleccione un equipo de la tabla",
                        "Sin selección",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fila = table.getSelectedRow();
                if (fila != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(ListarEquipos.this,
                        "¿Está seguro de eliminar este equipo?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        String id = (String) table.getValueAt(fila, 0);
                        controladora.eliminarEquipo(id);
                        ((DefaultTableModel) table.getModel()).removeRow(fila);
                        JOptionPane.showMessageDialog(ListarEquipos.this, "Equipo eliminado exitosamente");
                    }
                } else {
                    JOptionPane.showMessageDialog(ListarEquipos.this,
                        "Seleccione un equipo de la tabla",
                        "Sin selección",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelBotones.add(btnVerDetalles);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        ListarEquipos dialog = new ListarEquipos(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}