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
    
    public void agregarEstadisticaLocal(Jugador jugador, int puntos, int rebotes, int asistencias, int robos, int bloqueos) {
		this.idsJugadoresLocales.add(jugador.getID());
		this.statsLocales.add(new int[]{puntos, rebotes, asistencias, robos, bloqueos});
	}

	public void agregarEstadisticaVisitante(Jugador jugador, int puntos, int rebotes, int asistencias, int robos, int bloqueos) {
		this.idsJugadoresVisitantes.add(jugador.getID());
		this.statsVisitantes.add(new int[]{puntos, rebotes, asistencias, robos, bloqueos});
	}
}