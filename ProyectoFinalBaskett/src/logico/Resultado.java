package logico;

import java.util.ArrayList;

public class Resultado {
    private int puntosLocal;
    private int puntosVisitante;
    private ArrayList<int[]> statsLocales;
    private ArrayList<int[]> statsVisitantes;
    private ArrayList<String> idsJugadoresLocales;
    private ArrayList<String> idsJugadoresVisitantes;

    public Resultado(int puntosLocal, int puntosVisitante) {
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.idsJugadoresLocales = new ArrayList<>();
        this.statsLocales = new ArrayList<>();
        this.idsJugadoresVisitantes = new ArrayList<>();
        this.statsVisitantes = new ArrayList<>();
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public ArrayList<String> getIdsJugadoresLocales() {
        return idsJugadoresLocales;
    }

    public ArrayList<int[]> getStatsLocales() {
        return statsLocales;
    }

    public ArrayList<String> getIdsJugadoresVisitantes() {
        return idsJugadoresVisitantes;
    }

    public ArrayList<int[]> getStatsVisitantes() {
        return statsVisitantes;
    }
    
    public void setPuntosLocal(int puntos) {
        this.puntosLocal = puntos;
    }

    public void setPuntosVisitante(int puntos) {
        this.puntosVisitante = puntos;
    }
    
    public void agregarEstadisticaLocal(Jugador jugador, int[] stats) {
        int index = idsJugadoresLocales.indexOf(jugador.getID());
        if (index == -1) {
            idsJugadoresLocales.add(jugador.getID());
            statsLocales.add(stats);
        } else {
            statsLocales.set(index, stats);
        }
        puntosLocal += stats[0];
    }

    public void agregarEstadisticaVisitante(Jugador jugador, int[] stats) {
        int index = idsJugadoresVisitantes.indexOf(jugador.getID());
        if (index == -1) {
            idsJugadoresVisitantes.add(jugador.getID());
            statsVisitantes.add(stats);
        } else {
            statsVisitantes.set(index, stats);
        }
        puntosVisitante += stats[0];
    }
}