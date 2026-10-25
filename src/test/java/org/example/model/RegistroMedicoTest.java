package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistroMedicoTest {
    private Perro perro;

    @BeforeEach
    void setUp() {
        Refugio refugio = new Refugio(1L, "Refugio Central", "Quito");
        perro = new Perro(1L, "Luna", 3, "Juguetona", "Sana", "Jaula 1",
                true, LocalDate.of(2026, 1, 15), refugio);
    }

    @Test
    void registrarVacunaLaAsociaALaAgendaDelPerro() {
        RegistroMedico vacuna = new RegistroMedico(1L, RegistroMedico.Tipo.VACUNA,
                "Antirrábica", LocalDate.of(2026, 11, 5));

        perro.agregarRegistroMedico(vacuna);

        assertEquals(List.of(vacuna), perro.getRegistrosMedicos());
        assertEquals(RegistroMedico.Tipo.VACUNA, perro.getRegistrosMedicos().get(0).getTipo());
        assertEquals("Antirrábica", perro.getRegistrosMedicos().get(0).getDetalle());
    }
}