package logico;

import java.io.Serializable;

public class EstadisticasEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int partidosJugados;
    private int victorias;
    private int derrotas;
    private int totalPuntos;
    private int robosTotales;
    private int bloqueosTotales;
    private int asistenciasTotales;

    public EstadisticasEquipo() {
        this.partidosJugados = 0;
        this.victorias = 0;
        this.derrotas = 0;
        this.totalPuntos = 0;
        this.robosTotales = 0;
        this.bloqueosTotales = 0;
        this.asistenciasTotales = 0;
    }

    public int getPartidosJugados() { return partidosJugados; }
    public void setPartidosJugados(int partidosJugados) { this.partidosJugados = partidosJugados; }
    public int getVictorias() { return victorias; }
    public void setVictorias(int victorias) { this.victorias = victorias; }
    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }
    public int getTotalPuntos() { return totalPuntos; }
    public void setTotalPuntos(int totalPuntos) { this.totalPuntos = totalPuntos; }
    public int getRobosTotales() { return robosTotales; }
    public void setRobosTotales(int robosTotales) { this.robosTotales = robosTotales; }
    public int getBloqueosTotales() { return bloqueosTotales; }
    public void setBloqueosTotales(int bloqueosTotales) { this.bloqueosTotales = bloqueosTotales; }
    public int getAsistenciasTotales() { return asistenciasTotales; }
    public void setAsistenciasTotales(int asistenciasTotales) { this.asistenciasTotales = asistenciasTotales; }

    public float getPorcentajeVictorias() {
        return partidosJugados > 0 ? (victorias * 100.0f / partidosJugados) : 0;
    }

    public float getPromedioPuntosPorPartido() {
        return partidosJugados > 0 ? (totalPuntos / (float) partidosJugados) : 0;
    }
}