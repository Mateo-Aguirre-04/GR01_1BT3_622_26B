package org.example.model;

import java.time.LocalDate;

public class MovimientoSalida {
    private final String material;
    private final int cantidad;
    private final String unidad;
    private final String motivo;
    private final LocalDate fecha;

    public MovimientoSalida(String material, int cantidad, String unidad, String motivo) {
        this.material = material;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.motivo = motivo;
        this.fecha = LocalDate.now();
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

    public String getMotivo() {
        return motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}