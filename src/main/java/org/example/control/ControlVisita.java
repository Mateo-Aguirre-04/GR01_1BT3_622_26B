package org.example.control;

import org.example.model.ServicioDisponibilidad;
import org.example.model.Perro;
import org.example.model.Visita;

import java.time.LocalDate;


public class ControlVisita {
    private ServicioDisponibilidad servicio;

    // Constructor original (sin servicio) — NO LO BORRES
    public ControlVisita() {
        this.servicio = null;
    }

    // Constructor nuevo con inyección del servicio
    public ControlVisita(ServicioDisponibilidad servicio) {
        this.servicio = servicio;
    }

    public void aceptarVisita(Visita visita) {
        validarVisita(visita);
        if (horarioOcupado(visita.getFechaVisita())) {
            visita.rechazar();
            return;
        }
        visita.aceptar();
    }

    private boolean horarioOcupado(LocalDate fecha) {
        return servicio != null && !servicio.estaLibre(fecha);
    }

    public void rechazarVisita(Visita visita) {
        validarVisita(visita);
        visita.rechazar();
    }

    private void validarVisita(Visita visita) {
        if (visita == null) {
            throw new IllegalArgumentException("La visita es obligatoria");
        }
    }

    public Visita solicitarVisita(Perro perro, LocalDate fechaVisita) {
        validarFecha(fechaVisita);
        if (!perro.estaDisponible()) {
            throw new IllegalArgumentException("El perro no está disponible");
        }
        return new Visita(fechaVisita);
    }

    private void validarFecha(LocalDate fechaVisita) {
        if (fechaVisita == null) {
            throw new IllegalArgumentException("La fecha de visita es obligatoria");
        }
        if (!fechaVisita.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de visita debe ser futura");
        }
    }
}