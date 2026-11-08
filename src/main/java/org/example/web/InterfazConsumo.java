package org.example.web;

import org.example.control.ControlConsumo;
import org.example.model.Material;

import java.util.List;
import java.util.Objects;

public class InterfazConsumo {
    private static final String FORMULARIO = "/WEB-INF/vistas/registroConsumo.jsp";

    private final ControlConsumo controlConsumo;

    public InterfazConsumo(ControlConsumo controlConsumo) {
        this.controlConsumo = Objects.requireNonNull(controlConsumo, "El control de consumos es obligatorio");
    }

    public List<Material> mostrarMateriales() {
        return controlConsumo.listarMateriales();
    }

    public String registrarConsumo(String material, String cantidad, String motivo) {
        return controlConsumo.registrarConsumo(material, cantidad, motivo);
    }

    public String formulario() {
        return FORMULARIO;
    }
}
