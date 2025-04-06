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
        controladora = ControladoraLiga.getInstance();
        initComponents();
    }

    
    public RegJugador(Jugador jugador) {
        this();
        this.jugadorEditando = jugador;
        cargarDatosJugador();
        configurarModoEdicion();
    }

    private void initComponents() {
        setType(Type.POPUP);
        setTitle("Registrar Jugador");
        setBounds(100, 100, 520, 431);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 147, 30));
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPanel.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        
        JPanel panelID = new JPanel();
        panelID.setBackground(new Color(169, 169, 169));
        panelID.setBounds(12, 13, 221, 42);
        panel.add(panelID);
        panelID.setLayout(null);

        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(12, 13, 56, 16);
        panelID.add(lblID);

        txtID = new JTextField();
        txtID.setEditable(false);
        txtID.setBounds(37, 10, 166, 22);
        panelID.add(txtID);
        txtID.setColumns(10);
        txtID.setText(generarNuevoId());

       
        JPanel panelNombre = new JPanel();
        panelNombre.setBackground(new Color(169, 169, 169));
        panelNombre.setBounds(12, 68, 474, 42);
        panel.add(panelNombre);
        panelNombre.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(12, 13, 56, 16);
        panelNombre.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(67, 10, 395, 22);
        panelNombre.add(txtNombre);
        txtNombre.setColumns(10);

        
        JPanel panelFecha = new JPanel();
        panelFecha.setBackground(new Color(169, 169, 169));
        panelFecha.setBounds(12, 123, 254, 42);
        panel.add(panelFecha);
        panelFecha.setLayout(null);

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
        panelFecha.add(spnFecha);
        JSpinner.DateEditor de_spnFecha = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(de_spnFecha);
        spnFecha.setValue(new Date());

        
        JPanel panelEdad = new JPanel();
        panelEdad.setBackground(new Color(169, 169, 169));
        panelEdad.setBounds(284, 123, 202, 42);
        panel.add(panelEdad);
        panelEdad.setLayout(null);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(12, 13, 56, 16);
        panelEdad.add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setEditable(false);
        txtEdad.setBounds(68, 10, 122, 22);
        panelEdad.add(txtEdad);
        txtEdad.setColumns(10);

        
        JPanel panelPosicion = new JPanel();
        panelPosicion.setBackground(new Color(169, 169, 169));
        panelPosicion.setBounds(12, 178, 193, 42);
        panel.add(panelPosicion);
        panelPosicion.setLayout(null);

        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setBounds(12, 13, 56, 16);
        panelPosicion.add(lblPosicion);

        cbxPosicion = new JComboBox<>();
        cbxPosicion.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"}));
        cbxPosicion.setBounds(69, 10, 112, 22);
        panelPosicion.add(cbxPosicion);

        
        JPanel panelNacionalidad = new JPanel();
        panelNacionalidad.setBackground(new Color(169, 169, 169));
        panelNacionalidad.setBounds(217, 178, 269, 42);
        panel.add(panelNacionalidad);
        panelNacionalidad.setLayout(null);

        JLabel lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setBounds(12, 13, 88, 16);
        panelNacionalidad.add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        txtNacionalidad.setBounds(100, 10, 157, 22);
        panelNacionalidad.add(txtNacionalidad);
        txtNacionalidad.setColumns(10);

       
        JPanel panelPeso = new JPanel();
        panelPeso.setBackground(new Color(169, 169, 169));
        panelPeso.setBounds(12, 233, 221, 42);
        panel.add(panelPeso);
        panelPeso.setLayout(null);

        JLabel lblPeso = new JLabel("Peso (KG):");
        lblPeso.setBounds(12, 13, 75, 16);
        panelPeso.add(lblPeso);

        spnPeso = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 200.0, 0.5));
        spnPeso.setBounds(89, 10, 120, 22);
        panelPeso.add(spnPeso);

        
        JPanel panelAltura = new JPanel();
        panelAltura.setBackground(new Color(169, 169, 169));
        panelAltura.setBounds(271, 233, 215, 42);
        panel.add(panelAltura);
        panelAltura.setLayout(null);

        JLabel lblAltura = new JLabel("Altura (m):");
        lblAltura.setBounds(12, 13, 74, 16);
        panelAltura.add(lblAltura);

        spnAltura = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 3.0, 0.01));
        spnAltura.setBounds(89, 10, 114, 22);
        panelAltura.add(spnAltura);

      
        JPanel panelNumero = new JPanel();
        panelNumero.setBackground(new Color(169, 169, 169));
        panelNumero.setBounds(12, 288, 178, 42);
        panel.add(panelNumero);
        panelNumero.setLayout(null);

        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setBounds(12, 13, 56, 16);
        panelNumero.add(lblNumero);

        spnNumero = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        spnNumero.setBounds(68, 10, 100, 22);
        panelNumero.add(spnNumero);

        
        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                procesarJugador();
            }
        });
        buttonPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCancelar);
    }

    private void configurarModoEdicion() {
        setTitle("Modificar Jugador");
        btnRegistrar.setText("Guardar Cambios");
        txtID.setEditable(false);
    }

    private void cargarDatosJugador() {
        if (jugadorEditando != null) {
            txtID.setText(jugadorEditando.getID());
            txtNombre.setText(jugadorEditando.getNombre());
            cbxPosicion.setSelectedItem(jugadorEditando.getPosicion());
            txtNacionalidad.setText(jugadorEditando.getNacionalidad());
            spnFecha.setValue(jugadorEditando.getFechaDeNacimiento());
            spnPeso.setValue(jugadorEditando.getPeso());
            spnAltura.setValue(jugadorEditando.getAltura());
            spnNumero.setValue(jugadorEditando.getNumero());
            txtEdad.setText(String.valueOf(jugadorEditando.getEdad()));
        }
    }

    private void procesarJugador() {
        try {
            validarCampos();

            String id = txtID.getText();
            String nombre = txtNombre.getText();
            String posicion = cbxPosicion.getSelectedItem().toString();
            String nacionalidad = txtNacionalidad.getText();
            Date fechaNac = (Date) spnFecha.getValue();
            float peso = ((Number) spnPeso.getValue()).floatValue();
            float altura = ((Number) spnAltura.getValue()).floatValue();
            int numero = (int) spnNumero.getValue();
            int edad = Integer.parseInt(txtEdad.getText());

            if (jugadorEditando == null) {
                Jugador nuevo = new Jugador(id, nombre, edad, posicion, nacionalidad, fechaNac, peso, altura, numero);
                controladora.agregarJugador(nuevo);
                manejarNuevoRegistro();
            } else {
                actualizarJugadorExistente(nombre, posicion, nacionalidad, fechaNac, peso, altura, numero, edad);
            }

            dispose();
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

    private void actualizarJugadorExistente(String nombre, String posicion, String nacionalidad,
                                          Date fechaNac, float peso, float altura, int numero, int edad) {
        jugadorEditando.setNombre(nombre);
        jugadorEditando.setPosicion(posicion);
        jugadorEditando.setNacionalidad(nacionalidad);
        jugadorEditando.setFechaDeNacimiento(fechaNac);
        jugadorEditando.setPeso(peso);
        jugadorEditando.setAltura(altura);
        jugadorEditando.setNumero(numero);
        jugadorEditando.setEdad(edad);
        
        controladora.actualizarJugador(jugadorEditando);
        JOptionPane.showMessageDialog(this, "Jugador actualizado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void manejarNuevoRegistro() {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Desea agregar otro jugador?",
            "Registro exitoso",
            JOptionPane.YES_NO_OPTION
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            reiniciarFormulario();
        }
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
        int age = Period.between(birthLocalDate, LocalDate.now()).getYears();
        txtEdad.setText(String.valueOf(age));
    }

    public static void main(String[] args) {
        RegJugador dialog = new RegJugador();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}