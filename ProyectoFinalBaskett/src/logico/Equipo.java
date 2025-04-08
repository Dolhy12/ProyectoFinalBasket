package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ID;
    private String tiempoFundado;
    private ArrayList<Jugador> jugadores;
    private String nombreDeLaMascota;
    private String nombre;
    private String ciudad;
    private String entrenador;
    private EstadisticasEquipo estadisticas;

    public Equipo(String ID, String tiempoFundado, ArrayList<Jugador> jugadores, String nombreDeLaMascota, 
                  String nombre, String ciudad, String entrenador) {
        this.ID = ID;
        this.tiempoFundado = tiempoFundado;
        this.jugadores = (jugadores != null) ? jugadores : new ArrayList<>();
        this.nombreDeLaMascota = nombreDeLaMascota;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.entrenador = entrenador;
        this.estadisticas = new EstadisticasEquipo();
    }

    // Getters y Setters
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public String getTiempoFundado() { return tiempoFundado; }
    public void setTiempoFundado(String tiempoFundado) { this.tiempoFundado = tiempoFundado; }
    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(ArrayList<Jugador> jugadores) { this.jugadores = jugadores; }
    public String getNombreDeLaMascota() { return nombreDeLaMascota; }
    public void setNombreDeLaMascota(String nombreDeLaMascota) { this.nombreDeLaMascota = nombreDeLaMascota; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getEntrenador() { return entrenador; }
    public void setEntrenador(String entrenador) { this.entrenador = entrenador; }
    public EstadisticasEquipo getEstadisticas() { return estadisticas; }
    public void setEstadisticas(EstadisticasEquipo estadisticas) { this.estadisticas = estadisticas; }

    @Override
    public String toString() {
        return nombre + " (" + ID + ")";
    }
}