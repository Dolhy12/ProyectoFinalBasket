package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.ControladoraLiga;
import logico.Jugador;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class RegJugador extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNombre;
	private JTextField txtNacionalidad;
	private ControladoraLiga controladora;
	private JComboBox cbxPosicion;
	private JSpinner spnFecha;
	private JSpinner spnPeso;
	private JSpinner spnAltura;
	private JSpinner spnNumero;
	private JTextField textField;
	
	
	public RegJugador(ControladoraLiga controladora) {
	    this();
	    this.controladora = controladora;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegJugador dialog = new RegJugador();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegJugador() {
		setType(Type.POPUP);
		setTitle("Registrar Jugador");
		setBounds(100, 100, 520, 431);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(255, 147, 30));
			panel.setBorder(new EmptyBorder(0, 0, 0, 0));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JPanel panelID = new JPanel();
				panelID.setBackground(new Color(169, 169, 169));
				panelID.setBounds(12, 13, 221, 42);
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
					txtID.setBounds(37, 10, 166, 22);
					panelID.add(txtID);
					txtID.setText(generarNuevoId());
					txtID.setColumns(10);
				}
			}
			{
				JPanel panelNombre = new JPanel();
				panelNombre.setBackground(new Color(169, 169, 169));
				panelNombre.setBounds(12, 68, 474, 42);
				panel.add(panelNombre);
				panelNombre.setLayout(null);
				{
					JLabel lblNombre = new JLabel("Nombre:");
					lblNombre.setBounds(12, 13, 56, 16);
					panelNombre.add(lblNombre);
				}
				{
					txtNombre = new JTextField();
					txtNombre.setBounds(67, 10, 395, 22);
					panelNombre.add(txtNombre);
					txtNombre.setColumns(10);
				}
			}
			{
				JPanel panelEdad = new JPanel();
				panelEdad.setBackground(new Color(169, 169, 169));
				panelEdad.setBounds(284, 123, 202, 42);
				panel.add(panelEdad);
				panelEdad.setLayout(null);
				{
					JLabel lblEdad = new JLabel("Edad:");
					lblEdad.setBounds(12, 13, 56, 16);
					panelEdad.add(lblEdad);
				}
				
				textField = new JTextField();
				textField.setEditable(false);
				textField.setBounds(68, 10, 122, 22);
				panelEdad.add(textField);
				textField.setColumns(10);
			}
			{
				JPanel panelPosicion = new JPanel();
				panelPosicion.setBackground(new Color(169, 169, 169));
				panelPosicion.setBounds(12, 178, 193, 42);
				panel.add(panelPosicion);
				panelPosicion.setLayout(null);
				{
					JLabel lblPosicion = new JLabel("Posici\u00F3n:");
					lblPosicion.setBounds(12, 13, 56, 16);
					panelPosicion.add(lblPosicion);
				}
				{
					cbxPosicion = new JComboBox();
					cbxPosicion.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>", "Base", "Escolta", "Alero", "Ala-P\u00EDvot", "P\u00EDvot"}));
					cbxPosicion.setBounds(69, 10, 112, 22);
					panelPosicion.add(cbxPosicion);
				}
			}
			{
				JPanel panelNacionalidad = new JPanel();
				panelNacionalidad.setBackground(new Color(169, 169, 169));
				panelNacionalidad.setBounds(217, 178, 269, 42);
				panel.add(panelNacionalidad);
				panelNacionalidad.setLayout(null);
				{
					JLabel lblNacionalidad = new JLabel("Nacionalidad:");
					lblNacionalidad.setBounds(12, 13, 88, 16);
					panelNacionalidad.add(lblNacionalidad);
				}
				{
					txtNacionalidad = new JTextField();
					txtNacionalidad.setBounds(100, 10, 157, 22);
					panelNacionalidad.add(txtNacionalidad);
					txtNacionalidad.setColumns(10);
				}
			}
			{
				Calendar calendar = Calendar.getInstance();
		        calendar.add(Calendar.YEAR, -18);
		        Date fechaInicial = calendar.getTime();
		        
				JPanel panelFecha = new JPanel();
				panelFecha.setBackground(new Color(169, 169, 169));
				panelFecha.setBounds(12, 123, 254, 42);
				panel.add(panelFecha);
				panelFecha.setLayout(null);
				{
					JLabel lblFecha = new JLabel("Fecha:");
					lblFecha.setBounds(12, 13, 56, 16);
					panelFecha.add(lblFecha);
				}
				{
					
					spnFecha = new JSpinner(new SpinnerDateModel());
					spnFecha.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							actualizarEdad();
						}
					});
					spnFecha.setBounds(74, 13, 170, 22);
					panelFecha.add(spnFecha);
					JSpinner.DateEditor de_spnFecha = new JSpinner.DateEditor(spnFecha, "dd-MM-yyyy");
					spnFecha.setEditor(de_spnFecha);
					spnFecha.setPreferredSize(new Dimension(150, 25));
					spnFecha.setValue(fechaInicial);
				}
			}
			{
				JPanel panelPeso = new JPanel();
				panelPeso.setBackground(new Color(169, 169, 169));
				panelPeso.setBounds(12, 233, 221, 42);
				panel.add(panelPeso);
				panelPeso.setLayout(null);
				{
					JLabel lblPeso = new JLabel("Peso (KG):");
					lblPeso.setBounds(12, 13, 75, 16);
					panelPeso.add(lblPeso);
				}
				{
					spnPeso = new JSpinner();
					spnPeso.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
					spnPeso.setBounds(89, 10, 120, 22);
					panelPeso.add(spnPeso);
				}
			}
			{
				JPanel panelAltura = new JPanel();
				panelAltura.setBackground(new Color(169, 169, 169));
				panelAltura.setBounds(271, 233, 215, 42);
				panel.add(panelAltura);
				panelAltura.setLayout(null);
				{
					JLabel lblAltura = new JLabel("Altura (m):");
					lblAltura.setBounds(12, 13, 74, 16);
					panelAltura.add(lblAltura);
				}
				{
					spnAltura = new JSpinner();
					spnAltura.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
					spnAltura.setBounds(89, 10, 114, 22);
					panelAltura.add(spnAltura);
				}
			}
			{
				JPanel panelNumero = new JPanel();
				panelNumero.setBackground(new Color(169, 169, 169));
				panelNumero.setBounds(12, 288, 178, 42);
				panel.add(panelNumero);
				panelNumero.setLayout(null);
				{
					JLabel lblNumero = new JLabel("N\u00FAmero:");
					lblNumero.setBounds(12, 13, 56, 16);
					panelNumero.add(lblNumero);
				}
				{
					spnNumero = new JSpinner();
					spnNumero.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
					spnNumero.setBounds(68, 10, 100, 22);
					panelNumero.add(spnNumero);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        try {
				            if (txtNombre.getText().isEmpty() || 
				                cbxPosicion.getSelectedIndex() == 0 || 
				                txtNacionalidad.getText().isEmpty()) {
				                throw new IllegalArgumentException("Complete los campos obligatorios (*)");
				            }

				            String id = txtID.getText();
				            String nombre = txtNombre.getText();
				            String posicion = cbxPosicion.getSelectedItem().toString();
				            String nacionalidad = txtNacionalidad.getText();
				            Date fechaNac = (Date) spnFecha.getValue();
				            float peso = ((Number) spnPeso.getValue()).floatValue();
				            float altura = ((Number) spnAltura.getValue()).floatValue();
				            int numero = (int) spnNumero.getValue();
				            int edad = Integer.parseInt(textField.getText());
				            
				            if (peso <= 0 || altura <= 0) {
				                throw new IllegalArgumentException("Peso y altura deben ser mayores a 0");
				            }

				            Jugador jugador = new Jugador(
				                id, 
				                nombre, 
				                edad, 
				                posicion, 
				                nacionalidad, 
				                fechaNac, 
				                peso, 
				                altura, 
				                numero
				            );

				            controladora.agregarJugador(jugador);
				            
				            JOptionPane.showMessageDialog(RegJugador.this, 
				                "Jugador registrado exitosamente", 
				                "�xito", 
				                JOptionPane.INFORMATION_MESSAGE);
				            
				            dispose();
				            
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(RegJugador.this, 
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
				JButton btnCancerlar = new JButton("Cancelar");
				btnCancerlar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancerlar.setActionCommand("Cancel");
				buttonPane.add(btnCancerlar);
			}
		}
	}
	

	private void actualizarEdad() {
		Date birthDate = (Date) spnFecha.getValue();
        int age = calcularEdad(birthDate);
        textField.setText(Integer.toString(age));
	}
	
	private int calcularEdad(Date birthDate) {
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);
        Calendar nowCal = Calendar.getInstance();

        int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

        if (nowCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
	
	private String generarNuevoId() {
		if (controladora == null) {
	        controladora = ControladoraLiga.getInstance();
	    }
	    int numJugador = controladora.getMisJugadores().size(); 
	    return "JUG-" + (numJugador + 1);
	}
}