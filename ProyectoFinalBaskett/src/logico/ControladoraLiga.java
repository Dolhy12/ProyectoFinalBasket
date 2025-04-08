package logico;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladoraLiga {
    private static ControladoraLiga instance = null;
    private ArrayList<Equipo> misEquipos;
    private ArrayList<Jugador> misJugadores;
    private CalendarioJuegos calendario;

    private static final String ARCHIVO_EQUIPOS = "equipos.dat";
    private static final String ARCHIVO_JUGADORES = "jugadores.dat";
    private static final String ARCHIVO_CALENDARIO = "calendario.dat";

    private ControladoraLiga() {
        misEquipos = new ArrayList<>();
        misJugadores = new ArrayList<>();
        calendario = new CalendarioJuegos();
        cargarDatos();
    }

    public static ControladoraLiga getInstance() {
        if (instance == null) {
            instance = new ControladoraLiga();
        }
        return instance;
    }

    
    public void agregarEquipo(Equipo equipo) {
        misEquipos.add(equipo);
        guardarDatos();
    }

    public void agregarJugador(Jugador jugador) {
        misJugadores.add(jugador);
        guardarDatos();
    }

    public void agregarJuego(Juego juego) {
        calendario.getJuegos().add(juego);
        guardarDatos();
    }

    public void actualizarEquipo(Equipo equipo) {
        for (int i = 0; i < misEquipos.size(); i++) {
            if (misEquipos.get(i).getID().equals(equipo.getID())) {
                misEquipos.set(i, equipo);
                break;
            }
        }
        guardarDatos();
    }

    public void eliminarEquipo(String id) {
        misEquipos.removeIf(e -> e.getID().equals(id));
        guardarDatos();
    }

    public void eliminarJugador(String id) {
        misJugadores.removeIf(j -> j.getID().equals(id));
        guardarDatos();
    }

    public Equipo buscarEquipo(String id) {
        return misEquipos.stream().filter(e -> e.getID().equals(id)).findFirst().orElse(null);
    }

    public Jugador buscarJugador(String id) {
        return misJugadores.stream().filter(j -> j.getID().equals(id)).findFirst().orElse(null);
    }

    public boolean existeJugador(String id) {
        return misJugadores.stream().anyMatch(j -> j.getID().equals(id));
    }

    public void actualizarJugador(Jugador jugador) {
        for (int i = 0; i < misJugadores.size(); i++) {
            if (misJugadores.get(i).getID().equals(jugador.getID())) {
                misJugadores.set(i, jugador);
                break;
            }
        }
        guardarDatos();
    }

    public void agregarLesionAJugador(String idJugador, String tipo, String parteCuerpo, int duracionEstimada, 
                                     String tratamiento, LocalDate fechaLesion, int duracion) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            Lesion lesion = new Lesion(tipo, parteCuerpo, duracionEstimada, tratamiento, fechaLesion);
            jugador.getLesiones().add(lesion);
            guardarDatos();
        }
    }

    public Equipo buscarEquipoPorJugador(String idJugador) {
        return misEquipos.stream()
                .filter(e -> e.getJugadores().stream().anyMatch(j -> j.getID().equals(idJugador)))
                .findFirst().orElse(null);
    }

    public ArrayList<Juego> getJuegosPorJugador(String idJugador) {
        ArrayList<Juego> juegosJugador = new ArrayList<>();
        for (Juego juego : calendario.getJuegos()) {
            if ((juego.getEquipoLocal().getJugadores().stream().anyMatch(j -> j.getID().equals(idJugador)) ||
                 juego.getEquipoVisitante().getJugadores().stream().anyMatch(j -> j.getID().equals(idJugador))) &&
                juego.getResultado() != null) {
                juegosJugador.add(juego);
            }
        }
        return juegosJugador;
    }

    public void actualizarResultadoJuego(String idJuego, Resultado resultado) {
        for (Juego juego : calendario.getJuegos()) {
            if (juego.getID().equals(idJuego)) {
                juego.setResultado(resultado);
                juego.setEstado("Finalizado");
                
                EstadisticasEquipo statsLocal = juego.getEquipoLocal().getEstadisticas();
                statsLocal.setPartidosJugados(statsLocal.getPartidosJugados() + 1);
                statsLocal.setTotalPuntos(statsLocal.getTotalPuntos() + resultado.getPuntosLocal());
                for (int[] stats : resultado.getStatsLocales()) {
                    statsLocal.setRobosTotales(statsLocal.getRobosTotales() + stats[3]);
                    statsLocal.setBloqueosTotales(statsLocal.getBloqueosTotales() + stats[4]);
                    statsLocal.setAsistenciasTotales(statsLocal.getAsistenciasTotales() + stats[2]);
                }
                if (resultado.getPuntosLocal() > resultado.getPuntosVisitante()) {
                    statsLocal.setVictorias(statsLocal.getVictorias() + 1);
                } else {
                    statsLocal.setDerrotas(statsLocal.getDerrotas() + 1);
                }

                EstadisticasEquipo statsVisitante = juego.getEquipoVisitante().getEstadisticas();
                statsVisitante.setPartidosJugados(statsVisitante.getPartidosJugados() + 1);
                statsVisitante.setTotalPuntos(statsVisitante.getTotalPuntos() + resultado.getPuntosVisitante());
                for (int[] stats : resultado.getStatsVisitantes()) {
                    statsVisitante.setRobosTotales(statsVisitante.getRobosTotales() + stats[3]);
                    statsVisitante.setBloqueosTotales(statsVisitante.getBloqueosTotales() + stats[4]);
                    statsVisitante.setAsistenciasTotales(statsVisitante.getAsistenciasTotales() + stats[2]);
                }
                if (resultado.getPuntosVisitante() > resultado.getPuntosLocal()) {
                    statsVisitante.setVictorias(statsVisitante.getVictorias() + 1);
                } else {
                    statsVisitante.setDerrotas(statsVisitante.getDerrotas() + 1);
                }
                
                break;
            }
        }
        guardarDatos();
    }

    public ArrayList<Equipo> getMisEquipos() { return misEquipos; }
    public ArrayList<Jugador> getMisJugadores() { return misJugadores; }
    public CalendarioJuegos getCalendario() { return calendario; }
    public void setCalendario(CalendarioJuegos calendario) { this.calendario = calendario; }

    public void guardarDatos() {
        try {
            try (ObjectOutputStream oosEquipos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_EQUIPOS))) {
                oosEquipos.writeObject(misEquipos);
            }
            try (ObjectOutputStream oosJugadores = new ObjectOutputStream(new FileOutputStream(ARCHIVO_JUGADORES))) {
                oosJugadores.writeObject(misJugadores);
            }
            try (ObjectOutputStream oosCalendario = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CALENDARIO))) {
                oosCalendario.writeObject(calendario);
            }
            System.out.println("Datos guardados exitosamente en archivos .dat");
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try {
            File archivoEquipos = new File(ARCHIVO_EQUIPOS);
            if (archivoEquipos.exists()) {
                try (ObjectInputStream oisEquipos = new ObjectInputStream(new FileInputStream(ARCHIVO_EQUIPOS))) {
                    misEquipos = (ArrayList<Equipo>) oisEquipos.readObject();
                }
            }
            File archivoJugadores = new File(ARCHIVO_JUGADORES);
            if (archivoJugadores.exists()) {
                try (ObjectInputStream oisJugadores = new ObjectInputStream(new FileInputStream(ARCHIVO_JUGADORES))) {
                    misJugadores = (ArrayList<Jugador>) oisJugadores.readObject();
                }
            }
            File archivoCalendario = new File(ARCHIVO_CALENDARIO);
            if (archivoCalendario.exists()) {
                try (ObjectInputStream oisCalendario = new ObjectInputStream(new FileInputStream(ARCHIVO_CALENDARIO))) {
                    calendario = (CalendarioJuegos) oisCalendario.readObject();
                }
            }
            System.out.println("Datos cargados exitosamente desde archivos .dat");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            misEquipos = new ArrayList<>();
            misJugadores = new ArrayList<>();
            calendario = new CalendarioJuegos();
        }
    }
}