package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Equipo;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegJuego extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private ControladoraLiga controladora;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegJuego dialog = new RegJuego();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegJuego() {
		setBounds(100, 100, 609, 324);
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
				JPanel panelID = new JPanel();
				panelID.setBackground(new Color(169, 169, 169));
				panelID.setBounds(177, 10, 225, 43);
				panel.add(panelID);
				panelID.setLayout(null);
				{
					JLabel lblID = new JLabel("ID:");
					lblID.setBounds(12, 16, 56, 16);
					panelID.add(lblID);
				}
				{
					txtID = new JTextField();
					txtID.setBounds(42, 13, 170, 22);
					panelID.add(txtID);
					txtID.setEditable(false);
					txtID.setText(generarNuevoId());
					txtID.setColumns(10);
				}
			}
			{
				JPanel panelFecha = new JPanel();
				panelFecha.setBackground(new Color(169, 169, 169));
				panelFecha.setBounds(12, 66, 255, 43);
				panel.add(panelFecha);
				panelFecha.setLayout(null);
				{
					JLabel lblFecha = new JLabel("Fecha:");
					lblFecha.setBounds(12, 16, 56, 16);
					panelFecha.add(lblFecha);
				}
				
				JSpinner spnFecha = new JSpinner(new SpinnerDateModel());
				spnFecha.setBounds(74, 13, 170, 22);
				panelFecha.add(spnFecha);
				JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
				spnFecha.setEditor(dateEditor);
				spnFecha.setPreferredSize(new Dimension(150, 25));
				spnFecha.setValue(new Date());
			}
			{
				JPanel EquipoLocal = new JPanel();
				EquipoLocal.setBackground(new Color(169, 169, 169));
				EquipoLocal.setBounds(12, 139, 255, 80);
				panel.add(EquipoLocal);
				EquipoLocal.setLayout(null);
				{
					JLabel lblEquipoLocal = new JLabel("Equipo Local:");
					lblEquipoLocal.setBounds(12, 13, 82, 16);
					EquipoLocal.add(lblEquipoLocal);
				}
				{
					JComboBox cbxEquipoLocal = new JComboBox();
					cargarEquipos(cbxEquipoLocal);
					cbxEquipoLocal.setBounds(12, 31, 231, 22);
					EquipoLocal.add(cbxEquipoLocal);
				}
			}
			{
				JPanel EquipoVisitante = new JPanel();
				EquipoVisitante.setLayout(null);
				EquipoVisitante.setBackground(new Color(169, 169, 169));
				EquipoVisitante.setBounds(314, 139, 255, 80);
				panel.add(EquipoVisitante);
				{
					JLabel lblEquipoVisitante = new JLabel("Equipo Visitante:");
					lblEquipoVisitante.setBounds(12, 13, 108, 16);
					EquipoVisitante.add(lblEquipoVisitante);
				}
				{
					JComboBox cbxEquipoVisitante = new JComboBox();
					cargarEquipos(cbxEquipoVisitante);
					cbxEquipoVisitante.setBounds(12, 31, 231, 22);
					EquipoVisitante.add(cbxEquipoVisitante);
				}
			}
			{
				JPanel panelUbicacion = new JPanel();
				panelUbicacion.setBackground(new Color(169, 169, 169));
				panelUbicacion.setBounds(314, 66, 255, 43);
				panel.add(panelUbicacion);
				panelUbicacion.setLayout(null);
				{
					JLabel lblUbicacion = new JLabel("Ubicacion:");
					lblUbicacion.setBounds(12, 13, 64, 16);
					panelUbicacion.add(lblUbicacion);
				}
				{
					JComboBox cbxUbicacion = new JComboBox();
					cargarLugares(cbxUbicacion);
					cbxUbicacion.setBounds(88, 10, 155, 22);
					panelUbicacion.add(cbxUbicacion);
				}
			}
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
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

	private void cargarLugares(JComboBox<String> comboBox) {
        Set<String> lugares = new HashSet<>();
        for (Equipo equipo : controladora.getMisEquipos()) {
            lugares.add(equipo.getCiudad());
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(lugares.toArray(new String[0]));
        comboBox.setModel(model);
    }

	private void cargarEquipos(JComboBox<Equipo> comboBox) {
        DefaultComboBoxModel<Equipo> model = new DefaultComboBoxModel<>();
        for (Equipo equipo : controladora.getMisEquipos()) {
            model.addElement(equipo);
        }
        comboBox.setModel(model);
    }

	private String generarNuevoId() {
	    if (controladora == null) {
	        controladora = new ControladoraLiga();
	    }
	    int numJuegos = controladora.getCalendario().getJuegos().size(); 
	    return "JUE-" + (numJuegos + 1);
	}
}
