package org.example.control;

import org.example.model.HistorialMovimientos;
import org.example.model.Inventario;
import org.example.model.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlConsumoTest {

    @Test
    void registrarConsumoConExistenciasSuficientesDescuentaElStockYGuardaLaSalida() {
        Inventario inventario = new Inventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        HistorialMovimientos historial = new HistorialMovimientos();
        ControlConsumo control = new ControlConsumo(inventario, historial);

        String confirmacion = control.registrarConsumo("Croquetas", "5", "Alimentación diaria");

        assertEquals(15, inventario.buscar("Croquetas").getStock());
        assertEquals("Consumo registrado: Croquetas, cantidad consumida 5 kg, stock restante 15 kg.",
                confirmacion);
        assertEquals(1, historial.listar().size());
    }
}