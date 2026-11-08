package org.example.model;

import java.util.List;

public interface RegistroMovimientos {
    void guardar(MovimientoSalida movimiento);

    List<MovimientoSalida> listar();
}