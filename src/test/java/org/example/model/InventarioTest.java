package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventarioTest {
    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
    }

    @Test
    void agregarMaterialLoRegistraYPermiteSumarSuStock() {
        Material croquetas = new Material("Croquetas", "kg", 20);
        inventario.agregar(croquetas);

        croquetas.agregar(5);

        assertTrue(inventario.existe("Croquetas"));
        assertSame(croquetas, inventario.buscar("  croquetas "));
        assertEquals(25, croquetas.getStock());
    }
}