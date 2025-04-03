package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulacionDeJuego extends JDialog {
    private JTable tblLocal, tblVisitante;
    private JButton btnFinalizar;
    private ControladoraLiga controladora;
    private Juego juego;
    private Resultado resultado;

    public SimulacionDeJuego(ControladoraLiga controladora, Juego juego) {
        this.controladora = controladora;
        this.juego = juego;
        this.resultado = new Resultado(0, 0);
        
        initComponents();
        cargarJugadoresDisponibles();
    }

    private void initComponents() {
        setTitle("Simulación: " + juego.getEquipoLocal().getNombre() + " vs " + juego.getEquipoVisitante().getNombre());
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panelLocal = crearPanelEquipo(juego.getEquipoLocal(), true);
        JPanel panelVisitante = crearPanelEquipo(juego.getEquipoVisitante(), false);
        
        tabbedPane.addTab(juego.getEquipoLocal().getNombre(), panelLocal);
        tabbedPane.addTab(juego.getEquipoVisitante().getNombre(), panelVisitante);

        add(tabbedPane, BorderLayout.CENTER);
        
        btnFinalizar = new JButton("Finalizar Juego");
        btnFinalizar.addActionListener(e -> guardarResultado());
        
        JPanel panelSur = new JPanel();
        panelSur.add(btnFinalizar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private JPanel crearPanelEquipo(Equipo equipo, boolean esLocal) {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Nombre", "Posición", "Número"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla;
		if (esLocal)
			tabla = tblLocal = new JTable(model);
		else
			tabla = tblVisitante = new JTable(model);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    Jugador jugador = obtenerJugadorDesdeTabla(tabla, fila);
                    mostrarDialogoEstadisticas(jugador, esLocal);
                }
            }
        });
        
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void cargarJugadoresDisponibles() {
        cargarJugadoresEnTabla(tblLocal, juego.getEquipoLocal());
        cargarJugadoresEnTabla(tblVisitante, juego.getEquipoVisitante());
    }

    private void cargarJugadoresEnTabla(JTable tabla, Equipo equipo) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        
        for (Jugador jugador : equipo.getJugadores()) {
            if (jugador.getLesionesActivas().isEmpty()) {
                model.addRow(new Object[]{
                    jugador.getNombre(),
                    jugador.getPosicion(),
                    jugador.getNumero()
                });
            }
        }
    }

    private Jugador obtenerJugadorDesdeTabla(JTable tabla, int fila) {
        String nombre = (String) tabla.getValueAt(fila, 0);
        for (Jugador j : juego.getEquipoLocal().getJugadores()) {
            if (j.getNombre().equals(nombre)) return j;
        }
        for (Jugador j : juego.getEquipoVisitante().getJugadores()) {
            if (j.getNombre().equals(nombre)) return j;
        }
        return null;
    }

    private void mostrarDialogoEstadisticas(Jugador jugador, boolean esLocal) {
        JDialog dialog = new JDialog(this, "Estadísticas de " + jugador.getNombre(), true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));
        
        JSpinner spnPuntos = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JSpinner spnRebotes = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner spnAsistencias = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        JSpinner spnRobos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spnBloqueos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

        dialog.add(new JLabel("Puntos:"));
        dialog.add(spnPuntos);
        dialog.add(new JLabel("Rebotes:"));
        dialog.add(spnRebotes);
        dialog.add(new JLabel("Asistencias:"));
        dialog.add(spnAsistencias);
        dialog.add(new JLabel("Robos:"));
        dialog.add(spnRobos);
        dialog.add(new JLabel("Bloqueos:"));
        dialog.add(spnBloqueos);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
        	int[] stats = {
        	        (int) spnPuntos.getValue(),
        	        (int) spnRebotes.getValue(),
        	        (int) spnAsistencias.getValue(),
        	        (int) spnRobos.getValue(),
        	        (int) spnBloqueos.getValue()
        	    };
        	    
        	    if (esLocal) {
        	        resultado.agregarEstadisticaLocal(jugador, stats[0], stats[1], stats[2], stats[3], stats[4]);
        	    } else {
        	        resultado.agregarEstadisticaVisitante(jugador, stats[0], stats[1], stats[2], stats[3], stats[4]);
        	    }
            
            dialog.dispose();
        });

        dialog.add(btnGuardar);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void guardarResultado() {
        int puntosLocal = calcularPuntosTotales(true);
        int puntosVisitante = calcularPuntosTotales(false);
        
        resultado.setPuntosLocal(puntosLocal);
        resultado.setPuntosVisitante(puntosVisitante);
        
        controladora.actualizarResultadoJuego(juego.getID(), resultado);
        JOptionPane.showMessageDialog(this, "Resultado guardado exitosamente!");
        dispose();
    }

    private int calcularPuntosTotales(boolean esLocal) {
        int total = 0;
        ArrayList<int[]> stats = esLocal ? resultado.getStatsLocales() : resultado.getStatsVisitantes();
        for (int[] stat : stats) {
            total += stat[0];
        }
        return total;
    }
}