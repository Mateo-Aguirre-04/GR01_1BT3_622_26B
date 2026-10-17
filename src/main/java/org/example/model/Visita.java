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
        if(estado != Estado.PENDIENTE){
            throw new IllegalStateException("La visita ya ha sido aceptada");
        }
        estado = Estado.ACEPTADA;
    }

    public enum Estado {
        PENDIENTE,
        ACEPTADA
    }
}