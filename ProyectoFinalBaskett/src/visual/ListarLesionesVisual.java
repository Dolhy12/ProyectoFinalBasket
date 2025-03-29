package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
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
        setTitle("Lista de Todas las Lesiones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 400); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(255, 147, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] columnas = {"Jugador", "Equipo", "Tipo", "Parte del Cuerpo", "Días de Baja", "Tratamiento", "Fecha Lesión", "Duración Estimada", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 960, 340); 
        contentPane.add(scrollPane);

        cargarLesiones();
    }

    private void cargarLesiones() {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); 

        ArrayList<Jugador> jugadores = controladora.getMisJugadores();
        for (Jugador jugador : jugadores) {
            ArrayList<Lesion> lesiones = jugador.getLesiones();
            if (!lesiones.isEmpty()) { 
                String nombreEquipo = "Sin equipo"; 
                for (Equipo equipo : controladora.getMisEquipos()) {
                    if (equipo.getJugadores().contains(jugador)) { // Cambié a getJugadores()
                        nombreEquipo = equipo.getNombre();
                        break;
                    }
                }
                for (Lesion lesion : lesiones) {
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