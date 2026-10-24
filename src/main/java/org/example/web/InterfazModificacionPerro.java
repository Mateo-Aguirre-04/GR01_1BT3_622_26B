package org.example.web;

import org.example.control.ControlModificacionPerro;
import org.example.model.Perro;

import java.util.Map;
import java.util.Objects;

/** Boundary del caso de uso "Modificar datos del perro". */
public class InterfazModificacionPerro {
    private final ControlModificacionPerro control;

    public InterfazModificacionPerro(ControlModificacionPerro control) {
        this.control = Objects.requireNonNull(control, "El control de modificación es obligatorio");
    }

    public Perro seleccionarPerro(Long idPerro) {
        return control.obtenerPerro(idPerro);
    }

    public Perro enviarDatosPerro(Long idPerro, Map<String, String> datos) {
        return control.modificarPerro(idPerro, datos);
    }

    public String mostrarFormulario() {
        return "/WEB-INF/vistas/modificarPerro.jsp";
    }
}
