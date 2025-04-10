package logico;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladoraLiga {
    private static ControladoraLiga instance = null;
    private ArrayList<Equipo> misEquipos;
    private ArrayList<Jugador> misJugadores;
    private CalendarioJuegos calendario;
    private static final String ARCHIVO_DATOS = "datos.dat";

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
    	            
    	            for (int i = 0; i < resultado.getIdsJugadoresLocales().size(); i++) {
    	                String idJugador = resultado.getIdsJugadoresLocales().get(i);
    	                int[] stats = resultado.getStatsLocales().get(i);
    	                Jugador jugador = buscarJugador(idJugador);
    	                if (jugador != null) {
    	                    EstadisticasJugador estadisticas = jugador.getEstadisticas();
    	                    estadisticas.agregarPuntosNormales(stats[5]);
    	                    estadisticas.agregarPuntosTriples(stats[6]);
    	                    estadisticas.agregarPuntosTirosLibres(stats[7]);
    	                    estadisticas.agregarRebotes(stats[1]);
    	                    estadisticas.agregarAsistencias(stats[2]);
    	                    estadisticas.agregarRobos(stats[3]);
    	                    estadisticas.agregarBloqueos(stats[4]);
    	                    estadisticas.agregarMinutosJugados(stats[8]);
    	                    estadisticas.verificarDoblesDobles();
    	                }
    	            }

    	            for (int i = 0; i < resultado.getIdsJugadoresVisitantes().size(); i++) {
    	                String idJugador = resultado.getIdsJugadoresVisitantes().get(i);
    	                int[] stats = resultado.getStatsVisitantes().get(i);
    	                Jugador jugador = buscarJugador(idJugador);
    	                if (jugador != null) {
    	                    EstadisticasJugador estadisticas = jugador.getEstadisticas();
    	                    estadisticas.agregarPuntosNormales(stats[5]);
    	                    estadisticas.agregarPuntosTriples(stats[6]);
    	                    estadisticas.agregarPuntosTirosLibres(stats[7]);
    	                    estadisticas.agregarRebotes(stats[1]);
    	                    estadisticas.agregarAsistencias(stats[2]);
    	                    estadisticas.agregarRobos(stats[3]);
    	                    estadisticas.agregarBloqueos(stats[4]);
    	                    estadisticas.agregarMinutosJugados(stats[8]);
    	                    estadisticas.verificarDoblesDobles();
    	                }
    	            }
                
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
    
    public void actualizarEstadoLesiones() {
        LocalDate hoy = LocalDate.now();
        for (Jugador jugador : misJugadores) {
            for (Lesion lesion : jugador.getLesiones()) {
                if (lesion.getEstado().equals("Activa") 
                    && hoy.isAfter(lesion.getFechaLesion().plusDays(lesion.getDuracionEstimada()))) {
                    lesion.setEstado("Inactiva");
                }
            }
        }
        guardarDatos();
    }
    
    public ArrayList<Equipo> getMisEquipos() { 
    	return misEquipos; 
    }
    
    public ArrayList<Jugador> getMisJugadores() { 
    	return misJugadores; 
    }
    
    public CalendarioJuegos getCalendario() { 
    	return calendario; 
    }
    
    public void setCalendario(CalendarioJuegos calendario) {
    	this.calendario = calendario; 
    }
    
    public void guardarDatos() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
                Object[] datos = {
                    misEquipos,
                    misJugadores,
                    calendario
                };
                oos.writeObject(datos);
            }
            System.out.println("Datos guardados exitosamente en " + ARCHIVO_DATOS);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try {
            File archivo = new File(ARCHIVO_DATOS);
            if (archivo.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
                    
                    Object[] datos = (Object[]) ois.readObject();
                    
                    misEquipos = (ArrayList<Equipo>) datos[0];
                    misJugadores = (ArrayList<Jugador>) datos[1];
                    calendario = (CalendarioJuegos) datos[2];
                    
                    System.out.println("Datos cargados desde " + ARCHIVO_DATOS);
                }
            } else {
                System.out.println("No se encontró archivo de datos, inicializando nuevas estructuras");
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            misEquipos = new ArrayList<>();
            misJugadores = new ArrayList<>();
            calendario = new CalendarioJuegos();
        }
    }
}