package org.example.control;

import org.example.model.Encargado;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.web.InterfazGestionPerros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlRegistroTest {
    private Refugio refugio;
    private Encargado encargado;
    private ControlRegistro controlRegistro;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        encargado = new Encargado(10L, "Encargado");
        controlRegistro = new ControlRegistro(encargado, refugio);
    }

    @Test
    void registrarPerroAgregaAlRefugioYAsociaAlEncargado() {
        InterfazGestionPerros interfaz = new InterfazGestionPerros(controlRegistro);

        Perro registrado = interfaz.enviarDatosPerros(datosValidos(100L, 1L, true));

        assertSame(registrado, refugio.obtenerFichaPerro(100L));
        assertSame(registrado, encargado.getUltimoRegistro());
        assertEquals("Luna", registrado.getNombre());
        assertEquals(3, registrado.getEdad());
        assertEquals(LocalDate.of(2026, 1, 15), registrado.getFechaIngreso());
        assertEquals(1L, refugio.obtenerId());
        assertEquals(1, refugio.listarPerrosDisponibles().size());
        assertEquals("/WEB-INF/vistas/registroPerro.jsp", interfaz.mostrarFormularioRegistro());
        assertEquals("/WEB-INF/vistas/confirmacionRegistro.jsp", interfaz.mostrarConfirmacion());
    }

    @Test
    void registrarPerroNoDisponibleNoApareceEnCatalogo() {
        controlRegistro.registrarPerro(datosValidos(101L, 1L, false));

        assertTrue(refugio.listarPerrosDisponibles().isEmpty());
    }

    @Test
    void registrarPerroRechazaDatosInvalidosSinModificarElRefugio() {
        HttpServletRequest datos = SolicitudRegistroTestFactory.crear(
                102L, " ", 2, "Cruza", "Sano", "1", true, LocalDate.now(), 1L);

        assertThrows(IllegalArgumentException.class, () -> controlRegistro.registrarPerro(datos));

        assertTrue(refugio.getPerros().isEmpty());
        assertNull(encargado.getUltimoRegistro());
    }

    @Test
    void registrarPerroRechazaIdDuplicado() {
        controlRegistro.registrarPerro(datosValidos(103L, 1L, true));

        assertThrows(IllegalArgumentException.class,
                () -> controlRegistro.registrarPerro(datosValidos(103L, 1L, true)));

        assertEquals(1, refugio.getPerros().size());
    }

    @Test
    void registrarPerroRechazaRefugioDistintoAlConfigurado() {
        assertThrows(IllegalArgumentException.class,
                () -> controlRegistro.registrarPerro(datosValidos(104L, 2L, true)));

        assertTrue(refugio.getPerros().isEmpty());
    }

    private HttpServletRequest datosValidos(Long idPerro, Long idRefugio, boolean disponible) {
        return SolicitudRegistroTestFactory.crear(
                idPerro,
                "Luna",
                3,
                "Cruza",
                "Sano",
                "1",
                disponible,
                LocalDate.of(2026, 1, 15),
                idRefugio);
    }
}
