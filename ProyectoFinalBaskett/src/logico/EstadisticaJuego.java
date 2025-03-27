package logico;

public class EstadisticaJuego {
    private Jugador jugador;
    private EstadisticasJugador estadisticas;

    public EstadisticaJuego(Jugador jugador, EstadisticasJugador estadisticas) {
        this.jugador = jugador;
        this.estadisticas = estadisticas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public EstadisticasJugador getEstadisticas() {
        return estadisticas;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setEstadisticas(EstadisticasJugador estadisticas) {
        this.estadisticas = estadisticas;
    }
}
