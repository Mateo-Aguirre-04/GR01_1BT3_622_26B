package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class HistorialMovimientos implements RegistroMovimientos {
    private final List<MovimientoSalida> movimientos = new ArrayList<>();

    @Override
    public void guardar(MovimientoSalida movimiento) {
        movimientos.add(movimiento);
    }

    @Override
    public List<MovimientoSalida> listar() {
        return List.copyOf(movimientos);
    }
}