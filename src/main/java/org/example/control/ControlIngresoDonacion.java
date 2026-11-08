package org.example.control;

import org.example.model.Donacion;
import org.example.model.Inventario;
import org.example.model.RepositorioDonaciones;

import java.util.List;

public class ControlIngresoDonacion {
    private final RepositorioDonaciones repositorio;
    private final Inventario inventario;

    public ControlIngresoDonacion(RepositorioDonaciones repositorio, Inventario inventario) {
        this.repositorio = repositorio;
        this.inventario = inventario;
    }

    public List<Donacion> listarDonacionesPorIngresar() {
        return repositorio.listarDonaciones();
    }
}
