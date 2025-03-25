package logico;

import java.time.LocalDate;

public class Juego {
    private String ID;
    private LocalDate fecha;
    private String lugar;
    private String estado;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Resultado resultado;

    public Juego(String ID, LocalDate fecha, String lugar, Equipo equipoLocal, Equipo equipoVisitante) {
        this.ID = ID;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = "Programado";
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    public String getID() {
        return ID;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public String getEstado() {
        return estado;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
        this.estado = "Finalizado";
    }
}