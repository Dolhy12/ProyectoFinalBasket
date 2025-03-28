package visual;

import java.awt.BorderLayout;
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

public class equipovisual extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtCiudad;
    private JTextField txtEntrenador;
    private JTextField txtCapitan;
    private JTextField txtNombreMascota;
    private JTextField txtTiempoFundado;
    private ControladoraLiga controladora;

   
    public equipovisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }
 
   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   
                    ControladoraLiga controladora = new ControladoraLiga();
                    equipovisual frame = new equipovisual(controladora);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    
    private void initialize() {
        setTitle("Registrar Equipo");
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
        txtNombre.setColumns(10);

    
        JLabel lblCiudad = new JLabel("CIUDAD:");
        lblCiudad.setFont(new Font("Arial", Font.BOLD, 14));
        lblCiudad.setBounds(20, 80, 80, 25);
        contentPane.add(lblCiudad);

        txtCiudad = new JTextField();
        txtCiudad.setBounds(20, 105, 240, 30);
        contentPane.add(txtCiudad);
        txtCiudad.setColumns(10);

       
        JLabel lblEntrenador = new JLabel("ENTRENADOR:");
        lblEntrenador.setFont(new Font("Arial", Font.BOLD, 14));
        lblEntrenador.setBounds(20, 140, 100, 25);
        contentPane.add(lblEntrenador);

        txtEntrenador = new JTextField();
        txtEntrenador.setBounds(20, 165, 240, 30);
        contentPane.add(txtEntrenador);
        txtEntrenador.setColumns(10);

       
        JLabel lblCapitan = new JLabel("CAPITÁN:");
        lblCapitan.setFont(new Font("Arial", Font.BOLD, 14));
        lblCapitan.setBounds(20, 200, 80, 25);
        contentPane.add(lblCapitan);

        txtCapitan = new JTextField();
        txtCapitan.setBounds(20, 225, 240, 30);
        contentPane.add(txtCapitan);
        txtCapitan.setColumns(10);

      
        JLabel lblNombreMascota = new JLabel("MASCOTA:");
        lblNombreMascota.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombreMascota.setBounds(20, 260, 80, 25);
        contentPane.add(lblNombreMascota);

        txtNombreMascota = new JTextField();
        txtNombreMascota.setBounds(20, 285, 240, 30);
        contentPane.add(txtNombreMascota);
        txtNombreMascota.setColumns(10);

   
        JLabel lblTiempoFundado = new JLabel("Fundacion:");
        lblTiempoFundado.setFont(new Font("Arial", Font.BOLD, 14));
        lblTiempoFundado.setBounds(20, 320, 120, 25);
        contentPane.add(lblTiempoFundado);

        txtTiempoFundado = new JTextField();
        txtTiempoFundado.setBounds(20, 345, 240, 30);
        contentPane.add(txtTiempoFundado);
        txtTiempoFundado.setColumns(10);

   
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(20, 400, 100, 30);
        contentPane.add(btnRegistrar);

    
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 400, 100, 30);
        contentPane.add(btnCancelar);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText();
                    String ciudad = txtCiudad.getText();
                    String entrenador = txtEntrenador.getText();
                    String capitan = txtCapitan.getText();
                    String nombreMascota = txtNombreMascota.getText();
                    int tiempoFundado = Integer.parseInt(txtTiempoFundado.getText());

                
                    String idEquipo = "EQ" + (controladora.getMisEquipos().size() + 1);

                    Equipo equipo = new Equipo(idEquipo, tiempoFundado, capitan, nombreMascota, nombre, ciudad, entrenador);

                    controladora.agregarEquipo(equipo);

                    JOptionPane.showMessageDialog(null, "Equipo registrado con éxito. ID: " + idEquipo);
                    dispose(); 
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El tiempo fundado debe ser un número.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el equipo: " + ex.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}