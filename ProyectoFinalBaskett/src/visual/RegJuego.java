package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RegJuego extends JDialog {

    private JTextField txtID;
    private JComboBox<Equipo> cbxEquipoLocal;
    private JComboBox<Equipo> cbxEquipoVisitante;
    private JComboBox<String> cbxUbicacion;
    private JSpinner spnFecha;
    private ControladoraLiga controladora = ControladoraLiga.getInstance();

    public RegJuego() {
        setTitle("Registrar Juego");
        setSize(650, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(5, 1, 15, 15));

        txtID = new JTextField(generarNuevoId());
        txtID.setEditable(false);
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel panelID = new JPanel(new BorderLayout(10, 5));
        panelID.setBackground(Color.WHITE);
        panelID.add(new JLabel("ID:"), BorderLayout.WEST);
        panelID.add(txtID, BorderLayout.CENTER);
        mainPanel.add(panelID);

        spnFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy HH:mm");
        spnFecha.setEditor(dateEditor);
        ((JSpinner.DefaultEditor) spnFecha.getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel panelFecha = new JPanel(new BorderLayout(10, 5));
        panelFecha.setBackground(Color.WHITE);
        panelFecha.add(new JLabel("Fecha y Hora:"), BorderLayout.WEST);
        panelFecha.add(spnFecha, BorderLayout.CENTER);
        mainPanel.add(panelFecha);

        JPanel panelEquipos = new JPanel(new GridLayout(1, 2, 15, 0));
        panelEquipos.setBackground(Color.WHITE);
        
        cbxEquipoLocal = new JComboBox<>();
        cbxEquipoVisitante = new JComboBox<>();
        for (Equipo equipo : controladora.getMisEquipos()) {
            cbxEquipoLocal.addItem(equipo);
            cbxEquipoVisitante.addItem(equipo);
        }
        
        JPanel panelLocal = new JPanel(new BorderLayout(10, 5));
        panelLocal.setBackground(Color.WHITE);
        panelLocal.add(new JLabel("Equipo Local:"), BorderLayout.WEST);
        panelLocal.add(cbxEquipoLocal, BorderLayout.CENTER);
        
        JPanel panelVisitante = new JPanel(new BorderLayout(10, 5));
        panelVisitante.setBackground(Color.WHITE);
        panelVisitante.add(new JLabel("Equipo Visitante:"), BorderLayout.WEST);
        panelVisitante.add(cbxEquipoVisitante, BorderLayout.CENTER);
        
        panelEquipos.add(panelLocal);
        panelEquipos.add(panelVisitante);
        mainPanel.add(panelEquipos);

        cbxUbicacion = new JComboBox<>();
        Set<String> lugares = new HashSet<>();
        for (Equipo equipo : controladora.getMisEquipos()) {
            lugares.add(equipo.getCiudad());
        }
        for (String lugar : lugares) {
            cbxUbicacion.addItem(lugar);
        }
        
        JPanel panelUbicacion = new JPanel(new BorderLayout(10, 5));
        panelUbicacion.setBackground(Color.WHITE);
        panelUbicacion.add(new JLabel("Ubicación:"), BorderLayout.WEST);
        panelUbicacion.add(cbxUbicacion, BorderLayout.CENTER);
        mainPanel.add(panelUbicacion);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
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
                if (cbxEquipoLocal.getSelectedItem() == null || 
                    cbxEquipoVisitante.getSelectedItem() == null || 
                    cbxUbicacion.getSelectedItem() == null) {
                    
                    JOptionPane.showMessageDialog(this, 
                        "Complete todos los campos", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (cbxEquipoLocal.getSelectedItem() == cbxEquipoVisitante.getSelectedItem()) {
                    JOptionPane.showMessageDialog(this, 
                        "Los equipos deben ser diferentes", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Equipo local = (Equipo) cbxEquipoLocal.getSelectedItem();
                Equipo visitante = (Equipo) cbxEquipoVisitante.getSelectedItem();
                
                Date fechaSeleccionada = (Date) spnFecha.getValue();
                LocalDateTime fechaHora = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
                
                Juego nuevoJuego = new Juego(
                    txtID.getText(),
                    fechaHora,
                    (String) cbxUbicacion.getSelectedItem(),
                    local,
                    visitante
                );
                
                controladora.agregarJuego(nuevoJuego);
                
                int respuesta = JOptionPane.showConfirmDialog(
                    this, 
                    "¿Desea agregar otro juego?", 
                    "Registro exitoso", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    txtID.setText("JUE-" + (controladora.getCalendario().getJuegos().size() + 1));
                    spnFecha.setValue(new Date());
                    cbxEquipoLocal.setSelectedIndex(-1);
                    cbxEquipoVisitante.setSelectedIndex(-1);
                    cbxUbicacion.setSelectedIndex(-1);
                } else {
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private String generarNuevoId() {
        return "JUE-" + (controladora.getCalendario().getJuegos().size() + 1);
    }
    
    public static void main(String[] args) {
        RegJuego dialog = new RegJuego();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}