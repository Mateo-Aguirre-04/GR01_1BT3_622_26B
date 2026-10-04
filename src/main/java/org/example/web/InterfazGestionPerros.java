package org.example.web;

import org.example.control.ControlRegistro;
import org.example.model.DatosRegistroPerro;
import org.example.model.Perro;

import java.util.Objects;

/**
 * Boundary for the "Registrar Perro" sequence (Incremento 1).
 */
public class InterfazGestionPerros {
    private static final String FORMULARIO = "/WEB-INF/vistas/registroPerro.jsp";
    private static final String CONFIRMACION = "/WEB-INF/vistas/confirmacionRegistro.jsp";

    private final ControlRegistro controlRegistro;

    public InterfazGestionPerros(ControlRegistro controlRegistro) {
        this.controlRegistro = Objects.requireNonNull(controlRegistro, "El control de registro es obligatorio");
    }

    public String mostrarFormularioRegistro() {
        return FORMULARIO;
    }

    public Perro enviarDatosPerros(DatosRegistroPerro datos) {
        return controlRegistro.registrarPerro(datos);
    }

    public String mostrarConfirmacion() {
        return CONFIRMACION;
    }
}
