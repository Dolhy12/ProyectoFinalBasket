package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ID;
    private String nombre;
    private int edad;
    private String posicion;
    private String nacionalidad;
    private Date fechaDeNacimiento;
    private float peso;
    private float altura;
    private int numero;
    private EstadisticasJugador estadisticas;
    private ArrayList<Lesion> lesiones;

    public Jugador(String ID, String nombre, int edad, String posicion, String nacionalidad, 
                   Date fechaDeNacimiento, float peso, float altura, int numero) {
        this.ID = ID;
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
        this.nacionalidad = nacionalidad;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.peso = peso;
        this.altura = altura;
        this.numero = numero;
        this.estadisticas = new EstadisticasJugador();
        this.lesiones = new ArrayList<>();
    }

    // Getters y Setters
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public Date getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(Date fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }
    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }
    public float getAltura() { return altura; }
    public void setAltura(float altura) { this.altura = altura; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public EstadisticasJugador getEstadisticas() { return estadisticas; }
    public void setEstadisticas(EstadisticasJugador estadisticas) { this.estadisticas = estadisticas; }
    public ArrayList<Lesion> getLesiones() { return lesiones; }
    public void setLesiones(ArrayList<Lesion> lesiones) { this.lesiones = lesiones; }

    public ArrayList<Lesion> getLesionesActivas() {
        ArrayList<Lesion> activas = new ArrayList<>();
        for (Lesion lesion : lesiones) {
            if ("Activa".equals(lesion.getEstado())) {
                activas.add(lesion);
            }
        }
        return activas;
    }

    @Override
    public String toString() {
        return ID + " - " + nombre + " (" + posicion + ")";
    }
}