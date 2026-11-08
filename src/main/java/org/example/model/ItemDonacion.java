package org.example.model;

public class ItemDonacion {
    private static final String UNIDAD_POR_DEFECTO = "unidades";

    private final String material;
    private final int cantidad;
    private final String unidad;

    public ItemDonacion(String material, int cantidad) {
        this(material, cantidad, UNIDAD_POR_DEFECTO);
    }

    public ItemDonacion(String material, int cantidad, String unidad) {
        if (material == null || material.isBlank()) {
            throw new IllegalArgumentException("El nombre del material es obligatorio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de " + material.trim() + " debe ser mayor que cero");
        }
        this.material = material.trim();
        this.cantidad = cantidad;
        this.unidad = unidad == null || unidad.isBlank() ? UNIDAD_POR_DEFECTO : unidad.trim();
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

    public String getUnidad() {
        return unidad;
    }
}
