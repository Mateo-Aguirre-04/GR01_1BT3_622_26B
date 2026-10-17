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

    public Visita solicitarVisita(Perro perro, LocalDate fechaVisita) {
        if (!fechaVisita.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de visita debe ser futura");
        }
        if (!perro.estaDisponible()) {
            throw new IllegalArgumentException("El perro no está disponible");
        }

        return new Visita();
    }
}