package logico;

import java.io.Serializable;

public class EstadisticasJugador implements Serializable {
    private static final long serialVersionUID = 1L;
    private int puntosTotales;
    private int puntosNormales;
    private int puntosTriples;
    private int puntosTirosLibres;
    private int rebotes;
    private int asistencias;
    private int robos;
    private int bloqueos;
    private int doblesDobles;
    private int triplesDobles;
    private int minutosJugados;

    public EstadisticasJugador() {
        this.puntosTotales = 0;
        this.puntosNormales = 0;
        this.puntosTriples = 0;
        this.puntosTirosLibres = 0;
        this.rebotes = 0;
        this.asistencias = 0;
        this.robos = 0;
        this.bloqueos = 0;
        this.doblesDobles = 0;
        this.triplesDobles = 0;
        this.minutosJugados = 0;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRobos() {
        return robos;
    }

    public void setRobos(int robos) {
        this.robos = robos;
    }

    public int getBloqueos() {
        return bloqueos;
    }

    public void setBloqueos(int bloqueos) {
        this.bloqueos = bloqueos;
    }

    public int getDoblesDobles() {
        return doblesDobles;
    }

    public void setDoblesDobles(int doblesDobles) {
        this.doblesDobles = doblesDobles;
    }

    public int getTriplesDobles() {
        return triplesDobles;
    }

    public void setTriplesDobles(int triplesDobles) {
        this.triplesDobles = triplesDobles;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
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


    public void agregarPuntosNormales(int puntos) { 
        this.puntosNormales += puntos; 
        this.puntosTotales += puntos; 
    }
    public void agregarPuntosTriples(int triples) { 
        this.puntosTriples += triples; 
        this.puntosTotales += triples * 3; 
    }
    public void agregarPuntosTirosLibres(int tiros) { 
        this.puntosTirosLibres += tiros; 
        this.puntosTotales += tiros; 
    }

    public void verificarDoblesDobles() {
        int categoriasDobleDigito = 0;
        if (puntosTotales >= 10) categoriasDobleDigito++;
        if (rebotes >= 10) categoriasDobleDigito++;
        if (asistencias >= 10) categoriasDobleDigito++;
        if (robos >= 10) categoriasDobleDigito++;
        if (bloqueos >= 10) categoriasDobleDigito++;

        if (categoriasDobleDigito >= 2) doblesDobles++;
        if (categoriasDobleDigito >= 3) triplesDobles++;
    }
}