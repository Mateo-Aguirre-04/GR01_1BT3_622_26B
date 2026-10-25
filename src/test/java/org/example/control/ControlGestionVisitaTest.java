package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControlGestionVisitaTest {
    private Refugio refugio;
    private ControlGestionVisita control;
    private Visita visitaAprobada;
    private Visita visitaRechazada;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio", "Ubicación");
        Perro perro = new Perro(5L, "Luna", 3, "Amigable", "Saludable", "Patio",
                true, LocalDate.of(2026, 10, 1), refugio);
        refugio.agregarPerro(perro);
        control = new ControlGestionVisita(refugio);
        visitaAprobada = crearVisita(1L, perro);
        visitaRechazada = crearVisita(2L, perro);
        refugio.agregarVisita(visitaAprobada);
        refugio.agregarVisita(visitaRechazada);
    }

    @Test
    void visitasPendientesPuedenAprobarseYRechazarse() {
        assertTrue(control.aprobarVisita(1L).contains("aprobada"));
        assertTrue(control.rechazarVisita(2L).contains("rechazada"));

        assertEquals(Visita.Estado.APROBADA, visitaAprobada.getEstado());
        assertEquals(Visita.Estado.RECHAZADA, visitaRechazada.getEstado());
        assertTrue(control.listarVisitasPendientes().isEmpty());
    }

    @Test
    void visitasProcesadasNoSePuedenVolverAProcesarNiCambiarEstado() {
        control.aprobarVisita(1L);
        control.rechazarVisita(2L);

        assertTrue(control.rechazarVisita(1L).contains("ya fue procesada"));
        assertTrue(control.aprobarVisita(2L).contains("ya fue procesada"));
        assertTrue(control.aprobarVisita(1L).contains("ya fue procesada"));
        assertTrue(control.rechazarVisita(2L).contains("ya fue procesada"));
        assertEquals(Visita.Estado.APROBADA, visitaAprobada.getEstado());
        assertEquals(Visita.Estado.RECHAZADA, visitaRechazada.getEstado());
        assertEquals(List.of(), refugio.listarVisitasPendientes());
    }

    @Test
    void idsInvalidosOInexistentesSeInformanSinCambiarColecciones() {
        assertThrows(IllegalArgumentException.class, () -> control.aprobarVisita(99L));
        assertThrows(IllegalArgumentException.class, () -> control.rechazarVisita(null));
        assertEquals(2, refugio.listarVisitas().size());
        assertEquals(2, control.listarVisitasPendientes().size());
    }

    private Visita crearVisita(Long id, Perro perro) {
        return new Visita(id, LocalDate.now().plusDays(1),
                new Adoptante("Ana", "López", "0991234567", "ana@example.com"), perro);
    }
}
