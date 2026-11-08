package org.example.control;

import org.example.model.Inventario;
import org.example.model.RegistroMovimientos;

public class ControlConsumo {
    private final Inventario inventario;
    private final RegistroMovimientos registroMovimientos;

    public ControlConsumo(Inventario inventario, RegistroMovimientos registroMovimientos) {
        this.inventario = inventario;
        this.registroMovimientos = registroMovimientos;
    }

    public String registrarConsumo(String nombreMaterial, String cantidadTexto, String motivo) {
        return null;
    }
}