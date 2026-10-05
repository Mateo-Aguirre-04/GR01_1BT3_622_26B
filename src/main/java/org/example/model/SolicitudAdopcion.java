package org.example.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * Adoption request entity from the adoption and request-management sequences.
 */
public class SolicitudAdopcion {
    private final Long idSolicitud;
    private final Long idPerro;
    private String estado;
    private final LocalDate fechaSolicitud;
    private FormularioAdoptante formulario;

    public SolicitudAdopcion(Long idSolicitud, Long idPerro) {
        if (idSolicitud == null || idSolicitud <= 0) {
            throw new IllegalArgumentException("El ID de la solicitud debe ser mayor que cero");
        }
        if (idPerro == null || idPerro <= 0) {
            throw new IllegalArgumentException("El ID del perro debe ser mayor que cero");
        }
        this.idSolicitud = idSolicitud;
        this.idPerro = idPerro;
        estado = "Pendiente";
        fechaSolicitud = LocalDate.now();
    }

    public synchronized void asociarFormulario(FormularioAdoptante formulario) {
        this.formulario = Objects.requireNonNull(formulario, "El formulario del adoptante es obligatorio");
    }

    public synchronized Map<String, String> obtenerDatosFormulario() {
        if (formulario == null) {
            throw new IllegalStateException("La solicitud no tiene un formulario asociado");
        }
        return formulario.obtenerDatos();
    }

    public Long obtenerIdPerro() {
        return idPerro;
    }

    public synchronized void aprobar() {
        validarPendiente();
        estado = "Aprobada";
    }

    public synchronized void rechazar() {
        validarPendiente();
        estado = "Rechazada";
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public Long getIdPerro() {
        return idPerro;
    }

    public synchronized String getEstado() {
        return estado;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public synchronized FormularioAdoptante getFormulario() {
        return formulario;
    }

    private void validarPendiente() {
        if (!"Pendiente".equals(estado)) {
            throw new IllegalStateException("La solicitud ya fue resuelta");
        }
    }
}
