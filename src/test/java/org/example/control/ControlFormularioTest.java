package org.example.control;

import org.example.model.DatosRegistroPerro;
import org.example.model.Encargado;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControlFormularioTest {
    @Test
    void iniciarFormularioObtieneLaFichaDelPerro() {
        Refugio refugio = new Refugio(1L, "Refugio Central", "Quito");
        ControlRegistro registro = new ControlRegistro(new Encargado(10L, "Encargado"), refugio);
        registro.registrarPerro(new DatosRegistroPerro(401L, "Luna", 3, "Cruza", "Sana",
                "1", true, LocalDate.of(2026, 1, 15), 1L));
        ControlFormulario control = new ControlFormulario(new ControlConsulta(refugio));

        Perro perro = control.iniciarFormulario(401L);

        assertEquals(401L, perro.getIdPerro());
        assertTrue(perro.estaDisponible());
    }

    @Test
    void iniciarFormularioRechazaUnPerroInexistente() {
        ControlFormulario control = new ControlFormulario(new ControlConsulta(
                new Refugio(1L, "Refugio Central", "Quito")));

        assertThrows(IllegalArgumentException.class, () -> control.iniciarFormulario(999L));
    }
}
