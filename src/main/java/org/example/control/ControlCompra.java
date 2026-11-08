package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;

class ControlCompra {

    private Inventario inventario;

    public ControlCompra(Inventario inventario) {
        this.inventario = inventario;
    }

    public String registrarCompra(String nombre, String cantidad) {
        int cantidadComprada = Integer.parseInt(cantidad);

        Material material = inventario.buscar(nombre);
        material.agregar(cantidadComprada);

        return "Compra registrada: " + nombre
                + ", cantidad comprada " + cantidadComprada
                + " " + material.getUnidad()
                + ", stock actualizado " + material.getStock()
                + " " + material.getUnidad() + ".";
    }
}