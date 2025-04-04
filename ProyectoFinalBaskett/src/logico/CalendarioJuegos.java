package logico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarioJuegos {
    private ArrayList<Juego> juegos;
    private String temporada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
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

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
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

    public void agregarJuego(Juego juego) throws IllegalStateException, IllegalArgumentException {
        validarFechasTemporada();
        
        if (juego.getFecha().isBefore(fechaInicio) || juego.getFecha().isAfter(fechaFin)) {
            throw new IllegalArgumentException("El juego está fuera de la temporada (" + 
                fechaInicio.toLocalDate() + " - " + fechaFin.toLocalDate() + ")");
        }
        
        if (tieneConflicto(juego.getEquipoLocal(), juego.getFecha()) || 
            tieneConflicto(juego.getEquipoVisitante(), juego.getFecha())) {
            throw new IllegalArgumentException("Conflicto de horario para: " + 
                juego.getEquipoLocal().getNombre() + " o " + 
                juego.getEquipoVisitante().getNombre());
        }
        
        juegos.add(juego);
    }

    public boolean eliminarJuego(String idJuego) {
        return juegos.removeIf(j -> j.getID().equals(idJuego));
    }

    public void actualizarResultadoJuego(String idJuego, Resultado resultado) {
        Juego juego = buscarJuego(idJuego);
        if (juego != null && "Finalizado".equals(juego.getEstado())) {
            throw new IllegalStateException("El juego ya está finalizado");
        }
        
        if (juego != null) {
            juego.setResultado(resultado);
            actualizarEstadisticasEquipos(juego);
            juego.setEstado("Finalizado");
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

        ControladoraLiga controladora = ControladoraLiga.getInstance();
        
        ArrayList<String> idsLocales = resultado.getIdsJugadoresLocales();
        ArrayList<int[]> statsLocales = resultado.getStatsLocales();
        
        for (int i = 0; i < idsLocales.size(); i++) {
            Jugador jugador = controladora.buscarJugador(idsLocales.get(i));
            if (jugador != null) {
                int[] stats = statsLocales.get(i);
                
                jugador.getEstadisticas().agregarPuntos(stats[0]);
                jugador.getEstadisticas().agregarRebotes(stats[1]);
                jugador.getEstadisticas().agregarAsistencias(stats[2]);
                jugador.getEstadisticas().agregarRobos(stats[3]);
                jugador.getEstadisticas().agregarBloqueos(stats[4]);
                
                jugador.getEstadisticas().verificarDoblesDobles();
                
                local.getEstadisticas().agregarRobos(stats[3]);
                local.getEstadisticas().agregarBloqueos(stats[4]);
                local.getEstadisticas().agregarAsistencias(stats[2]);
            }
        }

        ArrayList<String> idsVisitantes = resultado.getIdsJugadoresVisitantes();
        ArrayList<int[]> statsVisitantes = resultado.getStatsVisitantes();
        
        for (int i = 0; i < idsVisitantes.size(); i++) {
            Jugador jugador = controladora.buscarJugador(idsVisitantes.get(i));
            if (jugador != null) {
                int[] stats = statsVisitantes.get(i);
                
                jugador.getEstadisticas().agregarPuntos(stats[0]);
                jugador.getEstadisticas().agregarRebotes(stats[1]);
                jugador.getEstadisticas().agregarAsistencias(stats[2]);
                jugador.getEstadisticas().agregarRobos(stats[3]);
                jugador.getEstadisticas().agregarBloqueos(stats[4]);
                
                jugador.getEstadisticas().verificarDoblesDobles();
                
                visitante.getEstadisticas().agregarRobos(stats[3]);
                visitante.getEstadisticas().agregarBloqueos(stats[4]);
                visitante.getEstadisticas().agregarAsistencias(stats[2]);
            }
        }

        local.getEstadisticas().incrementarPartidosJugados();
        visitante.getEstadisticas().incrementarPartidosJugados();
    }
    
    private void validarFechasTemporada() {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalStateException("Temporada no configurada correctamente");
        }
    }

    
    
    public Juego buscarJuego(String idJuego) {
        return juegos.stream()
                .filter(j -> j.getID().equals(idJuego))
                .findFirst()
                .orElse(null);
    }

    private boolean tieneConflicto(Equipo equipo, LocalDateTime fecha) {
        return juegos.stream().anyMatch(j ->
            (j.getEquipoLocal().equals(equipo) || j.getEquipoVisitante().equals(equipo)) &&
            j.getFecha().equals(fecha)
        );
    }
}