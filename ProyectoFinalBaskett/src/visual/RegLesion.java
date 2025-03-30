package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class RegLesion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static ControladoraLiga controladora;
	private static String idJugador;
	private JComboBox cbxLesion;
	private JComboBox cbxTratamiento;
	private JSpinner spnFecha;
	private JSpinner spnDuracion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegLesion dialog = new RegLesion(controladora, idJugador);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegLesion(ControladoraLiga controladora, String idJugador) {
        this.controladora = controladora;
        this.idJugador = idJugador;
        
		setTitle("Registrar Lesion");
		setBounds(100, 100, 506, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(255, 147, 30));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelTipo = new JPanel();
				panelTipo.setBackground(new Color(169, 169, 169));
				panelTipo.setBounds(12, 13, 230, 66);
				panel.add(panelTipo);
				panelTipo.setLayout(null);
				{
					JLabel lblTipoLesion = new JLabel("Tipo de lesi\u00F3n:");
					lblTipoLesion.setBounds(12, 13, 119, 16);
					panelTipo.add(lblTipoLesion);
				}
				{
					cbxLesion = new JComboBox();
					cbxLesion.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>", "Esguince de tobillo", "Rotura de ligamentos ", "Tendinitis rotuliana ", "Distensi\u00F3n muscular ", "Fracturas por estr\u00E9s ", "Lesiones en el tend\u00F3n de Aquiles", "Luxaci\u00F3n de hombro", "Esguince de dedos", "Fracturas en manos/mu\u00F1ecas", "Lesiones en el manguito rotador", "Concusi\u00F3n", "Contusiones/moratones", "Lesiones oculares", "Hernias discales", "Fascitis plantar", "S\u00EDndrome de la banda iliotibial", "Bursitis", "Tendinitis en el codo"}));
					cbxLesion.setBounds(12, 31, 199, 22);
					panelTipo.add(cbxLesion);
				}
			}
			{
				JPanel panelTratamiento = new JPanel();
				panelTratamiento.setBackground(new Color(169, 169, 169));
				panelTratamiento.setBounds(12, 92, 191, 66);
				panel.add(panelTratamiento);
				panelTratamiento.setLayout(null);
				{
					JLabel lblTratamiento = new JLabel("Tratamiento:");
					lblTratamiento.setBounds(12, 13, 104, 16);
					panelTratamiento.add(lblTratamiento);
				}
				{
					cbxTratamiento = new JComboBox();
					cbxTratamiento.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>", "Reposo y Protecci\u00F3n", "Hielo y Compresi\u00F3n (RICE)", "Medicamentos", "Fisioterapia y Ejercicios", "Inmovilizaci\u00F3n", "Cirug\u00EDa", "Prevenci\u00F3n y Adaptaciones", "Terapias Avanzadas"}));
					cbxTratamiento.setBounds(12, 31, 161, 22);
					panelTratamiento.add(cbxTratamiento);
				}
			}
			{
				JPanel panelFecha = new JPanel();
				panelFecha.setBackground(new Color(169, 169, 169));
				panelFecha.setBounds(281, 13, 184, 66);
				panel.add(panelFecha);
				panelFecha.setLayout(null);
				{
					JLabel lblFecha = new JLabel("Fecha (DD-MM-YYYY):");
					lblFecha.setBounds(12, 13, 157, 16);
					panelFecha.add(lblFecha);
				}
				{
					spnFecha = new JSpinner(new SpinnerDateModel());
					spnFecha.setBounds(12, 31, 157, 22);
					panelFecha.add(spnFecha);
					JSpinner.DateEditor de_spnFecha = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
					spnFecha.setEditor(de_spnFecha);
					spnFecha.setPreferredSize(new Dimension(150, 25));
					spnFecha.setValue(new Date());
				}
			}
			{
				JPanel panelDuracion = new JPanel();
				panelDuracion.setBackground(new Color(169, 169, 169));
				panelDuracion.setBounds(281, 92, 153, 66);
				panel.add(panelDuracion);
				panelDuracion.setLayout(null);
				{
					JLabel lblDuracion = new JLabel("Duraci\u00F3n (d\u00EDas):");
					lblDuracion.setBounds(12, 13, 102, 16);
					panelDuracion.add(lblDuracion);
				}
				{
					spnDuracion = new JSpinner();
					spnDuracion.setBounds(12, 31, 124, 22);
					spnDuracion.setModel(new javax.swing.SpinnerNumberModel(1, 1, 365, 1));
					panelDuracion.add(spnDuracion);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String tipoLesion = (String) cbxLesion.getSelectedItem();
				        String tratamiento = (String) cbxTratamiento.getSelectedItem();
				        Date fechaLesion = (Date) spnFecha.getValue();
				        int duracion = (Integer) spnDuracion.getValue();

				        if (tipoLesion.equals("<Seleccionar>") || tratamiento.equals("<Seleccionar>")) {
				            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
				        } else {
				            LocalDate localFechaLesion = new java.sql.Date(fechaLesion.getTime()).toLocalDate();
				            
				            controladora.agregarLesionAJugador(idJugador, tipoLesion, "Parte del cuerpo no especificada", 0, tratamiento, localFechaLesion, duracion);
				            
				            JOptionPane.showMessageDialog(null, "Lesión registrada exitosamente.");
				            dispose();
				        }
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

}
