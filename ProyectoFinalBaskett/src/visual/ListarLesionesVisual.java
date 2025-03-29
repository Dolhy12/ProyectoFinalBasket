package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.ControladoraLiga;
import logico.Equipo;
import logico.Jugador;
import logico.Lesion;

public class ListarLesionesVisual extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> cmbJugadores;
    private JComboBox<String> cmbEquipos;
    private ControladoraLiga controladora;

    public ListarLesionesVisual(ControladoraLiga controladora) {
        this.controladora = controladora;
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControladoraLiga controladora = new ControladoraLiga();
                    ListarLesionesVisual frame = new ListarLesionesVisual(controladora);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        setTitle("Lista de Lesiones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ComboBox para Jugadores
        JLabel lblJugador = new JLabel("Jugador:");
        lblJugador.setFont(new Font("Arial", Font.BOLD, 14));
        lblJugador.setBounds(10, 10, 80, 25);
        contentPane.add(lblJugador);

        cmbJugadores = new JComboBox<>();
        cmbJugadores.addItem("Todos");
        for (Jugador j : controladora.getMisJugadores()) {
            cmbJugadores.addItem(j.getNombre() + " (" + j.getID() + ")");
        }
        cmbJugadores.setBounds(90, 10, 300, 25);
        contentPane.add(cmbJugadores);

        // ComboBox para Equipos
        JLabel lblEquipo = new JLabel("Equipo:");
        lblEquipo.setFont(new Font("Arial", Font.BOLD, 14));
        lblEquipo.setBounds(400, 10, 80, 25);
        contentPane.add(lblEquipo);

        cmbEquipos = new JComboBox<>();
        cmbEquipos.addItem("Todos");
        for (Equipo e : controladora.getMisEquipos()) {
            cmbEquipos.addItem(e.getNombre() + " (" + e.getID() + ")");
        }
        cmbEquipos.setBounds(480, 10, 300, 25);
        contentPane.add(cmbEquipos);

        // Botones
        JButton btnHistorial = new JButton("Ver Historial");
        btnHistorial.setBounds(790, 10, 100, 25);
        contentPane.add(btnHistorial);

        JButton btnLesionados = new JButton("Ver Lesionados");
        btnLesionados.setBounds(900, 10, 100, 25);
        contentPane.add(btnLesionados);

        // Tabla
        String[] columnas = {"Jugador", "Equipo", "Tipo", "Parte del Cuerpo", "Días de Baja", "Tratamiento", "Fecha Lesión", "Duración Estimada", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 960, 400);
        contentPane.add(scrollPane);

        // Cargar todas las lesiones por defecto
        cargarLesiones(null, null);

        // Acción para ver historial de un jugador
        btnHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) cmbJugadores.getSelectedItem();
                if (seleccion != null) {
                    if (seleccion.equals("Todos")) {
                        cargarLesiones(null, null);
                    } else {
                        String idJugador = seleccion.split("\\(")[1].replace(")", "");
                        cargarLesiones(idJugador, null);
                    }
                }
            }
        });

        // Acción para ver lesionados de un equipo
        btnLesionados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) cmbEquipos.getSelectedItem();
                if (seleccion != null) {
                    if (seleccion.equals("Todos")) {
                        cargarLesiones(null, null);
                    } else {
                        String idEquipo = seleccion.split("\\(")[1].replace(")", "");
                        cargarLesiones(null, idEquipo);
                    }
                }
            }
        });
    }

    private void cargarLesiones(String idJugadorFiltro, String idEquipoFiltro) {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0);

        ArrayList<Jugador> jugadores = controladora.getMisJugadores();
        for (Jugador jugador : jugadores) {
            if (idJugadorFiltro != null && !jugador.getID().equals(idJugadorFiltro)) {
                continue;
            }

            ArrayList<Lesion> lesiones = jugador.getLesiones();
            if (!lesiones.isEmpty()) {
                String nombreEquipo = "Sin equipo";
                for (Equipo equipo : controladora.getMisEquipos()) {
                    if (equipo.getJugadores().contains(jugador)) {
                        nombreEquipo = equipo.getNombre();
                        if (idEquipoFiltro != null && !equipo.getID().equals(idEquipoFiltro)) {
                            continue;
                        }
                        break;
                    }
                }

                if (idEquipoFiltro != null && nombreEquipo.equals("Sin equipo")) {
                    continue;
                }

                ArrayList<Lesion> lesionesAFiltrar = idEquipoFiltro != null ? jugador.getLesionesActivas() : lesiones;
                if (lesionesAFiltrar.isEmpty() && idEquipoFiltro != null) {
                    continue;
                }

                for (Lesion lesion : lesionesAFiltrar) {
                    Object[] fila = {
                        jugador.getNombre(),
                        nombreEquipo,
                        lesion.getTipo(),
                        lesion.getParteCuerpo(),
                        lesion.getDiasBajaEstimado(),
                        lesion.getTratamiento(),
                        lesion.getFechaLesion(),
                        lesion.getDuracionEstimada(),
                        lesion.getEstado()
                    };
                    modelo.addRow(fila);
                }
            }
        }
    }
}