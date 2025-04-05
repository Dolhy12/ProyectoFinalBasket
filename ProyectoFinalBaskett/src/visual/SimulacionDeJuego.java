package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SimulacionDeJuego extends JDialog {
    
    private ControladoraLiga controladora;
    private Juego juego;
    private Resultado resultado;

    public SimulacionDeJuego(ControladoraLiga controladora, Juego juego) {
        this.controladora = controladora;
        this.juego = juego;
        this.resultado = new Resultado(0, 0);
        
        setTitle("Simulación: " + juego.getEquipoLocal().getNombre() + " vs " + juego.getEquipoVisitante().getNombre());
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel panelLocal = new JPanel(new BorderLayout());
        String[] columnas = {"Nombre", "Posición", "Número"};
        DefaultTableModel modelLocal = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable tblLocal = new JTable(modelLocal);
        tblLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        for (Jugador jugador : juego.getEquipoLocal().getJugadores()) {
            if (jugador.getLesionesActivas().isEmpty()) {
                modelLocal.addRow(new Object[]{jugador.getNombre(), jugador.getPosicion(), jugador.getNumero()});
            }
        }
        
        tblLocal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblLocal.getSelectedRow() >= 0) {
                    String nombre = (String) tblLocal.getValueAt(tblLocal.getSelectedRow(), 0);
                    Jugador jugador = controladora.getMisJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombre)).findFirst().orElse(null);
                    mostrarDialogoEstadisticas(jugador, true);
                }
            }
        });
        
        panelLocal.add(new JScrollPane(tblLocal), BorderLayout.CENTER);
        
        JPanel panelVisitante = new JPanel(new BorderLayout());
        DefaultTableModel modelVisitante = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable tblVisitante = new JTable(modelVisitante);
        tblVisitante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        for (Jugador jugador : juego.getEquipoVisitante().getJugadores()) {
            if (jugador.getLesionesActivas().isEmpty()) {
                modelVisitante.addRow(new Object[]{jugador.getNombre(), jugador.getPosicion(), jugador.getNumero()});
            }
        }
        
        tblVisitante.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblVisitante.getSelectedRow() >= 0) {
                    String nombre = (String) tblVisitante.getValueAt(tblVisitante.getSelectedRow(), 0);
                    Jugador jugador = controladora.getMisJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombre)).findFirst().orElse(null);
                    mostrarDialogoEstadisticas(jugador, false);
                }
            }
        });
        
        panelVisitante.add(new JScrollPane(tblVisitante), BorderLayout.CENTER);
        
        tabbedPane.addTab(juego.getEquipoLocal().getNombre(), panelLocal);
        tabbedPane.addTab(juego.getEquipoVisitante().getNombre(), panelVisitante);
        add(tabbedPane, BorderLayout.CENTER);
        
         JButton btnFinalizar = new JButton("Finalizar Juego");
        btnFinalizar.addActionListener(e -> {
            int puntosLocal = resultado.getStatsLocales().stream().mapToInt(stat -> stat[0]).sum();
            int puntosVisitante = resultado.getStatsVisitantes().stream().mapToInt(stat -> stat[0]).sum();
            
            resultado.setPuntosLocal(puntosLocal);
            resultado.setPuntosVisitante(puntosVisitante);
            
            String mensaje = "Resultado final:\n" + juego.getEquipoLocal().getNombre() + ": " + puntosLocal + "\n" +
                             juego.getEquipoVisitante().getNombre() + ": " + puntosVisitante + "\n\n" +
                             (puntosLocal > puntosVisitante ? "Ganador: " + juego.getEquipoLocal().getNombre() :
                             puntosVisitante > puntosLocal ? "Ganador: " + juego.getEquipoVisitante().getNombre() : "¡Empate!");
            
            controladora.actualizarResultadoJuego(juego.getID(), resultado);
            JOptionPane.showMessageDialog(this, mensaje, "Resultado del Juego", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        
        JPanel panelSur = new JPanel();
        panelSur.add(btnFinalizar);
        add(panelSur, BorderLayout.SOUTH);
    }

    private void mostrarDialogoEstadisticas(Jugador jugador, boolean esLocal) {
        JDialog dialog = new JDialog(this, "Estadísticas de " + jugador.getNombre(), true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));
        
        JSpinner spnPuntosNormales = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JSpinner spnPuntosTriples = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner spnTirosLibres = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        JSpinner spnRebotes = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner spnAsistencias = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        JSpinner spnRobos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spnBloqueos = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner spnMinutos = new JSpinner(new SpinnerNumberModel(0, 0, 48, 1));

        dialog.add(new JLabel("Puntos normales:"));
        dialog.add(spnPuntosNormales);
        dialog.add(new JLabel("Puntos triples:"));
        dialog.add(spnPuntosTriples);
        dialog.add(new JLabel("Tiros libres:"));
        dialog.add(spnTirosLibres);
        dialog.add(new JLabel("Rebotes:"));
        dialog.add(spnRebotes);
        dialog.add(new JLabel("Asistencias:"));
        dialog.add(spnAsistencias);
        dialog.add(new JLabel("Robos:"));
        dialog.add(spnRobos);
        dialog.add(new JLabel("Bloqueos:"));
        dialog.add(spnBloqueos);
        dialog.add(new JLabel("Minutos jugados:"));
        dialog.add(spnMinutos);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            int puntosNormales = (int) spnPuntosNormales.getValue();
            int puntosTriples = (int) spnPuntosTriples.getValue();
            int tirosLibres = (int) spnTirosLibres.getValue();
            int rebotes = (int) spnRebotes.getValue();
            int asistencias = (int) spnAsistencias.getValue();
            int robos = (int) spnRobos.getValue();
            int bloqueos = (int) spnBloqueos.getValue();
            int minutos = (int) spnMinutos.getValue();

            int totalPuntos = puntosNormales + (puntosTriples * 3) + tirosLibres;
            
            jugador.getEstadisticas().agregarPuntosNormales(puntosNormales);
            jugador.getEstadisticas().agregarPuntosTriples(puntosTriples);
            jugador.getEstadisticas().agregarPuntosTirosLibres(tirosLibres);
            jugador.getEstadisticas().agregarRebotes(rebotes);
            jugador.getEstadisticas().agregarAsistencias(asistencias);
            jugador.getEstadisticas().agregarRobos(robos);
            jugador.getEstadisticas().agregarBloqueos(bloqueos);
            jugador.getEstadisticas().agregarMinutosJugados(minutos);
            jugador.getEstadisticas().verificarDoblesDobles();

            int[] stats = {
                totalPuntos, 
                rebotes, 
                asistencias, 
                robos, 
                bloqueos,
                puntosNormales,
                puntosTriples,
                tirosLibres,
                minutos
            };

            if (esLocal) {
                resultado.agregarEstadisticaLocal(jugador, stats);
            } else {
                resultado.agregarEstadisticaVisitante(jugador, stats);
            }
            
            dialog.dispose();
            ListarEstadisticasEquipo.refrescarDatos();
        });
        
        dialog.add(btnGuardar);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}