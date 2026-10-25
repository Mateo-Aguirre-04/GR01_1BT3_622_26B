package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonacionTest {

    private Refugio refugio;
    private Adoptante donante;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        donante = new Adoptante("Ana", "Pérez", "0991234567", "ana@correo.com");
    }
    @Test
    void donacionNuevaQuedaPendienteConSusMateriales() {
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(), donante, "Material nuevo");
        donacion.agregarMaterial("Croquetas", 10);
        donacion.agregarMaterial("Mantas", 3);

        refugio.agregarDonacion(donacion);

        assertEquals(Donacion.Estado.PENDIENTE, donacion.getEstado());
        assertEquals(2, donacion.cantidadDeMateriales());
        assertEquals(10, donacion.cantidadDe("Croquetas"));
        assertEquals(List.of(donacion), refugio.listarDonacionesPendientes());
    }
}
