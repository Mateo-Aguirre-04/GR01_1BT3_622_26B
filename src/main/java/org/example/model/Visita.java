package org.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Visita {
    private final Long idVisita;
    private final Adoptante visitante;
    private final Perro perro;
    private Estado estado;
    private final LocalDate fechaVisita;

    public Visita(LocalDate fechaVisita) {
        this(null, fechaVisita, null, null);
    }

    public Visita(Long idVisita, LocalDate fechaVisita, Adoptante visitante, Perro perro) {
        if (idVisita != null && idVisita <= 0) {
            throw new IllegalArgumentException("El ID de la visita debe ser mayor que cero");
        }
        this.idVisita = idVisita;
        this.fechaVisita = Objects.requireNonNull(fechaVisita, "La fecha de visita es obligatoria");
        this.visitante = visitante;
        this.perro = perro;
        estado = Estado.PENDIENTE;
    }

    public Long getIdVisita() {
        return idVisita;
    }

    public Adoptante getVisitante() {
        return visitante;
    }

    public Perro getPerro() {
        return perro;
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

    public synchronized void aceptar() {
        validarPendiente();
        estado = Estado.APROBADA;
    }

    public synchronized void aprobar() {
        validarPendiente();
        estado = Estado.APROBADA;
    }

    public synchronized void rechazar(){
        validarPendiente();
        estado = Estado.RECHAZADA;
    }

    public enum Estado {
        PENDIENTE,
        APROBADA,
        RECHAZADA
    }
}