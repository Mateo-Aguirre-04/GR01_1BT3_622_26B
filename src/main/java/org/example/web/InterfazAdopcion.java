package org.example.web;

import org.example.control.ControlEnvioSolicitud;
import org.example.control.ControlFormulario;
import org.example.model.Perro;
import org.example.model.SolicitudAdopcion;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Boundary for the "Solicitar Adopcion" sequence (Incremento 2).
 */
public class InterfazAdopcion {
    private static final String FORMULARIO = "/WEB-INF/vistas/formularioAdopcion.jsp";
    private static final String CONFIRMACION = "/WEB-INF/vistas/confirmacionAdopcion.jsp";

    private final ControlFormulario controlFormulario;
    private final ControlEnvioSolicitud controlEnvioSolicitud;

    public InterfazAdopcion(ControlFormulario controlFormulario, ControlEnvioSolicitud controlEnvioSolicitud) {
        this.controlFormulario = Objects.requireNonNull(controlFormulario, "El control del formulario es obligatorio");
        this.controlEnvioSolicitud = Objects.requireNonNull(controlEnvioSolicitud, "El control de envío es obligatorio");
    }

    public Perro manifestarInteres(Long idPerro) {
        return controlFormulario.iniciarFormulario(idPerro);
    }

    public String mostrarFormulario(Perro ficha) {
        Objects.requireNonNull(ficha, "La ficha del perro es obligatoria");
        return FORMULARIO;
    }

    public SolicitudAdopcion enviarDatosFormulario(Map<String, String> datos) {
        Objects.requireNonNull(datos, "Los datos del formulario son obligatorios");
        String idPerroTexto = datos.get("idPerro");
        if (idPerroTexto == null || idPerroTexto.isBlank()) {
            throw new IllegalArgumentException("El ID del perro es obligatorio");
        }

        final Long idPerro;
        try {
            idPerro = Long.valueOf(idPerroTexto.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El ID del perro no es válido", exception);
        }

        Map<String, String> datosFormulario = new LinkedHashMap<>(datos);
        datosFormulario.remove("idPerro");
        return controlEnvioSolicitud.enviarFormulario(idPerro, datosFormulario);
    }

    public String mostrarConfirmacion(Long idSolicitud) {
        Objects.requireNonNull(idSolicitud, "El ID de la solicitud es obligatorio");
        return CONFIRMACION;
    }
}
