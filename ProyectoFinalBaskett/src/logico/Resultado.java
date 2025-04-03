package logico;

import java.util.ArrayList;

public class Resultado {
    private int puntosLocal;
    private int puntosVisitante;
    private ArrayList<Jugador> jugadoresLocales;
    private ArrayList<int[]> statsLocales;
    private ArrayList<Jugador> jugadoresVisitantes;
    private ArrayList<int[]> statsVisitantes;

    public Resultado(int puntosLocal, int puntosVisitante) {
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.jugadoresLocales = new ArrayList<>();
        this.statsLocales = new ArrayList<>();
        this.jugadoresVisitantes = new ArrayList<>();
        this.statsVisitantes = new ArrayList<>();
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public ArrayList<Jugador> getJugadoresLocales() {
        return jugadoresLocales;
    }

    public ArrayList<int[]> getStatsLocales() {
        return statsLocales;
    }

    public ArrayList<Jugador> getJugadoresVisitantes() {
        return jugadoresVisitantes;
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
    
    public void agregarEstadisticaLocal(Jugador jugador, int puntos, int rebotes, int asistencias, int robos, int bloqueos) {
        jugadoresLocales.add(jugador);
        statsLocales.add(new int[]{puntos, rebotes, asistencias, robos, bloqueos});
    }

    public void agregarEstadisticaVisitante(Jugador jugador, int puntos, int rebotes, int asistencias, int robos, int bloqueos) {
        jugadoresVisitantes.add(jugador);
        statsVisitantes.add(new int[]{puntos, rebotes, asistencias, robos, bloqueos});
    }
}