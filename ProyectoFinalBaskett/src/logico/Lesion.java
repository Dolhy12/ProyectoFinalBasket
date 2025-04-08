package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Lesion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tipo;
    private String parteCuerpo;
    private int duracionEstimada;
    private String tratamiento;
    private LocalDate fechaLesion;
    private String estado;

    public Lesion(String tipo, String parteCuerpo, int duracionEstimada, String tratamiento, LocalDate fechaLesion) {
        this.tipo = tipo;
        this.parteCuerpo = parteCuerpo;
        this.duracionEstimada = duracionEstimada;
        this.tratamiento = tratamiento;
        this.fechaLesion = fechaLesion;
        this.estado = "Activa";
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getParteCuerpo() { return parteCuerpo; }
    public void setParteCuerpo(String parteCuerpo) { this.parteCuerpo = parteCuerpo; }
    public int getDuracionEstimada() { return duracionEstimada; }
    public void setDuracionEstimada(int duracionEstimada) { this.duracionEstimada = duracionEstimada; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public LocalDate getFechaLesion() { return fechaLesion; }
    public void setFechaLesion(LocalDate fechaLesion) { this.fechaLesion = fechaLesion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}