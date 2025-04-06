package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Jugador;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class RegJugador extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtNacionalidad;
    private ControladoraLiga controladora;
    private JComboBox<String> cbxPosicion;
    private JSpinner spnFecha;
    private JSpinner spnPeso;
    private JSpinner spnAltura;
    private JSpinner spnNumero;
    private JTextField txtEdad;
    private Jugador jugadorEditando;
    private JButton btnRegistrar;

    public RegJugador() {
        this(null);
    }

    public RegJugador(Jugador jugador) {
        controladora = ControladoraLiga.getInstance();
        jugadorEditando = jugador;
        setTitle(jugador == null ? "Registrar Jugador" : "Modificar Jugador");
        setModal(true);
        setSize(520, 500);
        setLocationRelativeTo(null);
        initComponents();
        if (jugador != null) cargarDatosJugador();
    }

    private void initComponents() {
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 147, 30));
        mainPanel.setLayout(null);
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        // Panel ID
        JPanel panelID = new JPanel();
        panelID.setBounds(12, 13, 221, 42);
        panelID.setBackground(new Color(169, 169, 169));
        panelID.setLayout(null);
        mainPanel.add(panelID);

        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(12, 13, 56, 16);
        panelID.add(lblID);

        txtID = new JTextField(generarNuevoId());
        txtID.setEditable(false);
        txtID.setBounds(37, 10, 166, 22);
        panelID.add(txtID);

        // Panel Nombre
        JPanel panelNombre = new JPanel();
        panelNombre.setBounds(12, 68, 474, 42);
        panelNombre.setBackground(new Color(169, 169, 169));
        panelNombre.setLayout(null);
        mainPanel.add(panelNombre);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(12, 13, 56, 16);
        panelNombre.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(67, 10, 395, 22);
        panelNombre.add(txtNombre);

        // Panel Fecha
        JPanel panelFecha = new JPanel();
        panelFecha.setBounds(12, 123, 254, 42);
        panelFecha.setBackground(new Color(169, 169, 169));
        panelFecha.setLayout(null);
        mainPanel.add(panelFecha);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(12, 13, 56, 16);
        panelFecha.add(lblFecha);

        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                actualizarEdad();
            }
        });
        spnFecha.setBounds(74, 13, 170, 22);
        JSpinner.DateEditor de_spnFecha = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(de_spnFecha);
        spnFecha.setValue(new Date());
        panelFecha.add(spnFecha);

        // Panel Edad
        JPanel panelEdad = new JPanel();
        panelEdad.setBounds(284, 123, 202, 42);
        panelEdad.setBackground(new Color(169, 169, 169));
        panelEdad.setLayout(null);
        mainPanel.add(panelEdad);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(12, 13, 56, 16);
        panelEdad.add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setEditable(false);
        txtEdad.setBounds(68, 10, 122, 22);
        panelEdad.add(txtEdad);

        // Panel Posición
        JPanel panelPosicion = new JPanel();
        panelPosicion.setBounds(12, 178, 193, 42);
        panelPosicion.setBackground(new Color(169, 169, 169));
        panelPosicion.setLayout(null);
        mainPanel.add(panelPosicion);

        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setBounds(12, 13, 56, 16);
        panelPosicion.add(lblPosicion);

        cbxPosicion = new JComboBox<>();
        cbxPosicion.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"}));
        cbxPosicion.setBounds(69, 10, 112, 22);
        panelPosicion.add(cbxPosicion);

        // Panel Nacionalidad
        JPanel panelNacionalidad = new JPanel();
        panelNacionalidad.setBounds(217, 178, 269, 42);
        panelNacionalidad.setBackground(new Color(169, 169, 169));
        panelNacionalidad.setLayout(null);
        mainPanel.add(panelNacionalidad);

        JLabel lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setBounds(12, 13, 88, 16);
        panelNacionalidad.add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        txtNacionalidad.setBounds(100, 10, 157, 22);
        panelNacionalidad.add(txtNacionalidad);

        // Panel Peso
        JPanel panelPeso = new JPanel();
        panelPeso.setBounds(12, 233, 221, 42);
        panelPeso.setBackground(new Color(169, 169, 169));
        panelPeso.setLayout(null);
        mainPanel.add(panelPeso);

        JLabel lblPeso = new JLabel("Peso (KG):");
        lblPeso.setBounds(12, 13, 75, 16);
        panelPeso.add(lblPeso);

        spnPeso = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 200.0, 0.5));
        spnPeso.setBounds(89, 10, 120, 22);
        panelPeso.add(spnPeso);

        // Panel Altura
        JPanel panelAltura = new JPanel();
        panelAltura.setBounds(271, 233, 215, 42);
        panelAltura.setBackground(new Color(169, 169, 169));
        panelAltura.setLayout(null);
        mainPanel.add(panelAltura);

        JLabel lblAltura = new JLabel("Altura (m):");
        lblAltura.setBounds(12, 13, 74, 16);
        panelAltura.add(lblAltura);

        spnAltura = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 3.0, 0.01));
        spnAltura.setBounds(89, 10, 114, 22);
        panelAltura.add(spnAltura);

        // Panel Número
        JPanel panelNumero = new JPanel();
        panelNumero.setBounds(12, 288, 178, 42);
        panelNumero.setBackground(new Color(169, 169, 169));
        panelNumero.setLayout(null);
        mainPanel.add(panelNumero);

        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setBounds(12, 13, 56, 16);
        panelNumero.add(lblNumero);

        spnNumero = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        spnNumero.setBounds(68, 10, 100, 22);
        panelNumero.add(spnNumero);

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPanel.add(buttonPane, BorderLayout.SOUTH);

        btnRegistrar = new JButton(jugadorEditando == null ? "Registrar" : "Guardar Cambios");
        btnRegistrar.addActionListener(e -> procesarJugador());
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);
    }

    private void cargarDatosJugador() {
        txtID.setText(jugadorEditando.getID());
        txtNombre.setText(jugadorEditando.getNombre());
        cbxPosicion.setSelectedItem(jugadorEditando.getPosicion());
        txtNacionalidad.setText(jugadorEditando.getNacionalidad());
        spnFecha.setValue(jugadorEditando.getFechaDeNacimiento());
        spnPeso.setValue(jugadorEditando.getPeso());
        spnAltura.setValue(jugadorEditando.getAltura());
        spnNumero.setValue(jugadorEditando.getNumero());
        txtEdad.setText(String.valueOf(jugadorEditando.getEdad()));
        txtID.setEditable(false);
    }

    private void procesarJugador() {
        try {
            validarCampos();

            Jugador jugador = new Jugador(
                txtID.getText(),
                txtNombre.getText(),
                Integer.parseInt(txtEdad.getText()),
                cbxPosicion.getSelectedItem().toString(),
                txtNacionalidad.getText(),
                (Date) spnFecha.getValue(),
                ((Number) spnPeso.getValue()).floatValue(),
                ((Number) spnAltura.getValue()).floatValue(),
                (int) spnNumero.getValue()
            );

            if (jugadorEditando == null) {
                controladora.agregarJugador(jugador);
                manejarNuevoRegistro();
            } else {
                controladora.actualizarJugador(jugador);
                JOptionPane.showMessageDialog(this, "Jugador actualizado exitosamente!");
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarCampos() throws Exception {
        if (txtNombre.getText().isEmpty() ||
            cbxPosicion.getSelectedIndex() == 0 ||
            txtNacionalidad.getText().isEmpty()) {
            throw new Exception("Complete los campos obligatorios (*)");
        }

        if (((Number) spnPeso.getValue()).floatValue() <= 0 ||
            ((Number) spnAltura.getValue()).floatValue() <= 0) {
            throw new Exception("Peso y altura deben ser mayores a 0");
        }
    }

    private void manejarNuevoRegistro() {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Desea agregar otro jugador?",
            "Registro exitoso",
            JOptionPane.YES_NO_OPTION
        );
        
        if (respuesta == JOptionPane.YES_OPTION) reiniciarFormulario();
        else dispose();
    }

    private void reiniciarFormulario() {
        txtID.setText(generarNuevoId());
        txtNombre.setText("");
        spnFecha.setValue(new Date());
        txtEdad.setText("");
        cbxPosicion.setSelectedIndex(0);
        txtNacionalidad.setText("");
        spnAltura.setValue(0.0);
        spnPeso.setValue(0.0);
        spnNumero.setValue(0);
    }

    private String generarNuevoId() {
        return "JUG-" + (controladora.getMisJugadores().size() + 1);
    }

    private void actualizarEdad() {
        Date birthDate = (Date) spnFecha.getValue();
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        txtEdad.setText(String.valueOf(Period.between(birthLocalDate, LocalDate.now()).getYears()));
    }

    public static void main(String[] args) {
        RegJugador dialog = new RegJugador();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}