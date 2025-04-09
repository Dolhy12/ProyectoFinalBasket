package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import logico.ControladoraLiga;
import logico.Juego;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VerCalendario extends JDialog {
    
    private ControladoraLiga controladora;
    private JTable table;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VerCalendario(ControladoraLiga controladora, String rol) {
        this.controladora = controladora;
        
        setTitle("Calendario de Juegos - [" + rol + "]");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        String[] columnas = {"ID", "Fecha", "Equipo Local", "Equipo Visitante", "Lugar", "Estado", "Resultado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
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
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(255, 147, 30));
        table.setSelectionForeground(Color.WHITE);
        
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, 
                            isSelected, hasFocus, row, column);
                String estado = (String) value;
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if (!isSelected) {
                    switch(estado) {
                        case "Programado": c.setBackground(new Color(255, 255, 200)); break;
                        case "En Juego": c.setBackground(new Color(200, 255, 200)); break;
                        case "Finalizado": c.setBackground(new Color(220, 220, 220)); break;
                        default: c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnSimular = new JButton("Simular Juego");
        btnSimular.setBackground(new Color(255, 147, 30));
        btnSimular.setForeground(Color.WHITE);
        btnSimular.setFont(new Font("Arial", Font.BOLD, 14));
        btnSimular.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnSimular.setEnabled(false);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = table.getSelectedRow();
                btnSimular.setEnabled(fila != -1 && 
                    LocalDateTime.now().isAfter(controladora.getCalendario().getJuegos().get(fila).getFecha()) && 
                    "Programado".equals(controladora.getCalendario().getJuegos().get(fila).getEstado()));
            }
        });
        
        btnSimular.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if (fila != -1) {
                Juego juego = controladora.getCalendario().getJuegos().get(fila);
                SimulacionDeJuego simulacion = new SimulacionDeJuego(controladora, juego, this::actualizarTabla);
                simulacion.setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSimular);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
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
        
        table.repaint();
        table.revalidate();
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