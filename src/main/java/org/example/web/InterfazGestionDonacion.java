package org.example.web;

import org.example.control.ControlGestionDonacion;
import org.example.model.Donacion;

import java.util.List;
import java.util.Objects;


public class InterfazGestionDonacion {
    private static final String LISTA = "/WEB-INF/vistas/gestionarDonaciones.jsp";
    private static final String DETALLE = "/WEB-INF/vistas/detalleDonacion.jsp";

    private final ControlGestionDonacion controlGestionDonacion;

    public InterfazGestionDonacion(ControlGestionDonacion controlGestionDonacion) {
        this.controlGestionDonacion = Objects.requireNonNull(
                controlGestionDonacion, "El control de gestión de donaciones es obligatorio");
    }

    public List<Donacion> mostrarDonacionesPendientes() {
        return controlGestionDonacion.listarDonacionesPendientes();
    }

    public Donacion mostrarDetalle(Long idDonacion) {
        return controlGestionDonacion.obtenerDonacion(idDonacion);
    }

    public String confirmarRecepcion(Long idDonacion) {
        return controlGestionDonacion.confirmarRecepcion(idDonacion);
    }

    public String vistaLista() {
        return LISTA;
    }

    public String vistaDetalle() {
        return DETALLE;
    }
}
