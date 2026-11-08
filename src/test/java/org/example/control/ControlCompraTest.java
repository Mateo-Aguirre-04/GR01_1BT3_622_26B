package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlCompraTest {

    private Inventario inventario;
    private ControlCompra control;
    private Material material;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        inventario.agregar(new Material("Arena sanitaria", "sacos", 4));
        control = new ControlCompra(inventario);
    }

    @Test
    void registrarCompraDeMaterialExistenteSumaLaCantidadAlStock() {
        String confirmacion = control.registrarCompra("Croquetas", "15");

        assertEquals("Compra registrada: Croquetas, cantidad comprada 15 kg, stock actualizado 35 kg.",
                confirmacion);
    }

    @Test
    void registrarCompraDeMaterialNuevoLoCreaConLaCantidadComprada() {
        String confirmacion = control.registrarCompraMaterialNuevo(
                "Arena sanitaria", "sacos", "4");inventario.agregar(new Material("Arena sanitaria", "sacos", 4));

        assertTrue(inventario.existe("Arena sanitaria"));

        Material arena = inventario.buscar("Arena sanitaria");

        assertEquals(4, arena.getStock());
        assertEquals("sacos", arena.getUnidad());

        assertEquals(
                "Compra registrada: Arena sanitaria, cantidad registrada 4 sacos, stock inicial 4 sacos.",
                confirmacion
        );
    }
}