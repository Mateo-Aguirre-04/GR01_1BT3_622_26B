package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Donacion {
    private final Long idDonacion;
    private final Adoptante donante;
    private final String descripcion;
    private final LocalDate fechaRegistro;
    private final List<ItemDonacion> items = new ArrayList<>();
    private Estado estado;

    public Donacion(Long idDonacion, Adoptante donante, String descripcion) {
        if (idDonacion == null || idDonacion <= 0) {
            throw new IllegalArgumentException("El ID de la donación debe ser mayor que cero");
        }
        this.idDonacion = idDonacion;
        this.donante = Objects.requireNonNull(donante, "Los datos del donante son obligatorios");
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.fechaRegistro = LocalDate.now();
        this.estado = Estado.PENDIENTE;
    }

    public synchronized void agregarMaterial(String material, int cantidad) {
        items.add(new ItemDonacion(material, cantidad));
    }

    public synchronized int cantidadDeMateriales() {
        return items.size();
    }

    public synchronized int cantidadDe(String material) {
        for (ItemDonacion item : items) {
            if (item.esDelMaterial(material)) {
                return item.getCantidad();
            }
        }
        return 0;
    }

    public synchronized List<ItemDonacion> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public synchronized void marcarRecibida() {
        validarPendiente();
        estado = Estado.RECIBIDA;
    }

    private void validarPendiente() {
        if (estado != Estado.PENDIENTE) {
            throw new IllegalStateException("La donación ya fue recibida");
        }
    }

    public Long getIdDonacion() {
        return idDonacion;
    }

    public Adoptante getDonante() {
        return donante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public Estado getEstado() {
        return estado;
    }

    public enum Estado {
        PENDIENTE,
        RECIBIDA
    }
}
