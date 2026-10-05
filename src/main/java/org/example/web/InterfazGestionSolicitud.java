package org.example.web;

import org.example.control.ControlSolicitud;
import org.example.model.SolicitudAdopcion;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Boundary for the "Gestionar Solicitud" sequence (Incremento 2).
 */
public class InterfazGestionSolicitud {
    private static final String RESULTADO = "/WEB-INF/vistas/resultadoSolicitud.jsp";

    private final ControlSolicitud controlSolicitud;

    public InterfazGestionSolicitud(ControlSolicitud controlSolicitud) {
        this.controlSolicitud = Objects.requireNonNull(controlSolicitud, "El control de solicitudes es obligatorio");
    }

    public List<SolicitudAdopcion> mostrarSolicitudes() {
        return controlSolicitud.listarSolicitudes();
    }

    public SolicitudAdopcion seleccionarSolicitud(Long idSolicitud) {
        return controlSolicitud.obtenerSolicitud(idSolicitud);
    }

    public SolicitudAdopcion mostrarDetalleSolicitud(SolicitudAdopcion solicitud) {
        return Objects.requireNonNull(solicitud, "La solicitud es obligatoria");
    }

    public Map<String, String> mostrarDatosAdoptante(Map<String, String> datos) {
        return Objects.requireNonNull(datos, "Los datos del adoptante son obligatorios");
    }

    public String mostrarFichaPerro(String ficha) {
        return Objects.requireNonNull(ficha, "La ficha del perro es obligatoria");
    }

    public Map<String, String> obtenerFormulario(Long idSolicitud) {
        return controlSolicitud.obtenerFormulario(idSolicitud);
    }

    public String obtenerFichaPerro(Long idSolicitud) {
        return controlSolicitud.obtenerFichaPerro(idSolicitud);
    }

    public String enviarDecision(Long idSolicitud, boolean aprobada) {
        if (!aprobada) {
            throw new UnsupportedOperationException("El rechazo manual está pendiente de confirmación");
        }
        return controlSolicitud.aprobarSolicitud(idSolicitud);
    }

    public String mostrarResultado() {
        return RESULTADO;
    }
}
