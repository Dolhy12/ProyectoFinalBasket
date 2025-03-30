package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;

public class RegJugador extends JFrame {

    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> cmbPosicion;
    private JTextField txtNacionalidad;
    private JComboBox<String> cmbDia;
    private JComboBox<String> cmbMes;
    private JComboBox<String> cmbAnio;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtNumero;
    private ControladoraLiga controladora;
    private String idJugador; 

    /**
     * @wbp.parser.constructor
     */
    public RegJugador(ControladoraLiga controladora) {
        this(controladora, null); 
    }

    public RegJugador(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.idJugador = idJugador;
        initialize();
        if (idJugador != null) {
            cargarDatosJugador();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControladoraLiga controladora = new ControladoraLiga();
                RegJugador frame = new RegJugador(controladora);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        setTitle(idJugador == null ? "Registrar Jugador" : "Modificar Jugador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblID = new JLabel("ID:");
        lblID.setFont(new Font("Arial", Font.BOLD, 14));
        lblID.setBounds(20, 20, 80, 25);
        contentPane.add(lblID);

        txtID = new JTextField(idJugador == null ? generarIDUnico() : idJugador);
        txtID.setEditable(false);
        txtID.setBounds(100, 20, 260, 25);
        contentPane.add(txtID);

        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(20, 60, 80, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 260, 25);
        contentPane.add(txtNombre);

        JLabel lblEdad = new JLabel("EDAD:");
        lblEdad.setFont(new Font("Arial", Font.BOLD, 14));
        lblEdad.setBounds(20, 100, 80, 25);
        contentPane.add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setBounds(100, 100, 260, 25);
        contentPane.add(txtEdad);

        JLabel lblPosicion = new JLabel("POSICIÓN:");
        lblPosicion.setFont(new Font("Arial", Font.BOLD, 14));
        lblPosicion.setBounds(20, 140, 80, 25);
        contentPane.add(lblPosicion);

        cmbPosicion = new JComboBox<>();
        String[] posiciones = {"Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"};
        for (String posicion : posiciones) {
            cmbPosicion.addItem(posicion);
        }
        cmbPosicion.setBounds(100, 140, 260, 25);
        contentPane.add(cmbPosicion);

        JLabel lblNacionalidad = new JLabel("NACIONALIDAD:");
        lblNacionalidad.setFont(new Font("Arial", Font.BOLD, 14));
        lblNacionalidad.setBounds(20, 180, 80, 25);
        contentPane.add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        txtNacionalidad.setBounds(100, 180, 260, 25);
        contentPane.add(txtNacionalidad);

        JLabel lblFechaNacimiento = new JLabel("FECHA NAC.:");
        lblFechaNacimiento.setFont(new Font("Arial", Font.BOLD, 14));
        lblFechaNacimiento.setBounds(20, 220, 80, 25);
        contentPane.add(lblFechaNacimiento);

        cmbDia = new JComboBox<>();
        String[] dias = new String[31];
        for (int i = 0; i < 31; i++) dias[i] = String.valueOf(i + 1);
        for (String dia : dias) {
            cmbDia.addItem(dia);
        }
        cmbDia.setBounds(100, 220, 60, 25);
        contentPane.add(cmbDia);

        cmbMes = new JComboBox<>();
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            cmbMes.addItem(mes);
        }
        cmbMes.setBounds(170, 220, 100, 25);
        contentPane.add(cmbMes);

        cmbAnio = new JComboBox<>();
        String[] anios = new String[46];
        for (int i = 0; i < 46; i++) anios[i] = String.valueOf(1980 + i);
        for (String anio : anios) {
            cmbAnio.addItem(anio);
        }
        cmbAnio.setBounds(280, 220, 80, 25);
        contentPane.add(cmbAnio);

        JLabel lblPeso = new JLabel("PESO (kg):");
        lblPeso.setFont(new Font("Arial", Font.BOLD, 14));
        lblPeso.setBounds(20, 260, 80, 25);
        contentPane.add(lblPeso);

        txtPeso = new JTextField();
        txtPeso.setBounds(100, 260, 260, 25);
        contentPane.add(txtPeso);

        JLabel lblAltura = new JLabel("ALTURA (m):");
        lblAltura.setFont(new Font("Arial", Font.BOLD, 14));
        lblAltura.setBounds(20, 300, 80, 25);
        contentPane.add(lblAltura);

        txtAltura = new JTextField();
        txtAltura.setBounds(100, 300, 260, 25);
        contentPane.add(txtAltura);

        JLabel lblNumero = new JLabel("NÚMERO:");
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        lblNumero.setBounds(20, 340, 80, 25);
        contentPane.add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(100, 340, 260, 25);
        contentPane.add(txtNumero);

        JButton btnGuardar = new JButton(idJugador == null ? "Registrar" : "Guardar");
        btnGuardar.setBounds(20, 380, 100, 30);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(130, 380, 100, 30);
        contentPane.add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarJugador());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosJugador() {
        Jugador jugador = controladora.buscarJugador(idJugador);
        if (jugador != null) {
            txtNombre.setText(jugador.getNombre());
            txtEdad.setText(String.valueOf(jugador.getEdad()));
            cmbPosicion.setSelectedItem(jugador.getPosicion());
            txtNacionalidad.setText(jugador.getNacionalidad());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = sdf.format(jugador.getFechaDeNacimiento());
            cmbDia.setSelectedItem(fecha.split("/")[0]);
            cmbMes.setSelectedIndex(Integer.parseInt(fecha.split("/")[1]) - 1);
            cmbAnio.setSelectedItem(fecha.split("/")[2]);
            txtPeso.setText(String.valueOf(jugador.getPeso()));
            txtAltura.setText(String.valueOf(jugador.getAltura()));
            txtNumero.setText(String.valueOf(jugador.getNumero()));
        }
    }

    private void guardarJugador() {
        try {
            String nombre = txtNombre.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            String posicion = (String) cmbPosicion.getSelectedItem();
            String nacionalidad = txtNacionalidad.getText().trim();
            String dia = (String) cmbDia.getSelectedItem();
            int mesIndex = cmbMes.getSelectedIndex() + 1;
            String mes = String.format("%02d", mesIndex);
            String anio = (String) cmbAnio.getSelectedItem();
            String fechaNacimientoStr = dia + "/" + mes + "/" + anio;
            float peso = Float.parseFloat(txtPeso.getText().trim());
            float altura = Float.parseFloat(txtAltura.getText().trim());
            int numero = Integer.parseInt(txtNumero.getText().trim());

            if (nombre.isEmpty() || nacionalidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y nacionalidad son obligatorios.");
                return;
            }
            if (edad <= 0 || peso <= 0 || altura <= 0 || numero < 0) {
                JOptionPane.showMessageDialog(this, "Edad, peso, altura y número deben ser positivos.");
                return;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = formatter.parse(fechaNacimientoStr);

            if (idJugador == null) { 
                Jugador jugador = new Jugador(txtID.getText(), nombre, edad, posicion, nacionalidad, fechaNacimiento, peso, altura, numero);
                controladora.agregarJugador(jugador);
                JOptionPane.showMessageDialog(this, "Jugador registrado con éxito. ID: " + txtID.getText());
            } else { 
                controladora.modificarJugador(idJugador, nombre, edad, posicion, numero);
                Jugador jugador = controladora.buscarJugador(idJugador);
                jugador.setNacionalidad(nacionalidad);
                jugador.setFechaDeNacimiento(fechaNacimiento);
                jugador.setPeso(peso);
                jugador.setAltura(altura);
                JOptionPane.showMessageDialog(this, "Jugador modificado con éxito.");
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private String generarIDUnico() {
        int numeroJugadores = controladora.getMisJugadores().size();
        return "JUG-" + (numeroJugadores + 1);
    }
}