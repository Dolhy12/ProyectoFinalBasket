package logico;

import java.util.ArrayList;

public class Jugador {
    private String ID;
    private String nombre;
    private int edad;
    private String posicion;
    private int numero;
    private ArrayList<Lesion> lesiones;
    private EstadisticasJugador estadisticas;

    public Jugador(String ID, String nombre, int edad, String posicion, int numero) {
        this.ID = ID;
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
        this.numero = numero;
        this.lesiones = new ArrayList<>();
        this.estadisticas = new EstadisticasJugador();
    }

    public String getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getNumero() {
        return numero;
    }

    public ArrayList<Lesion> getLesiones() {
        return lesiones;
    }

    public EstadisticasJugador getEstadisticas() {
        return estadisticas;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLesiones(ArrayList<Lesion> lesiones) {
        this.lesiones = lesiones;
    }

    public void setEstadisticas(EstadisticasJugador estadisticas) {
        this.estadisticas = estadisticas;
    }
    
    public void agregarLesion(Lesion lesion) {
        lesiones.add(lesion);
    }
}