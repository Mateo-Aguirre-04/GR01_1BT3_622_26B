package org.example.web;

import org.example.control.ControlConsulta;
import org.example.control.ControlSolicitudVisita;
import org.example.model.Perro;
import org.example.model.Visita;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InterfazSolicitarVisita {
    private final ControlConsulta controlConsulta;
    private final ControlSolicitudVisita controlSolicitudVisita;

    public InterfazSolicitarVisita(ControlConsulta controlConsulta,
                                   ControlSolicitudVisita controlSolicitudVisita) {
        this.controlConsulta = Objects.requireNonNull(controlConsulta, "El control de consulta es obligatorio");
        this.controlSolicitudVisita = Objects.requireNonNull(
                controlSolicitudVisita, "El control de solicitud de visitas es obligatorio");
    }

    public List<Perro> mostrarPerrosDisponibles() {
        return controlConsulta.consultarRegistro();
    }

    public Perro seleccionarPerro(Long idPerro) {
        return mostrarPerrosDisponibles().stream()
                .filter(perro -> Objects.equals(perro.getIdPerro(), idPerro))
                .findFirst()
                .orElse(null);
    }

    public Visita solicitarVisita(Long idPerro, LocalDate fecha,
                                   String nombres, String apellidos, String telefono, String correo) {
        return controlSolicitudVisita.solicitarVisita(idPerro, fecha, nombres, apellidos, telefono, correo);
    }
}
