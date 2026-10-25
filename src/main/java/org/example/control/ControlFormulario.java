package org.example.control;

import org.example.model.Perro;

import java.util.Objects;

/**
 * Coordinates the dog lookup in the "Solicitar Adopcion" sequence.
 */
public class ControlFormulario {
    private final ControlConsulta controlConsulta;

    public ControlFormulario(ControlConsulta controlConsulta) {
        this.controlConsulta = Objects.requireNonNull(controlConsulta, "El control de consulta es obligatorio");
    }

    public Perro iniciarFormulario(Long idPerro) {
        Perro perro = controlConsulta.consultarFicha(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        return perro;
    }
}
