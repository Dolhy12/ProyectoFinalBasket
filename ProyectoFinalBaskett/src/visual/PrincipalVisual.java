package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane; 
import javax.swing.border.BevelBorder;
import logico.ControladoraLiga; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PrincipalVisual extends JFrame {

    private JPanel contentPane;
    ControladoraLiga controladora = ControladoraLiga.getInstance();
    private String rolUsuario;
    private JMenu mnCalendario;
    private JMenuItem mntmVerCalendario;
    private JMenuItem mntmAgregarJuego;
    private JMenu mnEquipos;
    private JMenu mnJugadores;
    private JMenu mnEstadisticas;
    private JMenu mnLesiones;

    /**
     * Launch the application.
     */
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

    /**
     * Create the frame 
     */
    public PrincipalVisual(String rol) {
        this.rolUsuario = rol;
        
        setTitle("Serie Nacional de Basketball - [" + rolUsuario + "]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        Dimension dim = getToolkit().getScreenSize();
        setSize(dim.width, dim.height - 40);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        menuBar.setBackground(new Color(70, 70, 70));
        
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
        mnEstadisticas = new JMenu("Estadísticas");
        menuBar.add(mnEstadisticas);

        JMenuItem mntmEstadisticasEquipos = new JMenuItem("Estadísticas de Equipos");
        mntmEstadisticasEquipos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarEstadisticasEquipo ventanaEst = new ListarEstadisticasEquipo(controladora);
                ventanaEst.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmEstadisticasEquipos);

        JMenuItem mntmEstadisticasJugadores = new JMenuItem("Estadísticas de Jugadores");
        mntmEstadisticasJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarJugadores ventanaListJugadores = new ListarJugadores(controladora);
                ventanaListJugadores.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmEstadisticasJugadores);
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
        configurarColoresMenu(menuBar);
        configurarAccesoSegunRol();
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        getContentPane().setBackground(new Color(70, 70, 70));

        ImageIcon imagenFondo = new ImageIcon("Imagenes/Logo.png");
        JLabel fondoLabel = new JLabel(imagenFondo);
        fondoLabel.setBounds(0, 0, getWidth(), getHeight());

        JPanel panelFondo = new JPanel(null);
        panelFondo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelFondo.setBackground(new Color(255, 147, 30));
        panelFondo.add(fondoLabel);
        contentPane.add(panelFondo, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                fondoLabel.setSize(getWidth(), getHeight());
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
            mnEstadisticas.setEnabled(false);
            mnLesiones.setEnabled(false);
            mntmAgregarJuego.setEnabled(false);
        }
    }
    
    private void configurarColoresMenu(JMenuBar menuBar) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            menu.setOpaque(true);
            menu.setBackground(new Color(70, 70, 70));
            menu.setForeground(Color.WHITE);
        }
        
        configurarColorMenuItems(mnCalendario);
        configurarColorMenuItems(mnEquipos);
        configurarColorMenuItems(mnJugadores);
        configurarColorMenuItems(mnEstadisticas);
        configurarColorMenuItems(mnLesiones);
    }
    
    private void configurarColorMenuItems(JMenu menu) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            if (item != null) {
                item.setOpaque(true);
                item.setBackground(new Color(90, 90, 90)); 
                item.setForeground(Color.WHITE);
            }
        }
    }
}