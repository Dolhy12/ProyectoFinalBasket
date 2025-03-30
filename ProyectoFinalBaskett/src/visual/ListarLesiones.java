package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import logico.Lesion;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarLesiones extends JDialog {
    
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private ControladoraLiga controladora;
    private JComboBox<String> cmbJugadores;
    private JComboBox<String> cmbEquipos;
    private JButton btnFiltrarJugador;
    private JButton btnFiltrarEquipo;
    
    public ListarLesiones(ControladoraLiga controladora) {
        this.controladora = controladora;

        setTitle("Listado de Lesiones");
        setBounds(100, 100, 800, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Panel superior para filtros
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));

        // ComboBox de jugadores
        cmbJugadores = new JComboBox<>();
        cmbJugadores.addItem("Todos");
        for (Jugador j : controladora.getMisJugadores()) {
            cmbJugadores.addItem(j.getNombre() + " (" + j.getID() + ")");
        }
        panelFiltros.add(new JLabel("Jugador:"));
        panelFiltros.add(cmbJugadores);

        // Botón para filtrar por jugador
        btnFiltrarJugador = new JButton("Filtrar por Jugador");
        panelFiltros.add(btnFiltrarJugador);

        // ComboBox de equipos
        cmbEquipos = new JComboBox<>();
        cmbEquipos.addItem("Todos");
        for (Equipo e : controladora.getMisEquipos()) {
            cmbEquipos.addItem(e.getNombre() + " (" + e.getID() + ")");
        }
        panelFiltros.add(new JLabel("Equipo:"));
        panelFiltros.add(cmbEquipos);

        // Botón para filtrar por equipo
        btnFiltrarEquipo = new JButton("Filtrar por Equipo");
        panelFiltros.add(btnFiltrarEquipo);

        getContentPane().add(panelFiltros, BorderLayout.NORTH);

        // Tabla de lesiones
        String[] columnas = {"Jugador", "Equipo", "Tipo de Lesión", "Tratamiento", "Fecha de Lesión", "Duración", "Estado"};
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

        // Eventos de filtrado
        btnFiltrarJugador.addActionListener(e -> aplicarFiltro());
        btnFiltrarEquipo.addActionListener(e -> aplicarFiltro());

        cargarLesiones(null, null);
    }
    

    private void aplicarFiltro() {
        String idJugador = null;
        String idEquipo = null;

        // Obtener el ID del jugador seleccionado
        String seleccionJugador = (String) cmbJugadores.getSelectedItem();
        if (seleccionJugador != null && !seleccionJugador.equals("Todos")) {
            idJugador = seleccionJugador.split("\\(")[1].replace(")", "");
        }

        // Obtener el ID del equipo seleccionado
        String seleccionEquipo = (String) cmbEquipos.getSelectedItem();
        if (seleccionEquipo != null && !seleccionEquipo.equals("Todos")) {
            idEquipo = seleccionEquipo.split("\\(")[1].replace(")", "");
        }

        // Recargar la tabla con los filtros aplicados
        cargarLesiones(idJugador, idEquipo);
    }


    private void cargarLesiones(String idJugadorFiltro, String idEquipoFiltro) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ArrayList<Jugador> jugadores = controladora.getMisJugadores();
        for (Jugador jugador : jugadores) {
            if (idJugadorFiltro != null && !jugador.getID().equals(idJugadorFiltro)) {
                continue;
            }

            String nombreEquipo = "Sin equipo";
            Equipo equipo = controladora.buscarEquipoPorJugador(jugador.getID());
            if (equipo != null) {
                nombreEquipo = equipo.getNombre();
                if (idEquipoFiltro != null && !equipo.getID().equals(idEquipoFiltro)) {
                    continue;
                }
            } else if (idEquipoFiltro != null) {
                continue;
            }

            for (Lesion lesion : jugador.getLesiones()) {
                Object[] fila = {
                    jugador.getNombre(),
                    nombreEquipo,
                    lesion.getTipo(),
                    lesion.getTratamiento(),
                    lesion.getFechaLesion(),
                    lesion.getDuracionEstimada(),
                    lesion.getEstado()
                };
                model.addRow(fila);
            }
        }
    }


	public static void main(String[] args) {
        try {
            ControladoraLiga controladora = new ControladoraLiga();
            ListarLesiones dialog = new ListarLesiones(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}