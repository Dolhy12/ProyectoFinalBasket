package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {
    private JComboBox<String> cmbRol;
    private JPasswordField txtContrasena;
    private boolean autenticado = false;
    private String rol = "";

    public Login() {
    	Login();
    }

    private void Login() {
        setTitle("Inicio de Sesión");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setModal(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 25));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(255, 147, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(2, 1, 10, 25));
        panelCampos.setBackground(new Color(240, 240, 240));

        JPanel panelRol = new JPanel(new BorderLayout(10, 0));
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 16));
        
        cmbRol = new JComboBox<>();
        cmbRol.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selected = (String) cmbRol.getSelectedItem();
                if ("Usuario".equals(selected)) {
                    txtContrasena.setEnabled(false);
                    txtContrasena.setText("Puede Ingresar");
                } else {
                    txtContrasena.setEnabled(true);
                }
        	}
        });
        cmbRol.setModel(new DefaultComboBoxModel<>(new String[]{"Administrador", "Anotador", "Usuario"}));
        cmbRol.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbRol.setPreferredSize(new Dimension(350, 40)); 
        panelRol.add(lblRol, BorderLayout.WEST);
        panelRol.add(cmbRol, BorderLayout.CENTER);

        JPanel panelContrasena = new JPanel(new BorderLayout(10, 0));
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 16));
        
        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(350, 40));
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        panelContrasena.add(lblContrasena, BorderLayout.WEST);
        panelContrasena.add(txtContrasena, BorderLayout.CENTER);

        panelCampos.add(panelRol);
        panelCampos.add(panelContrasena);
        mainPanel.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panelBotones.setBackground(new Color(240, 240, 240));
        
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(255, 147, 30));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(140, 45));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(this::validarCredenciales);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancelar.setBackground(new Color(204, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setPreferredSize(new Dimension(140, 45));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(8, 25, 8, 25));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> System.exit(0));
        
        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void validarCredenciales(ActionEvent e) {
        String selectedRol = (String) cmbRol.getSelectedItem();
        String contrasena = new String(txtContrasena.getPassword());

        if ("Usuario".equals(selectedRol)) {
            rol = selectedRol;
            autenticado = true;
            dispose();
        } else if (("Administrador".equals(selectedRol) && "admin".equals(contrasena)) ||
            ("Anotador".equals(selectedRol) && "anotador".equals(contrasena))) {
            rol = selectedRol;
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Credenciales inválidas para el rol seleccionado",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean estaAutenticado() {
        return autenticado;
    }

    public String getRol() {
        return rol;
    }

    public static void main(String[] args) {
        Login dialog = new Login();
        dialog.setVisible(true);
        
        if (dialog.estaAutenticado()) {
            System.out.println("Autenticación exitosa como: " + dialog.getRol());
        } else {
            System.out.println("La autenticación falló");
        }
    }
}