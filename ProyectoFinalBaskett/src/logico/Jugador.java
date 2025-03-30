package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Jugador {
    private String ID;
    private String nombre;
    private int edad;
    private String posicion;
    private String nacionalidad; 
    private Date fechaDeNacimiento; 
    private float peso; 
    private float altura; 
    private int numero;
    private ArrayList<Lesion> lesiones;
    private EstadisticasJugador estadisticas;

    public Jugador(String ID, String nombre, int edad, String posicion, String nacionalidad, Date fechaDeNacimiento, float peso, float altura, int numero) {
        this.ID = ID;
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
        this.peso = peso; 
        this.nacionalidad = nacionalidad; 
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.altura = altura; 
        this.numero = numero;
        this.lesiones = new ArrayList<>();
        this.estadisticas = new EstadisticasJugador();
    }

    public String getID() { return ID; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getPosicion() { return posicion; }
    public String getNacionalidad() { return nacionalidad; }
    public Date getFechaDeNacimiento() { return fechaDeNacimiento; }
    public float getPeso() { return peso; }
    public float getAltura() { return altura; }
    public int getNumero() { return numero; }
    public ArrayList<Lesion> getLesiones() { return lesiones; }
    public EstadisticasJugador getEstadisticas() { return estadisticas; }

    public void setID(String ID) { this.ID = ID; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public void setFechaDeNacimiento(Date fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }
    public void setPeso(float peso) { this.peso = peso; }
    public void setAltura(float altura) { this.altura = altura; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setLesiones(ArrayList<Lesion> lesiones) { this.lesiones = lesiones; }
    public void setEstadisticas(EstadisticasJugador estadisticas) { this.estadisticas = estadisticas; }

    public void agregarLesion(Lesion lesion) {
        if (!lesiones.contains(lesion)) { 
            lesiones.add(lesion);
        }
    }

    public void verificarLesiones() {
        LocalDate hoy = LocalDate.now();
        lesiones.removeIf(lesion -> {
            LocalDate fechaFin = lesion.getFechaLesion().plusDays(lesion.getDuracionEstimada());
            if (hoy.isAfter(fechaFin)) {
                lesion.setEstado("Recuperada");
                return true;
            }
            return false;
        });
    }

    public boolean eliminarLesion(String tipo) {
        return lesiones.removeIf(l -> l.getTipo().equals(tipo));
    }

    public ArrayList<Lesion> getLesionesActivas() {
        ArrayList<Lesion> activas = new ArrayList<>();
        for (Lesion lesion : lesiones) {
            if (lesion.getEstado().equals("Activa")) {
                activas.add(lesion);
            }
        }
        return activas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return ID.equals(jugador.ID);
    }
}