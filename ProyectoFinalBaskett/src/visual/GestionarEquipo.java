package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestionarEquipo extends JDialog {
    private ControladoraLiga controladora;
    private JComboBox<Equipo> cbxEquipos;
    private JList<Jugador> lstTodosJugadores;
    private JList<Jugador> lstJugadoresEquipo;
    private DefaultListModel<Jugador> modelTodos;
    private DefaultListModel<Jugador> modelEquipo;

    public GestionarEquipo(ControladoraLiga controladora) {
        this.controladora = controladora;
        initComponents();
        cargarEquipos();
        cargarJugadores();
    }

    private void initComponents() {
        setTitle("Gestión de Equipos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior: Selección de equipo
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSuperior.add(new JLabel("Seleccionar Equipo:"));
        cbxEquipos = new JComboBox<>();
        cbxEquipos.addActionListener(e -> actualizarJugadoresEquipo());
        panelSuperior.add(cbxEquipos);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central: Listas de jugadores
        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 10, 0));
        panelCentral.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Lista de todos los jugadores
        modelTodos = new DefaultListModel<>();
        lstTodosJugadores = new JList<>(modelTodos);
        panelCentral.add(new JScrollPane(lstTodosJugadores));

        // Botones de gestión
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 0, 10));
        JButton btnAgregar = new JButton(">");
        btnAgregar.addActionListener(e -> agregarJugador());
        JButton btnEliminar = new JButton("<");
        btnEliminar.addActionListener(e -> eliminarJugador());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelCentral.add(panelBotones);

        // Lista de jugadores del equipo
        modelEquipo = new DefaultListModel<>();
        lstJugadoresEquipo = new JList<>(modelEquipo);
        panelCentral.add(new JScrollPane(lstJugadoresEquipo));

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior: Botones
        JPanel panelInferior = new JPanel();
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelInferior.add(btnGuardar);
        panelInferior.add(btnCancelar);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarEquipos() {
        cbxEquipos.removeAllItems();
        for (Equipo equipo : controladora.getMisEquipos()) {
            cbxEquipos.addItem(equipo);
        }
    }

    private void cargarJugadores() {
        modelTodos.clear();
        for (Jugador jugador : controladora.getMisJugadores()) {
            modelTodos.addElement(jugador);
        }
    }

    private void actualizarJugadoresEquipo() {
        modelEquipo.clear();
        Equipo seleccionado = (Equipo) cbxEquipos.getSelectedItem();
        if (seleccionado != null) {
            for (Jugador jugador : seleccionado.getJugadores()) {
                modelEquipo.addElement(jugador);
            }
        }
    }

    private void agregarJugador() {
        Jugador seleccionado = lstTodosJugadores.getSelectedValue();
        if (seleccionado != null) {
            modelTodos.removeElement(seleccionado);
            modelEquipo.addElement(seleccionado);
        }
    }

    private void eliminarJugador() {
        Jugador seleccionado = lstJugadoresEquipo.getSelectedValue();
        if (seleccionado != null) {
            modelEquipo.removeElement(seleccionado);
            modelTodos.addElement(seleccionado);
        }
    }

    private void guardarCambios() {
        Equipo equipo = (Equipo) cbxEquipos.getSelectedItem();
        if (equipo != null) {
            ArrayList<Jugador> nuevosJugadores = new ArrayList<>();
            for (int i = 0; i < modelEquipo.size(); i++) {
                nuevosJugadores.add(modelEquipo.getElementAt(i));
            }
            equipo.setJugadores(nuevosJugadores);
            controladora.actualizarEquipo(equipo);
            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente!");
            dispose();
        }
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        GestionarEquipo dialog = new GestionarEquipo(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}