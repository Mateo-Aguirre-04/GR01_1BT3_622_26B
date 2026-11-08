package org.example.control;

import org.example.model.Inventario;
import org.example.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlCompraTest {

    private Inventario inventario;
    private ControlCompra control;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.agregar(new Material("Croquetas", "kg", 20));
        control = new ControlCompra(inventario);
    }

    @Test
    void registrarCompraDeMaterialExistenteSumaLaCantidadAlStock() {
        String confirmacion = control.registrarCompra("Croquetas", "15");

        assertEquals("Compra registrada: Croquetas, cantidad comprada 15 kg, stock actualizado 35 kg.",
                confirmacion);
    }
}