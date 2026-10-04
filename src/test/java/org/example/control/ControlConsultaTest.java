package org.example.control;

import org.example.model.DatosRegistroPerro;
import org.example.model.Encargado;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.web.InterfazConsultaRegistro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlConsultaTest {
    private Refugio refugio;
    private ControlConsulta controlConsulta;
    private InterfazConsultaRegistro interfaz;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        ControlRegistro controlRegistro = new ControlRegistro(
                new Encargado(10L, "Encargado"), refugio);
        controlRegistro.registrarPerro(datos(201L, true));
        controlRegistro.registrarPerro(datos(202L, false));
        controlConsulta = new ControlConsulta(refugio);
        interfaz = new InterfazConsultaRegistro(controlConsulta);
    }

    @Test
    void consultarRegistroListaSoloPerrosDisponibles() {
        List<Perro> disponibles = interfaz.mostrarPerrosDisponibles();

        assertEquals(1, disponibles.size());
        assertEquals(201L, disponibles.getFirst().getIdPerro());
        assertTrue(disponibles.getFirst().estaDisponible());
    }

    @Test
    void consultarFichaRetornaPerroYLaInterfazLoEntregaParaMostrar() {
        Perro ficha = interfaz.solicitarFicha(201L);

        assertSame(refugio.obtenerFichaPerro(201L), interfaz.mostrarFicha(ficha));
        assertEquals("Perro{id=201, nombre='Luna', edad=3, estadoSalud='Sano', disponible=true}",
                ficha.obtenerFicha());
    }

    @Test
    void consultarFichaDePerroNoDisponibleConservaSuEstado() {
        Perro ficha = interfaz.solicitarFicha(202L);

        assertFalse(ficha.estaDisponible());
        assertEquals(202L, ficha.getIdPerro());
    }

    @Test
    void consultarFichaRetornaNullParaIdInvalidoOInexistente() {
        assertNull(controlConsulta.consultarFicha(null));
        assertNull(controlConsulta.consultarFicha(0L));
        assertNull(interfaz.solicitarFicha(999L));
    }

    private DatosRegistroPerro datos(Long idPerro, boolean disponible) {
        return new DatosRegistroPerro(
                idPerro,
                "Luna",
                3,
                "Cruza",
                "Sano",
                "1",
                disponible,
                LocalDate.of(2026, 1, 15),
                1L);
    }
}
