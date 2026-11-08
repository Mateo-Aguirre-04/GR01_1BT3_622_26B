package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;

import java.util.List;
import java.util.Objects;

public class ControlCompra {
    private final Inventario inventario;
    private final ValidadorCantidad validadorCantidad = new ValidadorCantidad();

    public ControlCompra(Inventario inventario) {
        this.inventario = Objects.requireNonNull(inventario, "El inventario es obligatorio");
    }

    public List<Material> listarMateriales() {
        return inventario.listarMateriales();
    }

    public synchronized String registrarCompra(String nombreMaterial, String cantidadTexto) {
        int cantidad = validadorCantidad.convertir(cantidadTexto);
        Material material = inventario.buscar(nombreMaterial);
        if (material == null) {
            throw new IllegalArgumentException("El material seleccionado no existe en el inventario.");
        }
        material.agregar(cantidad);
        return "Compra registrada: " + material.getNombre()
                + ", cantidad comprada " + cantidad + " " + material.getUnidad()
                + ", stock actualizado " + material.getStock() + " " + material.getUnidad() + ".";
    }

    public synchronized String registrarCompraMaterialNuevo(String nombre, String unidad, String cantidadTexto) {
        int cantidad = validadorCantidad.convertir(cantidadTexto);
        Material material = new Material(nombre, unidad, cantidad);
        inventario.agregar(material);
        return "Compra registrada: " + material.getNombre()
                + ", cantidad registrada " + cantidad + " " + material.getUnidad()
                + ", stock inicial " + material.getStock() + " " + material.getUnidad() + ".";
    }
}