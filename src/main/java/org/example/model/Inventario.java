package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private final List<Material> materiales = new ArrayList<>();

    public void agregar(Material material) {
        materiales.add(material);
    }

    public boolean existe(String nombre) {
        return buscar(nombre) != null;
    }

    public Material buscar(String nombre) {
        for (Material material : materiales) {
            if (material.getNombre().trim()
                    .equalsIgnoreCase(nombre.trim())) {
                return material;
            }
        }
        return null;
    }
}