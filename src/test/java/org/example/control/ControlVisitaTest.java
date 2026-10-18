package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControlVisitaTest {
    Visita visita = null;
    ControlVisita control = null;
    Refugio refugio = null;

    @BeforeEach
    public void setUp(){
        visita = new Visita(LocalDate.now().plusDays(1));
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
    void aceptarVisitaYaRechazadaLanzaExcepcion() {
        control.rechazarVisita(visita);

        assertThrows(IllegalStateException.class, () -> control.aceptarVisita(visita));

        assertEquals(Visita.Estado.RECHAZADA, visita.getEstado());
    }

    @Test
    void rechazarVisitaYaRechazadaLanzaExcepcion() {
        control.rechazarVisita(visita);

        assertThrows(IllegalStateException.class, () -> control.rechazarVisita(visita));

        assertEquals(Visita.Estado.RECHAZADA, visita.getEstado());
    }

    @Test
    void rechazarVisitaYaAceptadaLanzaExcepcion() {
        control.aceptarVisita(visita);

        assertThrows(IllegalStateException.class, () -> control.rechazarVisita(visita));

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

    @Test
    void solicitarVisitaConservaLaFechaSolicitada() {
        Perro perro = new Perro(1L,"-", 3, "-", "-", "-",
                true, LocalDate.of(2026, 3, 10), refugio);
        LocalDate fecha = LocalDate.now().plusDays(3);

        Visita resultado = control.solicitarVisita(perro, fecha);

        assertEquals(fecha, resultado.getFechaVisita());
    }

    //Prueba con parámetros
    static Stream<Arguments> fechasInvalidas() {
        return Stream.of(
                Arguments.of("ayer", LocalDate.now().minusDays(1)),
                Arguments.of("hace una semana", LocalDate.now().minusDays(7)),
                Arguments.of("hoy", LocalDate.now()),
                Arguments.of("fecha nula", null)
        );
    }

    @ParameterizedTest(name = "rechaza fecha: {0}")
    @MethodSource("fechasInvalidas")
    void solicitarVisitaConFechaInvalidaLanzaExcepcion(String descripcion, LocalDate fecha) {
        // Given: perro disponible, así la única causa posible del error es la fecha
        Perro perro = new Perro(1L, "-", 3, "-", "-", "-", true,
                LocalDate.of(2026, 3, 10), refugio);

        // When + Then
        assertThrows(IllegalArgumentException.class,
                () -> control.solicitarVisita(perro, fecha));
    }

    @Test
    void rechazarVisitaPendienteCambiaEstadoARechazada() {
        assertEquals(Visita.Estado.PENDIENTE, visita.getEstado());

        control.rechazarVisita(visita);

        assertEquals(Visita.Estado.RECHAZADA, visita.getEstado());
    }
}