package org.example.control;

import org.example.model.Donacion;
import org.example.model.Inventario;
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

    public String aprobarIngreso(Long idDonacion) {
        return null;
    }

}
