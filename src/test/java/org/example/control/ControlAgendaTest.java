package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlAgendaTest {

    private ControlAgenda control;
    private Refugio refugio;
    private Perro perro;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        perro = new Perro(1L, "Luna", 3, "Juguetona", "Sana", "Jaula 1",
                true, LocalDate.of(2026, 1, 15), refugio);
        refugio.agregarPerro(perro);
        control = new ControlAgenda(refugio);
    }

    @ParameterizedTest(name = "{4}")
    @MethodSource("datosInvalidos")
    void registroConDatosInvalidosLanzaExcepcion(Long idPerro, String tipo, String detalle,
                                                 LocalDate fecha, String caso) {
        assertThrows(IllegalArgumentException.class,
                () -> control.registrarEnAgenda(idPerro, tipo, detalle, fecha));
        assertTrue(perro.getRegistrosMedicos().isEmpty());
    }

    static Stream<Arguments> datosInvalidos() {
        LocalDate fecha = LocalDate.of(2026, 11, 5);
        return Stream.of(
                Arguments.of(99L, "VACUNA", "Antirrábica", fecha, "perro inexistente"),
                Arguments.of(1L, "", "Antirrábica", fecha, "tipo vacío"),
                Arguments.of(1L, "CIRUGIA", "Antirrábica", fecha, "tipo no válido"),
                Arguments.of(1L, "VACUNA", " ", fecha, "detalle vacío"),
                Arguments.of(1L, "CONTROL", "Revisión general", null, "fecha vacía"));
    }
}
