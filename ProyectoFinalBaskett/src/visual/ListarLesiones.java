package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        getContentPane().add(panelFiltros, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Jugador", "Equipo", "Tipo", "Tratamiento", "Fecha", "Duración", "Estado"}, 0
        );
        JTable tabla = new JTable(model);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(
                        ListarLesiones.this,
                        "Seleccione una lesión para eliminar.",
                        "Advertencia",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(
                    ListarLesiones.this,
                    "¿Está seguro de eliminar esta lesión?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    String nombreJugador = (String) tabla.getValueAt(filaSeleccionada, 0); // Columna "Jugador"
                    String tipo = (String) tabla.getValueAt(filaSeleccionada, 2); // Columna "Tipo"
                    String fechaStr = (String) tabla.getValueAt(filaSeleccionada, 4); // Columna "Fecha"
                    LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    // Buscar jugadores con ese nombre
                    java.util.List<Jugador> jugadoresCoincidentes = controladora.getMisJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombreJugador))
                        .collect(java.util.stream.Collectors.toList());

                    if (jugadoresCoincidentes.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            ListarLesiones.this,
                            "No se encontró ningún jugador con el nombre: " + nombreJugador,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    boolean eliminada = false;
                    Jugador jugadorModificado = null;

                    // Iterar sobre jugadores con el mismo nombre
                    for (Jugador jugador : jugadoresCoincidentes) {
                        boolean removido = jugador.getLesiones().removeIf(l ->
                            l.getTipo().equals(tipo) && l.getFechaLesion().equals(fecha)
                        );
                        if (removido) {
                            eliminada = true;
                            jugadorModificado = jugador;
                            controladora.actualizarJugador(jugador);
                            break; // Salir del bucle tras eliminar la lesión
                        }
                    }

                    if (eliminada) {
                        btnFiltrar.doClick(); // Refrescar la tabla
                        JOptionPane.showMessageDialog(
                            ListarLesiones.this,
                            "Lesión eliminada exitosamente del jugador: " + nombreJugador
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            ListarLesiones.this,
                            "No se encontró la lesión para el jugador: " + nombreJugador,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }

                    // Si hay más de un jugador con el mismo nombre, advertir al usuario
                    if (jugadoresCoincidentes.size() > 1) {
                        JOptionPane.showMessageDialog(
                            ListarLesiones.this,
                            "Advertencia: Hay " + jugadoresCoincidentes.size() + " jugadores con el nombre '" + nombreJugador + "'. " +
                            "Se eliminó la lesión del primer jugador encontrado.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            }
        });

        JButton btnCerrar = new JButton("Cerrar");
        
        for (JButton btn : new JButton[]{btnEliminar, btnCerrar}) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            btn.setForeground(Color.WHITE);
        }
        btnEliminar.setBackground(new Color(178, 34, 34));
        btnCerrar.setBackground(new Color(100, 100, 100));
        
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

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
                        lesion.getFechaLesion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
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