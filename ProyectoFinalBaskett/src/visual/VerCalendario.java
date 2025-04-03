package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logico.ControladoraLiga;
import logico.Juego;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerCalendario extends JDialog {
    private JTable table;
    private JButton btnSimular;
    private ControladoraLiga controladora;

    public VerCalendario(ControladoraLiga controladora) {
        this.controladora = controladora;
        initComponents();
        cargarJuegos();
    }

    private void initComponents() {
        setTitle("Calendario de Juegos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {"ID", "Fecha", "Equipo Local", "Equipo Visitante", "Lugar", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnSimular = new JButton("Simular Juego");
        btnSimular.setEnabled(false);
        btnSimular.addActionListener(e -> abrirSimulacion());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSimular);
        add(panelBotones, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                Juego juego = controladora.getCalendario().getJuegos().get(fila);
                boolean enFecha = LocalDateTime.now().isAfter(juego.getFecha()) 
                                && "Programado".equals(juego.getEstado());
                btnSimular.setEnabled(enFecha);
            }
        });
    }

    private void cargarJuegos() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (Juego juego : controladora.getCalendario().getJuegos()) {
            Object[] fila = {
                juego.getID(),
                juego.getFecha().toString(),
                juego.getEquipoLocal().getNombre(),
                juego.getEquipoVisitante().getNombre(),
                juego.getLugar(),
                juego.getEstado()
            };
            model.addRow(fila);
        }
    }

    private void abrirSimulacion() {
        int fila = table.getSelectedRow();
        if (fila >= 0) {
            Juego juego = controladora.getCalendario().getJuegos().get(fila);
            SimulacionDeJuego simulacion = new SimulacionDeJuego(controladora, juego);
            simulacion.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        ControladoraLiga controladora = ControladoraLiga.getInstance();
        VerCalendario dialog = new VerCalendario(controladora);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}