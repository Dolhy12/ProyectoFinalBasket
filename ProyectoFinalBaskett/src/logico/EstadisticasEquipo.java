package logico;

public class EstadisticasEquipo {
    private int totalPuntos;
    private int victorias;
    private int derrotas;
    private int robosTotales;
    private int asistenciasTotales;
    private int bloqueosTotales;

    public EstadisticasEquipo() {
        this.totalPuntos = 0;
        this.victorias = 0;
        this.derrotas = 0;
        this.robosTotales = 0;
        this.asistenciasTotales = 0;
        this.bloqueosTotales = 0;
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

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public void setRobosTotales(int robosTotales) {
        this.robosTotales = robosTotales;
    }

    public void setAsistenciasTotales(int asistenciasTotales) {
        this.asistenciasTotales = asistenciasTotales;
    }

    public void setBloqueosTotales(int bloqueosTotales) {
        this.bloqueosTotales = bloqueosTotales;
    }
    
    public void agregarPuntos(int puntos) {
        this.totalPuntos += puntos;
    }

    public void agregarVictoria() {
        this.victorias++;
    }

    public void agregarDerrota() {
        this.derrotas++;
    }

    public void agregarRobos(int robos) {
        this.robosTotales += robos;
    }

    public void agregarAsistencias(int asistencias) {
        this.asistenciasTotales += asistencias;
    }

    public void agregarBloqueos(int bloqueos) {
        this.bloqueosTotales += bloqueos;
    }

}