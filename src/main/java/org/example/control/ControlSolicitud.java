package org.example.control;

import org.example.model.Perro;
import org.example.model.Refugio;
import org.example.model.SolicitudAdopcion;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Coordinates the "Gestionar Solicitud" sequence (Incremento 2).
 */
public class ControlSolicitud {
    private final Refugio refugio;

    public ControlSolicitud(Refugio refugio) {
        this.refugio = Objects.requireNonNull(refugio, "El refugio es obligatorio");
    }

    public List<SolicitudAdopcion> listarSolicitudes() {
        return refugio.listarSolicitudes();
    }

    public SolicitudAdopcion obtenerSolicitud(Long idSolicitud) {
        SolicitudAdopcion solicitud = refugio.obtenerSolicitud(idSolicitud);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró una solicitud con ese ID");
        }
        return solicitud;
    }

    public Map<String, String> obtenerFormulario(Long idSolicitud) {
        return obtenerSolicitud(idSolicitud).obtenerDatosFormulario();
    }

    public String obtenerFichaPerro(Long idSolicitud) {
        SolicitudAdopcion solicitud = obtenerSolicitud(idSolicitud);
        Perro perro = refugio.obtenerFichaPerro(solicitud.obtenerIdPerro());
        if (perro == null) {
            throw new IllegalStateException("No se encontró el perro asociado a la solicitud");
        }
        return perro.obtenerFicha();
    }

    public synchronized String aprobarSolicitud(Long idSolicitud) {
        SolicitudAdopcion solicitud = obtenerSolicitud(idSolicitud);
        Long idPerro = solicitud.obtenerIdPerro();
        if (refugio.verificarDisponibilidad(idPerro)) {
            solicitud.aprobar();
            refugio.marcarNoDisponible(idPerro);
        }
        return comunicarDecision(idSolicitud);
    }

    public synchronized String rechazarSolicitud(Long idSolicitud) {
        SolicitudAdopcion solicitud = obtenerSolicitud(idSolicitud);
        solicitud.rechazar();
        return "Solicitud rechazada";
    }

    public String comunicarDecision(Long idSolicitud) {
        SolicitudAdopcion solicitud = obtenerSolicitud(idSolicitud);
        if ("Aprobada".equals(solicitud.getEstado())) {
            return "Solicitud aprobada";
        }
        return "Rechazada: perro no disponible";
    }
}