package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane; 
import logico.ControladoraLiga;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class PrincipalVisual extends JFrame {

    private JPanel contentPane;
    ControladoraLiga controladora = ControladoraLiga.getInstance();
    private String rolUsuario;
    private JMenu mnCalendario;
    private JMenuItem mntmVerCalendario;
    private JMenuItem mntmAgregarJuego;
    private JMenu mnEquipos;
    private JMenu mnJugadores;
    private JMenu mnLesiones;
    private JMenu mnRespaldo;

    public static void main(String[] args) {        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login login = new Login();
                    login.setVisible(true);
                    
                    if (login.estaAutenticado()) {
                        PrincipalVisual frame = new PrincipalVisual(login.getRol());
                        frame.setVisible(true);
                    } else {
                        System.exit(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PrincipalVisual(String rol) throws IOException {
        this.rolUsuario = rol;
        setTitle("Serie Nacional de Basketball - [" + rolUsuario + "]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        Dimension dim = getToolkit().getScreenSize();
        setSize(dim.width, dim.height - 40);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        menuBar.setBackground(Color.WHITE);
        
        mnCalendario = new JMenu("Calendario");
        menuBar.add(mnCalendario);

        mntmVerCalendario = new JMenuItem("Ver Calendario");
        mntmVerCalendario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VerCalendario ventanaVerCalendario = new VerCalendario(controladora, rolUsuario);
                ventanaVerCalendario.setVisible(true);
            }
        });
        mnCalendario.add(mntmVerCalendario);

        mntmAgregarJuego = new JMenuItem("Agregar Juego");
        mntmAgregarJuego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegJuego ventanaJuego = new RegJuego();
                ventanaJuego.setVisible(true);
            }
        });
        mnCalendario.add(mntmAgregarJuego);
        mnEquipos = new JMenu("Equipos");
        menuBar.add(mnEquipos);

        JMenuItem mntmRegistrarEquipo = new JMenuItem("Registrar Equipo");
        mntmRegistrarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegEquipo ventanaRegEquipo = new RegEquipo();
                ventanaRegEquipo.setVisible(true);
            }
        });
        mnEquipos.add(mntmRegistrarEquipo);

        JMenuItem mntmListarEquipos = new JMenuItem("Listar Equipos");
        mntmListarEquipos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarEquipos ventanaListEquipos = new ListarEquipos(controladora);
                ventanaListEquipos.setVisible(true);
            }
        });
        
        JMenuItem mntmGestionarEquipo = new JMenuItem("Gestionar Equipo");
        mntmGestionarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestionarEquipo ventana = new GestionarEquipo(controladora);
                ventana.setVisible(true);
            }
        });
        mnEquipos.add(mntmGestionarEquipo);
        mnEquipos.add(mntmListarEquipos);
        mnJugadores = new JMenu("Jugadores");
        menuBar.add(mnJugadores);

        JMenuItem mntmRegistrarJugador = new JMenuItem("Registrar Jugador");
        mntmRegistrarJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegJugador ventanaRegJugador = new RegJugador();
                ventanaRegJugador.setVisible(true);
            }
        });
        mnJugadores.add(mntmRegistrarJugador);

        JMenuItem mntmListarJugadores = new JMenuItem("Listar Jugadores");
        mntmListarJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarJugadores ventanaListJugadores = new ListarJugadores(controladora);
                ventanaListJugadores.setVisible(true);
            }
        });
        mnJugadores.add(mntmListarJugadores);
        mnLesiones = new JMenu("Lesiones");
        menuBar.add(mnLesiones);

        JMenuItem mntmRegistrarLesion = new JMenuItem("Registrar Lesión");
        mntmRegistrarLesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegLesion ventanaLesion = new RegLesion(controladora);
                ventanaLesion.setVisible(true);
            }
        });
        mnLesiones.add(mntmRegistrarLesion);

        JMenuItem mntmListarLesiones = new JMenuItem("Listar Lesiones");
        mntmListarLesiones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarLesiones ventanaListLesiones = new ListarLesiones(controladora);
                ventanaListLesiones.setVisible(true);
            }
        });
        mnLesiones.add(mntmListarLesiones);
        
        mnRespaldo = new JMenu("Respaldo");
        menuBar.add(mnRespaldo);
        
        JMenuItem mntmRespaldar = new JMenuItem("Realizar respaldo");
        mntmRespaldar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarRespaldo();
            }
        });
        mnRespaldo.add(mntmRespaldar);
        configurarAccesoSegunRol();
        
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        getContentPane().setBackground(Color.WHITE);
        
        ImageIcon imagenFondo = new ImageIcon("Imagenes/Logo.png");
        JLabel fondoLabel = new JLabel(imagenFondo);
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(new Color(255, 147, 30));
        panelImagen.add(fondoLabel, BorderLayout.CENTER);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(new Color(255, 147, 30));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel lblRol = new JLabel("Rol actual: " + rolUsuario);
        lblRol.setFont(new Font("Arial", Font.BOLD, 12));
        lblRol.setForeground(new Color(0, 0, 0));
        statusPanel.add(lblRol);
        
        contentPane.add(panelImagen, BorderLayout.CENTER);
        contentPane.add(statusPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                fondoLabel.setSize(contentPane.getWidth(), contentPane.getHeight());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controladora.guardarDatos();
                System.exit(0); 
            }
        });
    }

    private void configurarAccesoSegunRol() {
        if (rolUsuario.equals("Anotador")) {
            mnEquipos.setEnabled(false);
            mnJugadores.setEnabled(false);
            mnLesiones.setEnabled(false);
            mntmAgregarJuego.setEnabled(false);
        }
    }
    
    private void generarRespaldo() {
        new Thread(() -> {
            File f = new File("datos.dat");
            
            try (Socket socket = new Socket("127.0.0.1", 7000);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 FileInputStream fis = new FileInputStream(f)) {
                
                out.writeUTF(f.getName());
                out.writeLong(f.length());
                
                byte[] buffer = new byte[4096];
                int leidos;
                while ((leidos = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, leidos);
                }
                
                JOptionPane.showMessageDialog(this, "Respaldo completo realizado");
                
            } catch (ConnectException ce) {
                JOptionPane.showMessageDialog(this, "Servidor de respaldo no disponible", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error en respaldo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }
}