package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class RegJugador extends JDialog {

    private JTextField txtID, txtNombre, txtNacionalidad, txtEdad;
    private JComboBox<String> cbxPosicion;
    private JSpinner spnFecha, spnPeso, spnAltura, spnNumero;
    private ControladoraLiga controladora = ControladoraLiga.getInstance();

    public RegJugador(Jugador jugador) {
        this();
        cargarDatosJugador(jugador);
    }

    public RegJugador() {
        setTitle("Registrar Jugador");
        setSize(700, 600);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel(new BorderLayout(15, 5));
        idPanel.setBackground(Color.WHITE);
        idPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblID = new JLabel("ID:");
        lblID.setFont(new Font("Arial", Font.BOLD, 14));
        txtID = new JTextField(generarNuevoId());
        txtID.setEditable(false);
        txtID.setPreferredSize(new Dimension(250, 30));
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        idPanel.add(lblID, BorderLayout.WEST);
        idPanel.add(txtID, BorderLayout.CENTER);
        mainPanel.add(idPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel nombrePanel = new JPanel(new BorderLayout(15, 5));
        nombrePanel.setBackground(Color.WHITE);
        nombrePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(250, 30));
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        nombrePanel.add(lblNombre, BorderLayout.WEST);
        nombrePanel.add(txtNombre, BorderLayout.CENTER);
        mainPanel.add(nombrePanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel fechaEdadPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        fechaEdadPanel.setBackground(Color.WHITE);
        
        JPanel fechaPanel = new JPanel(new BorderLayout(15, 5));
        fechaPanel.setBackground(Color.WHITE);
        fechaPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblFecha = new JLabel("Fecha Nacimiento:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(dateEditor);
        spnFecha.setValue(Date.from(LocalDate.now().minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        spnFecha.setPreferredSize(new Dimension(180, 35));
        ((JSpinner.DefaultEditor) spnFecha.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        spnFecha.addChangeListener(e -> actualizarEdad());
        fechaPanel.add(lblFecha, BorderLayout.WEST);
        fechaPanel.add(spnFecha, BorderLayout.CENTER);
        
        JPanel edadPanel = new JPanel(new BorderLayout(15, 5));
        edadPanel.setBackground(Color.WHITE);
        edadPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setFont(new Font("Arial", Font.BOLD, 14));
        txtEdad = new JTextField();
        txtEdad.setEditable(false);
        txtEdad.setPreferredSize(new Dimension(250, 30));
        txtEdad.setFont(new Font("Arial", Font.PLAIN, 14));
        edadPanel.add(lblEdad, BorderLayout.WEST);
        edadPanel.add(txtEdad, BorderLayout.CENTER);
        
        fechaEdadPanel.add(fechaPanel);
        fechaEdadPanel.add(edadPanel);
        mainPanel.add(fechaEdadPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel posNacPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        posNacPanel.setBackground(Color.WHITE);
        
        JPanel posicionPanel = new JPanel(new BorderLayout(15, 5));
        posicionPanel.setBackground(Color.WHITE);
        posicionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblPosicion = new JLabel("Posición:");
        lblPosicion.setFont(new Font("Arial", Font.BOLD, 14));
        cbxPosicion = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{"<Seleccionar>", "Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"}));
        cbxPosicion.setPreferredSize(new Dimension(180, 35));
        posicionPanel.add(lblPosicion, BorderLayout.WEST);
        posicionPanel.add(cbxPosicion, BorderLayout.CENTER);
        
        JPanel nacionalidadPanel = new JPanel(new BorderLayout(15, 5));
        nacionalidadPanel.setBackground(Color.WHITE);
        nacionalidadPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblNacionalidad = new JLabel("Nacionalidad:");
        lblNacionalidad.setFont(new Font("Arial", Font.BOLD, 14));
        txtNacionalidad = new JTextField();
        txtNacionalidad.setPreferredSize(new Dimension(250, 30));
        txtNacionalidad.setFont(new Font("Arial", Font.PLAIN, 14));
        nacionalidadPanel.add(lblNacionalidad, BorderLayout.WEST);
        nacionalidadPanel.add(txtNacionalidad, BorderLayout.CENTER);
        
        posNacPanel.add(posicionPanel);
        posNacPanel.add(nacionalidadPanel);
        mainPanel.add(posNacPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel pesoAlturaPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        pesoAlturaPanel.setBackground(Color.WHITE);
        
        JPanel pesoPanel = new JPanel(new BorderLayout(15, 5));
        pesoPanel.setBackground(Color.WHITE);
        pesoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setFont(new Font("Arial", Font.BOLD, 14));
        spnPeso = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 200.0, 0.5));
        spnPeso.setPreferredSize(new Dimension(180, 35));
        ((JSpinner.DefaultEditor) spnPeso.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        pesoPanel.add(lblPeso, BorderLayout.WEST);
        pesoPanel.add(spnPeso, BorderLayout.CENTER);
        
        JPanel alturaPanel = new JPanel(new BorderLayout(15, 5));
        alturaPanel.setBackground(Color.WHITE);
        alturaPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblAltura = new JLabel("Altura (m):");
        lblAltura.setFont(new Font("Arial", Font.BOLD, 14));
        spnAltura = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 3.0, 0.05));
        spnAltura.setPreferredSize(new Dimension(180, 35));
        ((JSpinner.DefaultEditor) spnAltura.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        alturaPanel.add(lblAltura, BorderLayout.WEST);
        alturaPanel.add(spnAltura, BorderLayout.CENTER);
        
        pesoAlturaPanel.add(pesoPanel);
        pesoAlturaPanel.add(alturaPanel);
        mainPanel.add(pesoAlturaPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel numeroPanel = new JPanel(new BorderLayout(15, 5));
        numeroPanel.setBackground(Color.WHITE);
        numeroPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        spnNumero = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        spnNumero.setPreferredSize(new Dimension(180, 35));
        ((JSpinner.DefaultEditor) spnNumero.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        numeroPanel.add(lblNumero, BorderLayout.WEST);
        numeroPanel.add(spnNumero, BorderLayout.CENTER);
        mainPanel.add(numeroPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(255, 147, 30));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(178, 34, 34));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btnRegistrar.addActionListener(e -> registrarJugador());
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        actualizarEdad();
    }

    private void registrarJugador() {
        try {
            if (validarCampos()) {
                Jugador jugador = new Jugador(
                    txtID.getText(),
                    txtNombre.getText().trim(),
                    Integer.parseInt(txtEdad.getText()),
                    cbxPosicion.getSelectedItem().toString(),
                    txtNacionalidad.getText().trim(),
                    (Date) spnFecha.getValue(),
                    ((Number) spnPeso.getValue()).floatValue(),
                    ((Number) spnAltura.getValue()).floatValue(),
                    (int) spnNumero.getValue()
                );

                if (controladora.existeJugador(jugador.getID())) {
                    controladora.actualizarJugador(jugador);
                    JOptionPane.showMessageDialog(this, 
                        "Jugador actualizado exitosamente!", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    controladora.agregarJugador(jugador);
                    int respuesta = JOptionPane.showConfirmDialog(
                        this, 
                        "¿Desea agregar otro jugador?", 
                        "Registro exitoso", 
                        JOptionPane.YES_NO_OPTION);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        resetForm();
                    } else {
                        dispose();
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
            cbxPosicion.getSelectedIndex() == 0 ||
            txtNacionalidad.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Complete todos los campos obligatorios (*)", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (((Number) spnPeso.getValue()).floatValue() <= 0 ||
            ((Number) spnAltura.getValue()).floatValue() <= 0) {
            
            JOptionPane.showMessageDialog(this, 
                "Peso y altura deben ser mayores a cero", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void actualizarEdad() {
        Date fecha = (Date) spnFecha.getValue();
        LocalDate nacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int edad = Period.between(nacimiento, LocalDate.now()).getYears();
        txtEdad.setText(String.valueOf(edad));
    }

    private void resetForm() {
        txtID.setText(generarNuevoId());
        txtNombre.setText("");
        spnFecha.setValue(Date.from(LocalDate.now().minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        cbxPosicion.setSelectedIndex(0);
        txtNacionalidad.setText("");
        spnPeso.setValue(0.0);
        spnAltura.setValue(0.0);
        spnNumero.setValue(0);
        actualizarEdad();
    }

    private void cargarDatosJugador(Jugador jugador) {
        if (jugador != null) {
            txtID.setText(jugador.getID());
            txtNombre.setText(jugador.getNombre());
            spnFecha.setValue(jugador.getFechaDeNacimiento());
            txtNacionalidad.setText(jugador.getNacionalidad());
            spnPeso.setValue(jugador.getPeso());
            spnAltura.setValue(jugador.getAltura());
            spnNumero.setValue(jugador.getNumero());
            cbxPosicion.setSelectedItem(jugador.getPosicion());
            actualizarEdad();
        }
    }

    private String generarNuevoId() {
        return "JUG-" + (controladora.getMisJugadores().size() + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegJugador dialog = new RegJugador();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}