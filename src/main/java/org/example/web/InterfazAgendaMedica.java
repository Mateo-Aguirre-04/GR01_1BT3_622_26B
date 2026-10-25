package org.example.web;

import org.example.control.ControlAgenda;
import org.example.model.Perro;
import org.example.model.RegistroMedico;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Frontera "UI Agenda Médica" del diagrama de robustez (HU Registrar vacuna o control médico).
 */
public class InterfazAgendaMedica {
    private static final String FORMULARIO = "/WEB-INF/vistas/registroMedico.jsp";
    private static final String AGENDA = "/WEB-INF/vistas/agendaMedica.jsp";

    private final ControlAgenda controlAgenda;

    public InterfazAgendaMedica(ControlAgenda controlAgenda) {
        this.controlAgenda = Objects.requireNonNull(controlAgenda, "El control de agenda es obligatorio");
    }

    public List<Perro> mostrarPerros() {
        return controlAgenda.listarPerros();
    }

    public Perro seleccionarPerro(Long idPerro) {
        return controlAgenda.obtenerPerro(idPerro);
    }

    public RegistroMedico registrar(Long idPerro, String tipo, String detalle, LocalDate fecha) {
        return controlAgenda.registrarEnAgenda(idPerro, tipo, detalle, fecha);
    }

    public List<RegistroMedico> consultarAgenda(Long idPerro) {
        return controlAgenda.consultarAgenda(idPerro);
    }

    public String vistaFormulario() {
        return FORMULARIO;
    }

    public String vistaAgenda() {
        return AGENDA;
    }
}
