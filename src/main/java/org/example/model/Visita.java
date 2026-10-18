package org.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Visita {
    private Estado estado;
    private final LocalDate fechaVisita;

    public Visita(LocalDate fechaVisita) {
        this.fechaVisita = Objects.requireNonNull(fechaVisita, "La fecha de visita es obligatoria");
        estado = Estado.PENDIENTE;
    }

    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    public Estado getEstado() {
        return estado;
    }

    private void validarPendiente(){
        if(estado != Estado.PENDIENTE){
            throw new IllegalStateException("La visita ya ha sido gestionada");
        }
    }

    public void aceptar() {
        validarPendiente();
        estado = Estado.ACEPTADA;
    }

    public void rechazar(){
        estado = Estado.RECHAZADA;
    }

    public enum Estado {
        PENDIENTE,
        ACEPTADA,
        RECHAZADA
    }
}