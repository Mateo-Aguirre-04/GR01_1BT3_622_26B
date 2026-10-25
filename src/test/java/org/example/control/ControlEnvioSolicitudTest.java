package org.example.control;

import org.example.model.Encargado;
import org.example.model.Refugio;
import org.example.model.SolicitudAdopcion;
import org.example.web.InterfazAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlEnvioSolicitudTest {
    private Refugio refugio;
    private ControlEnvioSolicitud control;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        ControlRegistro controlRegistro = new ControlRegistro(new Encargado(10L, "Encargado"), refugio);
        controlRegistro.registrarPerro(datosPerro(301L, true));
        controlRegistro.registrarPerro(datosPerro(302L, false));
        control = new ControlEnvioSolicitud(refugio);
    }

    @Test
    void enviarFormularioCreaYAsociaSolicitud() {
        SolicitudAdopcion solicitud = control.enviarFormulario(301L, datosFormulario());

        assertEquals(1L, solicitud.getIdSolicitud());
        assertEquals(301L, solicitud.obtenerIdPerro());
        assertEquals("Pendiente", solicitud.getEstado());
        assertEquals("Ada", solicitud.obtenerDatosFormulario().get("nombres"));
        assertEquals(solicitud, refugio.obtenerSolicitud(solicitud.getIdSolicitud()));
        assertEquals(1, refugio.listarSolicitudes().size());
    }

    @Test
    void enviarFormularioRechazaPerroNoDisponibleAntesDeValidarDatos() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> control.enviarFormulario(302L, Map.of()));

        assertEquals("Perro no disponible", exception.getMessage());
        assertTrue(refugio.listarSolicitudes().isEmpty());
    }

    @Test
    void enviarFormularioRechazaDatosIncompletosSinCrearSolicitud() {
        Map<String, String> datos = datosFormulario();
        datos.put("correo", " ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> control.enviarFormulario(301L, datos));

        assertEquals("Datos incompletos", exception.getMessage());
        assertTrue(refugio.listarSolicitudes().isEmpty());
        assertTrue(refugio.verificarDisponibilidad(301L));
    }

    @Test
    void enviarFormularioRechazaPerroInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> control.enviarFormulario(999L, datosFormulario()));
        assertTrue(refugio.listarSolicitudes().isEmpty());
    }

    @Test
    void marcarNoDisponibleCambiaElEstadoDelPerro() {
        refugio.marcarNoDisponible(301L);

        assertFalse(refugio.verificarDisponibilidad(301L));
    }

    @Test
    void siguienteSolicitudUsaIdNoConsumidoPorEnvioInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> control.enviarFormulario(301L, Map.of()));

        SolicitudAdopcion solicitud = control.enviarFormulario(301L, datosFormulario());

        assertEquals(1L, solicitud.getIdSolicitud());
        assertFalse(refugio.listarSolicitudes().isEmpty());
    }

    @Test
    void interfazAdopcionCoordinaManifestacionYEnvioDelFormulario() {
        InterfazAdopcion interfaz = new InterfazAdopcion(
                new ControlFormulario(new ControlConsulta(refugio)), control);
        Map<String, String> datos = datosFormulario();
        datos.put("idPerro", "301");

        var perro = interfaz.manifestarInteres(301L);
        SolicitudAdopcion solicitud = interfaz.enviarDatosFormulario(datos);

        assertEquals(301L, perro.getIdPerro());
        assertEquals(301L, solicitud.obtenerIdPerro());
        assertEquals("/WEB-INF/vistas/formularioAdopcion.jsp", interfaz.mostrarFormulario(perro));
        assertEquals("/WEB-INF/vistas/confirmacionAdopcion.jsp",
                interfaz.mostrarConfirmacion(solicitud.getIdSolicitud()));
    }

    private jakarta.servlet.http.HttpServletRequest datosPerro(Long idPerro, boolean disponible) {
        return SolicitudRegistroTestFactory.crear(idPerro, "Luna", 3, "Cruza", "Sana", "1",
                disponible, LocalDate.of(2026, 1, 15), 1L);
    }

    private Map<String, String> datosFormulario() {
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("nombres", "Ada");
        datos.put("apellidos", "Lovelace");
        datos.put("telefono", "0991234567");
        datos.put("correo", "ada@example.com");
        datos.put("infoDomicilio", "Casa con patio");
        datos.put("experienciaMascotas", "He cuidado perros");
        datos.put("condicionesHogar", "Espacio seguro");
        datos.put("disponibilidadTiempo", "Varias horas al día");
        datos.put("aceptacionTerminos", "true");
        return datos;
    }
}
