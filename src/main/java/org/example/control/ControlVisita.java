package org.example.control;

import org.example.model.Visita;

public class ControlVisita {

    public void aceptarVisita(Visita visita) {
        if (visita == null) {
            throw new IllegalArgumentException("La visita es obligatoria");
        }
        visita.aceptar();
    }
}