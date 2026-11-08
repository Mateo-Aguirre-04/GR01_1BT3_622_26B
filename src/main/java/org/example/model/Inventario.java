package org.example.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Inventario {
    private final Map<String, Material> materiales = new LinkedHashMap<>();

    public synchronized void agregar(Material material) {
        Objects.requireNonNull(material, "El material es obligatorio.");
        if (existe(material.getNombre())) {
            throw new IllegalArgumentException("El material ya está registrado en el inventario.");
        }
        materiales.put(clave(material.getNombre()), material);
    }

    public synchronized Material buscar(String nombre) {
        if (nombre == null) {
            return null;
        }
        return materiales.get(clave(nombre));
    }

    public synchronized Material registrarIngreso(String nombre, String unidad, int cantidad) {
        Material material = buscar(nombre);
        if (material == null) {
            material = new Material(nombre, unidad, cantidad);
            agregar(material);
        } else {
            material.agregar(cantidad);
        }
        return material;
    }

    public synchronized boolean existe(String nombre) {
        return buscar(nombre) != null;
    }

    public synchronized List<Material> listarMateriales() {
        return List.copyOf(materiales.values());
    }

    private String clave(String nombre) {
        return nombre.trim().toLowerCase(Locale.ROOT);
    }
}
