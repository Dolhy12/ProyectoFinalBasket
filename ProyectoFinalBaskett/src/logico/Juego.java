package logico;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Juego {
    private String ID;
    private LocalDateTime fechaHora;
    private String lugar;
    private String estado;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Resultado resultado;

    public Juego(String ID, LocalDateTime fechaHora, String lugar, Equipo local, Equipo visitante)  {
        this.ID = ID;
        this.fechaHora = fechaHora;
        this.lugar = lugar;
        this.estado = "Programado";
        this.equipoLocal = local;
        this.equipoVisitante = visitante;
    }

    public String getID() {
        return ID;
    }

    public LocalDateTime getFecha() {
        return (LocalDateTime) fechaHora;
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

    public void setFecha(LocalDateTime fecha) {
        this.fechaHora = fecha;
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