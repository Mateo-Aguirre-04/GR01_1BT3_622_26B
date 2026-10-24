package org.example.control;

import org.example.model.Adoptante;
import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.Visita;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

public class ControlSolicitudVisita {
    private final Refugio refugio;
    private final Clock reloj;

    public ControlSolicitudVisita(Refugio refugio) {
        this(refugio, Clock.systemDefaultZone());
    }

    public ControlSolicitudVisita(Refugio refugio, Clock reloj) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
        this.reloj = Objects.requireNonNull(reloj, "El reloj es obligatorio");
    }

    public synchronized Visita solicitarVisita(Long idPerro, LocalDate fechaVisita,
                                                String nombres, String apellidos,
                                                String telefono, String correo) {
        validarVisitante(nombres, apellidos, telefono, correo);
        if (idPerro == null || idPerro <= 0) {
            throw new IllegalArgumentException("El ID del perro debe ser mayor que cero");
        }
        Perro perro = refugio.obtenerFichaPerro(idPerro);
        if (perro == null) {
            throw new IllegalArgumentException("No se encontró un perro con ese ID");
        }
        if (!perro.estaDisponible()) {
            throw new IllegalStateException("El perro no está disponible para recibir visitas");
        }
        if (fechaVisita == null || !fechaVisita.isAfter(LocalDate.now(reloj))) {
            throw new IllegalArgumentException("La fecha de visita debe ser futura");
        }

        Adoptante visitante = new Adoptante(nombres.trim(), apellidos.trim(), telefono.trim(), correo.trim());
        Visita visita = new Visita(refugio.obtenerIdVisita(), fechaVisita, visitante, perro);
        refugio.agregarVisita(visita);
        return visita;
    }

    private void validarVisitante(String nombres, String apellidos, String telefono, String correo) {
        if (vacio(nombres) || vacio(apellidos) || vacio(telefono) || vacio(correo)) {
            throw new IllegalArgumentException("Los nombres, apellidos, teléfono y correo son obligatorios");
        }
    }

    private boolean vacio(String valor) {
        return valor == null || valor.isBlank();
    }
}
