package logico;

public class EstadisticasEquipo {
    private int totalPuntos;
    private int victorias;
    private int derrotas;
    private int robosTotales;
    private int asistenciasTotales;
    private int bloqueosTotales;
    private int partidosJugados; 

    public EstadisticasEquipo() {
        this.totalPuntos = 0;
        this.victorias = 0;
        this.derrotas = 0;
        this.robosTotales = 0;
        this.asistenciasTotales = 0;
        this.bloqueosTotales = 0;
        this.partidosJugados = 0;
    }

    public void agregarVictoria() {
        this.victorias++;
        this.partidosJugados++;
    }

    public void agregarDerrota() {
        this.derrotas++;
        this.partidosJugados++;
    }

    public double getPorcentajeVictorias() {
        if (partidosJugados == 0) return 0.0;
        return (victorias * 100.0) / partidosJugados;
    }

    public double getPromedioPuntosPorPartido() {
        if (partidosJugados == 0) return 0.0;
        return (double) totalPuntos / partidosJugados;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getRobosTotales() {
        return robosTotales;
    }

    public int getAsistenciasTotales() {
        return asistenciasTotales;
    }

    public int getBloqueosTotales() {
        return bloqueosTotales;
    }

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.totalPuntos += puntos;
        }
    }

    public void agregarRobos(int robos) {
        if (robos > 0) {
            this.robosTotales += robos;
        }
    }

    public void agregarAsistencias(int asistencias) {
        if (asistencias > 0) {
            this.asistenciasTotales += asistencias;
        }
    }

    public void agregarBloqueos(int bloqueos) {
        if (bloqueos > 0) {
            this.bloqueosTotales += bloqueos;
        }
    }
}