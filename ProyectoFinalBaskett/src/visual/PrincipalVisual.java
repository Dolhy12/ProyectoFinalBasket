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

public class PrincipalVisual extends JFrame {

    private JPanel contentPane;
    ControladoraLiga controladora = new ControladoraLiga();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PrincipalVisual frame = new PrincipalVisual();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PrincipalVisual() {
        setTitle("Serie Nacional de Basketball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        Dimension dim = getToolkit().getScreenSize();
        setSize(dim.width, dim.height - 40);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        menuBar.setBackground(new Color(70, 70, 70));
        
        JMenu mnCalendario = new JMenu("Calendario");
        menuBar.add(mnCalendario);

        JMenuItem mntmVerCalendario = new JMenuItem("Ver Calendario");
        mnCalendario.add(mntmVerCalendario);

        JMenuItem mntmAgregarJuego = new JMenuItem("Agregar Juego");
        mnCalendario.add(mntmAgregarJuego);

        JMenu mnEquipos = new JMenu("Equipos");
        menuBar.add(mnEquipos);

        JMenuItem mntmRegistrarEquipo = new JMenuItem("Registrar Equipo");
        mnEquipos.add(mntmRegistrarEquipo);

        JMenuItem mntmListarEquipos = new JMenuItem("Listar Equipos");
        mnEquipos.add(mntmListarEquipos);

        JMenu mnJugadores = new JMenu("Jugadores");
        menuBar.add(mnJugadores);

        JMenuItem mntmRegistrarJugador = new JMenuItem("Registrar Jugador");
        mnJugadores.add(mntmRegistrarJugador);

        JMenuItem mntmListarJugadores = new JMenuItem("Listar Jugadores");
        mnJugadores.add(mntmListarJugadores);

        JMenu mnEstadisticas = new JMenu("Estadísticas");
        menuBar.add(mnEstadisticas);

        JMenuItem mntmEstadisticasEquipos = new JMenuItem("Estadísticas de Equipos");
        mnEstadisticas.add(mntmEstadisticasEquipos);

        JMenuItem mntmEstadisticasJugadores = new JMenuItem("Estadísticas de Jugadores");
        mnEstadisticas.add(mntmEstadisticasJugadores);

        JMenu mnLesiones = new JMenu("Lesiones");
        menuBar.add(mnLesiones);

        JMenuItem mntmRegistrarLesion = new JMenuItem("Registrar Lesión");
        mnLesiones.add(mntmRegistrarLesion);

        JMenuItem mntmListarLesiones = new JMenuItem("Listar Lesiones");
        mnLesiones.add(mntmListarLesiones);
        
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            menu.setOpaque(true);
            menu.setBackground(new Color(70, 70, 70));
            menu.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnCalendario.getItemCount(); i++) {
            JMenuItem item = mnCalendario.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnEquipos.getItemCount(); i++) {
            JMenuItem item = mnEquipos.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnEquipos.getItemCount(); i++) {
            JMenuItem item = mnEquipos.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnJugadores.getItemCount(); i++) {
            JMenuItem item = mnJugadores.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnEstadisticas.getItemCount(); i++) {
            JMenuItem item = mnEstadisticas.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
        for (int i = 0; i < mnLesiones.getItemCount(); i++) {
            JMenuItem item = mnLesiones.getItem(i);
            item.setOpaque(true);
            item.setBackground(new Color(90, 90, 90)); 
            item.setForeground(Color.WHITE);
        }
        
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

        mntmRegistrarJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jugadorvisual ventanaJugador = new jugadorvisual(controladora);
                ventanaJugador.setVisible(true);
            }
        });

        mntmRegistrarLesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idJugador = JOptionPane.showInputDialog("Ingrese el ID del jugador:");
                if (idJugador != null && !idJugador.trim().isEmpty()) {
                    if (controladora.existeJugador(idJugador)) {
                        lesionvisual ventanaLesion = new lesionvisual(controladora, idJugador);
                        ventanaLesion.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
                    }
                }
            }
        });

        mntmRegistrarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                equipovisual ventanaEquipo = new equipovisual(controladora);
                ventanaEquipo.setVisible(true);
            }
        });

        mntmListarEquipos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarEquiposVisual ventanaLista = new ListarEquiposVisual(controladora);
                ventanaLista.setVisible(true);
            }
        });

        mntmListarJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarJugadoresVisual ventanaLista = new ListarJugadoresVisual(controladora);
                ventanaLista.setVisible(true);
            }
        });

        mntmListarLesiones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarLesionesVisual ventanaLista = new ListarLesionesVisual(controladora);
                ventanaLista.setVisible(true);
            }
        });
    }
}