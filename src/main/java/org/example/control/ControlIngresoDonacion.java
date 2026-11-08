package org.example.control;

import org.example.model.Donacion;
import org.example.model.Inventario;
import org.example.model.ItemDonacion;
import org.example.model.Material;
import org.example.model.RepositorioDonaciones;

import java.util.List;
import java.util.Objects;

public class ControlIngresoDonacion {
    private final RepositorioDonaciones repositorio;
    private final Inventario inventario;

    public ControlIngresoDonacion(RepositorioDonaciones repositorio, Inventario inventario) {
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio de donaciones es obligatorio");
        this.inventario = Objects.requireNonNull(inventario, "El inventario es obligatorio");
    }

    public List<Donacion> listarDonacionesPorIngresar() {
        return repositorio.listarDonaciones().stream()
                .filter(donacion -> donacion.getEstado() == Donacion.Estado.RECIBIDA)
                .toList();
    }

    public synchronized String aprobarIngreso(Long idDonacion) {
        Donacion donacion = obtenerDonacion(idDonacion);
        donacion.marcarIngresada();
        StringBuilder confirmacion = new StringBuilder("Donación #" + donacion.getIdDonacion() + " ingresada al inventario.");
        for (ItemDonacion item : donacion.getItems()) {
            boolean materialNuevo = !inventario.existe(item.getMaterial());
            Material material = ingresarAlInventario(item.getMaterial(), item.getUnidad(), item.getCantidad());
            confirmacion.append(' ').append(describirIngreso(material, item.getCantidad(), materialNuevo));
        }
        return confirmacion.toString();
    }

    private Donacion obtenerDonacion(Long idDonacion) {
        if (idDonacion == null || idDonacion <= 0) {
            throw new IllegalArgumentException("El ID de la donación debe ser mayor que cero");
        }
        Donacion donacion = repositorio.obtenerDonacion(idDonacion);
        if (donacion == null) {
            throw new IllegalArgumentException("No se encontró una donación con ese ID");
        }
        return donacion;
    }

    private Material ingresarAlInventario(String nombre, String unidad, int cantidad) {
        Material material = inventario.buscar(nombre);
        if (material == null) {
            material = new Material(nombre, unidad, cantidad);
            inventario.agregar(material);
        } else {
            material.agregar(cantidad);
        }
        return material;
    }

    private String describirIngreso(Material material, int cantidad, boolean materialNuevo) {
        String unidad = material.getUnidad();
        if (materialNuevo) {
            return material.getNombre() + ": cantidad recibida " + cantidad + " " + unidad
                    + ", stock inicial registrado " + material.getStock() + " " + unidad + ".";
        }
        return material.getNombre() + ": cantidad ingresada " + cantidad + " " + unidad
                + ", stock actualizado " + material.getStock() + " " + unidad + ".";
    }
}
