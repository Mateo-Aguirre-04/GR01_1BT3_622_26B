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
        validarPendiente();
        estado = Estado.ACEPTADA;
    }

    private void validarPendiente(){
        if(estado != Estado.PENDIENTE){
            throw new IllegalStateException("La visita ya ha sido aceptada");
        }
    }

    public enum Estado {
        PENDIENTE,
        ACEPTADA
    }
}