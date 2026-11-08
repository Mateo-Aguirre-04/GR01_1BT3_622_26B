package org.example.model;

import java.util.List;

public class HistorialMovimientos implements RegistroMovimientos {

    @Override
    public void guardar(MovimientoSalida movimiento) {
    }

    @Override
    public List<MovimientoSalida> listar() {
        return List.of();
    }
}