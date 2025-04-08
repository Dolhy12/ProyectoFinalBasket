package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;
    private int puntosLocal;
    private int puntosVisitante;
    private ArrayList<String> idsJugadoresLocales;
    private ArrayList<String> idsJugadoresVisitantes;
    private ArrayList<int[]> statsLocales;
    private ArrayList<int[]> statsVisitantes;

    public Resultado(int puntosLocal, int puntosVisitante) {
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.idsJugadoresLocales = new ArrayList<>();
        this.idsJugadoresVisitantes = new ArrayList<>();
        this.statsLocales = new ArrayList<>();
        this.statsVisitantes = new ArrayList<>();
    }

    // Getters y Setters
    public int getPuntosLocal() { return puntosLocal; }
    public void setPuntosLocal(int puntosLocal) { this.puntosLocal = puntosLocal; }
    public int getPuntosVisitante() { return puntosVisitante; }
    public void setPuntosVisitante(int puntosVisitante) { this.puntosVisitante = puntosVisitante; }
    public ArrayList<String> getIdsJugadoresLocales() { return idsJugadoresLocales; }
    public void setIdsJugadoresLocales(ArrayList<String> idsJugadoresLocales) { this.idsJugadoresLocales = idsJugadoresLocales; }
    public ArrayList<String> getIdsJugadoresVisitantes() { return idsJugadoresVisitantes; }
    public void setIdsJugadoresVisitantes(ArrayList<String> idsJugadoresVisitantes) { this.idsJugadoresVisitantes = idsJugadoresVisitantes; }
    public ArrayList<int[]> getStatsLocales() { return statsLocales; }
    public void setStatsLocales(ArrayList<int[]> statsLocales) { this.statsLocales = statsLocales; }
    public ArrayList<int[]> getStatsVisitantes() { return statsVisitantes; }
    public void setStatsVisitantes(ArrayList<int[]> statsVisitantes) { this.statsVisitantes = statsVisitantes; }

    public void agregarEstadisticaLocal(Jugador jugador, int[] stats) {
        idsJugadoresLocales.add(jugador.getID());
        statsLocales.add(stats);
    }

    public void agregarEstadisticaVisitante(Jugador jugador, int[] stats) {
        idsJugadoresVisitantes.add(jugador.getID());
        statsVisitantes.add(stats);
    }
}