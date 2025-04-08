package logico;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarioJuegos implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Juego> juegos;

    public CalendarioJuegos() {
        this.juegos = new ArrayList<>();
    }

    public ArrayList<Juego> getJuegos() {
        return juegos;
    }

    public void setJuegos(ArrayList<Juego> juegos) {
        this.juegos = juegos;
    }

    public ArrayList<Juego> getJuegosProgramados() {
        ArrayList<Juego> programados = new ArrayList<>();
        for (Juego juego : juegos) {
            if ("Programado".equals(juego.getEstado())) {
                programados.add(juego);
            }
        }
        return programados;
    }

    public ArrayList<Juego> getJuegosFinalizados() {
        ArrayList<Juego> finalizados = new ArrayList<>();
        for (Juego juego : juegos) {
            if ("Finalizado".equals(juego.getEstado())) {
                finalizados.add(juego);
            }
        }
        return finalizados;
    }

    public Juego buscarJuego(String id) {
        for (Juego juego : juegos) {
            if (juego.getID().equals(id)) {
                return juego;
            }
        }
        return null;
    }

    public ArrayList<Juego> buscarJuegosPorFecha(LocalDateTime fecha) {
        ArrayList<Juego> juegosFecha = new ArrayList<>();
        for (Juego juego : juegos) {
            if (juego.getFecha().toLocalDate().equals(fecha.toLocalDate())) {
                juegosFecha.add(juego);
            }
        }
        return juegosFecha;
    }

    public void agregarJuego(Juego juego) {
        juegos.add(juego);
    }
}