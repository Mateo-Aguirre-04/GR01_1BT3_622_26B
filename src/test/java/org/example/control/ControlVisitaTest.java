package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.example.model.ServicioDisponibilidad;

import java.util.stream.Stream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)

class ControlVisitaTest {
    Visita visita = null;
    ControlVisita control = null;
    Refugio refugio = null;

    @BeforeEach
    public void setUp() {
        visita = new Visita(LocalDate.now().plusDays(1));
        control = new ControlVisita();
        refugio = new Refugio(1L, "-", "-");
    }

    @Test
    void aceptarVisitaPendienteCambiaEstadoAAceptada() {
        // Act
        control.aceptarVisita(visita);

        // Assert
        assertEquals(Visita.Estado.APROBADA, visita.getEstado());
    }

    @Test
    void aceptarVisitaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> control.aceptarVisita(null));
    }

    @Test
    void visitaYaGestionadaLanzaExcepcionAlProcesarseDeNuevo() {
        // Arrange
        Visita aceptada = new Visita(LocalDate.now().plusDays(1));
        Visita rechazada = new Visita(LocalDate.now().plusDays(1));
        control.aceptarVisita(aceptada);
        control.rechazarVisita(rechazada);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> control.aceptarVisita(aceptada));
        assertThrows(IllegalStateException.class, () -> control.rechazarVisita(aceptada));
        assertThrows(IllegalStateException.class, () -> control.aceptarVisita(rechazada));
        assertThrows(IllegalStateException.class, () -> control.rechazarVisita(rechazada));

        // Assert
        assertEquals(Visita.Estado.APROBADA, aceptada.getEstado());
        assertEquals(Visita.Estado.RECHAZADA, rechazada.getEstado());
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
        Perro perro = new Perro(1L, "-", 3, "-", "-", "-",
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

    @Test
    void aceptarVisitaConHorarioOcupadoRechazaVisita() {
        ServicioDisponibilidad servicioMock = mock(ServicioDisponibilidad.class);
        when(servicioMock.estaLibre(visita.getFechaVisita())).thenReturn(false);

        ControlVisita controlConServicio = new ControlVisita(servicioMock);

        controlConServicio.aceptarVisita(visita);

        assertEquals(Visita.Estado.RECHAZADA, visita.getEstado());

        verify(servicioMock).estaLibre(visita.getFechaVisita());
    }
}