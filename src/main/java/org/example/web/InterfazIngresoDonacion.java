package org.example.web;

import org.example.control.ControlIngresoDonacion;
import org.example.model.Donacion;

import java.util.List;
import java.util.Objects;

public class InterfazIngresoDonacion {
    private static final String VISTA = "/WEB-INF/vistas/ingresoDonaciones.jsp";
    private static final String SIN_PENDIENTES = "No existen donaciones pendientes de ingreso al inventario.";

    private final ControlIngresoDonacion controlIngresoDonacion;

    public InterfazIngresoDonacion(ControlIngresoDonacion controlIngresoDonacion) {
        this.controlIngresoDonacion = Objects.requireNonNull(
                controlIngresoDonacion, "El control de ingreso de donaciones es obligatorio");
    }

    public List<Donacion> mostrarDonacionesPorIngresar() {
        return controlIngresoDonacion.listarDonacionesPorIngresar();
    }

    public String mensajeSinPendientes() {
        return SIN_PENDIENTES;
    }

    public String vista() {
        return VISTA;
    }
}
