package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;

public class SimulacionDeJuego extends JDialog {
    
    private ControladoraLiga controladora;
    private Juego juego;
    private Resultado resultado;
    private Runnable onCloseCallback;

    public SimulacionDeJuego(ControladoraLiga controladora, Juego juego, Runnable onCloseCallback) {
        this.controladora = controladora;
        this.juego = juego;
        this.resultado = new Resultado(0, 0);
        this.onCloseCallback = onCloseCallback;
        controladora.actualizarEstadoLesiones();
        
        setTitle("Simulación: " + juego.getEquipoLocal().getNombre() + " vs " + juego.getEquipoVisitante().getNombre());
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        DefaultTableModel modelLocal = crearModeloTabla();
        JTable tblLocal = configurarTabla(modelLocal, true);
        
        DefaultTableModel modelVisitante = crearModeloTabla();
        JTable tblVisitante = configurarTabla(modelVisitante, false);

        tabbedPane.addTab(juego.getEquipoLocal().getNombre(), new JScrollPane(tblLocal));
        tabbedPane.addTab(juego.getEquipoVisitante().getNombre(), new JScrollPane(tblVisitante));
        
        JButton btnFinalizar = crearBotonFinalizar();
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelSur.setBackground(Color.WHITE);
        panelSur.add(btnFinalizar);

        add(tabbedPane, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    private DefaultTableModel crearModeloTabla() {
        return new DefaultTableModel(new String[]{"Nombre", "Posición", "Número"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable configurarTabla(DefaultTableModel model, boolean esLocal) {
    	Equipo equipo = esLocal ? juego.getEquipoLocal() : juego.getEquipoVisitante();
        
        for (Jugador j : equipo.getJugadores()) {
            if (j.getLesionesActivas().isEmpty()) { 
                model.addRow(new Object[]{
                    j.getNombre(), 
                    j.getPosicion(), 
                    j.getNumero()
                });
            }
        }
        
        JTable tabla = new JTable(model);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                String nombre = (String) tabla.getValueAt(tabla.getSelectedRow(), 0);
                Jugador jugador = controladora.getMisJugadores().stream()
                        .filter(j -> j.getNombre().equals(nombre))
                        .findFirst()
                        .orElse(null);
                mostrarEstadisticas(jugador, esLocal);
            }
        });
        
        return tabla;
    }

    private JButton crearBotonFinalizar() {
        JButton btnFinalizar = new JButton("Finalizar Juego");
        btnFinalizar.setBackground(new Color(255, 147, 30));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        btnFinalizar.addActionListener(e -> {
            int puntosLocal = resultado.getStatsLocales().stream().mapToInt(stat -> stat[0]).sum();
            int puntosVisitante = resultado.getStatsVisitantes().stream().mapToInt(stat -> stat[0]).sum();
            
            resultado.setPuntosLocal(puntosLocal);
            resultado.setPuntosVisitante(puntosVisitante);
            juego.setEstado("Finalizado");
            controladora.actualizarResultadoJuego(juego.getID(), resultado);
            
            JOptionPane.showMessageDialog(this, 
                "Resultado final:\n" + 
                juego.getEquipoLocal().getNombre() + ": " + puntosLocal + "\n" +
                juego.getEquipoVisitante().getNombre() + ": " + puntosVisitante, 
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            if (onCloseCallback != null) {
                onCloseCallback.run();
            }
        });
        
        return btnFinalizar;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
    }

    private void mostrarEstadisticas(Jugador jugador, boolean esLocal) {
        JDialog dialog = new JDialog(this, "Estadísticas de " + jugador.getNombre(), true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(9, 2, 10, 5));
        dialog.getContentPane().setBackground(Color.WHITE);

        JSpinner[] spinners = crearSpinners();
        String[] labels = {
            "Puntos normales:", "Puntos triples:", "Tiros libres:", 
            "Rebotes:", "Asistencias:", "Robos:", "Bloqueos:", "Minutos:"
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            dialog.add(label);
            spinners[i].setFont(new Font("Arial", Font.PLAIN, 12));
            dialog.add(spinners[i]);
        }

        JButton btnGuardar = crearBotonGuardar(spinners, esLocal, jugador, dialog);
        dialog.add(new JLabel());
        dialog.add(btnGuardar);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JSpinner[] crearSpinners() {
        return new JSpinner[]{
            new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 50, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 30, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 50, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 30, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)),
            new JSpinner(new SpinnerNumberModel(0, 0, 48, 1))
        };
    }

    private JButton crearBotonGuardar(JSpinner[] spinners, boolean esLocal, Jugador jugador, JDialog dialog) {
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(50, 150, 50));
        btnGuardar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            int totalPuntos = calcularPuntos(spinners);
            int[] stats = crearArrayStats(spinners, totalPuntos);
            
            if (esLocal) {
                resultado.agregarEstadisticaLocal(jugador, stats);
            } else {
                resultado.agregarEstadisticaVisitante(jugador, stats);
            }
            
            dialog.dispose();
        });
        
        return btnGuardar;
    }

    private int calcularPuntos(JSpinner[] spinners) {
        return ((int)spinners[0].getValue()) + 
               ((int)spinners[1].getValue())*3 + 
               ((int)spinners[2].getValue());
    }

    private int[] crearArrayStats(JSpinner[] spinners, int totalPuntos) {
        return new int[]{
            totalPuntos,
            ((int)spinners[3].getValue()),
            ((int)spinners[4].getValue()),
            ((int)spinners[5].getValue()),
            ((int)spinners[6].getValue()),
            ((int)spinners[0].getValue()),
            ((int)spinners[1].getValue()),
            ((int)spinners[2].getValue()),
            ((int)spinners[7].getValue())
        };
    }
}