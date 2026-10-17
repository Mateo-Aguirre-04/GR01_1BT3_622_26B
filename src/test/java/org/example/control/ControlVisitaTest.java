package org.example.control;

import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControlVisitaTest {
    Visita visita = null;
    ControlVisita control = null;

    @BeforeEach
    public void setUp(){
        visita = new Visita();
        control = new ControlVisita();
    }
    @Test
    void aceptarVisitaPendienteCambiaEstadoAAceptada() {
        // Act
        control.aceptarVisita(visita);

        // Assert
        assertEquals(Visita.Estado.ACEPTADA, visita.getEstado());
    }

    @Test
    void aceptarVisitaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> control.aceptarVisita(null));
    }
}