package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;

public class RegLesion extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private ControladoraLiga controladora;
    private JComboBox<Jugador> cbxJugadores;
    private JComboBox<String> cbxLesion;
    private JComboBox<String> cbxTratamiento;
    private JSpinner spnFecha;
    private JSpinner spnDuracion;

    public static void main(String[] args) {
        try {
            ControladoraLiga controladora = ControladoraLiga.getInstance();
            RegLesion dialog = new RegLesion(controladora);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RegLesion(ControladoraLiga controladora) {
        this.controladora = controladora;
        setTitle("Registrar Lesión");
        setBounds(100, 100, 600, 264);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Panel para selección de jugador
        JPanel panelJugador = new JPanel();
        panelJugador.setBackground(new Color(169, 169, 169));
        panelJugador.setBounds(12, 13, 560, 40);
        contentPanel.add(panelJugador);
        panelJugador.setLayout(null);
        
        JLabel lblJugador = new JLabel("Jugador:");
        lblJugador.setBounds(12, 13, 80, 16);
        panelJugador.add(lblJugador);
        
        cbxJugadores = new JComboBox<>();
        cbxJugadores.setBounds(100, 10, 440, 22);
        panelJugador.add(cbxJugadores);
        cargarJugadores();

        // Panel tipo de lesión
        JPanel panelTipo = new JPanel();
        panelTipo.setBackground(new Color(169, 169, 169));
        panelTipo.setBounds(12, 66, 270, 40);
        contentPanel.add(panelTipo);
        panelTipo.setLayout(null);
        
        JLabel lblTipoLesion = new JLabel("Tipo de lesión:");
        lblTipoLesion.setBounds(12, 13, 100, 16);
        panelTipo.add(lblTipoLesion);
        
        cbxLesion = new JComboBox<>();
        cbxLesion.setModel(new DefaultComboBoxModel<>(new String[] {
            "<Seleccionar>", "Esguince de tobillo", "Rotura de ligamentos", "Tendinitis rotuliana",
            "Distensión muscular", "Fracturas por estrés", "Lesiones en el tendón de Aquiles",
            "Luxación de hombro", "Esguince de dedos", "Fracturas en manos/muñecas"
        }));
        cbxLesion.setBounds(120, 10, 140, 22);
        panelTipo.add(cbxLesion);

        // Panel tratamiento
        JPanel panelTratamiento = new JPanel();
        panelTratamiento.setBackground(new Color(169, 169, 169));
        panelTratamiento.setBounds(294, 66, 270, 40);
        contentPanel.add(panelTratamiento);
        panelTratamiento.setLayout(null);
        
        JLabel lblTratamiento = new JLabel("Tratamiento:");
        lblTratamiento.setBounds(12, 13, 100, 16);
        panelTratamiento.add(lblTratamiento);
        
        cbxTratamiento = new JComboBox<>();
        cbxTratamiento.setModel(new DefaultComboBoxModel<>(new String[] {
            "<Seleccionar>", "Reposo y Protección", "Hielo y Compresión", "Medicamentos",
            "Fisioterapia", "Inmovilización", "Cirugía"
        }));
        cbxTratamiento.setBounds(120, 10, 140, 22);
        panelTratamiento.add(cbxTratamiento);

        // Panel fecha
        JPanel panelFecha = new JPanel();
        panelFecha.setBackground(new Color(169, 169, 169));
        panelFecha.setBounds(12, 119, 270, 40);
        contentPanel.add(panelFecha);
        panelFecha.setLayout(null);
        
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(12, 13, 100, 16);
        panelFecha.add(lblFecha);
        
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor fechaEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        spnFecha.setEditor(fechaEditor);
        spnFecha.setBounds(120, 10, 140, 22);
        panelFecha.add(spnFecha);

        // Panel duración
        JPanel panelDuracion = new JPanel();
        panelDuracion.setBackground(new Color(169, 169, 169));
        panelDuracion.setBounds(294, 119, 270, 40);
        contentPanel.add(panelDuracion);
        panelDuracion.setLayout(null);
        
        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(12, 13, 100, 16);
        panelDuracion.add(lblDuracion);
        
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        spnDuracion.setBounds(120, 10, 140, 22);
        panelDuracion.add(spnDuracion);

        // Botones
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(Color.WHITE);
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarLesion());
        buttonPane.add(btnRegistrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPane.add(btnCancelar);
    }

    private void cargarJugadores() {
        DefaultComboBoxModel<Jugador> model = new DefaultComboBoxModel<>();
        for (Jugador jugador : controladora.getMisJugadores()) {
            model.addElement(jugador);
        }
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
        Jugador jugador = (Jugador) cbxJugadores.getSelectedItem();
        
        if (jugador == null || 
            cbxLesion.getSelectedIndex() == 0 || 
            cbxTratamiento.getSelectedIndex() == 0) {
            
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios");
            return;
        }

        String tipoLesion = (String) cbxLesion.getSelectedItem();
        String tratamiento = (String) cbxTratamiento.getSelectedItem();
        Date fecha = (Date) spnFecha.getValue();
        int duracion = (int) spnDuracion.getValue();

        controladora.agregarLesionAJugador(
            jugador.getID(),
            tipoLesion,
            "General", // Parte del cuerpo
            duracion,
            tratamiento,
            fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
            duracion
        );

        JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente");
        dispose();
    }
}