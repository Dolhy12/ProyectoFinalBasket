package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioJuegos {
    private ArrayList<Juego> juegos;
    private String temporada;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public ArrayList<Juego> getJuegos() {
        return juegos;
    }

    public void setJuegos(ArrayList<Juego> juegos) {
        this.juegos = juegos;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public CalendarioJuegos() {
        this.juegos = new ArrayList<>();
    }

    public void agregarJuego(Juego juego) {
        juegos.add(juego);
    }

    public boolean eliminarJuego(String idJuego) {
        return juegos.removeIf(j -> j.getID().equals(idJuego));
    }

    public void actualizarResultadoJuego(String idJuego, Resultado resultado) {
        Juego juego = buscarJuego(idJuego);
        if (juego != null) {
            juego.setResultado(resultado);
            actualizarEstadisticasEquipos(juego);
        }
    }

    private void actualizarEstadisticasEquipos(Juego juego) {
        Equipo local = juego.getEquipoLocal();
        Equipo visitante = juego.getEquipoVisitante();
        Resultado resultado = juego.getResultado();

        local.getEstadisticas().agregarPuntos(resultado.getPuntosLocal());
        if (resultado.getPuntosLocal() > resultado.getPuntosVisitante()) {
            local.getEstadisticas().agregarVictoria();
            visitante.getEstadisticas().agregarDerrota();
        } else {
            local.getEstadisticas().agregarDerrota();
            visitante.getEstadisticas().agregarVictoria();
        }
    }

    public Juego buscarJuego(String idJuego) {
        return juegos.stream()
                .filter(j -> j.getID().equals(idJuego))
                .findFirst()
                .orElse(null);
    }
}