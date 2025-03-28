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
        if (juego.getFecha().isBefore(this.fechaInicio) || juego.getFecha().isAfter(this.fechaFin)) {
            throw new IllegalArgumentException("El juego está fuera de la temporada.");
        }
        if (tieneConflicto(juego.getEquipoLocal(), juego.getFecha()) || 
            tieneConflicto(juego.getEquipoVisitante(), juego.getFecha())) {
            throw new IllegalArgumentException("Un equipo ya tiene un juego programado en esa fecha.");
        }

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
        visitante.getEstadisticas().agregarPuntos(resultado.getPuntosVisitante());

        if (resultado.getPuntosLocal() > resultado.getPuntosVisitante()) {
            local.getEstadisticas().agregarVictoria();
            visitante.getEstadisticas().agregarDerrota();
        } else {
            local.getEstadisticas().agregarDerrota();
            visitante.getEstadisticas().agregarVictoria();
        }

        ArrayList<Jugador> jugadoresLocales = resultado.getJugadoresLocales();
        ArrayList<int[]> statsLocales = resultado.getStatsLocales();
        for (int i = 0; i < jugadoresLocales.size(); i++) {
            Jugador jugador = jugadoresLocales.get(i);
            int[] stats = statsLocales.get(i);
            jugador.getEstadisticas().agregarPuntos(stats[0]);
            jugador.getEstadisticas().agregarRebotes(stats[1]);
            jugador.getEstadisticas().agregarAsistencias(stats[2]);
            jugador.getEstadisticas().verificarDoblesDobles();
        }

        ArrayList<Jugador> jugadoresVisitantes = resultado.getJugadoresVisitantes();
        ArrayList<int[]> statsVisitantes = resultado.getStatsVisitantes();
        for (int i = 0; i < jugadoresVisitantes.size(); i++) {
            Jugador jugador = jugadoresVisitantes.get(i);
            int[] stats = statsVisitantes.get(i);
            jugador.getEstadisticas().agregarPuntos(stats[0]);
            jugador.getEstadisticas().agregarRebotes(stats[1]);
            jugador.getEstadisticas().agregarAsistencias(stats[2]);
            jugador.getEstadisticas().verificarDoblesDobles();
        }
    }
    
    public Juego buscarJuego(String idJuego) {
        return juegos.stream()
                .filter(j -> j.getID().equals(idJuego))
                .findFirst()
                .orElse(null);
    }

    private boolean tieneConflicto(Equipo equipo, LocalDate fecha) {
        return juegos.stream().anyMatch(j ->
            (j.getEquipoLocal().equals(equipo) || j.getEquipoVisitante().equals(equipo)) &&
            j.getFecha().equals(fecha)
        );
    }
}