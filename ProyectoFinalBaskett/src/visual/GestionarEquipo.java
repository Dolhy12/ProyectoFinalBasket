package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GestionarEquipo extends JDialog {
    private ControladoraLiga controladora;
    private JComboBox<Equipo> cbxEquipos;
    private DefaultListModel<Jugador> modelTodos = new DefaultListModel<>();
    private DefaultListModel<Jugador> modelEquipo = new DefaultListModel<>();
    private JList<Jugador> lstJugadoresEquipo;

    public GestionarEquipo(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Gestión de Equipos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelSuperior.setBackground(Color.WHITE);
        
        JLabel lblEquipo = new JLabel("Seleccionar Equipo:");
        lblEquipo.setFont(new Font("Arial", Font.BOLD, 14));
        
        cbxEquipos = new JComboBox<>();
        cbxEquipos.setFont(new Font("Arial", Font.PLAIN, 14));
        cbxEquipos.setPreferredSize(new Dimension(300, 30));
        cbxEquipos.addActionListener(e -> {
            modelEquipo.clear();
            Equipo seleccionado = (Equipo) cbxEquipos.getSelectedItem();
            if (seleccionado != null) {
                for (Jugador jugador : seleccionado.getJugadores()) {
                    modelEquipo.addElement(jugador);
                }
            }
        });
        
        panelSuperior.add(lblEquipo);
        panelSuperior.add(cbxEquipos);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 20, 0));
        panelCentral.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelCentral.setBackground(Color.WHITE);

        JList<Jugador> lstTodosJugadores = new JList<>(modelTodos);
        lstTodosJugadores.setBackground(new Color(245, 245, 245));
        lstTodosJugadores.setForeground(new Color(60, 60, 60));
        lstTodosJugadores.setFont(new Font("Arial", Font.PLAIN, 14));
        lstTodosJugadores.setSelectionBackground(new Color(255, 147, 30));
        lstTodosJugadores.setSelectionForeground(Color.WHITE);
        lstTodosJugadores.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panelCentral.add(new JScrollPane(lstTodosJugadores));

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 15));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnAgregar = new JButton(">");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 18));
        btnAgregar.setToolTipText("Agregar al equipo");
        btnAgregar.setBackground(new Color(255, 147, 30));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> {
            Jugador seleccionado = lstTodosJugadores.getSelectedValue();
            if (seleccionado != null) {
                modelTodos.removeElement(seleccionado);
                modelEquipo.addElement(seleccionado);
            }
        });
        
        JButton btnQuitar = new JButton("<");
        btnQuitar.setFont(new Font("Arial", Font.BOLD, 18));
        btnQuitar.setToolTipText("Quitar del equipo");
        btnQuitar.setBackground(new Color(255, 147, 30));
        btnQuitar.setForeground(Color.WHITE);
        btnQuitar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnQuitar.setFocusPainted(false);
        btnQuitar.addActionListener(e -> {
            Jugador seleccionado = lstJugadoresEquipo.getSelectedValue();
            if (seleccionado != null) {
                modelEquipo.removeElement(seleccionado);
                modelTodos.addElement(seleccionado);
            }
        });
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnQuitar);
        panelCentral.add(panelBotones);

        lstJugadoresEquipo = new JList<>(modelEquipo);
        lstJugadoresEquipo.setBackground(new Color(245, 245, 245));
        lstJugadoresEquipo.setForeground(new Color(60, 60, 60));
        lstJugadoresEquipo.setFont(new Font("Arial", Font.PLAIN, 14));
        lstJugadoresEquipo.setSelectionBackground(new Color(32, 136, 203));
        lstJugadoresEquipo.setSelectionForeground(Color.WHITE);
        lstJugadoresEquipo.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panelCentral.add(new JScrollPane(lstJugadoresEquipo));

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelInferior.setBorder(new EmptyBorder(0, 15, 15, 15));
        panelInferior.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(46, 139, 87));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> {
        	Equipo equipo = (Equipo) cbxEquipos.getSelectedItem();
        	if (equipo != null) {
        	    ArrayList<Jugador> nuevosJugadores = new ArrayList<>();
        	    for (int i = 0; i < modelEquipo.getSize(); i++) {
        	        nuevosJugadores.add(modelEquipo.getElementAt(i));
        	    }
        	    equipo.setJugadores(nuevosJugadores);
        	    controladora.actualizarEquipo(equipo);
                JOptionPane.showMessageDialog(this,
                    "Cambios guardados exitosamente!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(178, 34, 34));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        panelInferior.add(btnGuardar);
        panelInferior.add(btnCancelar);
        add(panelInferior, BorderLayout.SOUTH);

        for (Equipo equipo : controladora.getMisEquipos()) {
            cbxEquipos.addItem(equipo);
        }
        for (Jugador jugador : controladora.getMisJugadores()) {
            modelTodos.addElement(jugador);
        }
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        GestionarEquipo dialog = new GestionarEquipo(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}