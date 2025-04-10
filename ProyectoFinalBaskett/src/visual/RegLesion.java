package visual;

import java.awt.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class RegLesion extends JDialog {

    private JComboBox<Jugador> cbxJugadores;
    private JComboBox<String> cbxLesion = new JComboBox<>();
    private JComboBox<String> cbxTratamiento = new JComboBox<>(); 
    private JSpinner spnFecha;
    private JSpinner spnDuracion;
    private ControladoraLiga controladora;

    public RegLesion(ControladoraLiga controladora) {
        this.controladora = controladora;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Lesión");
        setSize(720, 300);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(4, 1, 15, 15));

        JPanel jugadorPanel = new JPanel(new BorderLayout(10, 5));
        jugadorPanel.setBackground(Color.WHITE);
        JLabel lblJugador = new JLabel("Jugador:");
        lblJugador.setFont(new Font("Arial", Font.BOLD, 14));
        cbxJugadores = new JComboBox<>();
        cargarJugadores();
        jugadorPanel.add(lblJugador, BorderLayout.WEST);
        jugadorPanel.add(cbxJugadores, BorderLayout.CENTER);
        mainPanel.add(jugadorPanel);

        JPanel panelLesionTratamiento = new JPanel(new GridLayout(1, 2, 15, 0));
        panelLesionTratamiento.setBackground(Color.WHITE);
        
        JPanel panelLesion = new JPanel(new BorderLayout(10, 5));
        panelLesion.setBackground(Color.WHITE);
        JLabel lblLesion = new JLabel("Tipo de lesión:");
        lblLesion.setFont(new Font("Arial", Font.BOLD, 14));
        cbxLesion.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "Esguince de tobillo", "Rotura de ligamentos", "Tendinitis rotuliana", "Distensión muscular", "Fracturas por estrés", "Lesiones en el tendón de Aquiles", "Luxación de hombro", "Esguince de dedos", "Fracturas en manos/muñecas"}));
        panelLesion.add(lblLesion, BorderLayout.WEST);
        panelLesion.add(cbxLesion, BorderLayout.CENTER);
        
        JPanel panelTratamiento = new JPanel(new BorderLayout(10, 5));
        panelTratamiento.setBackground(Color.WHITE);
        JLabel lblTratamiento = new JLabel("Tratamiento:");
        lblTratamiento.setFont(new Font("Arial", Font.BOLD, 14));
        cbxTratamiento.setModel(new DefaultComboBoxModel<>(new String[] {"<Seleccionar>", "Reposo y Protección", "Hielo y Compresión", "Medicamentos", "Fisioterapia", "Inmovilización", "Cirugía"}));
        panelTratamiento.add(lblTratamiento, BorderLayout.WEST);
        panelTratamiento.add(cbxTratamiento, BorderLayout.CENTER);
        
        panelLesionTratamiento.add(panelLesion);
        panelLesionTratamiento.add(panelTratamiento);
        mainPanel.add(panelLesionTratamiento);

        JPanel panelFechaDuracion = new JPanel(new GridLayout(1, 2, 15, 0));
        panelFechaDuracion.setBackground(Color.WHITE);
        
        JPanel panelFecha = new JPanel(new BorderLayout(10, 5));
        panelFecha.setBackground(Color.WHITE);
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy"));
        spnFecha.setValue(new Date());
        panelFecha.add(lblFecha, BorderLayout.WEST);
        panelFecha.add(spnFecha, BorderLayout.CENTER);
        
        JPanel panelDuracion = new JPanel(new BorderLayout(10, 5));
        panelDuracion.setBackground(Color.WHITE);
        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setFont(new Font("Arial", Font.BOLD, 14));
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        panelDuracion.add(lblDuracion, BorderLayout.WEST);
        panelDuracion.add(spnDuracion, BorderLayout.CENTER);
        
        panelFechaDuracion.add(panelFecha);
        panelFechaDuracion.add(panelDuracion);
        mainPanel.add(panelFechaDuracion);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(255, 147, 46));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRegistrar.addActionListener(e -> registrarLesion());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(178, 34, 34));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Spinner.font", new Font("Arial", Font.PLAIN, 14));
    }

    private void cargarJugadores() {
        DefaultComboBoxModel<Jugador> modelo = new DefaultComboBoxModel<>();
        controladora.getMisJugadores().forEach(modelo::addElement);
        cbxJugadores.setModel(modelo);
        cbxJugadores.setRenderer(new DefaultListCellRenderer() {
            @Override @SuppressWarnings("rawtypes")
            public Component getListCellRendererComponent(JList list, Object value, int index,
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
        SwingUtilities.invokeLater(() -> {
            ControladoraLiga controladora = ControladoraLiga.getInstance();
            RegLesion dialog = new RegLesion(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}