package org.example.model;

public class Material {

    private String nombre;
    private String unidad;
    private int stock;

    public Material(String nombre, String unidad, int stock) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.stock = stock;
    }

    public void agregar(int cantidad) {
        stock += cantidad;
    }

    public synchronized void descontar(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (!puedeDescontar(cantidad)) {
            throw new IllegalStateException("El stock no puede quedar negativo.");
        }
        stock -= cantidad;
    }

    public synchronized boolean puedeDescontar(int cantidad) {
        return stock - cantidad >= 0;
    }

    public int getStock() {
        return stock;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getNombre() {
        return nombre;
    }
}