package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ControladoraLiga {
    private ArrayList<Equipo> misEquipos;
    private ArrayList<Jugador> misJugadores;
    private CalendarioJuegos calendario;

    public ControladoraLiga() {
        this.misEquipos = new ArrayList<>();
        this.misJugadores = new ArrayList<>();
        this.calendario = new CalendarioJuegos();
    }

    public void agregarEquipo(Equipo equipo) {
        if (equipo != null && !misEquipos.contains(equipo)) {
            misEquipos.add(equipo);
            for (Jugador jugador : equipo.getJugadores()) {
                if (!misJugadores.contains(jugador)) {
                    misJugadores.add(jugador);
                }
            }
        }
    }

    public boolean eliminarEquipo(String idEquipo) {
        Equipo equipo = buscarEquipo(idEquipo);
        if (equipo != null) {
            for (Jugador jugador : equipo.getJugadores()) {
                boolean enOtroEquipo = false;
                for (Equipo e : misEquipos) {
                    if (!e.getID().equals(idEquipo) && e.getJugadores().contains(jugador)) {
                        enOtroEquipo = true;
                        break;
                    }
                }
                if (!enOtroEquipo) {
                    misJugadores.remove(jugador);
                }
            }
            return misEquipos.remove(equipo);
        }
        return false;
    }

    public Equipo buscarEquipo(String idEquipo) {
        for (Equipo equipo : misEquipos) {
            if (equipo.getID().equals(idEquipo)) {
                return equipo;
            }
        }
        return null;
    }

    public void agregarJugador(Jugador jugador) {
        if (jugador != null && !misJugadores.contains(jugador)) {
            misJugadores.add(jugador);
        }
    }

    public boolean eliminarJugador(String idJugador) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            for (Equipo equipo : misEquipos) {
                equipo.getJugadores().remove(jugador);
            }
            return misJugadores.remove(jugador);
        }
        return false;
    }

    public Jugador buscarJugador(String idJugador) {
        for (Jugador jugador : misJugadores) {
            if (jugador.getID().equals(idJugador)) {
                return jugador;
            }
        }
        return null;
    }

    public void agregarJuego(Juego juego) {
        if (juego != null) {
            calendario.agregarJuego(juego);
        }
    }

    public boolean eliminarJuego(String idJuego) {
        return calendario.eliminarJuego(idJuego);
    }

    public void actualizarResultadoJuego(String idJuego, Resultado resultado) {
        Juego juego = calendario.buscarJuego(idJuego);
        if (juego != null) {
            Jugador jugadorLocal = juego.getEquipoLocal().getJugadores().get(0);
            resultado.agregarEstadisticaLocal(jugadorLocal, 20, 5, 3);

            Jugador jugadorVisitante = juego.getEquipoVisitante().getJugadores().get(0);
            resultado.agregarEstadisticaVisitante(jugadorVisitante, 15, 4, 2);

            juego.setResultado(resultado);
        }
    }

    public Juego buscarJuego(String idJuego) {
        return calendario.buscarJuego(idJuego);
    }

    public ArrayList<Jugador> getJugadoresPorEquipo(String idEquipo) {
        Equipo equipo = buscarEquipo(idEquipo);
        if (equipo != null) {
            return new ArrayList<>(equipo.getJugadores());
        }
        return new ArrayList<>();
    }

    public ArrayList<Juego> getJuegosPorEquipo(String idEquipo) {
        return calendario.getJuegos().stream()
            .filter(j -> j.getEquipoLocal().getID().equals(idEquipo) || 
                         j.getEquipoVisitante().getID().equals(idEquipo))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Equipo> getMisEquipos() {
        return misEquipos;
    }

    public void setMisEquipos(ArrayList<Equipo> misEquipos) {
        this.misEquipos = misEquipos;
        this.misJugadores.clear();
        for (Equipo equipo : misEquipos) {
            for (Jugador jugador : equipo.getJugadores()) {
                if (!misJugadores.contains(jugador)) {
                    misJugadores.add(jugador);
                }
            }
        }
    }

    public ArrayList<Jugador> getMisJugadores() {
        return misJugadores;
    }

    public void setMisJugadores(ArrayList<Jugador> misJugadores) {
        this.misJugadores = misJugadores;
    }

    public CalendarioJuegos getCalendario() {
        return calendario;
    }

    public void setCalendario(CalendarioJuegos calendario) {
        this.calendario = calendario;
    }

    public boolean existeEquipo(String idEquipo) {
        return buscarEquipo(idEquipo) != null;
    }

    public boolean existeJugador(String idJugador) {
        return buscarJugador(idJugador) != null;
    }

    public boolean existeJuego(String idJuego) {
        return buscarJuego(idJuego) != null;
    }

    public void transferirJugador(String idJugador, String idEquipoOrigen, String idEquipoDestino) {
        Jugador jugador = buscarJugador(idJugador);
        Equipo equipoOrigen = buscarEquipo(idEquipoOrigen);
        Equipo equipoDestino = buscarEquipo(idEquipoDestino);

        if (jugador != null && equipoOrigen != null && equipoDestino != null) {
            if (equipoOrigen.getJugadores().remove(jugador)) {
                equipoDestino.getJugadores().add(jugador);
            }
        }
    }

    public ArrayList<Jugador> buscarJugadoresPorNombre(String nombre) {
        ArrayList<Jugador> resultados = new ArrayList<>();
        for (Jugador jugador : misJugadores) {
            if (jugador.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(jugador);
            }
        }
        return resultados;
    }

    public ArrayList<Equipo> buscarEquiposPorCiudad(String ciudad) {
        ArrayList<Equipo> resultados = new ArrayList<>();
        for (Equipo equipo : misEquipos) {
            if (equipo.getCiudad().equalsIgnoreCase(ciudad)) {
                resultados.add(equipo);
            }
        }
        return resultados;
    }
    
    public ArrayList<Equipo> generarClasificacion() {
        return (ArrayList<Equipo>) misEquipos.stream()
            .sorted((e1, e2) -> {
                int cmp = Integer.compare(e2.getEstadisticas().getVictorias(), e1.getEstadisticas().getVictorias());
                if (cmp == 0) {
                    cmp = Integer.compare(e2.getEstadisticas().getTotalPuntos(), e1.getEstadisticas().getTotalPuntos());
                }
                return cmp;
            })
            .collect(Collectors.toList());
    }
    
    public ArrayList<Jugador> reportarJugadoresLesionados() {
        return (ArrayList<Jugador>) misJugadores.stream()
            .filter(j -> j.getLesiones().stream().anyMatch(l -> l.getEstado().equals("Activa")))
            .collect(Collectors.toList());
    }
    
    public ArrayList<Jugador> topJugadoresPorPuntos() {
        return (ArrayList<Jugador>) misJugadores.stream()
            .sorted((j1, j2) -> Integer.compare(j2.getEstadisticas().getPuntosTotales(), j1.getEstadisticas().getPuntosTotales()))
            .limit(10)
            .collect(Collectors.toList());
    }
    public void modificarEquipo(String idEquipo, String nombre, String ciudad, String entrenador, String capitan, String nombreDeLaMascota, int tiempoFundado) {
        Equipo equipo = buscarEquipo(idEquipo);
        if (equipo != null) {
            equipo.setNombre(nombre);
            equipo.setCiudad(ciudad);
            equipo.setEntrenador(entrenador);
            equipo.setCapitan(capitan);
            equipo.setNombreDeLaMascota(nombreDeLaMascota);
            equipo.setTiempoFundado(tiempoFundado);
        }
    }

    public void agregarJugadorAEquipo(String idJugador, String idEquipo) {
        Jugador jugador = buscarJugador(idJugador);
        Equipo equipo = buscarEquipo(idEquipo);
        if (jugador != null && equipo != null && !equipo.getJugadores().contains(jugador)) {
            equipo.agregarJugador(jugador);
            if (!misJugadores.contains(jugador)) {
                misJugadores.add(jugador);
            }
        }
    }

    public void modificarJugador(String idJugador, String nombre, int edad, String posicion, int numero) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            jugador.setNombre(nombre);
            jugador.setEdad(edad);
            jugador.setPosicion(posicion);
            jugador.setNumero(numero);
        }
    }

    public void agregarLesionAJugador(String idJugador, String tipo, String parteCuerpo, int diasBajaEstimado, String tratamiento, LocalDate fechaLesion, int duracionEstimada) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            Lesion lesion = new Lesion(tipo, parteCuerpo, diasBajaEstimado, tratamiento, fechaLesion, duracionEstimada);
            jugador.agregarLesion(lesion);
        }
    }

    public void verificarLesionesJugadores() {
        LocalDate hoy = LocalDate.now();
        for (Jugador jugador : misJugadores) {
            jugador.getLesiones().removeIf(lesion -> {
                LocalDate fechaFin = lesion.getFechaLesion().plusDays(lesion.getDuracionEstimada());
                if (hoy.isAfter(fechaFin)) {
                    lesion.setEstado("Recuperada");
                    return true; 
                }
                return false;
            });
        }
    }

    public void eliminarLesionDeJugador(String idJugador, String tipoLesion) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            jugador.getLesiones().removeIf(l -> l.getTipo().equals(tipoLesion));
        }
    }
}
