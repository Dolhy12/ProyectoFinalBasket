package logico;

import java.util.ArrayList;

public class Resultado {
    private int puntosLocal;
    private int puntosVisitante;

    public Resultado(int puntosLocal, int puntosVisitante) {
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }
}