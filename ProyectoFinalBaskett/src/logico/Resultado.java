package logico;

import java.util.ArrayList;

public class Resultado {
    private int puntosLocal;
    private int puntosVisitante;
    private ArrayList<EstadisticaJuego> estadisticasJugadores;

    public Resultado(int puntosLocal, int puntosVisitante) {
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.estadisticasJugadores = new ArrayList<>();
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public ArrayList<EstadisticaJuego> getEstadisticasJugadores() {
        return estadisticasJugadores;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public void setEstadisticasJugadores(ArrayList<EstadisticaJuego> estadisticasJugadores) {
        this.estadisticasJugadores = estadisticasJugadores;
    }
    
    public void agregarEstadisticaJugador(EstadisticaJuego estadistica) {
        estadisticasJugadores.add(estadistica);
    }
}