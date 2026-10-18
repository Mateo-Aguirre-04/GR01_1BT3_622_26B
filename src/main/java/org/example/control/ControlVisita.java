package org.example.control;

import org.example.model.Perro;
import org.example.model.Visita;

import java.time.LocalDate;

public class ControlVisita {

    public void aceptarVisita(Visita visita) {
        if (visita == null) {
            throw new IllegalArgumentException("La visita es obligatoria");
        }
        visita.aceptar();
    }

    public void rechazarVisita(Visita visita) {
        if (visita == null) {
            throw new IllegalArgumentException("La visita es obligatoria");
        }
        visita.rechazar();
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