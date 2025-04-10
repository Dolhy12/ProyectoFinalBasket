package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Equipo;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegEquipo extends JDialog {

    private JTextField txtNombre, txtCiudad, txtID, txtEntrenador, txtMascota;
    private JSpinner spnFundacion;
    private Equipo equipoExistente;
    private JButton btnRegistrar;
    private ControladoraLiga controladora = ControladoraLiga.getInstance();

    public RegEquipo(Equipo equipoEditar) {
        this();
        setTitle("Modificar Equipo");
        equipoExistente = equipoEditar;
        btnRegistrar.setText("Modificar");
        cargarDatosEquipo(equipoEditar);
    }

    public RegEquipo() {
        setTitle("Registrar Equipo");
        setSize(600, 500);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));

        txtID = new JTextField();
        txtID.setEditable(false);
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtID.setText(generarNuevoId());

        txtNombre = new JTextField();
        txtCiudad = new JTextField();
        txtEntrenador = new JTextField();
        txtMascota = new JTextField();

        for (JTextField field : new JTextField[]{txtNombre, txtCiudad, txtEntrenador, txtMascota}) {
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        spnFundacion = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFundacion, "dd-MM-yyyy");
        spnFundacion.setEditor(editor);
        spnFundacion.setValue(new Date());

        mainPanel.add(createLabelPanel("ID:", txtID));
        mainPanel.add(createLabelPanel("Nombre:", txtNombre));
        mainPanel.add(createLabelPanel("Ciudad:", txtCiudad));
        mainPanel.add(createLabelPanel("Entrenador:", txtEntrenador));
        mainPanel.add(createLabelPanel("Mascota:", txtMascota));
        mainPanel.add(createLabelPanel("Fundación:", spnFundacion));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(255, 147, 30));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRegistrar.addActionListener(e -> {
            try {
                if (txtNombre.getText().trim().isEmpty() || 
                    txtCiudad.getText().trim().isEmpty() || 
                    txtEntrenador.getText().trim().isEmpty()) {
                    
                    JOptionPane.showMessageDialog(this, 
                        "Complete los campos obligatorios (*)", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String fechaFundacion = new SimpleDateFormat("dd-MM-yyyy").format(spnFundacion.getValue());
                Equipo equipo = (equipoExistente == null) ? 
                    new Equipo(
                        txtID.getText(),
                        fechaFundacion,
                        null,
                        txtMascota.getText(),
                        txtNombre.getText(),
                        txtCiudad.getText(),
                        txtEntrenador.getText()
                    ) : 
                    actualizarEquipoExistente(fechaFundacion);

                if (equipoExistente == null) {
                    controladora.agregarEquipo(equipo);
                } else {
                    controladora.actualizarEquipo(equipo);
                }

                if (equipoExistente == null) {
                    int respuesta = JOptionPane.showConfirmDialog(this,
                        "¿Desea agregar otro equipo?",
                        "Registro exitoso",
                        JOptionPane.YES_NO_OPTION);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        txtID.setText(generarNuevoId());
                        txtNombre.setText("");
                        txtCiudad.setText("");
                        txtEntrenador.setText("");
                        txtMascota.setText("");
                        spnFundacion.setValue(new Date());
                    } else {
                        dispose();
                    }
                } else {
                	JOptionPane.showMessageDialog(this,
                            "Equipo modificado exitosamente",
                            "Modificación exitosa", 
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(178, 34, 34));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createLabelPanel(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setPreferredSize(new Dimension(90, lbl.getPreferredSize().height)); 
        panel.add(lbl, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private Equipo actualizarEquipoExistente(String fechaFundacion) {
        equipoExistente.setNombre(txtNombre.getText());
        equipoExistente.setCiudad(txtCiudad.getText());
        equipoExistente.setEntrenador(txtEntrenador.getText());
        equipoExistente.setNombreDeLaMascota(txtMascota.getText());
        equipoExistente.setTiempoFundado(fechaFundacion);
        return equipoExistente;
    }

    private void cargarDatosEquipo(Equipo equipo) {
        try {
            txtID.setText(equipo.getID());
            txtNombre.setText(equipo.getNombre());
            txtCiudad.setText(equipo.getCiudad());
            txtEntrenador.setText(equipo.getEntrenador());
            txtMascota.setText(equipo.getNombreDeLaMascota());
            spnFundacion.setValue(new SimpleDateFormat("dd-MM-yyyy").parse(equipo.getTiempoFundado()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarNuevoId() {
        return "EQU-" + (controladora.getMisEquipos().size() + 1);
    }

    public static void main(String[] args) {
        RegEquipo dialog = new RegEquipo();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}