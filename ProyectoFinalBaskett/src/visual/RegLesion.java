package visual;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Jugador;

public class RegLesion extends JDialog {

    private JComboBox<Jugador> cbxJugadores;
    private JComboBox<String> cbxLesion;
    private JComboBox<String> cbxTratamiento;
    private JSpinner spnFecha;
    private JSpinner spnDuracion;
    private ControladoraLiga controladora;

    public RegLesion(ControladoraLiga controladora) {
        this.controladora = controladora;
        initComponents();
        styleComponents();
    }

    private void initComponents() {
        setTitle("Registrar Lesión");
        setSize(720, 300);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(4, 1, 15, 15));

        cbxJugadores = new JComboBox<>();
        cargarJugadores();
        mainPanel.add(createInputPanel("Jugador:", cbxJugadores));

        JPanel panelLesionTratamiento = new JPanel(new GridLayout(1, 2, 15, 0));
        panelLesionTratamiento.setBackground(Color.WHITE);
        
        cbxLesion = new JComboBox<>(new String[]{
            "<Seleccionar>", "Esguince de tobillo", "Rotura de ligamentos", 
            "Tendinitis rotuliana", "Distensión muscular", "Fracturas por estrés", 
            "Lesiones en el tendón de Aquiles", "Luxación de hombro", 
            "Esguince de dedos", "Fracturas en manos/muñecas"
        });
        
        cbxTratamiento = new JComboBox<>(new String[]{
            "<Seleccionar>", "Reposo y Protección", "Hielo y Compresión", 
            "Medicamentos", "Fisioterapia", "Inmovilización", "Cirugía"
        });
        
        panelLesionTratamiento.add(createInputPanel("Tipo de lesión:", cbxLesion));
        panelLesionTratamiento.add(createInputPanel("Tratamiento:", cbxTratamiento));
        mainPanel.add(panelLesionTratamiento);

        JPanel panelFechaDuracion = new JPanel(new GridLayout(1, 2, 15, 0));
        panelFechaDuracion.setBackground(Color.WHITE);
        
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor fechaEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(fechaEditor);
        spnFecha.setValue(new Date());
        
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        
        panelFechaDuracion.add(createInputPanel("Fecha:", spnFecha));
        panelFechaDuracion.add(createInputPanel("Duración (días):", spnDuracion));
        mainPanel.add(panelFechaDuracion);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnRegistrar = createButton("Registrar", new Color(255, 147, 46));
        JButton btnCancelar = createButton("Cancelar", new Color(178, 34, 34));

        btnRegistrar.addActionListener(e -> registrarLesion());
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        Font mainFont = new Font("Arial", Font.PLAIN, 14);
        UIManager.put("ComboBox.font", mainFont);
        UIManager.put("Spinner.font", mainFont);
        UIManager.put("Label.font", mainFont.deriveFont(Font.BOLD));
    }

    private JPanel createInputPanel(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(label);
        component.setPreferredSize(new Dimension(250, 30));
        
        if(component instanceof JSpinner) {
            component.setPreferredSize(new Dimension(200, 30));
            ((JSpinner.DefaultEditor) ((JSpinner) component).getEditor()).getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        }
        
        panel.add(lbl, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setFocusPainted(false);
        return button;
    }

    private void cargarJugadores() {
        DefaultComboBoxModel<Jugador> model = new DefaultComboBoxModel<>();
        controladora.getMisJugadores().forEach(model::addElement);
        cbxJugadores.setModel(model);
        cbxJugadores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Jugador) {
                    Jugador j = (Jugador) value;
                    setText(j.getNombre() + " (" + j.getID() + ")");
                }
                return this;
            }
        });
    }

    private void registrarLesion() {
        try {
            Jugador jugador = (Jugador) cbxJugadores.getSelectedItem();
            
            if (jugador == null || 
                cbxLesion.getSelectedIndex() == 0 || 
                cbxTratamiento.getSelectedIndex() == 0) {
                
                throw new IllegalArgumentException("Complete todos los campos obligatorios");
            }

            String tipoLesion = (String) cbxLesion.getSelectedItem();
            String tratamiento = (String) cbxTratamiento.getSelectedItem();
            Date fecha = (Date) spnFecha.getValue();
            int duracion = (int) spnDuracion.getValue();

            controladora.agregarLesionAJugador(
                jugador.getID(),
                tipoLesion,
                "General",
                duracion,
                tratamiento,
                fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                duracion
            );

            JOptionPane.showMessageDialog(this, 
                "Lesión registrada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        RegLesion dialog = new RegLesion(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}