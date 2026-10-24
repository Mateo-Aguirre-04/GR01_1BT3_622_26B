package org.example.web;

import org.example.control.ControlConsulta;
import org.example.control.ControlGestionVisita;
import org.example.control.ControlSolicitudVisita;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class VisitasIntegracionTest {
    @Test
    void solicitudDeInterfazLlegaAlListadoDeGestionYSeProcesa() {
        Refugio refugio = new Refugio(1L, "Refugio", "Ubicación");
        Perro perro = new Perro(8L, "Sol", 2, "Juguetón", "Saludable", "Patio",
                true, LocalDate.of(2026, 10, 1), refugio);
        refugio.agregarPerro(perro);
        ControlConsulta consultas = new ControlConsulta(refugio);
        ControlSolicitudVisita solicitudes = new ControlSolicitudVisita(refugio,
                Clock.fixed(Instant.parse("2026-10-24T17:00:00Z"), ZoneOffset.UTC));
        InterfazSolicitarVisita interfazSolicitar = new InterfazSolicitarVisita(consultas, solicitudes);
        InterfazGestionVisitas interfazGestionar =
                new InterfazGestionVisitas(new ControlGestionVisita(refugio));

        assertEquals(1, interfazSolicitar.mostrarPerrosDisponibles().size());
        Visita visita = interfazSolicitar.solicitarVisita(8L, LocalDate.of(2026, 10, 25),
                "Luis", "Paz", "0991234567", "luis@example.com");

        assertEquals(1, interfazGestionar.mostrarVisitasPendientes().size());
        assertTrue(interfazGestionar.aprobarVisita(visita.getIdVisita()).contains("aprobada"));
        assertEquals(Visita.Estado.APROBADA, visita.getEstado());
        assertTrue(interfazGestionar.mostrarVisitasPendientes().isEmpty());
    }
}
