package org.example.control;

import org.example.model.HistorialMovimientos;
import org.example.model.Inventario;
import org.example.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void registrarConsumoDeTodasLasExistenciasDejaElStockEnCeroYConservaElMaterial() {
        Inventario inventario = new Inventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        HistorialMovimientos historial = new HistorialMovimientos();
        ControlConsumo control = new ControlConsumo(inventario, historial);

        String confirmacion = control.registrarConsumo("Croquetas", "20", "Alimentación semanal");

        assertEquals(0, inventario.buscar("Croquetas").getStock());
        assertTrue(inventario.existe("Croquetas"));
        assertEquals("Consumo registrado: Croquetas, cantidad consumida 20 kg, stock restante 0 kg.",
                confirmacion);
    }
}