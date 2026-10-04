package org.example.web;

import org.example.control.ControlConsulta;
import org.example.model.Perro;

import java.util.List;
import java.util.Objects;

/**
 * Boundary for the "Consultar Registro" sequence (Incremento 1).
 */
public class InterfazConsultaRegistro {
    private final ControlConsulta controlConsulta;

    public InterfazConsultaRegistro(ControlConsulta controlConsulta) {
        this.controlConsulta = Objects.requireNonNull(controlConsulta, "El control de consulta es obligatorio");
    }

    public List<Perro> mostrarPerrosDisponibles() {
        return controlConsulta.consultarRegistro();
    }

    public Perro solicitarFicha(Long idPerro) {
        return controlConsulta.consultarFicha(idPerro);
    }

    public Perro mostrarFicha(Perro ficha) {
        return Objects.requireNonNull(ficha, "La ficha del perro es obligatoria");
    }
}
