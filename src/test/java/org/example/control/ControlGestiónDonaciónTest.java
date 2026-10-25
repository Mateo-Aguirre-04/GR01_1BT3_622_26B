package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Donacion;
import org.example.model.Refugio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlGestiónDonaciónTest {

    @Test
    void confirmarRecepcionCambiaLaDonacionARecibida() {
        Refugio refugio = new Refugio(1L, "Refugio Central", "Quito");
        Donacion donacion = new Donacion(refugio.obtenerIdDonacion(),
                new Adoptante("Ana", "Pérez", "0991234567", "ana@correo.com"), "");
        donacion.agregarMaterial("Croquetas", 10);
        refugio.agregarDonacion(donacion);
        ControlGestionDonacion control = new ControlGestionDonacion(refugio);
        String resultado = control.confirmarRecepcion(donacion.getIdDonacion());

        assertEquals(Donacion.Estado.RECIBIDA, donacion.getEstado());
        assertEquals("La donación fue recibida correctamente.", resultado);
        assertTrue(control.listarDonacionesPendientes().isEmpty());
    }
}