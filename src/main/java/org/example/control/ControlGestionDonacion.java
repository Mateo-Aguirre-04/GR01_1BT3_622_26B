package org.example.control;

import org.example.model.Donacion;
import org.example.model.Refugio;

import java.util.List;
import java.util.Objects;

public class ControlGestionDonacion {
    private final Refugio refugio;

    public ControlGestionDonacion(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public List<Donacion> listarDonacionesPendientes() {
        return refugio.listarDonacionesPendientes();
    }

    public Donacion obtenerDonacion(Long idDonacion) {
        if (idDonacion == null || idDonacion <= 0) {
            throw new IllegalArgumentException("El ID de la donación debe ser mayor que cero");
        }
        Donacion donacion = refugio.obtenerDonacion(idDonacion);
        if (donacion == null) {
            throw new IllegalArgumentException("No se encontró una donación con ese ID");
        }
        return donacion;
    }

    public String confirmarRecepcion(Long idDonacion) {
        Donacion donacion = obtenerDonacion(idDonacion);
        synchronized (donacion) {
            validarPendiente(donacion);
            donacion.marcarRecibida();
        }
        return "";
    }

    private void validarPendiente(Donacion donacion) {
        if (donacion.getEstado() != Donacion.Estado.PENDIENTE) {
            throw new IllegalStateException("");
        }
    }
}
