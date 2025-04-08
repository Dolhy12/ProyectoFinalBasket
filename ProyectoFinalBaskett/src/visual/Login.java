package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JDialog {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private boolean autenticado = false;
    private String rol = "";

    public Login() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);
        
        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);
        
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(this::validarCredenciales);
        panel.add(btnLogin);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> System.exit(0));
        panel.add(btnCancelar);
        
        add(panel);
    }

    private void validarCredenciales(ActionEvent e) {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.equals("admin") && contrasena.equals("admin")) {
            rol = "Administrador";
            autenticado = true;
            dispose();
        } else if (usuario.equals("anotador") && contrasena.equals("anotador")) {
            rol = "Anotador";
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }
    }

    public boolean estaAutenticado() {
        return autenticado;
    }

    public String getRol() {
        return rol;
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
        
        if (login.estaAutenticado()) {
            PrincipalVisual principal = new PrincipalVisual(login.getRol());
            principal.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}