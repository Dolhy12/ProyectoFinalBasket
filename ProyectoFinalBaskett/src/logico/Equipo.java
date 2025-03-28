package logico;

import java.util.ArrayList;

public class Equipo {
    private String ID;
    private String nombre;
    private String ciudad;
    private String entrenador;
    private String capitan;
    private String nombreDeLaMascota;
    private int tiempoFundado; 
    private ArrayList<Jugador> jugadores;
    private EstadisticasEquipo estadisticas;

    public Equipo(String ID, int tiempoFundado, String capitan, String nombreDeLaMacota,String nombre, String ciudad, String entrenador) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.entrenador = entrenador;
        this.capitan = capitan; 
        this.tiempoFundado = tiempoFundado; 
        this.nombreDeLaMascota = nombreDeLaMascota;
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

	public String getCapitan() {
		return capitan;
	}

	public void setCapitan(String capitan) {
		this.capitan = capitan;
	}

	public String getNombreDeLaMascota() {
		return nombreDeLaMascota;
	}

	public void setNombreDeLaMascota(String nombreDeLaMascota) {
		this.nombreDeLaMascota = nombreDeLaMascota;
	}

	public int getTiempoFundado() {
		return tiempoFundado;
	}

	public void setTiempoFundado(int tiempoFundado) {
		this.tiempoFundado = tiempoFundado;
	}
	public Jugador buscarJugador(String idJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getID().equals(idJugador)) {
                return jugador;
            }
        }
        return null; 
    }

    public void modificarJugador(String idJugador, String nuevaPosicion, int nuevoNumero) {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador != null) {
            jugador.setPosicion(nuevaPosicion);
            jugador.setNumero(nuevoNumero);
        }
    }
}
