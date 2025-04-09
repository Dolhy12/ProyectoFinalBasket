package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

public class Login extends JDialog {
    private JComboBox<String> cmbRol;
    private JPasswordField txtContrasena;
    private boolean autenticado = false;
    private String rol = "";

    public Login() {
        setTitle("Inicio de Sesión");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setModal(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(255, 147, 30), 2));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 147, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        JPanel panelCampos = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
            }
        };
        panelCampos.setOpaque(false);
        panelCampos.setLayout(new GridBagLayout());
        GridBagConstraints gbcCampos = new GridBagConstraints();
        gbcCampos.insets = new Insets(5, 10, 5, 10);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 14));
        lblRol.setForeground(new Color(51, 51, 51));
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 0;
        panelCampos.add(lblRol, gbcCampos);

        cmbRol = new JComboBox<>(new String[]{"Administrador", "Anotador"});
        cmbRol.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbRol.setPreferredSize(new Dimension(200, 30));
        gbcCampos.gridx = 1;
        panelCampos.add(cmbRol, gbcCampos);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        lblContrasena.setForeground(new Color(51, 51, 51));
        gbcCampos.gridx = 0;
        gbcCampos.gridy = 1;
        panelCampos.add(lblContrasena, gbcCampos);

        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(200, 30));
        gbcCampos.gridx = 1;
        panelCampos.add(txtContrasena, gbcCampos);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(panelCampos, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(255, 147, 30));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(this::validarCredenciales);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(204, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> System.exit(0));

        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel);
    }

    private void validarCredenciales(ActionEvent e) {
        String selectedRol = (String) cmbRol.getSelectedItem();
        String contrasenaIngresada = new String(txtContrasena.getPassword());

        if (selectedRol.equals("Administrador") && contrasenaIngresada.equals("admin")) {
            rol = "Administrador";
            autenticado = true;
            dispose();
        } else if (selectedRol.equals("Anotador") && contrasenaIngresada.equals("anotador")) {
            rol = "Anotador";
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas para el rol seleccionado",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
            System.out.println("Usuario autenticado como: " + login.getRol());
        } else {
            System.exit(0);
        }
    }
}