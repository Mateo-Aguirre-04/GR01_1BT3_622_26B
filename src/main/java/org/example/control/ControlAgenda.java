package org.example.control;

import org.example.model.Perro;
import org.example.model.RegistroMedico;
import org.example.model.Refugio;

import java.time.LocalDate;

public class ControlAgenda {
    private final Refugio refugio;

    public ControlAgenda(Refugio refugio) {
        this.refugio = refugio;
    }

    public Perro obtenerPerro(Long idPerro) {
        return null;
    }

    public RegistroMedico registrarEnAgenda(Long idPerro, String tipo, String detalle, LocalDate fecha) {
        return null;
    }
}