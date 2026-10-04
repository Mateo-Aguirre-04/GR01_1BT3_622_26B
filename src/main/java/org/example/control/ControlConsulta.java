package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;

import java.util.List;
import java.util.Objects;

public class ControlConsulta {
    private final Refugio refugioActual;

    public ControlConsulta(Refugio refugioActual) {
        this.refugioActual = Objects.requireNonNull(refugioActual, "El refugio actual es obligatorio");
    }

    public List<Perro> consultarRegistro() {
        return refugioActual.listarPerrosDisponibles();
    }

    public Perro consultarFicha(Long idPerro) {
        if (idPerro == null || idPerro <= 0) {
            return null;
        }
        return refugioActual.obtenerFichaPerro(idPerro);
    }
}
