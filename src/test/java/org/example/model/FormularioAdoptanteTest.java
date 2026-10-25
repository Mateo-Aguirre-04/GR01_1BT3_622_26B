package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormularioAdoptanteTest {
    @Test
    void verificarAceptaFormularioCompletoYObtenerDatosNoPermiteMutacion() {
        Map<String, String> datos = datosCompletos();
        FormularioAdoptante formulario = new FormularioAdoptante(datos);

        assertTrue(formulario.verificar(datos));
        assertEquals("Ada", formulario.obtenerDatos().get("nombres"));
        assertThrows(UnsupportedOperationException.class,
                () -> formulario.obtenerDatos().put("nombres", "Otro nombre"));
    }

    @Test
    void verificarRechazaCampoVacioYTerminosNoAceptados() {
        Map<String, String> datos = datosCompletos();
        datos.put("condicionesHogar", "  ");
        datos.put("aceptacionTerminos", "false");
        FormularioAdoptante formulario = new FormularioAdoptante(datos);

        assertFalse(formulario.verificar(datos));
    }

    @Test
    void verificarTrataTerminosSinEnviarComoNoAceptados() {
        Map<String, String> datos = datosCompletos();
        datos.remove("aceptacionTerminos");
        FormularioAdoptante formulario = new FormularioAdoptante(datos);

        assertFalse(formulario.verificar(datos));
    }

    @Test
    void verificarRechazaDatosNulos() {
        FormularioAdoptante formulario = new FormularioAdoptante(datosCompletos());

        assertThrows(NullPointerException.class, () -> formulario.verificar(null));
    }

    private Map<String, String> datosCompletos() {
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
