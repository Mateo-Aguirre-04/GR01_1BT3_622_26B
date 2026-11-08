package org.example.web;

import org.example.control.ControlCompra;
import org.example.model.Material;

import java.util.List;
import java.util.Objects;

public class InterfazCompra {
    static final String OPCION_MATERIAL_NUEVO = "__nuevo__";
    private static final String FORMULARIO = "/WEB-INF/vistas/registroCompra.jsp";

    private final ControlCompra controlCompra;

    public InterfazCompra(ControlCompra controlCompra) {
        this.controlCompra = Objects.requireNonNull(controlCompra, "El control de compras es obligatorio");
    }

    public List<Material> mostrarMateriales() {
        return controlCompra.listarMateriales();
    }

    public String registrarCompra(String material, String nombreNuevo, String unidadNueva, String cantidad) {
        if (OPCION_MATERIAL_NUEVO.equals(material)) {
            return controlCompra.registrarCompraMaterialNuevo(nombreNuevo, unidadNueva, cantidad);
        }
        return controlCompra.registrarCompra(material, cantidad);
    }

    public String formulario() {
        return FORMULARIO;
    }
}
