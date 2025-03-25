package logico;

import java.time.LocalDate;
import java.util.ArrayList;

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
        calendario.actualizarResultadoJuego(idJuego, resultado);
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
        ArrayList<Juego> juegosEquipo = new ArrayList<>();
        for (Juego juego : calendario.getJuegos()) {
            if (juego.getEquipoLocal().getID().equals(idEquipo) || 
                juego.getEquipoVisitante().getID().equals(idEquipo)) {
                juegosEquipo.add(juego);
            }
        }
        return juegosEquipo;
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
}