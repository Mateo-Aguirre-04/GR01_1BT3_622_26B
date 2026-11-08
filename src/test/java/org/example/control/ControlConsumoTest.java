package org.example.control;

import org.example.model.HistorialMovimientos;
import org.example.model.Inventario;
import org.example.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlConsumoTest {
    private Inventario inventario;
    private HistorialMovimientos historial;
    private ControlConsumo control;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        historial = new HistorialMovimientos();
        control = new ControlConsumo(inventario, historial);
    }

    @Test
    void registrarConsumoConExistenciasSuficientesDescuentaElStockYGuardaLaSalida() {
        String confirmacion = control.registrarConsumo("Croquetas", "5", "Alimentación diaria");

        assertEquals(15, inventario.buscar("Croquetas").getStock());
        assertEquals("Consumo registrado: Croquetas, cantidad consumida 5 kg, stock restante 15 kg.",
                confirmacion);
        assertEquals(1, historial.listar().size());
    }
}