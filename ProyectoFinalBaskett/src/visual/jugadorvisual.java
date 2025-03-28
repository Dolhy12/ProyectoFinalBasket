package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;

public class jugadorvisual extends JFrame {

    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtPosicion;
    private JTextField txtNacionalidad;
    private JTextField txtFechaNacimiento;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtNumero;
    private ControladoraLiga controladora;

    public jugadorvisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                 
                    ControladoraLiga controladora = new ControladoraLiga();
                    jugadorvisual frame = new jugadorvisual(controladora);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        setTitle("Registrar Jugador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 600); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0)); 
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblID = new JLabel("ID:");
        lblID.setFont(new Font("Arial", Font.BOLD, 14));
        lblID.setBounds(20, 20, 80, 25);
        contentPane.add(lblID);

        txtID = new JTextField();
        txtID.setBounds(20, 45, 240, 30);
        contentPane.add(txtID);
        txtID.setColumns(10);
        
        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(20, 80, 80, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(20, 105, 240, 30);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblEdad = new JLabel("EDAD:");
        lblEdad.setFont(new Font("Arial", Font.BOLD, 14));
        lblEdad.setBounds(20, 140, 80, 25);
        contentPane.add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(20, 165, 240, 30);
        contentPane.add(txtEdad);
        txtEdad.setColumns(10);

        JLabel lblPosicion = new JLabel("POSICIÓN:");
        lblPosicion.setFont(new Font("Arial", Font.BOLD, 14));
        lblPosicion.setBounds(20, 200, 80, 25);
        contentPane.add(lblPosicion);

        txtPosicion = new JTextField();
        txtPosicion.setBounds(20, 225, 240, 30);
        contentPane.add(txtPosicion);
        txtPosicion.setColumns(10);

        JLabel lblNacionalidad = new JLabel("NACIONALIDAD:");
        lblNacionalidad.setFont(new Font("Arial", Font.BOLD, 14));
        lblNacionalidad.setBounds(20, 260, 120, 25);
        contentPane.add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        txtNacionalidad.setBounds(20, 285, 240, 30);
        contentPane.add(txtNacionalidad);
        txtNacionalidad.setColumns(10);

        JLabel lblFechaNacimiento = new JLabel("FECHA NACIMIENTO:");
        lblFechaNacimiento.setFont(new Font("Arial", Font.BOLD, 14));
        lblFechaNacimiento.setBounds(20, 320, 140, 25);
        contentPane.add(lblFechaNacimiento);

        txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setBounds(20, 345, 240, 30);
        contentPane.add(txtFechaNacimiento);
        txtFechaNacimiento.setColumns(10);
        txtFechaNacimiento.setToolTipText("Formato: dd/MM/yyyy");

        JLabel lblPeso = new JLabel("PESO (kg):");
        lblPeso.setFont(new Font("Arial", Font.BOLD, 14));
        lblPeso.setBounds(20, 380, 80, 25);
        contentPane.add(lblPeso);

        txtPeso = new JTextField();
        txtPeso.setBounds(20, 405, 240, 30);
        contentPane.add(txtPeso);
        txtPeso.setColumns(10);

        JLabel lblAltura = new JLabel("ALTURA (m):");
        lblAltura.setFont(new Font("Arial", Font.BOLD, 14));
        lblAltura.setBounds(20, 440, 80, 25);
        contentPane.add(lblAltura);

        txtAltura = new JTextField();
        txtAltura.setBounds(20, 465, 240, 30);
        contentPane.add(txtAltura);
        txtAltura.setColumns(10);

        JLabel lblNumero = new JLabel("NÚMERO:");
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        lblNumero.setBounds(20, 500, 80, 25);
        contentPane.add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(20, 525, 240, 30);
        contentPane.add(txtNumero);
        txtNumero.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(20, 560, 100, 30);
        contentPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 560, 100, 30);
        contentPane.add(btnCancelar);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtID.getText();
                    String nombre = txtNombre.getText();
                    int edad = Integer.parseInt(txtEdad.getText());
                    String posicion = txtPosicion.getText();
                    String nacionalidad = txtNacionalidad.getText();
                    String fechaNacimientoStr = txtFechaNacimiento.getText();
                    float peso = Float.parseFloat(txtPeso.getText());
                    float altura = Float.parseFloat(txtAltura.getText());
                    int numero = Integer.parseInt(txtNumero.getText());

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaNacimiento = formatter.parse(fechaNacimientoStr);

                    Jugador jugador = new Jugador(id, nombre, edad, posicion, nacionalidad, fechaNacimiento, peso, altura, numero);

                    controladora.agregarJugador(jugador);

                    JOptionPane.showMessageDialog(null, "Jugador registrado con éxito. ID: " + id);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Verifique que los campos numéricos (edad, peso, altura, número) sean correctos.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el jugador: " + ex.getMessage());
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