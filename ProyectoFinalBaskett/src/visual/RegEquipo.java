package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window.Type;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class RegEquipo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtCiudad;
	private JTextField txtID;
	private JTextField txtEntrenador;
	private JTextField txtMascota;
	private ControladoraLiga controladora;
	private JSpinner spnFundacion;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegEquipo dialog = new RegEquipo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegEquipo() {
		controladora = ControladoraLiga.getInstance();
		setType(Type.POPUP);
		setTitle("Registrar Equipo");
		setBounds(100, 100, 590, 466);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(212, 122, 25));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelID = new JPanel();
				panelID.setBackground(new Color(169, 169, 169));
				panelID.setBounds(160, 13, 211, 41);
				panel.add(panelID);
				panelID.setLayout(null);
				{
					JLabel lblID = new JLabel("ID:");
					lblID.setBounds(12, 13, 56, 16);
					panelID.add(lblID);
				}
				{
					txtID = new JTextField();
					txtID.setEditable(false);
					txtID.setBounds(44, 10, 155, 22);
					panelID.add(txtID);
					txtID.setText(generarNuevoId());
					txtID.setColumns(10);
				}
			}
			{
				JPanel panelNombre = new JPanel();
				panelNombre.setBackground(new Color(169, 169, 169));
				panelNombre.setLayout(null);
				panelNombre.setBounds(12, 83, 315, 41);
				panel.add(panelNombre);
				{
					JLabel lblNombre = new JLabel("Nombre:");
					lblNombre.setBounds(12, 13, 56, 16);
					panelNombre.add(lblNombre);
				}
				{
					txtNombre = new JTextField();
					txtNombre.setBounds(67, 10, 236, 22);
					panelNombre.add(txtNombre);
					txtNombre.setColumns(10);
				}
			}
			{
				JPanel panelCiudad = new JPanel();
				panelCiudad.setBackground(new Color(169, 169, 169));
				panelCiudad.setLayout(null);
				panelCiudad.setBounds(12, 147, 538, 41);
				panel.add(panelCiudad);
				{
					JLabel lblCiudad = new JLabel("Ciudad:");
					lblCiudad.setBounds(12, 13, 56, 16);
					panelCiudad.add(lblCiudad);
				}
				{
					txtCiudad = new JTextField();
					txtCiudad.setBounds(70, 10, 445, 22);
					panelCiudad.add(txtCiudad);
					txtCiudad.setColumns(10);
				}
			}
			{
				JPanel panelEntrenador = new JPanel();
				panelEntrenador.setBackground(new Color(169, 169, 169));
				panelEntrenador.setLayout(null);
				panelEntrenador.setBounds(12, 219, 538, 41);
				panel.add(panelEntrenador);
				{
					JLabel lblEntrenador = new JLabel("Entrenador:");
					lblEntrenador.setBounds(12, 13, 80, 16);
					panelEntrenador.add(lblEntrenador);
				}
				{
					txtEntrenador = new JTextField();
					txtEntrenador.setBounds(85, 10, 441, 22);
					panelEntrenador.add(txtEntrenador);
					txtEntrenador.setColumns(10);
				}
			}
			{
				JPanel panelMascota = new JPanel();
				panelMascota.setBackground(new Color(169, 169, 169));
				panelMascota.setLayout(null);
				panelMascota.setBounds(12, 288, 538, 41);
				panel.add(panelMascota);
				{
					JLabel lblMascota = new JLabel("Nombre de la Mascota:");
					lblMascota.setBounds(12, 13, 158, 16);
					panelMascota.add(lblMascota);
				}
				{
					txtMascota = new JTextField();
					txtMascota.setBounds(163, 10, 363, 22);
					panelMascota.add(txtMascota);
					txtMascota.setColumns(10);
				}
			}
			{
				JPanel panelFundacion = new JPanel();
				panelFundacion.setBackground(new Color(169, 169, 169));
				panelFundacion.setLayout(null);
				panelFundacion.setBounds(339, 83, 211, 41);
				panel.add(panelFundacion);
				{
					JLabel lblFundacion = new JLabel("Fundaci\u00F3n:");
					lblFundacion.setBounds(12, 13, 81, 16);
					panelFundacion.add(lblFundacion);
				}
				{
					spnFundacion = new JSpinner(new SpinnerDateModel());
					spnFundacion.setBounds(80, 10, 124, 22);
					panelFundacion.add(spnFundacion);
					JSpinner.DateEditor de_spnFundacion = new JSpinner.DateEditor(spnFundacion, "dd-MM-yyyy");
					spnFundacion.setEditor(de_spnFundacion);
					spnFundacion.setPreferredSize(new Dimension(150, 25));
					spnFundacion.setValue(new Date());
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
						try {
				            if (txtNombre.getText().isEmpty() || 
				                txtCiudad.getText().isEmpty() || 
				                txtEntrenador.getText().isEmpty()) {
				                throw new IllegalArgumentException("Complete los campos obligatorios (*)");
				            }

				            String id = txtID.getText();
				            String nombre = txtNombre.getText();
				            String ciudad = txtCiudad.getText();
				            String entrenador = txtEntrenador.getText();
				            String mascota = txtMascota.getText();

				            java.util.Date fechaUtil = (java.util.Date) spnFundacion.getValue();
				            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
				            String fechaFundacion = sdf.format(fechaUtil);

				            Equipo nuevoEquipo = new Equipo(
				                    id,
				                    fechaFundacion,
				                    null,
				                    mascota,     
				                    nombre,       
				                    ciudad,
				                    entrenador     
				                );

				            controladora.agregarEquipo(nuevoEquipo);
				            
				            JOptionPane.showMessageDialog(RegEquipo.this, 
				                "Equipo registrado exitosamente", 
				                "Éxito", 
				                JOptionPane.INFORMATION_MESSAGE);
				            
				            dispose();
				            
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(RegEquipo.this, 
				                "Error: " + ex.getMessage(), 
				                "Error", 
				                JOptionPane.ERROR_MESSAGE);
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
	
	private String generarNuevoId() {
	    if (controladora == null) {
	        controladora = ControladoraLiga.getInstance();
	    }
	    int numEqui = controladora.getMisEquipos().size(); 
	    return "EQU-" + (numEqui + 1);
	}
}
