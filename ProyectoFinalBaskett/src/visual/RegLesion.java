package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;
import logico.Lesion;

public class RegLesion extends JFrame {

    private JPanel contentPane;
    private JTextField txtTipo;
    private JTextField txtParteCuerpo;
    private JTextField txtDiasBajaEstimado;
    private JTextField txtTratamiento;
    private JTextField txtFechaLesion;
    private JTextField txtDuracionEstimada;
    private ControladoraLiga controladora;
    private String idJugador;
    private String tipoLesion; 

    /**
     * @wbp.parser.constructor
     */
    public RegLesion(ControladoraLiga controladora, String idJugador) {
        this(controladora, idJugador, null); 
    }

    public RegLesion(ControladoraLiga controladora, String idJugador, String tipoLesion) {
        this.controladora = controladora;
        this.idJugador = idJugador;
        this.tipoLesion = tipoLesion;
        initialize();
        if (tipoLesion != null) {
            cargarDatosLesion();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControladoraLiga controladora = new ControladoraLiga();
                RegLesion frame = new RegLesion(controladora, "J1");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        Jugador jugador = controladora.buscarJugador(idJugador);
        setTitle((tipoLesion == null ? "Registrar Lesión" : "Modificar Lesión") + " para " + 
                 (jugador != null ? jugador.getNombre() : "Jugador no encontrado"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTipo = new JLabel("TIPO:");
        lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTipo.setBounds(20, 20, 80, 25);
        contentPane.add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(20, 45, 240, 30);
        contentPane.add(txtTipo);

        JLabel lblParteCuerpo = new JLabel("PARTE DEL CUERPO:");
        lblParteCuerpo.setFont(new Font("Arial", Font.BOLD, 14));
        lblParteCuerpo.setBounds(20, 80, 140, 25);
        contentPane.add(lblParteCuerpo);

        txtParteCuerpo = new JTextField();
        txtParteCuerpo.setBounds(20, 105, 240, 30);
        contentPane.add(txtParteCuerpo);

        JLabel lblDiasBajaEstimado = new JLabel("DÍAS DE BAJA:");
        lblDiasBajaEstimado.setFont(new Font("Arial", Font.BOLD, 14));
        lblDiasBajaEstimado.setBounds(20, 140, 120, 25);
        contentPane.add(lblDiasBajaEstimado);

        txtDiasBajaEstimado = new JTextField();
        txtDiasBajaEstimado.setBounds(20, 165, 240, 30);
        contentPane.add(txtDiasBajaEstimado);

        JLabel lblTratamiento = new JLabel("TRATAMIENTO:");
        lblTratamiento.setFont(new Font("Arial", Font.BOLD, 14));
        lblTratamiento.setBounds(20, 200, 120, 25);
        contentPane.add(lblTratamiento);

        txtTratamiento = new JTextField();
        txtTratamiento.setBounds(20, 225, 240, 30);
        contentPane.add(txtTratamiento);

        JLabel lblFechaLesion = new JLabel("FECHA (YYYY-MM-DD):");
        lblFechaLesion.setFont(new Font("Arial", Font.BOLD, 14));
        lblFechaLesion.setBounds(20, 260, 160, 25);
        contentPane.add(lblFechaLesion);

        txtFechaLesion = new JTextField(LocalDate.now().toString());
        txtFechaLesion.setBounds(20, 285, 240, 30);
        contentPane.add(txtFechaLesion);

        JLabel lblDuracionEstimada = new JLabel("DURACIÓN (días):");
        lblDuracionEstimada.setFont(new Font("Arial", Font.BOLD, 14));
        lblDuracionEstimada.setBounds(20, 320, 120, 25);
        contentPane.add(lblDuracionEstimada);

        txtDuracionEstimada = new JTextField();
        txtDuracionEstimada.setBounds(20, 345, 240, 30);
        contentPane.add(txtDuracionEstimada);

        JButton btnGuardar = new JButton(tipoLesion == null ? "Registrar" : "Guardar");
        btnGuardar.setBounds(20, 390, 100, 30);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 390, 100, 30);
        contentPane.add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarLesion());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosLesion() {
        Jugador jugador = controladora.buscarJugador(idJugador);
        if (jugador != null) {
            for (Lesion lesion : jugador.getLesiones()) {
                if (lesion.getTipo().equals(tipoLesion)) {
                    txtTipo.setText(lesion.getTipo());
                    txtParteCuerpo.setText(lesion.getParteCuerpo());
                    txtDiasBajaEstimado.setText(String.valueOf(lesion.getDiasBajaEstimado()));
                    txtTratamiento.setText(lesion.getTratamiento());
                    txtFechaLesion.setText(lesion.getFechaLesion().toString());
                    txtDuracionEstimada.setText(String.valueOf(lesion.getDuracionEstimada()));
                    break;
                }
            }
        }
    }

    private void guardarLesion() {
        try {
            String tipo = txtTipo.getText().trim();
            String parteCuerpo = txtParteCuerpo.getText().trim();
            int diasBajaEstimado = Integer.parseInt(txtDiasBajaEstimado.getText().trim());
            String tratamiento = txtTratamiento.getText().trim();
            LocalDate fechaLesion = LocalDate.parse(txtFechaLesion.getText().trim());
            int duracionEstimada = Integer.parseInt(txtDuracionEstimada.getText().trim());

            if (tipo.isEmpty() || parteCuerpo.isEmpty() || tratamiento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tipo, parte del cuerpo y tratamiento son obligatorios.");
                return;
            }
            if (diasBajaEstimado < 0 || duracionEstimada < 0) {
                JOptionPane.showMessageDialog(this, "Días de baja y duración deben ser positivos.");
                return;
            }

            if (tipoLesion == null) { 
                controladora.agregarLesionAJugador(idJugador, tipo, parteCuerpo, diasBajaEstimado, tratamiento, fechaLesion, duracionEstimada);
                JOptionPane.showMessageDialog(this, "Lesión registrada con éxito.");
            } else { 
                Jugador jugador = controladora.buscarJugador(idJugador);
                if (jugador != null) {
                    jugador.eliminarLesion(tipoLesion); 
                    controladora.agregarLesionAJugador(idJugador, tipo, parteCuerpo, diasBajaEstimado, tratamiento, fechaLesion, duracionEstimada);
                    JOptionPane.showMessageDialog(this, "Lesión modificada con éxito.");
                }
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Días de baja y duración deben ser números.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}