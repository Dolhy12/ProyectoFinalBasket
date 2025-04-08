package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import logico.ControladoraLiga;
import logico.Juego;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VerCalendario extends JDialog {
    
    private ControladoraLiga controladora;
    private JTable table;
    private JButton btnSimular;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VerCalendario(ControladoraLiga controladora, String rol) {
        this.controladora = controladora;
        
        setTitle("Calendario de Juegos - [" + rol + "]");
        setSize(900, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        String[] columnas = {"ID", "Fecha", "Equipo Local", "Equipo Visitante", "Lugar", "Estado", "Resultado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        actualizarTabla(model);
        
        btnSimular = new JButton("Simular Juego");
        btnSimular.setEnabled(false);
        
        configurarListeners(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSimular);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTabla(DefaultTableModel model) {
        model.setRowCount(0); 
        for (Juego juego : controladora.getCalendario().getJuegos()) {
            String resultado = "N/A";
            if ("Finalizado".equals(juego.getEstado()) && juego.getResultado() != null) {
                resultado = juego.getResultado().getPuntosLocal() + " - " + juego.getResultado().getPuntosVisitante();
            }
            
            model.addRow(new Object[]{
                juego.getID(),
                juego.getFecha().format(formatter),
                juego.getEquipoLocal().getNombre(),
                juego.getEquipoVisitante().getNombre(),
                juego.getLugar(),
                juego.getEstado(),
                resultado
            });
        }
    }

    private void configurarListeners(DefaultTableModel model) {
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = table.getSelectedRow();
                if (fila != -1) {
                    Juego juego = controladora.getCalendario().getJuegos().get(fila);
                    boolean habilitar = LocalDateTime.now().isAfter(juego.getFecha()) 
                                     && "Programado".equals(juego.getEstado());
                    btnSimular.setEnabled(habilitar);
                }
            }
        });

       
        btnSimular.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila != -1) {
                Juego juego = controladora.getCalendario().getJuegos().get(fila);
                if (juego != null) {
                    new SimulacionDeJuego(controladora, juego).setVisible(true);
                    actualizarTabla(model); 
                }
            }
        });
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            VerCalendario dialog = new VerCalendario(
                ControladoraLiga.getInstance(), 
                "Anotador" 
            );
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });
    }
}