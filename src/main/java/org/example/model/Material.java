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