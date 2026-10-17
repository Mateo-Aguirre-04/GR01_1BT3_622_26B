package org.example.model;

public class Visita {
    private Estado estado;

    public Visita() {
        estado = Estado.PENDIENTE;
    }

    public Estado getEstado() {
        return estado;
    }

    public void aceptar() {
        estado = Estado.ACEPTADA;
    }

    public enum Estado {
        PENDIENTE,
        ACEPTADA
    }
}