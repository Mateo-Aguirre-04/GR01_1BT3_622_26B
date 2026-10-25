package org.example.web;

import jakarta.servlet.http.HttpServletRequest;
import org.example.control.ControlRegistroDonacion;
import org.example.model.Donacion;

import java.util.Objects;

public class InterfazDonacion {
    private static final String FORMULARIO = "/WEB-INF/vistas/registroDonacion.jsp";
    private static final String CONFIRMACION = "/WEB-INF/vistas/confirmacionDonacion.jsp";

    private final ControlRegistroDonacion controlRegistroDonacion;

    public InterfazDonacion(ControlRegistroDonacion controlRegistroDonacion) {
        this.controlRegistroDonacion = Objects.requireNonNull(
                controlRegistroDonacion, "El control de registro de donaciones es obligatorio");
    }

    public String mostrarFormulario() {
        return FORMULARIO;
    }

    public Donacion enviarDonacion(HttpServletRequest formulario) {
        return controlRegistroDonacion.registrarDonacion(formulario);
    }

    public String mostrarConfirmacion() {
        return CONFIRMACION;
    }
}
