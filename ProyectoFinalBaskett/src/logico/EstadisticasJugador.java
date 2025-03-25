package logico;

public class EstadisticasJugador {
    private int puntosTotales;
    private int puntosNormales;
    private int puntosTriples;
    private int puntosTirosLibres;
    private int rebotes;
    private int asistencias;
    private int robos;
    private int bloqueos;
    private int minutosJugados;
    private int doblesDobles;
    private int triplesDobles;

    public EstadisticasJugador() {
        this.puntosTotales = 0;
        this.puntosNormales = 0;
        this.puntosTriples = 0;
        this.puntosTirosLibres = 0;
        this.rebotes = 0;
        this.asistencias = 0;
        this.robos = 0;
        this.bloqueos = 0;
        this.minutosJugados = 0;
        this.doblesDobles = 0;
        this.triplesDobles = 0;
    }

    public void agregarPuntos(int puntos) {
        this.puntosTotales += puntos;
    }

    public void agregarPuntosNormales(int puntos) {
        this.puntosNormales += puntos;
        this.puntosTotales += puntos;
    }

    public void agregarPuntosTriples(int puntos) {
        this.puntosTriples += puntos;
        this.puntosTotales += puntos;
    }

    public void agregarPuntosTirosLibres(int puntos) {
        this.puntosTirosLibres += puntos;
        this.puntosTotales += puntos;
    }

    public void agregarRebotes(int rebotes) {
        this.rebotes += rebotes;
    }

    public void agregarAsistencias(int asistencias) {
        this.asistencias += asistencias;
    }

    public void agregarRobos(int robos) {
        this.robos += robos;
    }

    public void agregarBloqueos(int bloqueos) {
        this.bloqueos += bloqueos;
    }

    public void agregarMinutosJugados(int minutos) {
        this.minutosJugados += minutos;
    }

    public void verificarDoblesDobles() {
        int categorias = 0;
        if (puntosTotales >= 10) categorias++;
        if (rebotes >= 10) categorias++;
        if (asistencias >= 10) categorias++;
        if (robos >= 10) categorias++;
        if (bloqueos >= 10) categorias++;
        
        if (categorias >= 2) doblesDobles++;
        if (categorias >= 3) triplesDobles++;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public int getPuntosNormales() {
        return puntosNormales;
    }

    public int getPuntosTriples() {
        return puntosTriples;
    }

    public int getPuntosTirosLibres() {
        return puntosTirosLibres;
    }

    public int getRebotes() {
        return rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public int getRobos() {
        return robos;
    }

    public int getBloqueos() {
        return bloqueos;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public int getDoblesDobles() {
        return doblesDobles;
    }

    public int getTriplesDobles() {
        return triplesDobles;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public void setPuntosNormales(int puntosNormales) {
        this.puntosNormales = puntosNormales;
    }

    public void setPuntosTriples(int puntosTriples) {
        this.puntosTriples = puntosTriples;
    }

    public void setPuntosTirosLibres(int puntosTirosLibres) {
        this.puntosTirosLibres = puntosTirosLibres;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public void setRobos(int robos) {
        this.robos = robos;
    }

    public void setBloqueos(int bloqueos) {
        this.bloqueos = bloqueos;
    }

    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public void setDoblesDobles(int doblesDobles) {
        this.doblesDobles = doblesDobles;
    }

    public void setTriplesDobles(int triplesDobles) {
        this.triplesDobles = triplesDobles;
    }
}