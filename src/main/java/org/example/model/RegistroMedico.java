package org.example.model;

import java.time.LocalDate;

public class RegistroMedico {
    private final Long idRegistro;
    private final Tipo tipo;
    private final String detalle;
    private final LocalDate fecha;

    public RegistroMedico(Long idRegistro, Tipo tipo, String detalle, LocalDate fecha) {
        if (idRegistro == null || idRegistro <= 0) {
            throw new IllegalArgumentException("El ID del registro médico debe ser mayor que cero");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de registro es obligatorio");
        }
        if (detalle == null || detalle.isBlank()) {
            throw new IllegalArgumentException("El detalle de la vacuna o control es obligatorio");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        this.idRegistro = idRegistro;
        this.tipo = tipo;
        this.detalle = detalle.trim();
        this.fecha = fecha;
    }

    public Long getIdRegistro() {
        return idRegistro;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getNombreTipo() {
        return tipo == Tipo.VACUNA ? "Vacuna" : "Control médico";
    }

    public String getDetalle() {
        return detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public enum Tipo {
        VACUNA,
        CONTROL
    }
}