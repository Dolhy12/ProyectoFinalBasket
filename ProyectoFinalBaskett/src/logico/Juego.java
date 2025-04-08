package logico;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Juego implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ID;
    private LocalDateTime fecha;
    private String lugar;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Resultado resultado;
    private String estado;

    public Juego(String ID, LocalDateTime fecha, String lugar, Equipo equipoLocal, Equipo equipoVisitante) {
        this.ID = ID;
        this.fecha = fecha;
        this.lugar = lugar;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.resultado = null;
        this.estado = "Programado";
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}