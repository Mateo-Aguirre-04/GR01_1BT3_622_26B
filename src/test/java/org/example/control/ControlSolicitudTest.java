package org.example.control;

import org.example.model.Encargado;
import org.example.model.Refugio;
import org.example.model.SolicitudAdopcion;
import org.example.web.InterfazGestionSolicitud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlSolicitudTest {
    private Refugio refugio;
    private ControlSolicitud control;
    private InterfazGestionSolicitud interfaz;
    private SolicitudAdopcion solicitud;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        ControlRegistro controlRegistro = new ControlRegistro(
                new Encargado(10L, "Encargado"), refugio);
        controlRegistro.registrarPerro(SolicitudRegistroTestFactory.crear(
                501L, "Luna", 3, "Cruza", "Sana", "1", true,
                LocalDate.of(2026, 1, 15), 1L));
        control = new ControlSolicitud(refugio);
        solicitud = new ControlEnvioSolicitud(refugio)
                .enviarFormulario(501L, datosFormulario());
        interfaz = new InterfazGestionSolicitud(control);
    }

    @Test
    void interfazListaSeleccionaYPresentaElDetalleDeLaSolicitud() {
        List<SolicitudAdopcion> solicitudes = interfaz.mostrarSolicitudes();
        SolicitudAdopcion seleccionada = interfaz.seleccionarSolicitud(solicitud.getIdSolicitud());
        Map<String, String> datos = interfaz.obtenerFormulario(solicitud.getIdSolicitud());
        String ficha = interfaz.obtenerFichaPerro(solicitud.getIdSolicitud());

        assertEquals(1, solicitudes.size());
        assertEquals(solicitud, interfaz.mostrarDetalleSolicitud(seleccionada));
        assertEquals("Ada", interfaz.mostrarDatosAdoptante(datos).get("nombres"));
        assertEquals(solicitud.getIdPerro(), seleccionada.obtenerIdPerro());
        assertTrue(interfaz.mostrarFichaPerro(ficha).contains("Luna"));
    }

    @Test
    void aprobarSolicitudDisponibleApruebaYMarcaPerroNoDisponible() {
        String resultado = interfaz.enviarDecision(solicitud.getIdSolicitud(), true);

        assertEquals("Solicitud aprobada", resultado);
        assertEquals("Aprobada", solicitud.getEstado());
        assertFalse(refugio.verificarDisponibilidad(501L));
        assertEquals(resultado, control.comunicarDecision(solicitud.getIdSolicitud()));
        assertEquals("/WEB-INF/vistas/resultadoSolicitud.jsp", interfaz.mostrarResultado());
    }

    @Test
    void aprobarSolicitudRechazaAutomaticamenteSiElPerroYaNoEstaDisponible() {
        refugio.marcarNoDisponible(501L);

        String resultado = control.aprobarSolicitud(solicitud.getIdSolicitud());

        assertEquals("Rechazada: perro no disponible", resultado);
        assertEquals("Rechazada", solicitud.getEstado());
    }

    @Test
    void seleccionarSolicitudInexistenteLanzaErrorClaro() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> interfaz.seleccionarSolicitud(999L));

        assertEquals("No se encontró una solicitud con ese ID", exception.getMessage());
        assertThrows(IllegalArgumentException.class, () -> control.obtenerFormulario(999L));
        assertThrows(IllegalArgumentException.class, () -> control.obtenerFichaPerro(999L));
    }

    @Test
    void segundaDecisionSobreSolicitudResueltaEsRechazada() {
        control.aprobarSolicitud(solicitud.getIdSolicitud());

        assertThrows(IllegalStateException.class,
                () -> control.aprobarSolicitud(solicitud.getIdSolicitud()));
    }

    @Test
    void rechazarSolicitudCambiaEstadoSinAfectarDisponibilidadDelPerro() {
        String resultado = interfaz.enviarDecision(solicitud.getIdSolicitud(), false);

        assertEquals("Solicitud rechazada", resultado);
        assertEquals("Rechazada", solicitud.getEstado());
        assertTrue(refugio.verificarDisponibilidad(501L));
        assertThrows(IllegalStateException.class,
                () -> interfaz.enviarDecision(solicitud.getIdSolicitud(), false));
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
