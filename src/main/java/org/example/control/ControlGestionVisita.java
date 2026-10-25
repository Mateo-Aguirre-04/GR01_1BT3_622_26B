package org.example.control;

import org.example.model.Refugio;
import org.example.model.Visita;

import java.util.List;
import java.util.Objects;

public class ControlGestionVisita {
    private final Refugio refugio;

    public ControlGestionVisita(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public List<Visita> listarVisitasPendientes() {
        return refugio.listarVisitasPendientes();
    }

    public Visita obtenerVisita(Long idVisita) {
        validarId(idVisita);
        Visita visita = refugio.obtenerVisita(idVisita);
        if (visita == null) {
            throw new IllegalArgumentException("No se encontró una visita con ese ID");
        }
        return visita;
    }

    public String aprobarVisita(Long idVisita) {
        return procesar(idVisita, true);
    }

    public String rechazarVisita(Long idVisita) {
        return procesar(idVisita, false);
    }

    private String procesar(Long idVisita, boolean aprobar) {
        Visita visita = obtenerVisita(idVisita);
        synchronized (visita) {
            if (visita.getEstado() != Visita.Estado.PENDIENTE) {
                return "La visita ya fue procesada. Estado actual: " + nombreEstado(visita.getEstado()) + ".";
            }
            if (aprobar) {
                visita.aprobar();
                return "La visita fue aprobada correctamente.";
            }
            visita.rechazar();
            return "La visita fue rechazada correctamente.";
        }
    }

    private void validarId(Long idVisita) {
        if (idVisita == null || idVisita <= 0) {
            throw new IllegalArgumentException("El ID de la visita debe ser mayor que cero");
        }
    }

    private String nombreEstado(Visita.Estado estado) {
        return switch (estado) {
            case PENDIENTE -> "Pendiente";
            case APROBADA -> "Aprobada";
            case RECHAZADA -> "Rechazada";
        };
    }
}
