package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;
import org.example.model.MovimientoSalida;
import org.example.model.RegistroMovimientos;

public class ControlConsumo {
    private static final String SIN_EXISTENCIAS = "No existen suficientes unidades disponibles.";

    private final Inventario inventario;
    private final RegistroMovimientos registroMovimientos;

    public ControlConsumo(Inventario inventario, RegistroMovimientos registroMovimientos) {
        this.inventario = inventario;
        this.registroMovimientos = registroMovimientos;
    }

    public String registrarConsumo(String nombreMaterial, String cantidadTexto, String motivo) {
        int cantidad = Integer.parseInt(cantidadTexto.trim());
        Material material = inventario.buscar(nombreMaterial);
        int stockRestante = material.getStock() - cantidad;
        if (stockRestante < 0) {
            throw new IllegalArgumentException(SIN_EXISTENCIAS);
        }
        material.descontar(cantidad);
        registroMovimientos.guardar(new MovimientoSalida(material.getNombre(), cantidad, material.getUnidad(), motivo));
        return construirConfirmacion(material, cantidad, stockRestante);
    }

    private String construirConfirmacion(Material material, int cantidad, int stockRestante) {
        return "Consumo registrado: " + material.getNombre()
                + ", cantidad consumida " + cantidad + " " + material.getUnidad()
                + ", stock restante " + stockRestante + " " + material.getUnidad() + ".";
    }
}