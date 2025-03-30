package visual;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import logico.ControladoraLiga;
import logico.Equipo;
import logico.Juego;

public class Juegovisual extends JFrame {

    private JPanel contentPane;
    private JComboBox<Equipo> cmbLocal;
    private JComboBox<Equipo> cmbVisitante;
    private JTextField txtFecha;
    private JTextField txtLugar;
    private ControladoraLiga controladora;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Juegovisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    private void initialize() {
        setTitle("Registrar Juego");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        JLabel lblLocal = new JLabel("Equipo Local:");
        lblLocal.setBounds(20, 20, 100, 20);
        contentPane.add(lblLocal);

        cmbLocal = new JComboBox<>();
        cargarEquipos(cmbLocal);
        cmbLocal.setBounds(130, 20, 200, 25);
        contentPane.add(cmbLocal);

        JLabel lblVisitante = new JLabel("Equipo Visitante:");
        lblVisitante.setBounds(20, 60, 100, 20);
        contentPane.add(lblVisitante);

        cmbVisitante = new JComboBox<>();
        cargarEquipos(cmbVisitante);
        cmbVisitante.setBounds(130, 60, 200, 25);
        contentPane.add(cmbVisitante);

        
        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        lblFecha.setBounds(20, 100, 120, 20);
        contentPane.add(lblFecha);

        txtFecha = new JTextField(LocalDate.now().format(dateFormatter));
        txtFecha.setBounds(130, 100, 200, 25);
        contentPane.add(txtFecha);

        JLabel lblLugar = new JLabel("Lugar:");
        lblLugar.setBounds(20, 140, 100, 20);
        contentPane.add(lblLugar);

        txtLugar = new JTextField();
        txtLugar.setBounds(130, 140, 200, 25);
        contentPane.add(txtLugar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 180, 120, 30);
        contentPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 180, 120, 30);
        contentPane.add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrarJuego());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarEquipos(JComboBox<Equipo> combo) {
        DefaultComboBoxModel<Equipo> model = new DefaultComboBoxModel<>();
        for (Equipo equipo : controladora.getMisEquipos()) {
            model.addElement(equipo);
        }
        combo.setModel(model);
    }

    private void registrarJuego() {
        try {
            Equipo local = (Equipo) cmbLocal.getSelectedItem();
            Equipo visitante = (Equipo) cmbVisitante.getSelectedItem();
            
            
            if (local.getID().equals(visitante.getID())) {
                throw new Exception("¡Un equipo no puede jugar contra sí mismo!");
            }

            
            LocalDate fecha = LocalDate.parse(txtFecha.getText(), dateFormatter);
            
            String lugar = txtLugar.getText().trim();
            
            if (lugar.isEmpty()) {
                throw new Exception("El campo lugar es obligatorio");
            }

            String idJuego = "JG" + (controladora.getCalendario().getJuegos().size() + 1);
            Juego juego = new Juego(idJuego, fecha, lugar, local, visitante);
            controladora.agregarJuego(juego);

            JOptionPane.showMessageDialog(this, "Juego registrado!\nID: " + idJuego);
            dispose();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use YYYY-MM-DD");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Error: Seleccione ambos equipos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}