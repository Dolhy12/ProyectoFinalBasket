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

public class lesionvisual extends JFrame {

    private JPanel contentPane;
    private JTextField txtTipo;
    private JTextField txtParteCuerpo;
    private JTextField txtDiasBajaEstimado;
    private JTextField txtTratamiento;
    private JTextField txtFechaLesion;
    private JTextField txtDuracionEstimada;
    private ControladoraLiga controladora;
    private String idJugador; 

    public lesionvisual(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.idJugador = idJugador;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladoraLiga controladora = new ControladoraLiga();
                    lesionvisual frame = new lesionvisual(controladora, "J1");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        Jugador jugador = controladora.buscarJugador(idJugador);
        setTitle("Registrar Lesión para " + (jugador != null ? jugador.getNombre() : "Jugador no encontrado"));
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
        txtTipo.setColumns(10);

        JLabel lblParteCuerpo = new JLabel("PARTE DEL CUERPO:");
        lblParteCuerpo.setFont(new Font("Arial", Font.BOLD, 14));
        lblParteCuerpo.setBounds(20, 80, 140, 25);
        contentPane.add(lblParteCuerpo);

        txtParteCuerpo = new JTextField();
        txtParteCuerpo.setBounds(20, 105, 240, 30);
        contentPane.add(txtParteCuerpo);
        txtParteCuerpo.setColumns(10);

        JLabel lblDiasBajaEstimado = new JLabel("DÍAS DE BAJA:");
        lblDiasBajaEstimado.setFont(new Font("Arial", Font.BOLD, 14));
        lblDiasBajaEstimado.setBounds(20, 140, 120, 25);
        contentPane.add(lblDiasBajaEstimado);

        txtDiasBajaEstimado = new JTextField();
        txtDiasBajaEstimado.setBounds(20, 165, 240, 30);
        contentPane.add(txtDiasBajaEstimado);
        txtDiasBajaEstimado.setColumns(10);

        JLabel lblTratamiento = new JLabel("TRATAMIENTO:");
        lblTratamiento.setFont(new Font("Arial", Font.BOLD, 14));
        lblTratamiento.setBounds(20, 200, 120, 25);
        contentPane.add(lblTratamiento);

        txtTratamiento = new JTextField();
        txtTratamiento.setBounds(20, 225, 240, 30);
        contentPane.add(txtTratamiento);
        txtTratamiento.setColumns(10);

        JLabel lblFechaLesion = new JLabel("FECHA (YYYY-MM-DD):");
        lblFechaLesion.setFont(new Font("Arial", Font.BOLD, 14));
        lblFechaLesion.setBounds(20, 260, 160, 25);
        contentPane.add(lblFechaLesion);

        txtFechaLesion = new JTextField(LocalDate.now().toString());
        txtFechaLesion.setBounds(20, 285, 240, 30);
        contentPane.add(txtFechaLesion);
        txtFechaLesion.setColumns(10);

        JLabel lblDuracionEstimada = new JLabel("DURACIÓN (días):");
        lblDuracionEstimada.setFont(new Font("Arial", Font.BOLD, 14));
        lblDuracionEstimada.setBounds(20, 320, 120, 25);
        contentPane.add(lblDuracionEstimada);

        txtDuracionEstimada = new JTextField();
        txtDuracionEstimada.setBounds(20, 345, 240, 30);
        contentPane.add(txtDuracionEstimada);
        txtDuracionEstimada.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(20, 390, 100, 30);
        contentPane.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160, 390, 100, 30);
        contentPane.add(btnCancelar);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String tipo = txtTipo.getText();
                    String parteCuerpo = txtParteCuerpo.getText();
                    int diasBajaEstimado = Integer.parseInt(txtDiasBajaEstimado.getText());
                    String tratamiento = txtTratamiento.getText();
                    LocalDate fechaLesion = LocalDate.parse(txtFechaLesion.getText());
                    int duracionEstimada = Integer.parseInt(txtDuracionEstimada.getText());

                    controladora.agregarLesionAJugador(idJugador, tipo, parteCuerpo, diasBajaEstimado, tratamiento, fechaLesion, duracionEstimada);

                    JOptionPane.showMessageDialog(null, "Lesión registrada con éxito para " + (jugador != null ? jugador.getNombre() : idJugador));
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Los campos numéricos (días de baja, duración) deben ser números.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar la lesión: " + ex.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    } 
}