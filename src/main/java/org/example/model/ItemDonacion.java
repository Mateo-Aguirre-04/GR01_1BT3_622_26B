package org.example.model;

public class ItemDonacion {
    private final String material;
    private final int cantidad;

    public ItemDonacion(String material, int cantidad) {
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("El nombre del material es obligatorio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de " + material.trim() + " debe ser mayor que cero");
        }
        this.material = material.trim();
        this.cantidad = cantidad;
    }

    public boolean esDelMaterial(String nombre) {
        return material.equalsIgnoreCase(nombre);
    }

    public String getMaterial() {
        return material;
    }

    public int getCantidad() {
        return cantidad;
    }
}
