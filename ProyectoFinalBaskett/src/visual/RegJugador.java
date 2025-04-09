package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

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

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        txtID = new JTextField(generarNuevoId());
        txtID.setEditable(false);
        mainPanel.add(createInputPanel("ID:", txtID));
        mainPanel.add(Box.createVerticalStrut(20));

        txtNombre = new JTextField();
        mainPanel.add(createInputPanel("Nombre:", txtNombre));
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel fechaEdadPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        fechaEdadPanel.setBackground(Color.WHITE);
        
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(dateEditor);
        spnFecha.setValue(new Date());
        spnFecha.addChangeListener(e -> actualizarEdad());
        
        txtEdad = new JTextField();
        txtEdad.setEditable(false);
        
        fechaEdadPanel.add(createInputPanel("Fecha Nacimiento:", spnFecha));
        fechaEdadPanel.add(createInputPanel("Edad:", txtEdad));
        mainPanel.add(fechaEdadPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel posNacPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        posNacPanel.setBackground(Color.WHITE);
        
        cbxPosicion = new JComboBox<>(new String[]{"<Seleccionar>", "Base", "Escolta", "Alero", "Ala-Pívot", "Pívot"});
        txtNacionalidad = new JTextField();
        
        posNacPanel.add(createInputPanel("Posición:", cbxPosicion));
        posNacPanel.add(createInputPanel("Nacionalidad:", txtNacionalidad));
        mainPanel.add(posNacPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel pesoAlturaPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        pesoAlturaPanel.setBackground(Color.WHITE);
        
        spnPeso = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 200.0, 0.5));
        spnAltura = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 3.0, 0.05));
        
        pesoAlturaPanel.add(createInputPanel("Peso (kg):", spnPeso));
        pesoAlturaPanel.add(createInputPanel("Altura (m):", spnAltura));
        mainPanel.add(pesoAlturaPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        spnNumero = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
        mainPanel.add(createInputPanel("Número:", spnNumero));
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

        btnRegistrar.addActionListener(e -> {
            try {
                if (validarCampos()) {
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
        });

        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        actualizarEdad();
    }

    private JPanel createInputPanel(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        
        if (component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(180, 35));
            ((JSpinner.DefaultEditor) ((JSpinner) component).getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        } else {
            component.setPreferredSize(new Dimension(250, 30));
            component.setFont(new Font("Arial", Font.PLAIN, 14));
        }
        
        panel.add(lbl, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        
        return panel;
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
        spnFecha.setValue(new Date());
        cbxPosicion.setSelectedIndex(0);
        txtNacionalidad.setText("");
        spnPeso.setValue(0.0);
        spnAltura.setValue(0.0);
        spnNumero.setValue(0);
    }

    private void cargarDatosJugador(Jugador jugador) {
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

    private String generarNuevoId() {
        return "JUG-" + (controladora.getMisJugadores().size() + 1);
    }

    public static void main(String[] args) {
        RegJugador dialog = new RegJugador();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}