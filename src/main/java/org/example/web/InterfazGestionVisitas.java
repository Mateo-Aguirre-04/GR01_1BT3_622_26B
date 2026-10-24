package org.example.web;

import org.example.control.ControlGestionVisita;
import org.example.model.Visita;

import java.util.List;
import java.util.Objects;

public class InterfazGestionVisitas {
    private final ControlGestionVisita controlGestionVisita;

    public InterfazGestionVisitas(ControlGestionVisita controlGestionVisita) {
        this.controlGestionVisita = Objects.requireNonNull(
                controlGestionVisita, "El control de gestión de visitas es obligatorio");
    }

    public List<Visita> mostrarVisitasPendientes() {
        return controlGestionVisita.listarVisitasPendientes();
    }

    public String aprobarVisita(Long idVisita) {
        return controlGestionVisita.aprobarVisita(idVisita);
    }

    public String rechazarVisita(Long idVisita) {
        return controlGestionVisita.rechazarVisita(idVisita);
    }
}
