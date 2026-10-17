package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControlVisitaTest {
    Visita visita = null;
    ControlVisita control = null;
    Refugio refugio = null;

    @BeforeEach
    public void setUp(){
        visita = new Visita();
        control = new ControlVisita();
        refugio = new Refugio(1L, "-", "-");
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

    @Test

    void aceptarVisitaYaAceptadaLanzaExcepcion(){
        // Arrange
        control.aceptarVisita(visita);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> control.aceptarVisita(visita));

        // Assert
        assertEquals(Visita.Estado.ACEPTADA, visita.getEstado());

    }

    @Test
    void solicitarVisitaConPerroDisponibleYFechaFuturaCreaVisitaPendiente() {
        Perro perro = new Perro(1L,
                "-",
                3,
                "-",
                "-",
                "-",
                true,
                LocalDate.of(2026, 3, 10),
                refugio);

        Visita resultado = control.solicitarVisita(perro, LocalDate.now().plusDays(1));
        assertSame(Visita.Estado.PENDIENTE, resultado.getEstado());
    }
}