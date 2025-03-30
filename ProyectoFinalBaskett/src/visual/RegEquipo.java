package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Equipo;

public class RegEquipo extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtCiudad;
    private JTextField txtEntrenador;
    private JTextField txtCapitan;
    private JTextField txtNombreMascota;
    private JTextField txtTiempoFundado;
    private ControladoraLiga controladora;
    private String idEquipo; 

    /**
     * @wbp.parser.constructor
     */
    public RegEquipo(ControladoraLiga controladora) {
        this(controladora, null);
    }

    public RegEquipo(ControladoraLiga controladora, String idEquipo) {
        this.controladora = controladora;
        this.idEquipo = idEquipo;
        initialize();
        if (idEquipo != null) { 
            cargarDatosEquipo();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControladoraLiga controladora = new ControladoraLiga();
                RegEquipo frame = new RegEquipo(controladora);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        setTitle(idEquipo == null ? "Registrar Equipo" : "Modificar Equipo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(20, 20, 80, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(20, 45, 240, 30);
        contentPane.add(txtNombre);

        JLabel lblCiudad = new JLabel("CIUDAD:");
        lblCiudad.setFont(new Font("Arial", Font.BOLD, 14));
        lblCiudad.setBounds(20, 80, 80, 25);
        contentPane.add(lblCiudad);

        txtCiudad = new JTextField();
        txtCiudad.setBounds(20, 105, 240, 30);
        contentPane.add(txtCiudad);

        JLabel lblEntrenador = new JLabel("ENTRENADOR:");
        lblEntrenador.setFont(new Font("Arial", Font.BOLD, 14));
        lblEntrenador.setBounds(20, 140, 100, 25);
        contentPane.add(lblEntrenador);

        txtEntrenador = new JTextField();
        txtEntrenador.setBounds(20, 165, 240, 30);
        contentPane.add(txtEntrenador);

        JLabel lblCapitan = new JLabel("CAPITÁN:");
        lblCapitan.setFont(new Font("Arial", Font.BOLD, 14));
        lblCapitan.setBounds(20, 200, 80, 25);
        contentPane.add(lblCapitan);

        txtCapitan = new JTextField();
        txtCapitan.setBounds(20, 225, 240, 30);
        contentPane.add(txtCapitan);

        JLabel lblNombreMascota = new JLabel("MASCOTA:");
        lblNombreMascota.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombreMascota.setBounds(20, 260, 80, 25);
        contentPane.add(lblNombreMascota);

        txtNombreMascota = new JTextField();
        txtNombreMascota.setBounds(20, 285, 240, 30);
        contentPane.add(txtNombreMascota);

        JLabel lblTiempoFundado = new JLabel("Fundación:");
        lblTiempoFundado.setFont(new Font("Arial", Font.BOLD, 14));
        lblTiempoFundado.setBounds(20, 320, 120, 25);
        contentPane.add(lblTiempoFundado);

        txtTiempoFundado = new JTextField();
        txtTiempoFundado.setBounds(20, 345, 240, 30);
        contentPane.add(txtTiempoFundado);

        JButton btnGuardar = new JButton(idEquipo == null ? "Registrar" : "Guardar");
        btnGuardar.setBounds(20, 400, 100, 30);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 400, 100, 30);
        contentPane.add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarEquipo());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosEquipo() {
        Equipo equipo = controladora.buscarEquipo(idEquipo);
        if (equipo != null) {
            txtNombre.setText(equipo.getNombre());
            txtCiudad.setText(equipo.getCiudad());
            txtEntrenador.setText(equipo.getEntrenador());
            txtCapitan.setText(equipo.getCapitan());
            txtNombreMascota.setText(equipo.getNombreDeLaMascota());
            txtTiempoFundado.setText(String.valueOf(equipo.getTiempoFundado()));
        }
    }

    private void guardarEquipo() {
        try {
            String nombre = txtNombre.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String entrenador = txtEntrenador.getText().trim();
            String capitan = txtCapitan.getText().trim();
            String nombreMascota = txtNombreMascota.getText().trim();
            int tiempoFundado = Integer.parseInt(txtTiempoFundado.getText().trim());

            if (nombre.isEmpty() || ciudad.isEmpty() || entrenador.isEmpty() || capitan.isEmpty() || nombreMascota.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }
            if (tiempoFundado < 0) {
                JOptionPane.showMessageDialog(this, "El tiempo fundado no puede ser negativo.");
                return;
            }

            if (idEquipo == null) { 
                String nuevoId = "EQ" + (controladora.getMisEquipos().size() + 1);
                Equipo equipo = new Equipo(nuevoId, tiempoFundado, capitan, nombreMascota, nombre, ciudad, entrenador);
                controladora.agregarEquipo(equipo);
                JOptionPane.showMessageDialog(this, "Equipo registrado con éxito. ID: " + nuevoId);
            } else { 
                controladora.modificarEquipo(idEquipo, nombre, ciudad, entrenador, capitan, nombreMascota, tiempoFundado);
                JOptionPane.showMessageDialog(this, "Equipo modificado con éxito.");
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El tiempo fundado debe ser un número.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}