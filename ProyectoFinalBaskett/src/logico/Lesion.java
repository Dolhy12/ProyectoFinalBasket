package logico;
import java.time.LocalDate;

public class Lesion {
    private String tipo;
    private LocalDate fechaLesion;
    private int duracionEstimada;
    private String tratamiento; 
    private int diasBajaEstimado;
    private String parteCuerpo; 
    private String estado;

    public Lesion(String tipo, String parteCuerpo,int diasBajaEstimado, String tratamiento,  LocalDate fechaLesion, int duracionEstimada) {
        this.tipo = tipo;
        this.fechaLesion = fechaLesion;
        this.duracionEstimada = duracionEstimada;
        this.tratamiento = tratamiento; 
        this.parteCuerpo = parteCuerpo;
        this.diasBajaEstimado = diasBajaEstimado;
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

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public int getDiasBajaEstimado() {
		return diasBajaEstimado;
	}

	public void setDiasBajaEstimado(int diasBajaEstimado) {
		this.diasBajaEstimado = diasBajaEstimado;
	}

	public String getParteCuerpo() {
		return parteCuerpo;
	}

	public void setParteCuerpo(String parteCuerpo) {
		this.parteCuerpo = parteCuerpo;
	}
}