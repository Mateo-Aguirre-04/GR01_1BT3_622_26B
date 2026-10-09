package org.example.control;

import org.example.model.Encargado;
import org.example.model.Perro;
import org.example.model.Refugio;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Objects;

public class ControlRegistro {
    private final Encargado encargadoActual;
    private final Refugio refugioConfigurado;
    private Refugio refugioActual;

    public ControlRegistro(Encargado encargadoActual, Refugio refugioActual) {
        this.encargadoActual = Objects.requireNonNull(encargadoActual, "El encargado actual es obligatorio");
        this.refugioConfigurado = Objects.requireNonNull(refugioActual, "El refugio actual es obligatorio");
        this.refugioActual = refugioConfigurado;
    }

    public synchronized Perro registrarPerro(HttpServletRequest solicitud) {
        Objects.requireNonNull(solicitud, "La solicitud de registro es obligatoria");
        Long idPerro = Long.valueOf(parametro(solicitud, "idPerro"));
        String nombre = parametro(solicitud, "nombre");
        int edad = Integer.parseInt(parametro(solicitud, "edad"));
        String caracteristicas = parametro(solicitud, "caracteristicas");
        String estadoSalud = parametro(solicitud, "estadoSalud");
        String espacioAsignado = parametro(solicitud, "espacioAsignado");
        boolean disponible = disponibilidad(parametro(solicitud, "disponible"));
        LocalDate fechaIngreso = LocalDate.parse(parametro(solicitud, "fechaIngreso"));
        Long idRefugioSolicitado = Long.valueOf(parametro(solicitud, "idRefugio"));

        validar(idPerro, nombre, edad, caracteristicas, estadoSalud, espacioAsignado, fechaIngreso);
        asociarRefugio(idRefugioSolicitado);
        Long idRefugio = refugioActual.obtenerId();
        if (!idRefugio.equals(idRefugioSolicitado)) {
            throw new IllegalArgumentException("El refugio seleccionado no coincide con el refugio actual");
        }

        Perro perro = new Perro(
                idPerro,
                nombre.trim(),
                edad,
                caracteristicas.trim(),
                estadoSalud.trim(),
                espacioAsignado.trim(),
                disponible,
                fechaIngreso,
                refugioActual);

        refugioActual.agregarPerro(perro);
        encargadoActual.asociarRegistro(perro);
        return perro;
    }

    public synchronized void asociarRefugio(Long idRefugio) {
        if (idRefugio == null || !idRefugio.equals(refugioConfigurado.getIdRefugio())) {
            throw new IllegalArgumentException("El refugio seleccionado no está inicializado en la aplicación");
        }
        refugioActual = refugioConfigurado;
    }

    public Encargado getEncargadoActual() {
        return encargadoActual;
    }

    public synchronized Refugio getRefugioActual() {
        return refugioActual;
    }

    private String parametro(HttpServletRequest solicitud, String nombre) {
        String valor = solicitud.getParameter(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + nombre + " es obligatorio");
        }
        return valor.trim();
    }

    private boolean disponibilidad(String valor) {
        if ("true".equals(valor)) {
            return true;
        }
        if ("false".equals(valor)) {
            return false;
        }
        throw new IllegalArgumentException("La disponibilidad seleccionada no es válida");
    }

    private void validar(Long idPerro, String nombre, int edad, String caracteristicas,
                         String estadoSalud, String espacioAsignado, LocalDate fechaIngreso) {
        if (idPerro <= 0) {
            throw new IllegalArgumentException("El ID del perro debe ser mayor que cero");
        }
        if (nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del perro es obligatorio");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        if (caracteristicas.isBlank()) {
            throw new IllegalArgumentException("Las características son obligatorias");
        }
        if (estadoSalud.isBlank()) {
            throw new IllegalArgumentException("El estado de salud es obligatorio");
        }
        if (espacioAsignado.isBlank()) {
            throw new IllegalArgumentException("El espacio asignado es obligatorio");
        }
        if (fechaIngreso == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria");
        }
    }
}
