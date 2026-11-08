package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;

public class ControlCompra {

    private Inventario inventario;

    public ControlCompra(Inventario inventario) {
        this.inventario = inventario;
    }

    public String registrarCompra(String nombre, String cantidad) {
        if (cantidad == null || cantidad.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe ingresar una cantidad válida."
            );
        }

        int cantidadComprada;

        try {
            cantidadComprada = Integer.parseInt(cantidad.trim());

            if (cantidadComprada <= 0) {
                throw new IllegalArgumentException(
                        "Debe ingresar una cantidad válida."
                );
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Debe ingresar una cantidad válida."
            );
        }

        Material material = inventario.buscar(nombre);
        material.agregar(cantidadComprada);

        return "Compra registrada: " + nombre
                + ", cantidad comprada " + cantidadComprada
                + " " + material.getUnidad()
                + ", stock actualizado " + material.getStock()
                + " " + material.getUnidad() + ".";
    }

    public String registrarCompraMaterialNuevo(
            String nombre, String unidad, String cantidad) {

        int cantidadComprada = Integer.parseInt(cantidad);

        Material material = new Material(nombre, unidad, cantidadComprada);

        inventario.agregar(material);

        return "Compra registrada: " + nombre
                + ", cantidad registrada " + cantidadComprada
                + " " + unidad
                + ", stock inicial " + material.getStock()
                + " " + unidad + ".";
    }
}