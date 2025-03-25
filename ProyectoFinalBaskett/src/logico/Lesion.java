package logico;
import java.time.LocalDate;

public class Lesion {
    private String tipo;
    private LocalDate fechaLesion;
    private int duracionEstimada;
    private String estado;

    public Lesion(String tipo, LocalDate fechaLesion, int duracionEstimada) {
        this.tipo = tipo;
        this.fechaLesion = fechaLesion;
        this.duracionEstimada = duracionEstimada;
        this.estado = "Activa";
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaLesion() {
        return fechaLesion;
    }

    public int getDuracionEstimada() {
        return duracionEstimada;
    }

    public String getEstado() {
        return estado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaLesion(LocalDate fechaLesion) {
        this.fechaLesion = fechaLesion;
    }

    public void setDuracionEstimada(int duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}