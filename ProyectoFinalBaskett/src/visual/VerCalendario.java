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

    public VerCalendario(ControladoraLiga controladora) {
        this.controladora = controladora;
        
        setTitle("Calendario de Juegos");
        setSize(900, 400); // Aumentamos un poco el ancho
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Añadimos columna "Resultado"
        String[] columnas = {"ID", "Fecha", "Equipo Local", "Equipo Visitante", "Lugar", "Estado", "Resultado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Juego juego : controladora.getCalendario().getJuegos()) {
            String resultado = "N/A";
            if ("Finalizado".equals(juego.getEstado())) {
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnSimular = new JButton("Simular Juego");
        btnSimular.setEnabled(false);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int fila = table.getSelectedRow();
                if (fila >= 0) {
                    Juego juego = controladora.getCalendario().getJuegos().get(fila);
                    boolean enFecha = LocalDateTime.now().isAfter(juego.getFecha()) 
                                    && "Programado".equals(juego.getEstado());
                    btnSimular.setEnabled(enFecha);
                }
            }
        });
        
        btnSimular.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila >= 0) {
                Juego juego = controladora.getCalendario().getJuegos().get(fila);
                SimulacionDeJuego simulacion = new SimulacionDeJuego(controladora, juego);
                simulacion.setVisible(true);
                dispose();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSimular);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        VerCalendario dialog = new VerCalendario(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}