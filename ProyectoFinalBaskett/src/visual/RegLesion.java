package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
        setSize(703, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JPanel panelJugador = new JPanel(new BorderLayout(5, 5));
        panelJugador.setBackground(new Color(169, 169, 169));
        panelJugador.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblJugador = new JLabel("Jugador:");
        lblJugador.setFont(new Font("Arial", Font.BOLD, 14));
        panelJugador.add(lblJugador, BorderLayout.WEST);
        cbxJugadores = new JComboBox<>();
        cbxJugadores.setFont(new Font("Arial", Font.PLAIN, 14));
        cargarJugadores();
        panelJugador.add(cbxJugadores, BorderLayout.CENTER);
        contentPanel.add(panelJugador);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel panelTipoTratamiento = new JPanel();
        panelTipoTratamiento.setLayout(new BoxLayout(panelTipoTratamiento, BoxLayout.X_AXIS));
        panelTipoTratamiento.setOpaque(false);

        JPanel panelTipo = new JPanel(new BorderLayout(5, 5));
        panelTipo.setBackground(new Color(169, 169, 169));
        panelTipo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblTipoLesion = new JLabel("Tipo de lesión:");
        lblTipoLesion.setFont(new Font("Arial", Font.BOLD, 14));
        panelTipo.add(lblTipoLesion, BorderLayout.WEST);
        cbxLesion = new JComboBox<>();
        cbxLesion.setModel(new DefaultComboBoxModel<>(new String[]{
            "<Seleccionar>", "Esguince de tobillo", "Rotura de ligamentos", 
            "Tendinitis rotuliana", "Distensión muscular", "Fracturas por estrés", 
            "Lesiones en el tendón de Aquiles", "Luxación de hombro", 
            "Esguince de dedos", "Fracturas en manos/muñecas"
        }));
        cbxLesion.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTipo.add(cbxLesion, BorderLayout.CENTER);
        panelTipoTratamiento.add(panelTipo);
        panelTipoTratamiento.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel panelTratamiento = new JPanel(new BorderLayout(5, 5));
        panelTratamiento.setBackground(new Color(169, 169, 169));
        panelTratamiento.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblTratamiento = new JLabel("Tratamiento:");
        lblTratamiento.setFont(new Font("Arial", Font.BOLD, 14));
        panelTratamiento.add(lblTratamiento, BorderLayout.WEST);
        cbxTratamiento = new JComboBox<>();
        cbxTratamiento.setModel(new DefaultComboBoxModel<>(new String[]{
            "<Seleccionar>", "Reposo y Protección", "Hielo y Compresión", 
            "Medicamentos", "Fisioterapia", "Inmovilización", "Cirugía"
        }));
        cbxTratamiento.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTratamiento.add(cbxTratamiento, BorderLayout.CENTER);
        panelTipoTratamiento.add(panelTratamiento);

        contentPanel.add(panelTipoTratamiento);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel panelFechaDuracion = new JPanel();
        panelFechaDuracion.setLayout(new BoxLayout(panelFechaDuracion, BoxLayout.X_AXIS));
        panelFechaDuracion.setOpaque(false);

        JPanel panelFecha = new JPanel(new BorderLayout(5, 5));
        panelFecha.setBackground(new Color(169, 169, 169));
        panelFecha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        panelFecha.add(lblFecha, BorderLayout.WEST);
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor fechaEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
        fechaEditor.getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        spnFecha.setEditor(fechaEditor);
        panelFecha.add(spnFecha, BorderLayout.CENTER);
        panelFechaDuracion.add(panelFecha);
        panelFechaDuracion.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel panelDuracion = new JPanel(new BorderLayout(5, 5));
        panelDuracion.setBackground(new Color(169, 169, 169));
        panelDuracion.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setFont(new Font("Arial", Font.BOLD, 14));
        panelDuracion.add(lblDuracion, BorderLayout.WEST);
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        JSpinner.NumberEditor duracionEditor = new JSpinner.NumberEditor(spnDuracion, "#");
        duracionEditor.getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        spnDuracion.setEditor(duracionEditor);
        panelDuracion.add(spnDuracion, BorderLayout.CENTER);
        panelFechaDuracion.add(panelDuracion);

        contentPanel.add(panelFechaDuracion);

        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        buttonPane.setBackground(Color.WHITE);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(255, 147, 46));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrarLesion());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(178, 34, 34));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());

        buttonPane.add(btnRegistrar);
        buttonPane.add(btnCancelar);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
            "General",
            duracion,
            tratamiento,
            fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
            duracion
        );

        JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente");
        dispose();
    }
}