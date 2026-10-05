package org.example.model;

import java.time.LocalDate;

public record DatosRegistroPerro(
        Long idPerro,
        String nombre,
        int edad,
        String caracteristicas,
        String estadoSalud,
        String espacioAsignado,
        boolean disponible,
        LocalDate fechaIngreso,
        Long idRefugio) {
}
