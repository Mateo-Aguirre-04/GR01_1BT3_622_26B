package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;
import org.example.web.InterfazModificacionPerro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ControlModificacionPerroTest {
    private Refugio refugio;
    private Perro perro;
    private ControlModificacionPerro control;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio", "Quito");
        perro = new Perro(10L, "Luna", 3, "Tranquila", "Sana", "Patio 1",
                true, LocalDate.of(2026, 10, 1), refugio);
        refugio.agregarPerro(perro);
        control = new ControlModificacionPerro(refugio);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void modificaTodosLosDatosEditablesSinReemplazarAlPerroNiCambiarDisponibilidad(boolean disponible) {
        perro.setDisponible(disponible);
        InterfazModificacionPerro interfaz = new InterfazModificacionPerro(control);
        Map<String, String> datos = datosValidos();
        datos.put("idPerro", "999");
        datos.put("idRefugio", "999");
        datos.put("disponible", Boolean.toString(!disponible));

        Perro resultado = interfaz.enviarDatosPerro(10L, datos);

        assertSame(perro, resultado);
        assertSame(perro, interfaz.seleccionarPerro(10L));
        assertEquals(List.of("Sol", "4", "Juguetona", "En tratamiento", "Patio 2", "2026-10-02"), estadoEditable());
        assertEquals(10L, perro.getIdPerro());
        assertSame(refugio, perro.getRefugio());
        assertEquals(disponible, perro.estaDisponible());
        assertEquals(1, refugio.listarPerros().size());
        assertEquals("/WEB-INF/vistas/modificarPerro.jsp", interfaz.mostrarFormulario());
    }

    static Stream<Arguments> camposObligatorios() {
        Map<String, String> campos = Map.of("nombre", "nombre", "edad", "edad",
                "caracteristicas", "características", "estadoSalud", "estado de salud",
                "espacioAsignado", "espacio asignado", "fechaIngreso", "fecha de ingreso");
        return campos.entrySet().stream().flatMap(campo -> Stream.of(null, "", "   ")
                .map(valor -> Arguments.of(campo.getKey(), campo.getValue(), valor)));
    }

    @ParameterizedTest(name = "campo obligatorio: {0}, valor: [{2}]")
    @MethodSource("camposObligatorios")
    void campoAusenteVacioOBlancoNoActualizaNingunDato(String campo, String etiqueta, String valor) {
        List<String> antes = estadoEditable();
        Map<String, String> datos = datosValidos();
        datos.put(campo, valor);

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> control.modificarPerro(10L, datos));

        assertEquals("Debe completar el campo " + etiqueta + ".", error.getMessage());
        assertEquals(antes, estadoEditable());
    }

    static Stream<Arguments> formatosInvalidos() {
        return Stream.of(
                Arguments.of("edad", "-1", "edad"),
                Arguments.of("edad", "3.5", "edad"),
                Arguments.of("edad", "tres", "edad"),
                Arguments.of("edad", "2147483648", "edad"),
                Arguments.of("fechaIngreso", "2026-02-30", "fecha de ingreso"),
                Arguments.of("fechaIngreso", "24/10/2026", "fecha de ingreso"),
                Arguments.of("fechaIngreso", "fecha", "fecha de ingreso"));
    }

    @ParameterizedTest
    @MethodSource("formatosInvalidos")
    void datoConFormatoInvalidoIdentificaElCampoYNoActualizaNada(String campo, String valor, String etiqueta) {
        List<String> antes = estadoEditable();
        Map<String, String> datos = datosValidos();
        datos.put(campo, valor);

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> control.modificarPerro(10L, datos));

        assertTrue(error.getMessage().contains(etiqueta));
        assertTrue(error.getMessage().contains("no es válida"));
        assertEquals(antes, estadoEditable());
    }

    @Test
    void aceptaEdadCeroYRecortaLosTextos() {
        Map<String, String> datos = datosValidos();
        datos.put("edad", "0");
        control.modificarPerro(10L, datos);
        assertEquals(0, perro.getEdad());
        assertEquals("Sol", perro.getNombre());
        assertEquals("Juguetona", perro.getCaracteristicas());
    }

    static Stream<Long> idsInvalidos() {
        return Stream.of(null, 0L, -1L, 999L);
    }

    @ParameterizedTest
    @MethodSource("idsInvalidos")
    void perroInexistenteOIdInvalidoNoModificaElRegistro(Long id) {
        List<String> antes = estadoEditable();
        assertThrows(IllegalArgumentException.class, () -> control.obtenerPerro(id));
        assertThrows(IllegalArgumentException.class, () -> control.modificarPerro(id, datosValidos()));
        assertEquals(antes, estadoEditable());
        assertEquals(1, refugio.listarPerros().size());
    }

    @Test
    void conservaReferenciasDeVisitasYElEstadoDeUnaAdopcionAprobada() {
        Map<String, String> formulario = new LinkedHashMap<>(Map.of(
                "nombres", "Ana", "apellidos", "Paz", "telefono", "0991234567", "correo", "ana@example.com",
                "infoDomicilio", "Casa", "experienciaMascotas", "Sí", "condicionesHogar", "Patio",
                "disponibilidadTiempo", "Tardes", "aceptacionTerminos", "true"));
        var solicitud = new ControlEnvioSolicitud(refugio).enviarFormulario(10L, formulario);
        Visita visita = new Visita(1L, LocalDate.now().plusDays(1),
                new Adoptante("Ana", "Paz", "0991234567", "ana@example.com"), perro);
        refugio.agregarVisita(visita);
        ControlSolicitud solicitudes = new ControlSolicitud(refugio);
        solicitudes.aprobarSolicitud(solicitud.getIdSolicitud());

        control.modificarPerro(10L, datosValidos());

        assertSame(perro, visita.getPerro());
        assertEquals("Sol", visita.getPerro().getNombre());
        assertTrue(solicitudes.obtenerFichaPerro(solicitud.getIdSolicitud()).contains("Sol"));
        assertEquals("Aprobada", solicitud.getEstado());
        assertFalse(perro.estaDisponible());
        assertEquals(Visita.Estado.PENDIENTE, visita.getEstado());
    }

    private Map<String, String> datosValidos() {
        return new LinkedHashMap<>(Map.of("nombre", " Sol ", "edad", "4", "caracteristicas", " Juguetona ",
                "estadoSalud", "En tratamiento", "espacioAsignado", "Patio 2", "fechaIngreso", "2026-10-02"));
    }

    private List<String> estadoEditable() {
        return List.of(perro.getNombre(), Integer.toString(perro.getEdad()), perro.getCaracteristicas(),
                perro.getEstadoSalud(), perro.getEspacioAsignado(), perro.getFechaIngreso().toString());
    }
}
