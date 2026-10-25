package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Donacion;
import org.example.model.Refugio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlGestionDonacionTest {
    private Donacion donacion;
    private ControlGestionDonacion control;

    @BeforeEach
    void setUp() {
        Refugio refugio = new Refugio(1L, "Refugio Central", "Quito");
        donacion = new Donacion(refugio.obtenerIdDonacion(),
                new Adoptante("Ana", "Pérez", "0991234567", "ana@correo.com"), "");
        donacion.agregarMaterial("Croquetas", 10);
        refugio.agregarDonacion(donacion);
        control = new ControlGestionDonacion(refugio);
    }

    @Test
    void confirmarRecepcionCambiaLaDonacionARecibida() {
        String resultado = control.confirmarRecepcion(donacion.getIdDonacion());

        assertEquals(Donacion.Estado.RECIBIDA, donacion.getEstado());
        assertEquals("La donación fue recibida correctamente.", resultado);
        assertTrue(control.listarDonacionesPendientes().isEmpty());
    }

    @Test
    void confirmarDonacionYaRecibidaLanzaExcepcion() {
        control.confirmarRecepcion(donacion.getIdDonacion());

        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> control.confirmarRecepcion(donacion.getIdDonacion()));

        assertEquals("La donación ya fue recibida", error.getMessage());
        assertEquals(Donacion.Estado.RECIBIDA, donacion.getEstado());
    }
}
