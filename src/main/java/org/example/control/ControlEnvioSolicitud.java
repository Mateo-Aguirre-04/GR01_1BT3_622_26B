package org.example.control;

import org.example.model.FormularioAdoptante;
import org.example.model.Refugio;
import org.example.model.SolicitudAdopcion;

import java.util.Map;
import java.util.Objects;

/**
 * Coordinates form validation and request creation in the adoption sequence.
 */
public class ControlEnvioSolicitud {
    private final Refugio refugio;

    public ControlEnvioSolicitud(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public synchronized SolicitudAdopcion enviarFormulario(Long idPerro, Map<String, String> datos) {
        if (!refugio.verificarDisponibilidad(idPerro)) {
            throw new IllegalStateException("Perro no disponible");
        }

        FormularioAdoptante formulario = new FormularioAdoptante(datos);
        if (!formulario.verificar(datos)) {
            throw new IllegalArgumentException("Datos incompletos");
        }

        SolicitudAdopcion solicitud = new SolicitudAdopcion(refugio.obtenerIdSolicitud(), idPerro);
        solicitud.asociarFormulario(formulario);
        refugio.agregarSolicitud(solicitud);
        return solicitud;
    }
}
