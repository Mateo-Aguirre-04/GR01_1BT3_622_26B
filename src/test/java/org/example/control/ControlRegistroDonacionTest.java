package org.example.control;

import org.example.model.Donacion;
import org.example.model.Refugio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlRegistroDonacionTest {
    private Refugio refugio;
    private ControlRegistroDonacion control;

    @BeforeEach
    void setUp() {
        refugio = new Refugio(1L, "Refugio Central", "Quito");
        control = new ControlRegistroDonacion(refugio);
    }

    @Test
    void registrarDonacionValidaLaGuardaEnElRefugio() {
        Donacion donacion = control.registrarDonacion(DonacionRequestTestFactory.crear(
                DonacionRequestTestFactory.contactoCompleto(),
                new String[]{"Croquetas", "", ""}, new String[]{"10", "", ""}));

        assertEquals(List.of(donacion), refugio.listarDonacionesPendientes());
        assertEquals("Ana", donacion.getDonante().getNombres());
        assertEquals(10, donacion.cantidadDe("Croquetas"));
    }

    @Test
    void registrarDonacionConVariosMaterialesLosGuardaTodos() {
        Donacion donacion = control.registrarDonacion(DonacionRequestTestFactory.crear(
                DonacionRequestTestFactory.contactoCompleto(),
                new String[]{"Croquetas", "Mantas", "Juguetes"}, new String[]{"10", "3", "5"}));

        assertEquals(3, donacion.cantidadDeMateriales());
        assertEquals(3, donacion.cantidadDe("Mantas"));
        assertEquals(5, donacion.cantidadDe("Juguetes"));
    }

    @Test
    void registrarDonacionSinContactoLanzaExcepcion() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> control.registrarDonacion(DonacionRequestTestFactory.crear(
                        Map.of("nombres", "Ana", "apellidos", "Pérez"),
                        new String[]{"Croquetas"}, new String[]{"10"})));

        assertEquals("El teléfono del donante es obligatorio", error.getMessage());
        assertTrue(refugio.listarDonaciones().isEmpty());
    }
}
