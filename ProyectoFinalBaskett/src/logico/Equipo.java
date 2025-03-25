package logico;

import java.util.ArrayList;

public class Equipo {
    private String ID;
    private String nombre;
    private String ciudad;
    private String entrenador;
    private ArrayList<Jugador> jugadores;
    private EstadisticasEquipo estadisticas;

    public Equipo(String ID, String nombre, String ciudad, String entrenador) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.entrenador = entrenador;
        this.jugadores = new ArrayList<>();
        this.estadisticas = new EstadisticasEquipo();
    }

    public String getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public EstadisticasEquipo getEstadisticas() {
        return estadisticas;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void setEstadisticas(EstadisticasEquipo estadisticas) {
        this.estadisticas = estadisticas;
    }
    
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void eliminarJugador(String idJugador) {
        jugadores.removeIf(j -> j.getID().equals(idJugador));
    }
}