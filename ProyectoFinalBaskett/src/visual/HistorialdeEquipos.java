package visual;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.*;
import java.awt.*;
import java.util.ArrayList;

public class HistorialdeEquipos extends JDialog {
    private JComboBox<Equipo> cmbEquipoA, cmbEquipoB;
    private DefaultTableModel model;
    private ControladoraLiga controladora;
    private JLabel lblResumen;

    public HistorialdeEquipos(ControladoraLiga controladora) {
        this.controladora = controladora;
        setTitle("Historial de Enfrentamientos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltro.setBackground(Color.WHITE);

        cmbEquipoA = new JComboBox<>();
        cmbEquipoB = new JComboBox<>();

        for (Equipo equipo : controladora.getMisEquipos()) {
            cmbEquipoA.addItem(equipo);
            cmbEquipoB.addItem(equipo);
        }
        
        panelFiltro.add(new JLabel("Equipo A:"));
        cmbEquipoA.setPreferredSize(new Dimension(200, 30));
        panelFiltro.add(cmbEquipoA);

        panelFiltro.add(new JLabel("Equipo B:"));
        cmbEquipoB.setPreferredSize(new Dimension(200, 30));
        panelFiltro.add(cmbEquipoB);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(255, 147, 30));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnBuscar.addActionListener(e -> consultarHistorial());
        panelFiltro.add(btnBuscar);

        add(panelFiltro, BorderLayout.NORTH);

        String[] columnas = {"Fecha", "Resultado", "Lugar", "MVP"};
        model = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(model);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setSelectionBackground(new Color(255, 147, 30));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setFillsViewportHeight(true);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        lblResumen = new JLabel(" ");
        lblResumen.setFont(new Font("Arial", Font.BOLD, 16));
        lblResumen.setBorder(new EmptyBorder(10, 20, 10, 10));
        add(lblResumen, BorderLayout.SOUTH);
    }

    private void consultarHistorial() {
        Equipo equipoA = (Equipo) cmbEquipoA.getSelectedItem();
        Equipo equipoB = (Equipo) cmbEquipoB.getSelectedItem();
        if (equipoA == null || equipoB == null || equipoA.equals(equipoB)) {
            JOptionPane.showMessageDialog(this, "Seleccione dos equipos distintos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.setRowCount(0);
        int victoriasA = 0, victoriasB = 0;

        for (Juego juego : controladora.getCalendario().getJuegosFinalizados()) {
            boolean enfrentan = 
                (juego.getEquipoLocal().equals(equipoA) && juego.getEquipoVisitante().equals(equipoB)) ||
                (juego.getEquipoLocal().equals(equipoB) && juego.getEquipoVisitante().equals(equipoA));

            if (!enfrentan || juego.getResultado() == null) continue;

            String fecha = juego.getFecha().toLocalDate().toString();
            int ptsLocal = juego.getResultado().getPuntosLocal();
            int ptsVisit = juego.getResultado().getPuntosVisitante();
            String resultado = juego.getEquipoLocal().getNombre() + " " + ptsLocal + " - " +
                               ptsVisit + " " + juego.getEquipoVisitante().getNombre();

            String lugar = juego.getLugar();
            String mvp = obtenerMVP(juego);

            model.addRow(new Object[]{fecha, resultado, lugar, mvp});

            if (juego.getEquipoLocal().equals(equipoA) && ptsLocal > ptsVisit) victoriasA++;
            else if (juego.getEquipoVisitante().equals(equipoA) && ptsVisit > ptsLocal) victoriasA++;
            else victoriasB++;
        }

        lblResumen.setText("Victorias de " + equipoA.getNombre() + ": " + victoriasA + " | " +
                           "Victorias de " + equipoB.getNombre() + ": " + victoriasB);
    }

    private String obtenerMVP(Juego juego) {
        int maxPuntos = -1;
        String nombreMVP = "Desconocido";

        ArrayList<String> ids = new ArrayList<>();
        ids.addAll(juego.getResultado().getIdsJugadoresLocales());
        ids.addAll(juego.getResultado().getIdsJugadoresVisitantes());

        ArrayList<int[]> stats = new ArrayList<>();
        stats.addAll(juego.getResultado().getStatsLocales());
        stats.addAll(juego.getResultado().getStatsVisitantes());

        for (int i = 0; i < ids.size(); i++) {
            Jugador j = controladora.buscarJugador(ids.get(i));
            if (j != null && stats.get(i)[0] > maxPuntos) {
                maxPuntos = stats.get(i)[0];
                nombreMVP = j.getNombre();
            }
        }
        return nombreMVP;
    }

    public static void main(String[] args) {
        HistorialdeEquipos dialog = new HistorialdeEquipos(ControladoraLiga.getInstance());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
