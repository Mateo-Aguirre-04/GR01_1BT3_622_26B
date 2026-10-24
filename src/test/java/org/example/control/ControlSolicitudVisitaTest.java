package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControlSolicitudVisitaTest {
    private static final LocalDate HOY = LocalDate.of(2026, 10, 24);
    private Refugio refugio;
    private Perro perro;
    private ControlSolicitudVisita control;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio", "Ubicación");
        perro = new Perro(5L, "Luna", 3, "Amigable", "Saludable", "Patio",
                true, LocalDate.of(2026, 10, 1), refugio);
        refugio.agregarPerro(perro);
        Clock reloj = Clock.fixed(Instant.parse("2026-10-24T17:00:00Z"), ZoneOffset.UTC);
        control = new ControlSolicitudVisita(refugio, reloj);
    }

    @Test
    void fechaFuturaRegistraVisitaPendienteConVisitanteYPerro() {
        Visita visita = solicitar(HOY.plusDays(1));

        assertEquals(Visita.Estado.PENDIENTE, visita.getEstado());
        assertEquals(1L, visita.getIdVisita());
        assertEquals(HOY.plusDays(1), visita.getFechaVisita());
        assertSame(perro, visita.getPerro());
        assertEquals("Ana", visita.getVisitante().getNombres());
        assertEquals(List.of(visita), refugio.listarVisitas());
    }

    @Test
    void fechasPasadaYDeHoySeRechazanSinModificarColeccion() {
        assertThrows(IllegalArgumentException.class, () -> solicitar(HOY.minusDays(1)));
        assertThrows(IllegalArgumentException.class, () -> solicitar(HOY));
        assertTrue(refugio.listarVisitas().isEmpty());
    }

    @Test
    void fechaNulaYDatosObligatoriosSeRechazanSinRegistrar() {
        assertThrows(IllegalArgumentException.class, () -> solicitar(null));
        assertThrows(IllegalArgumentException.class,
                () -> control.solicitarVisita(5L, HOY.plusDays(1), " ", "Apellido", "099", "a@b.com"));
        assertTrue(refugio.listarVisitas().isEmpty());
    }

    @Test
    void perroInexistenteONoDisponibleNoGeneraVisita() {
        assertThrows(IllegalArgumentException.class,
                () -> control.solicitarVisita(99L, HOY.plusDays(1), "Ana", "López", "099", "ana@example.com"));
        perro.setDisponible(false);
        assertThrows(IllegalStateException.class, () -> solicitar(HOY.plusDays(1)));
        assertTrue(refugio.listarVisitas().isEmpty());
    }

    @Test
    void idsDeVisitaNoSePuedenDuplicar() {
        Visita existente = new Visita(1L, HOY.plusDays(1),
                new org.example.model.Adoptante("Ana", "López", "099", "ana@example.com"), perro);
        refugio.agregarVisita(existente);

        Visita duplicada = new Visita(1L, HOY.plusDays(2),
                new org.example.model.Adoptante("Eva", "Pérez", "098", "eva@example.com"), perro);
        assertThrows(IllegalArgumentException.class, () -> refugio.agregarVisita(duplicada));
        assertEquals(1, refugio.listarVisitas().size());
    }

    private Visita solicitar(LocalDate fecha) {
        return control.solicitarVisita(5L, fecha, "Ana", "López", "0991234567", "ana@example.com");
    }
}
